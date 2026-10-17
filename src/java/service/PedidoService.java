package service;

import dao.PedidoDAO;
import model.DecoratorAdicional;
import model.ItemPedido;
import model.Pedido;
import model.Produto;

import java.util.List;
import java.util.Optional;

public class PedidoService {
    private final PedidoDAO pedidoDAO;
    private final ProdutoService produtoService;
    private final DecoratorService decoratorService;

    public PedidoService(PedidoDAO pedidoDAO, ProdutoService produtoService, DecoratorService decoratorService) {
        this.pedidoDAO = pedidoDAO;
        this.produtoService = produtoService;
        this.decoratorService = decoratorService;
    }

    public List<Pedido> listarTodos() { return pedidoDAO.listarTodos(); }
    public Optional<Pedido> buscarPorId(int id) { return pedidoDAO.buscarPorId(id); }
    public void salvar(Pedido pedido) { validarPedido(pedido); pedidoDAO.salvar(pedido); }
    public void atualizar(Pedido pedido) { validarPedido(pedido); pedidoDAO.atualizar(pedido); }
    public void deletar(int id) { pedidoDAO.deletar(id); }

    public ItemPedido criarItem(int produtoId, int quantidade, int decoratorGarantiaId) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(produtoId);
        if (produtoOpt.isEmpty()) throw new IllegalArgumentException("Produto não encontrado.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        DecoratorAdicional garantia = buscarDecoratorPorTipo(decoratorGarantiaId, "GARANTIA");
        return new ItemPedido(0, produtoOpt.get(), quantidade, garantia);
    }

    public DecoratorAdicional buscarFrete(int id) { return buscarDecoratorPorTipo(id, "FRETE"); }
    public DecoratorAdicional buscarBrinde(int id) { return buscarDecoratorPorTipo(id, "BRINDE"); }
    public DecoratorAdicional buscarPersonalizacao(int id) { return buscarDecoratorPorTipo(id, "PERSONALIZACAO"); }

    private DecoratorAdicional buscarDecoratorPorTipo(int id, String tipo) {
        if (id <= 0) return null;
        return decoratorService.buscarPorId(id)
                .filter(d -> tipo.equalsIgnoreCase(d.getTipo()))
                .orElseThrow(() -> new IllegalArgumentException("Decorator do tipo " + tipo + " inválido."));
    }

    private void validarPedido(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().trim().isEmpty()) throw new IllegalArgumentException("Cliente é obrigatório.");
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) throw new IllegalArgumentException("Pedido deve ter pelo menos um item.");
    }
}
