
import java.util.ArrayList;
import java.util.List;
import clientes.ClienteService; // Import para orquestração
import produtos.ProdutoService;
// Certifique-se de que a classe Venda está no mesmo pacote ou importe-a
// import produtos.Venda;

public class VendaService {

    // Lista em memória para simular o banco de dados de vendas
    private static final List<Venda> vendas = new ArrayList<>();

    // Dependências para orquestrar a lógica de negócio
    private final ProdutoService produtoService;
    private final ClienteService clienteService; // Se for usar para atualizar categoria

    // Construtor para receber as dependências (Injeção de Dependência)
    public VendaService(ProdutoService produtoService, ClienteService clienteService) {
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    /**
     * Lógica de Negócio: Finaliza o processo de venda.
     * Esta é a função orquestradora que une várias partes do sistema.
     * @param venda O objeto Venda a ser processado e registrado.
     */
    public Venda finalizarVenda(Venda venda) {
        // 1. **Pré-validação** (Garantir que a venda tenha itens)
        if (venda.getItens() == null || venda.getItens().length == 0) {
            throw new IllegalArgumentException("Venda deve ter pelo menos um item.");
        }

        // 2. **Processamento e Validação de Estoque (Lógica Crítica)**
        for (ItensVenda itemVenda : venda.getItens()) {
            // No seu sistema: O objeto ItensVenda precisa ter um ID do Produto
            // Assumindo que ItensVenda tem um método getProdutoId()
            // E a quantidade vendida é obtida por getQuantidade()

            // Chama o service de produto para dar baixa no estoque
            try {
                // Assumindo que o ItemVenda tem o ID do produto e a quantidade vendida
                // Correção de lógica: O ItensVenda deve ter uma referência ao Produto, mas estamos simplificando

                // Assumindo que o ItemVenda tem um método getProdutoId()
                int produtoId = itemVenda.getProdutoId(); // ✅ usa o ID real do produto
                produtoService.darBaixaEstoque(produtoId, itemVenda.getQuantidade());


            } catch (RuntimeException e) {
                // Se der erro de estoque, cancela a venda e propaga o erro.
                throw new RuntimeException("Falha na venda: " + e.getMessage());
            }
        }

        // 3. **Atualização do Cliente (Lógica de Fidelidade)**
        // Se houver lógica de negócio para atualizar a categoria do cliente
        // com base no valor da venda, ela seria chamada aqui:
        // clienteService.atualizarCategoria(venda.getCliente(), venda.getValorTotal());

        // 4. **Registro e Persistência (Simulada)**
        vendas.add(venda);

        System.out.println("Venda finalizada com sucesso. Total: " + venda.getValorTotal());

        return venda;
    }

    /**
     * Retorna todas as vendas registradas.
     */
    public List<Venda> listarTodas() {
        return vendas;
    }
}