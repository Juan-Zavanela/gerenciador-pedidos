package model;

import decorator.AdicionalDecorator;
import decorator.Calculavel;
import decorator.PedidoBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido {
    private int id;
    private Date data;
    private String cliente;
    private List<ItemPedido> itens = new ArrayList<>();
    private DecoratorAdicional freteDecorator;
    private DecoratorAdicional brindeDecorator;
    private DecoratorAdicional personalizacaoDecorator;

    public Pedido() { this.data = new Date(); }
    public Pedido(int id, String cliente) { this.id = id; this.cliente = cliente; this.data = new Date(); }

    private Pedido(Builder builder) {
        this.id = builder.id;
        this.data = builder.data != null ? builder.data : new Date();
        this.cliente = builder.cliente;
        this.itens = builder.itens != null ? builder.itens : new ArrayList<>();
        this.freteDecorator = builder.freteDecorator;
        this.brindeDecorator = builder.brindeDecorator;
        this.personalizacaoDecorator = builder.personalizacaoDecorator;
    }

    public static Builder builder() { return new Builder(); }
    public void adicionarItem(ItemPedido item) { itens.add(item); }
    public double getTotalBase() { return itens.stream().mapToDouble(ItemPedido::getSubtotalSemGarantia).sum(); }
    public double getTotalGarantias() { return itens.stream().mapToDouble(ItemPedido::getValorGarantia).sum(); }
    public double getTotal() { return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum(); }
    public double getValorFrete() { return freteDecorator != null ? freteDecorator.getValor() : 0.0; }
    public double getValorBrinde() { return calcularValorAdicional(brindeDecorator); }
    public double getValorPersonalizacao() { return calcularValorAdicional(personalizacaoDecorator); }

    private double calcularValorAdicional(DecoratorAdicional d) {
        if (d == null) return 0.0;
        return d.isPercentual() ? getTotal() * d.getValor() / 100.0 : d.getValor();
    }

    public double getTotalAdicionaisPedido() { return getValorFrete() + getValorBrinde() + getValorPersonalizacao(); }

    public double getTotalFinal() {
        double base = getTotal();
        Calculavel calculo = new PedidoBase(base);
        if (freteDecorator != null && freteDecorator.getValor() > 0) calculo = new AdicionalDecorator(calculo, freteDecorator.getValor(), freteDecorator.isPercentual(), base);
        if (brindeDecorator != null && brindeDecorator.getValor() > 0) calculo = new AdicionalDecorator(calculo, brindeDecorator.getValor(), brindeDecorator.isPercentual(), base);
        if (personalizacaoDecorator != null && personalizacaoDecorator.getValor() > 0) calculo = new AdicionalDecorator(calculo, personalizacaoDecorator.getValor(), personalizacaoDecorator.isPercentual(), base);
        return Math.max(0, calculo.calcular());
    }

    public String getDescricaoCobrancas() {
        List<String> desc = new ArrayList<>();
        if (freteDecorator != null) desc.add(freteDecorator.getDescricao());
        if (brindeDecorator != null) desc.add(brindeDecorator.getDescricao());
        if (personalizacaoDecorator != null) desc.add(personalizacaoDecorator.getDescricao());
        return desc.isEmpty() ? "Sem adicionais" : desc.stream().collect(Collectors.joining(" + "));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    public DecoratorAdicional getFreteDecorator() { return freteDecorator; }
    public void setFreteDecorator(DecoratorAdicional freteDecorator) { this.freteDecorator = freteDecorator; }
    public DecoratorAdicional getBrindeDecorator() { return brindeDecorator; }
    public void setBrindeDecorator(DecoratorAdicional brindeDecorator) { this.brindeDecorator = brindeDecorator; }
    public DecoratorAdicional getPersonalizacaoDecorator() { return personalizacaoDecorator; }
    public void setPersonalizacaoDecorator(DecoratorAdicional personalizacaoDecorator) { this.personalizacaoDecorator = personalizacaoDecorator; }

    public static class Builder {
        private int id; private Date data; private String cliente; private List<ItemPedido> itens = new ArrayList<>();
        private DecoratorAdicional freteDecorator; private DecoratorAdicional brindeDecorator; private DecoratorAdicional personalizacaoDecorator;
        public Builder id(int id) { this.id = id; return this; }
        public Builder data(Date data) { this.data = data; return this; }
        public Builder cliente(String cliente) { this.cliente = cliente; return this; }
        public Builder itens(List<ItemPedido> itens) { this.itens = itens; return this; }
        public Builder freteDecorator(DecoratorAdicional freteDecorator) { this.freteDecorator = freteDecorator; return this; }
        public Builder brindeDecorator(DecoratorAdicional brindeDecorator) { this.brindeDecorator = brindeDecorator; return this; }
        public Builder personalizacaoDecorator(DecoratorAdicional personalizacaoDecorator) { this.personalizacaoDecorator = personalizacaoDecorator; return this; }
        public Pedido build() { return new Pedido(this); }
    }
}
