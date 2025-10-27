package clientes;
import java.util.ArrayList;
import java.util.List;


public class ClienteService {
    private static final List<Cliente> clientes = new ArrayList<>();

    public void cadastrar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        if (buscarPorId(cliente.getId()) != null) {
            throw new IllegalArgumentException("Cliente com ID " + cliente.getId() + " já está cadastrado.");
        }

        if (cliente instanceof ClientePessoaFisica) {
            ClientePessoaFisica cpf = (ClientePessoaFisica) cliente;
        }
        cliente.setCategoria((Categoria.BRONZE));
        clientes.add(cliente);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        return clientes;
    }


    public void promoverCategoria(int clienteId) {
        Cliente cliente = buscarPorId(clienteId);

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado para promoção.");
        }

        Categoria atual = cliente.getCategoria();

        Categoria nova;
        switch (atual) {
            case BRONZE:
                nova = Categoria.PRATA;
                break;
            case PRATA:
                nova = Categoria.OURO;
                break;
            case OURO:
                nova = Categoria.DIAMANTE;
                break;
            case DIAMANTE:
            default:
                nova = Categoria.DIAMANTE;
                break;
        }

        cliente.setCategoria(nova);
        System.out.println("Cliente " + cliente.getNome() + " promovido para " + nova);
    }
}