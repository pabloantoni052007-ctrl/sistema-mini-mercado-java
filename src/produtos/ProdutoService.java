package produtos;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private static final List<Produto> produtos = new ArrayList<>();

    public void cadastrar(Produto produto) {
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }

        if (buscarPorId(produto.getId()) != null) {
            throw new IllegalArgumentException("Produto com ID " + produto.getId() + " já existe.");
        }

        produtos.add(produto);
    }

    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public void darBaixaEstoque(int id, int quantidadeVendida) {
        Produto produto = buscarPorId(id);

        if (produto == null) {
            throw new RuntimeException("Erro: Produto com ID " + id + " não existe.");
        }

        if (produto.getEstoque() < quantidadeVendida) {
            throw new RuntimeException("Erro: Estoque insuficiente para o produto " + produto.getNome());
        }

        produto.setEstoque(produto.getEstoque() - quantidadeVendida);
    }

    public List<Produto> listarTodos() {
        return produtos;
    }
}