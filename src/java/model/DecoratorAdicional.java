package model;

public class DecoratorAdicional {
    private int id;
    private String nome;
    private String tipo; // FRETE, GARANTIA, BRINDE ou PERSONALIZACAO
    private double valor;
    private boolean percentual;
    private int meses;
    private boolean ativo;

    public DecoratorAdicional() {}

    public DecoratorAdicional(int id, String nome, String tipo, double valor, boolean percentual, int meses, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.percentual = percentual;
        this.meses = meses;
        this.ativo = ativo;
    }

    public boolean isGarantia() { return "GARANTIA".equalsIgnoreCase(tipo); }
    public boolean isFrete() { return "FRETE".equalsIgnoreCase(tipo); }
    public boolean isBrinde() { return "BRINDE".equalsIgnoreCase(tipo); }
    public boolean isPersonalizacao() { return "PERSONALIZACAO".equalsIgnoreCase(tipo); }

    public String getDescricao() {
        if (percentual) {
            return nome + " (+" + String.format("%.2f", valor) + "%)";
        }
        return nome + " (R$ " + String.format("%.2f", valor) + ")";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public boolean isPercentual() { return percentual; }
    public void setPercentual(boolean percentual) { this.percentual = percentual; }
    public int getMeses() { return meses; }
    public void setMeses(int meses) { this.meses = meses; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
