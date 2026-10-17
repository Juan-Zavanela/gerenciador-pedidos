package decorator;

public class PedidoBase implements Calculavel {
    private final double valorBase;
    public PedidoBase(double valorBase) { this.valorBase = valorBase; }
    @Override public double calcular() { return valorBase; }
}
