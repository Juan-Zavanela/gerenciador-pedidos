<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Produto"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
    
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
<body>
    <div class="container"><div class="topo"><h1>Lista de Produtos</h1><a class="btn btn-primary" href="novoProduto.jsp">Novo Produto</a></div>

    <%
        List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
    %>

    <div class="card"><table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Preço (R$)</th>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Processador</th>
                <th>Memória RAM</th>
                <th>Armazenamento</th>
                <th>Cor</th>
                <th>Voltagem</th>
                <th>Garantia</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (produtos != null && !produtos.isEmpty()) {
                for (Produto p : produtos) { %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNome() %></td>
                        <td><%= String.format("%.2f", p.getPreco()) %></td>
                        <td><%= p.getMarca() != null ? p.getMarca() : "" %></td>
                        <td><%= p.getModelo() != null ? p.getModelo() : "" %></td>
                        <td><%= p.getProcessador() != null ? p.getProcessador() : "" %></td>
                        <td><%= p.getMemoriaRam() != null ? p.getMemoriaRam() : "" %></td>
                        <td><%= p.getArmazenamento() != null ? p.getArmazenamento() : "" %></td>
                        <td><%= p.getCor() != null ? p.getCor() : "" %></td>
                        <td><%= p.getVoltagem() != null ? p.getVoltagem() : "" %></td>
                        <td><%= p.getGarantiaMeses() %> meses</td>
                        <td>
                            <a href="produtos?action=editar&id=<%= p.getId() %>">Editar</a> |
                            <a href="produtos?action=deletar&id=<%= p.getId() %>"
                               onclick="return confirm('Tem certeza que deseja deletar este produto?');">Deletar</a>
                        </td>
                    </tr>
            <%  }
               } else { %>
                    <tr>
                        <td colspan="12">Nenhum produto cadastrado.</td>
                    </tr>
            <% } %>
        </tbody>
    </table></div>

    <div class="acoes">
        <a href="produtos?action=novo">➕ Novo Produto</a> |
        <a href="pedidos?action=listar">Ver Pedidos</a>
    </div>
</div></body>
</html>
