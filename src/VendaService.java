import java.util.ArrayList;
import java.util.List;
import clientes.ClienteService;
import produtos.ProdutoService;

public class VendaService {

    private static final List<Venda> vendas = new ArrayList<>();

    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    public VendaService(ProdutoService produtoService, ClienteService clienteService) {
        this.produtoService = produtoService;
        this.clienteService = clienteService;
    }

    public Venda finalizarVenda(Venda venda) {
        if (venda.getItens() == null || venda.getItens().length == 0) {
            throw new IllegalArgumentException("Venda deve ter pelo menos um item.");
        }

        for (ItensVenda itemVenda : venda.getItens()) {
            try {
                int produtoId = itemVenda.getProdutoId();
                produtoService.darBaixaEstoque(produtoId, itemVenda.getQuantidade());


            } catch (RuntimeException e) {
                throw new RuntimeException("Falha na venda: " + e.getMessage());
            }
        }
        vendas.add(venda);

        System.out.println("Venda finalizada com sucesso. Total: " + venda.getValorTotal());

        return venda;
    }

    public List<Venda> listarTodas() {
        return vendas;
    }
}