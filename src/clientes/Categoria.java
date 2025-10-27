package clientes;

public enum Categoria {
    BRONZE(0.00),
    PRATA(0.02),
    OURO(0.05),
    DIAMANTE(0.10); // nome padronizado

    private final double percentualDesconto;

    Categoria(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }
}
