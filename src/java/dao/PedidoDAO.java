package dao;

import model.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoDAO {
    List<Pedido> listarTodos();
    Optional<Pedido> buscarPorId(int id);
    void salvar(Pedido pedido);
    void atualizar(Pedido pedido);
    void deletar(int id);
}