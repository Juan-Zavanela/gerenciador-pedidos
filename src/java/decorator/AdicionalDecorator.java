package decorator;

public class AdicionalDecorator extends PedidoDecorator {
    private final double valor;
    private final boolean percentual;
    private final double baseCalculo;

    public AdicionalDecorator(Calculavel pedido, double valor, boolean percentual, double baseCalculo) {
        super(pedido);
        this.valor = valor;
        this.percentual = percentual;
        this.baseCalculo = baseCalculo;
    }

    @Override
    public double calcular() {
        if (percentual) {
            return pedido.calcular() + (baseCalculo * valor / 100.0);
        }
        return pedido.calcular() + valor;
    }
}
