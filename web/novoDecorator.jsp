<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DecoratorAdicional"%>
<% DecoratorAdicional d = (DecoratorAdicional) request.getAttribute("decorator"); String erro = (String) request.getAttribute("erro"); boolean editando = d != null && d.getId() > 0; if (d == null) d = new DecoratorAdicional(); %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Novo Decorator</title><style>
body{font-family:Arial;background:#f4f6f8;margin:0;color:#1f2937}.container{max-width:700px;margin:35px auto;padding:0 20px}.card{background:#fff;border-radius:14px;padding:28px;box-shadow:0 8px 24px rgba(0,0,0,.08)}label{font-weight:bold;display:block;margin-top:14px}input,select{width:100%;padding:10px;border:1px solid #d1d5db;border-radius:8px;margin-top:6px}.check{display:flex;gap:8px;align-items:center;margin-top:14px}.check input{width:auto}.btn{display:inline-block;border:none;padding:10px 14px;border-radius:8px;text-decoration:none;margin-top:20px;cursor:pointer}.btn-primary{background:#2563eb;color:white}.btn-secondary{background:#e5e7eb;color:#111827}.erro{padding:12px;background:#fee2e2;color:#991b1b;border-radius:8px;margin-bottom:16px}.ajuda{font-size:13px;color:#6b7280;margin-top:6px}
</style></head><body><div class="container"><div class="card"><h1><%= editando ? "Editar Decorator" : "Novo Decorator" %></h1>
<% if (erro != null) { %><div class="erro"><%= erro %></div><% } %>
<form action="decorators" method="post"><input type="hidden" name="id" value="<%= d.getId() %>">
<label>Nome</label><input type="text" name="nome" value="<%= d.getNome() != null ? d.getNome() : "" %>" required placeholder="Ex: Frete expresso">
<label>Tipo</label><select name="tipo" required><option value="FRETE" <%= "FRETE".equals(d.getTipo()) ? "selected" : "" %>>Frete</option><option value="GARANTIA" <%= "GARANTIA".equals(d.getTipo()) ? "selected" : "" %>>Garantia</option><option value="BRINDE" <%= "BRINDE".equals(d.getTipo()) ? "selected" : "" %>>Brinde Premium</option><option value="PERSONALIZACAO" <%= "PERSONALIZACAO".equals(d.getTipo()) ? "selected" : "" %>>Personalização do produto</option></select>
<label>Valor</label><input type="number" name="valor" min="0" step="0.01" value="<%= d.getValor() %>" required><div class="ajuda">Para garantia pode usar percentual. Para frete, brinde e personalização normalmente use valor em reais. Ex: 25.50.</div>
<label>Meses</label><input type="number" name="meses" min="0" value="<%= d.getMeses() %>">
<div class="check"><input type="checkbox" name="percentual" <%= d.isPercentual() ? "checked" : "" %>> <span>Valor é percentual?</span></div>
<label>Status</label><select name="ativo"><option value="true" <%= d.isAtivo() ? "selected" : "" %>>Ativo</option><option value="false" <%= !d.isAtivo() ? "selected" : "" %>>Inativo</option></select>
<button class="btn btn-primary" type="submit">Salvar</button> <a class="btn btn-secondary" href="decorators?action=listar">Cancelar</a></form></div></div></body></html>
