package factory;

import service.DecoratorService;
import service.PedidoService;
import service.ProdutoService;

public final class ServiceFactory {
    private static final ProdutoService PRODUTO_SERVICE = new ProdutoService(DAOFactory.criarProdutoDAO());
    private static final DecoratorService DECORATOR_SERVICE = new DecoratorService(DAOFactory.criarDecoratorDAO());
    private static final PedidoService PEDIDO_SERVICE = new PedidoService(DAOFactory.criarPedidoDAO(), PRODUTO_SERVICE, DECORATOR_SERVICE);
    private ServiceFactory() {}
    public static ProdutoService criarProdutoService() { return PRODUTO_SERVICE; }
    public static PedidoService criarPedidoService() { return PEDIDO_SERVICE; }
    public static DecoratorService criarDecoratorService() { return DECORATOR_SERVICE; }
}
