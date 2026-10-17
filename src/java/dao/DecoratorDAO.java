package dao;

import model.DecoratorAdicional;
import java.util.List;
import java.util.Optional;

public interface DecoratorDAO {
    List<DecoratorAdicional> listarTodos();
    List<DecoratorAdicional> listarAtivosPorTipo(String tipo);
    Optional<DecoratorAdicional> buscarPorId(int id);
    void salvar(DecoratorAdicional decorator);
    void atualizar(DecoratorAdicional decorator);
    void deletar(int id);
}
