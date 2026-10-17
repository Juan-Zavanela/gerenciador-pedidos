package decorator;

public class GarantiaDecorator extends PedidoDecorator {
    private final double valorProduto;
    private final double percentual;
    public GarantiaDecorator(Calculavel pedido, double valorProduto, double percentual) {
        super(pedido);
        this.valorProduto = valorProduto;
        this.percentual = percentual;
    }
    @Override public double calcular() { return pedido.calcular() + (valorProduto * percentual / 100.0); }
}
