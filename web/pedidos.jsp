<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="model.Pedido"%>
<%@page import="model.ItemPedido"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedidos</title>
    <style>
        * { box-sizing: border-box; }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #f4f6f8;
            color: #1f2937;
        }

        .container {
            max-width: 1200px;
            margin: 35px auto;
            padding: 0 20px;
        }

        .topo {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 22px;
        }

        h1 { margin: 0; }

        .btn {
            display: inline-block;
            padding: 10px 14px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-primary { background: #2563eb; color: white; }
        .btn-secondary { background: #e5e7eb; color: #111827; }
        .btn-danger { background: #fee2e2; color: #991b1b; }

        .card {
            background: #ffffff;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th {
            background: #111827;
            color: #ffffff;
            text-align: left;
            padding: 13px;
            font-size: 14px;
        }

        td {
            padding: 13px;
            border-bottom: 1px solid #e5e7eb;
            vertical-align: top;
            font-size: 14px;
        }

        tr:hover td { background: #f9fafb; }

        ul {
            margin: 0;
            padding-left: 18px;
        }

        .preco-final {
            font-size: 17px;
            font-weight: bold;
            color: #047857;
        }

        .tag {
            display: inline-block;
            background: #eef2ff;
            color: #3730a3;
            padding: 4px 8px;
            border-radius: 999px;
            font-size: 12px;
            margin: 2px;
        }

        .vazio {
            text-align: center;
            padding: 35px;
            color: #6b7280;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="topo">
        <h1>Meus Pedidos</h1>
        <div>
            <a class="btn btn-secondary" href="produtos?action=listar">Ver produtos</a>
            <a class="btn btn-primary" href="pedidos?action=novo">+ Novo pedido</a>
        </div>
    </div>

    <%
        List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    %>

    <div class="card">
        <table>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Data</th>
                <th>Produtos</th>
                <th>Garantias</th>
                <th>Adicionais do pedido</th>
                <th>Total final</th>
                <th>Itens</th>
                <th>Ações</th>
            </tr>
            <% if (pedidos != null && !pedidos.isEmpty()) {
                for (Pedido ped : pedidos) { %>
                    <tr>
                        <td>#<%= ped.getId() %></td>
                        <td><strong><%= ped.getCliente() %></strong></td>
                        <td><%= ped.getData() != null ? sdf.format(ped.getData()) : "" %></td>
                        <td>R$ <%= String.format("%.2f", ped.getTotalBase()) %></td>
                        <td>R$ <%= String.format("%.2f", ped.getTotalGarantias()) %></td>
                        <td><span class="tag"><%= ped.getDescricaoCobrancas() %></span><br>R$ <%= String.format("%.2f", ped.getTotalAdicionaisPedido()) %></td>
                        <td><span class="preco-final">R$ <%= String.format("%.2f", ped.getTotalFinal()) %></span></td>
                        <td>
                            <ul>
                            <% for (ItemPedido item : ped.getItens()) { %>
                                <li>
                                    <%= item.getProduto().getDescricaoCurta() %><br>
                                    qtd: <%= item.getQuantidade() %> | <%= item.getDescricaoGarantia() %><br>
                                    garantia: R$ <%= String.format("%.2f", item.getValorGarantia()) %> | subtotal: R$ <%= String.format("%.2f", item.getSubtotal()) %>
                                </li>
                            <% } %>
                            </ul>
                        </td>
                        <td>
                            <a class="btn btn-danger" href="pedidos?action=deletar&id=<%= ped.getId() %>"
                               onclick="return confirm('Deseja excluir este pedido?');">Excluir</a>
                        </td>
                    </tr>
            <%  }
               } else { %>
                <tr>
                    <td class="vazio" colspan="9">Nenhum pedido cadastrado.</td>
                </tr>
            <% } %>
        </table>
    </div>
</div>
</body>
</html>
