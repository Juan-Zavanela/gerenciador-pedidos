<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Produto" %>
<%
    Produto produto = (Produto) request.getAttribute("produto");
    String erro = (String) request.getAttribute("erro");
    boolean editando = produto != null && produto.getId() > 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editando ? "Editar Produto" : "Novo Produto" %></title>
    
<style>
* { box-sizing: border-box; }

body {
    margin: 0;
    font-family: Arial, Helvetica, sans-serif;
    background: #f4f6f8;
    color: #1f2937;
}

.container {
    max-width: 1280px;
    margin: 35px auto;
    padding: 0 20px;
}

.topo {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 22px;
}

h1 {
    margin: 0;
}

.btn {
    display: inline-block;
    padding: 10px 14px;
    border-radius: 8px;
    text-decoration: none;
    font-size: 14px;
    border: none;
    cursor: pointer;
}

.btn-primary {
    background: #2563eb;
    color: white;
}

.btn-secondary {
    background: #e5e7eb;
    color: #111827;
}

.btn-danger {
    background: #fee2e2;
    color: #991b1b;
}

.card {
    background: white;
    border-radius: 14px;
    overflow: hidden;
    box-shadow: 0 8px 24px rgba(0,0,0,0.08);
}

table {
    border-collapse: collapse;
    width: 100%;
}

th {
    background: #111827;
    color: white;
    text-align: left;
    padding: 13px;
    font-size: 14px;
}

td {
    padding: 13px;
    border-bottom: 1px solid #e5e7eb;
    font-size: 14px;
}

tr:hover td {
    background: #f9fafb;
}

.form-card {
    background: white;
    border-radius: 14px;
    padding: 28px;
    box-shadow: 0 8px 24px rgba(0,0,0,0.08);
}

.grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(280px, 1fr));
    gap: 18px;
}

label {
    display: block;
    margin-bottom: 6px;
    font-weight: bold;
    font-size: 14px;
}

input {
    width: 100%;
    padding: 12px;
    border: 1px solid #d1d5db;
    border-radius: 8px;
    font-size: 14px;
}

input:focus {
    outline: none;
    border-color: #2563eb;
}

.acoes {
    margin-top: 24px;
    display: flex;
    gap: 12px;
}

.erro {
    background: #fee2e2;
    color: #991b1b;
    padding: 12px;
    border-radius: 8px;
    margin-bottom: 18px;
}
</style>

</head>
<body><div class='container'>
    <div class="topo"><h1><%= editando ? "Editar Produto" : "Novo Produto" %></h1></div><div class="form-card">

    <% if (erro != null && !erro.isEmpty()) { %>
        <p class="erro"><%= erro %></p>
    <% } %>

    <form action="produtos" method="post">
        <% if (editando) { %>
            <input type="hidden" name="id" value="<%= produto.getId() %>">
        <% } %>

        <div class="grid">
            <div>
                <label>Nome *</label>
                <input type="text" name="nome" required value="<%= produto != null ? produto.getNome() : "" %>">
            </div>
            <div>
                <label>Preço (R$) *</label>
                <input type="number" name="preco" step="0.01" min="0" required value="<%= produto != null ? produto.getPreco() : "" %>">
            </div>
            <div>
                <label>Marca *</label>
                <input type="text" name="marca" required value="<%= produto != null ? produto.getMarca() : "" %>">
            </div>
            <div>
                <label>Modelo</label>
                <input type="text" name="modelo" value="<%= produto != null ? produto.getModelo() : "" %>">
            </div>
            <div>
                <label>Processador</label>
                <input type="text" name="processador" value="<%= produto != null ? produto.getProcessador() : "" %>">
            </div>
            <div>
                <label>Memória RAM</label>
                <input type="text" name="memoriaRam" value="<%= produto != null ? produto.getMemoriaRam() : "" %>">
            </div>
            <div>
                <label>Armazenamento</label>
                <input type="text" name="armazenamento" value="<%= produto != null ? produto.getArmazenamento() : "" %>">
            </div>
            <div>
                <label>Cor</label>
                <input type="text" name="cor" value="<%= produto != null ? produto.getCor() : "" %>">
            </div>
            <div>
                <label>Voltagem</label>
                <input type="text" name="voltagem" value="<%= produto != null ? produto.getVoltagem() : "" %>">
            </div>
            <div>
                <label>Garantia (meses)</label>
                <input type="number" name="garantiaMeses" min="0" value="<%= produto != null ? produto.getGarantiaMeses() : "0" %>">
            </div>
        </div>

        <div class="acoes">
            <button type="submit"><%= editando ? "Salvar Alterações" : "Criar Produto" %></button>
            <a href="produtos?action=listar">← Voltar</a>
        </div>
    </form></div>
</div></body>
</html>
