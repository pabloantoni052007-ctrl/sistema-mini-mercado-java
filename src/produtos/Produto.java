package produtos;

public class Produto {
    private int id;
    private String nome;
    private double codigoBarras;
    private double preco;
    private double custoMedio;
    private int estoque;


    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getCodigoBarras() {
        return codigoBarras;
    }

    public double getPreco() {
        return preco;
    }

    public double getCustoMedio() {
        return custoMedio;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigoBarras(double codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCustoMedio(double custoMedio) {
        this.custoMedio = custoMedio;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
