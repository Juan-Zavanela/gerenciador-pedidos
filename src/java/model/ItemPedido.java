package model;

import decorator.AdicionalDecorator;
import decorator.Calculavel;
import decorator.PedidoBase;

public class ItemPedido {
    private int id;
    private Produto produto;
    private int quantidade;
    private DecoratorAdicional garantiaDecorator;

    public ItemPedido() {}
    public ItemPedido(int id, Produto produto, int quantidade, DecoratorAdicional garantiaDecorator) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.garantiaDecorator = garantiaDecorator;
    }

    public double getSubtotalSemGarantia() { return produto.getPreco() * quantidade; }

    public double getValorGarantia() {
        if (garantiaDecorator == null) return 0.0;
        if (garantiaDecorator.isPercentual()) return getSubtotalSemGarantia() * garantiaDecorator.getValor() / 100.0;
        return garantiaDecorator.getValor() * quantidade;
    }

    public double getSubtotal() {
        double base = getSubtotalSemGarantia();
        Calculavel calculo = new PedidoBase(base);
        if (garantiaDecorator != null && garantiaDecorator.getValor() > 0) {
            calculo = new AdicionalDecorator(calculo, garantiaDecorator.getValor(), garantiaDecorator.isPercentual(), base);
        }
        return calculo.calcular();
    }

    public String getDescricaoGarantia() { return garantiaDecorator != null ? garantiaDecorator.getDescricao() : "Sem garantia"; }
    public int getGarantiaMeses() { return garantiaDecorator != null ? garantiaDecorator.getMeses() : 0; }
    public double getPercentualGarantia() { return garantiaDecorator != null ? garantiaDecorator.getValor() : 0.0; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public DecoratorAdicional getGarantiaDecorator() { return garantiaDecorator; }
    public void setGarantiaDecorator(DecoratorAdicional garantiaDecorator) { this.garantiaDecorator = garantiaDecorator; }
}
