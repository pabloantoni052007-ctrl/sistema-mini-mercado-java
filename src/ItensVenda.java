public class ItensVenda {
    private int id;            // ID do item na venda
    private int produtoId;     // ID do produto real
    private String nomeProduto;
    private int quantidade;
    private double preco;

    public ItensVenda(int id, int produtoId, String nomeProduto, int quantidade, double precoUnitario) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = precoUnitario;
    }

    public int getId() { return id; }
    public int getProdutoId() { return produtoId; }
    public String getNomeProduto() { return nomeProduto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return preco; }

    public double getSubTotal(){
        return this.preco * this.quantidade;
    }
}
