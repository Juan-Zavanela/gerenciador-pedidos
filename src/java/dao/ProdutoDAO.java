package dao;

import model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoDAO {
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(int id);
    void salvar(Produto produto);
    void atualizar(Produto produto);
    void deletar(int id);
}