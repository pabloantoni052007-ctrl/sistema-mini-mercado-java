package produtos;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    // 1. O Serviço armazena a lista de produtos EM MEMÓRIA (simulando o banco)
    private static final List<Produto> produtos = new ArrayList<>();
    // Nota: Usar 'static' permite que todos os ProdutoService usem a mesma lista.

    /**
     * Lógica de Negócio: Cadastra um novo produto após validações.
     * @param produto O produto a ser adicionado.
     */
    public void cadastrar(Produto produto) {
        // Validação 1: O preço deve ser positivo.
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }

        // Validação 2: Verificar se o produto já existe (ex: por ID)
        if (buscarPorId(produto.getId()) != null) {
            throw new IllegalArgumentException("Produto com ID " + produto.getId() + " já existe.");
        }

        produtos.add(produto);
    }

    /**
     * Lógica de Negócio: Busca um produto por ID (simulando a busca no "banco").
     * @param id O ID do produto.
     * @return O Produto encontrado ou null.
     */
    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null; // Retorna null se não encontrar
    }

    /**
     * Lógica de Estoque: Baixa a quantidade de um produto após uma venda.
     * @param id O ID do produto.
     * @param quantidadeVendida A quantidade a ser removida do estoque.
     */
    public void darBaixaEstoque(int id, int quantidadeVendida) {
        Produto produto = buscarPorId(id);

        if (produto == null) {
            throw new RuntimeException("Erro: Produto com ID " + id + " não existe.");
        }

        // Lógica de Validação: Checar estoque suficiente (supondo que Produto tem getEstoque() e setEstoque())
        if (produto.getEstoque() < quantidadeVendida) {
            throw new RuntimeException("Erro: Estoque insuficiente para o produto " + produto.getNome());
        }

        // Lógica de Atualização: Reduz o estoque
        produto.setEstoque(produto.getEstoque() - quantidadeVendida);
    }

    // Método para listar todos
    public List<Produto> listarTodos() {
        return produtos;
    }
}