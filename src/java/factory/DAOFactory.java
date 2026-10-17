package factory;

import dao.DecoratorDAO;
import dao.DecoratorDAOImpl;
import dao.PedidoDAO;
import dao.PedidoDAOImpl;
import dao.ProdutoDAO;
import dao.ProdutoDAOImpl;

public final class DAOFactory {
    private DAOFactory() {}
    public static ProdutoDAO criarProdutoDAO() { return new ProdutoDAOImpl(); }
    public static PedidoDAO criarPedidoDAO() { return new PedidoDAOImpl(); }
    public static DecoratorDAO criarDecoratorDAO() { return new DecoratorDAOImpl(); }
}
