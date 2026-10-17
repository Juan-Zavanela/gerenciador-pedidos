package decorator;

public class FreteDecorator extends PedidoDecorator {
    private final double valorFrete;
    public FreteDecorator(Calculavel pedido, double valorFrete) {
        super(pedido);
        this.valorFrete = valorFrete;
    }
    @Override public double calcular() { return pedido.calcular() + valorFrete; }
}
