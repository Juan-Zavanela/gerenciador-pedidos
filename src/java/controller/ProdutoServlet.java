package controller;

import factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Produto;
import service.ProdutoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private final ProdutoService produtoService = ServiceFactory.criarProdutoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "listar";
        }

        switch (action) {
            case "listar":
                req.setAttribute("produtos", produtoService.listarTodos());
                req.getRequestDispatcher("/produtos.jsp").forward(req, resp);
                break;

            case "novo":
                req.getRequestDispatcher("/novoProduto.jsp").forward(req, resp);
                break;

            case "editar":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
                    if (produtoOpt.isPresent()) {
                        req.setAttribute("produto", produtoOpt.get());
                        req.getRequestDispatcher("/novoProduto.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect("produtos?action=listar");
                    }
                } catch (Exception e) {
                    resp.sendRedirect("produtos?action=listar");
                }
                break;

            case "deletar":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    produtoService.deletar(id);
                } catch (Exception e) {
                    req.getSession().setAttribute("erro", "Não foi possível excluir o produto.");
                }
                resp.sendRedirect("produtos?action=listar");
                break;

            default:
                resp.sendRedirect("produtos?action=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");

        try {
            Produto produto = montarProdutoParaRetorno(req, idStr);

            if (produto.getId() > 0) {
                produtoService.atualizar(produto);
            } else {
                produtoService.salvar(produto);
            }

            resp.sendRedirect("produtos?action=listar");
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("produto", montarProdutoParaRetorno(req, idStr));
            req.getRequestDispatcher("/novoProduto.jsp").forward(req, resp);
        }
    }

    private Produto montarProdutoParaRetorno(HttpServletRequest req, String idStr) {
        Produto produto = Produto.builder()
                .nome(req.getParameter("nome"))
                .preco(parseDouble(req.getParameter("preco")))
                .marca(req.getParameter("marca"))
                .modelo(req.getParameter("modelo"))
                .processador(req.getParameter("processador"))
                .memoriaRam(req.getParameter("memoriaRam"))
                .armazenamento(req.getParameter("armazenamento"))
                .cor(req.getParameter("cor"))
                .voltagem(req.getParameter("voltagem"))
                .garantiaMeses(parseInt(req.getParameter("garantiaMeses")))
                .build();
        if (idStr != null && !idStr.isBlank()) {
            try {
                produto.setId(Integer.parseInt(idStr));
            } catch (NumberFormatException ignored) {
            }
        }
        return produto;
    }

    private double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }

    private int parseInt(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
