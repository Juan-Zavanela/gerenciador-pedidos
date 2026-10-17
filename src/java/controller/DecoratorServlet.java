package controller;

import factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DecoratorAdicional;
import service.DecoratorService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/decorators")
public class DecoratorServlet extends HttpServlet {
    private final DecoratorService decoratorService = ServiceFactory.criarDecoratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) action = "listar";
        switch (action) {
            case "listar":
                req.setAttribute("decorators", decoratorService.listarTodos());
                req.getRequestDispatcher("/decorators.jsp").forward(req, resp);
                break;
            case "novo":
                req.getRequestDispatcher("/novoDecorator.jsp").forward(req, resp);
                break;
            case "editar":
                Optional<DecoratorAdicional> d = decoratorService.buscarPorId(parseInt(req.getParameter("id")));
                if (d.isPresent()) { req.setAttribute("decorator", d.get()); req.getRequestDispatcher("/novoDecorator.jsp").forward(req, resp); }
                else resp.sendRedirect("decorators?action=listar");
                break;
            case "deletar":
                try { decoratorService.deletar(parseInt(req.getParameter("id"))); } catch (Exception e) { req.getSession().setAttribute("erro", e.getMessage()); }
                resp.sendRedirect("decorators?action=listar");
                break;
            default:
                resp.sendRedirect("decorators?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        DecoratorAdicional d = new DecoratorAdicional();
        d.setId(parseInt(idStr));
        d.setNome(req.getParameter("nome"));
        d.setTipo(req.getParameter("tipo"));
        d.setValor(parseDouble(req.getParameter("valor")));
        d.setPercentual("on".equals(req.getParameter("percentual")) || "true".equals(req.getParameter("percentual")));
        d.setMeses(parseInt(req.getParameter("meses")));
        d.setAtivo(!"false".equals(req.getParameter("ativo")));
        try {
            if (d.getId() > 0) decoratorService.atualizar(d); else decoratorService.salvar(d);
            resp.sendRedirect("decorators?action=listar");
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("decorator", d);
            req.getRequestDispatcher("/novoDecorator.jsp").forward(req, resp);
        }
    }

    private int parseInt(String v) { if (v == null || v.isBlank()) return 0; return Integer.parseInt(v); }
    private double parseDouble(String v) { if (v == null || v.isBlank()) return 0.0; return Double.parseDouble(v.replace(",", ".")); }
}
