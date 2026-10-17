package controller;

import factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ItemPedido;
import model.Pedido;
import service.DecoratorService;
import service.PedidoService;
import service.ProdutoService;

import java.io.IOException;

@WebServlet("/pedidos")
public class PedidoServlet extends HttpServlet {
    private final PedidoService pedidoService = ServiceFactory.criarPedidoService();
    private final ProdutoService produtoService = ServiceFactory.criarProdutoService();
    private final DecoratorService decoratorService = ServiceFactory.criarDecoratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.trim().isEmpty()) action = "listar";
        switch (action) {
            case "listar":
                req.setAttribute("pedidos", pedidoService.listarTodos());
                req.getRequestDispatcher("/pedidos.jsp").forward(req, resp);
                break;
            case "novo":
                carregarCombos(req);
                req.getRequestDispatcher("/novoPedido.jsp").forward(req, resp);
                break;
            case "deletar":
                try { pedidoService.deletar(Integer.parseInt(req.getParameter("id"))); }
                catch (Exception e) { req.getSession().setAttribute("erro", "Não foi possível excluir o pedido."); }
                resp.sendRedirect("pedidos?action=listar");
                break;
            default:
                resp.sendRedirect("pedidos?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pedido pedido = Pedido.builder()
                .cliente(req.getParameter("cliente"))
                .freteDecorator(pedidoService.buscarFrete(parseInt(req.getParameter("decoratorFreteId"))))
                .brindeDecorator(pedidoService.buscarBrinde(parseInt(req.getParameter("decoratorBrindeId"))))
                .personalizacaoDecorator(pedidoService.buscarPersonalizacao(parseInt(req.getParameter("decoratorPersonalizacaoId"))))
                .build();
        try {
            String[] produtoIds = req.getParameterValues("produtoId");
            String[] quantidades = req.getParameterValues("quantidade");
            String[] decoratorGarantiaIds = req.getParameterValues("decoratorGarantiaId");
            if (produtoIds == null || quantidades == null || produtoIds.length == 0) throw new IllegalArgumentException("Adicione pelo menos um item ao pedido.");
            for (int i = 0; i < produtoIds.length; i++) {
                int produtoId = Integer.parseInt(produtoIds[i]);
                int quantidade = Integer.parseInt(quantidades[i]);
                if (quantidade <= 0) continue;
                int garantiaId = decoratorGarantiaIds != null && decoratorGarantiaIds.length > i ? parseInt(decoratorGarantiaIds[i]) : 0;
                ItemPedido item = pedidoService.criarItem(produtoId, quantidade, garantiaId);
                pedido.adicionarItem(item);
            }
            pedidoService.salvar(pedido);
            resp.sendRedirect("pedidos?action=listar");
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            carregarCombos(req);
            req.getRequestDispatcher("/novoPedido.jsp").forward(req, resp);
        }
    }

    private void carregarCombos(HttpServletRequest req) {
        req.setAttribute("produtos", produtoService.listarTodos());
        req.setAttribute("garantias", decoratorService.listarGarantiasAtivas());
        req.setAttribute("fretes", decoratorService.listarFretesAtivos());
        req.setAttribute("brindes", decoratorService.listarBrindesAtivos());
        req.setAttribute("personalizacoes", decoratorService.listarPersonalizacoesAtivas());
    }

    private int parseInt(String value) {
        if (value == null || value.isBlank()) return 0;
        return Integer.parseInt(value);
    }
}
