

import clientes.Categoria;
import clientes.Cliente;

public class DescontoFidelidade {

    public static double calcular(Cliente cliente, double subtotal) {
        if (cliente == null) return 0.0; // cliente opcional
        Categoria categoria = cliente.getCategoria();
        double percentual = categoria.getPercentualDesconto();
        return subtotal * percentual;
    }
}
