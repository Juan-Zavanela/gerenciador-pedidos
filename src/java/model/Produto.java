package model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String marca;
    private String modelo;
    private String processador;
    private String memoriaRam;
    private String armazenamento;
    private String cor;
    private String voltagem;
    private int garantiaMeses;

    public Produto() {
    }

    public Produto(int id, String nome, double preco, String marca, String modelo,
                   String processador, String memoriaRam, String armazenamento,
                   String cor, String voltagem, int garantiaMeses) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
        this.modelo = modelo;
        this.processador = processador;
        this.memoriaRam = memoriaRam;
        this.armazenamento = armazenamento;
        this.cor = cor;
        this.voltagem = voltagem;
        this.garantiaMeses = garantiaMeses;
    }

    private Produto(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.preco = builder.preco;
        this.marca = builder.marca;
        this.modelo = builder.modelo;
        this.processador = builder.processador;
        this.memoriaRam = builder.memoriaRam;
        this.armazenamento = builder.armazenamento;
        this.cor = builder.cor;
        this.voltagem = builder.voltagem;
        this.garantiaMeses = builder.garantiaMeses;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    public String getDescricaoCurta() {
        StringBuilder sb = new StringBuilder();
        if (nome != null && !nome.isBlank()) {
            sb.append(nome);
        }
        if (marca != null && !marca.isBlank()) {
            sb.append(" - ").append(marca);
        }
        if (modelo != null && !modelo.isBlank()) {
            sb.append(" ").append(modelo);
        }
        return sb.toString();
    }

    public static class Builder {
        private int id;
        private String nome;
        private double preco;
        private String marca;
        private String modelo;
        private String processador;
        private String memoriaRam;
        private String armazenamento;
        private String cor;
        private String voltagem;
        private int garantiaMeses;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder preco(double preco) {
            this.preco = preco;
            return this;
        }

        public Builder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder processador(String processador) {
            this.processador = processador;
            return this;
        }

        public Builder memoriaRam(String memoriaRam) {
            this.memoriaRam = memoriaRam;
            return this;
        }

        public Builder armazenamento(String armazenamento) {
            this.armazenamento = armazenamento;
            return this;
        }

        public Builder cor(String cor) {
            this.cor = cor;
            return this;
        }

        public Builder voltagem(String voltagem) {
            this.voltagem = voltagem;
            return this;
        }

        public Builder garantiaMeses(int garantiaMeses) {
            this.garantiaMeses = garantiaMeses;
            return this;
        }

        public Produto build() {
            return new Produto(this);
        }
    }
}
