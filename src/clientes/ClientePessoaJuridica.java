package clientes;

public class ClientePessoaJuridica extends Cliente{
    private final String cnpj;

    public ClientePessoaJuridica(int id, String nome, String telefone, Categoria categoria,  String cnpj) {
        super(id, nome, telefone, categoria);
        this.cnpj = cnpj;
    }
}
