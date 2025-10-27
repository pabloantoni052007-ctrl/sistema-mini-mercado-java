package clientes; // Assumindo o pacote 'clientes'

import java.util.ArrayList;
import java.util.List;


public class ClienteService {


    private static final List<Cliente> clientes = new ArrayList<>();

    public void cadastrar(Cliente cliente) {

        // Validação 1: O nome não pode ser nulo ou vazio.
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        // Validação 2: Verificar se o ID já existe
        if (buscarPorId(cliente.getId()) != null) {
            throw new IllegalArgumentException("Cliente com ID " + cliente.getId() + " já está cadastrado.");
        }

        // Validação 3: Lógica específica para Pessoa Física (Ex: Validação de CPF)
        if (cliente instanceof ClientePessoaFisica) {
            ClientePessoaFisica cpf = (ClientePessoaFisica) cliente;
            // Assumindo que ClientePessoaFisica tem um método getCpf()
            // if (!validaCPF(cpf.getCpf())) { ... lançar exceção ... }
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

        // Agora usa a variável 'nova' corretamente
        cliente.setCategoria(nova);
        System.out.println("Cliente " + cliente.getNome() + " promovido para " + nova);
    }
}