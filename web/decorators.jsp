<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.DecoratorAdicional"%>
<% List<DecoratorAdicional> decorators = (List<DecoratorAdicional>) request.getAttribute("decorators"); String erro = (String) session.getAttribute("erro"); session.removeAttribute("erro"); %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Decorators</title><style>
body{font-family:Arial;background:#f4f6f8;margin:0;color:#1f2937}.container{max-width:1000px;margin:35px auto;padding:0 20px}.topo{display:flex;justify-content:space-between;align-items:center;margin-bottom:20px}.card{background:#fff;border-radius:14px;box-shadow:0 8px 24px rgba(0,0,0,.08);overflow:hidden}table{width:100%;border-collapse:collapse}th{background:#111827;color:white;text-align:left;padding:13px}td{padding:13px;border-bottom:1px solid #e5e7eb}.btn{display:inline-block;padding:9px 12px;border-radius:8px;text-decoration:none;font-size:14px}.btn-primary{background:#2563eb;color:#fff}.btn-secondary{background:#e5e7eb;color:#111827}.btn-danger{background:#fee2e2;color:#991b1b}.tag{padding:4px 8px;border-radius:999px;background:#eef2ff;color:#3730a3;font-size:12px}.erro{padding:12px;background:#fee2e2;color:#991b1b;border-radius:8px;margin-bottom:16px}
</style></head><body><div class="container"><div class="topo"><h1>Gerenciar Decorators</h1><div><a class="btn btn-secondary" href="index.html">Início</a> <a class="btn btn-primary" href="decorators?action=novo">+ Novo decorator</a></div></div>
<% if (erro != null) { %><div class="erro"><%= erro %></div><% } %>
<div class="card"><table><tr><th>ID</th><th>Nome</th><th>Tipo</th><th>Valor</th><th>Meses</th><th>Ativo</th><th>Ações</th></tr>
<% if (decorators != null && !decorators.isEmpty()) { for (DecoratorAdicional d : decorators) { %>
<tr><td>#<%= d.getId() %></td><td><%= d.getNome() %></td><td><span class="tag"><%= d.getTipo() %></span></td><td><%= d.isPercentual() ? String.format("%.2f%%", d.getValor()) : "R$ " + String.format("%.2f", d.getValor()) %></td><td><%= d.getMeses() %></td><td><%= d.isAtivo() ? "Sim" : "Não" %></td><td><a class="btn btn-secondary" href="decorators?action=editar&id=<%= d.getId() %>">Editar</a> <a class="btn btn-danger" href="decorators?action=deletar&id=<%= d.getId() %>" onclick="return confirm('Excluir decorator?')">Excluir</a></td></tr>
<% }} else { %><tr><td colspan="7">Nenhum decorator cadastrado.</td></tr><% } %>
</table></div></div></body></html>
