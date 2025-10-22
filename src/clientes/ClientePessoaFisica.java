package clientes;

public class ClientePessoaFisica extends Cliente{
    private final String cpf;

    public ClientePessoaFisica(int id, String nome, String telefone, Categoria categoria, String cpf) {
        super(id, nome, telefone, categoria);
        this.cpf = cpf;
    }
}
