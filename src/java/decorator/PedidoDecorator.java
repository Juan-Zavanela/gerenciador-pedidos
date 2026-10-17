package decorator;

public abstract class PedidoDecorator implements Calculavel {
    protected final Calculavel pedido;
    public PedidoDecorator(Calculavel pedido) { this.pedido = pedido; }
}
