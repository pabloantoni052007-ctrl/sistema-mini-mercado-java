import clientes.Cliente;

import java.time.LocalDateTime;

public class Venda {
    private final int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private ItensVenda[] itens;
    private double desconto;

    public Venda(int id, LocalDateTime dataHora, Cliente cliente, ItensVenda[] itens, double desconto) {
        this.id = id;
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.itens = itens;
        this.desconto = desconto;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ItensVenda[] getItens() {
        return itens;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setItens(ItensVenda[] itens) {
        this.itens = itens;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getValorTotal(){
        double subtotalVenda = 0.0;
        for (ItensVenda item : this.itens) {
            subtotalVenda += item.getSubTotal();
        }
        return subtotalVenda;
    }
}
