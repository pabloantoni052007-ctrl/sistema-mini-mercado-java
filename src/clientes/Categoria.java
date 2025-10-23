package clientes;

public enum Categoria {
    BRONZE(0.02),
    PRATA(0.05),
    OURO(0.10),
    PLATINA(0.15);

    private final double percentualDesconto;

    Categoria(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }
}
