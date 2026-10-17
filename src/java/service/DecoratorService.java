package service;

import dao.DecoratorDAO;
import model.DecoratorAdicional;
import java.util.List;
import java.util.Optional;

public class DecoratorService {
    private final DecoratorDAO decoratorDAO;
    public DecoratorService(DecoratorDAO decoratorDAO) { this.decoratorDAO = decoratorDAO; }
    public List<DecoratorAdicional> listarTodos() { return decoratorDAO.listarTodos(); }
    public List<DecoratorAdicional> listarGarantiasAtivas() { return decoratorDAO.listarAtivosPorTipo("GARANTIA"); }
    public List<DecoratorAdicional> listarFretesAtivos() { return decoratorDAO.listarAtivosPorTipo("FRETE"); }
    public List<DecoratorAdicional> listarBrindesAtivos() { return decoratorDAO.listarAtivosPorTipo("BRINDE"); }
    public List<DecoratorAdicional> listarPersonalizacoesAtivas() { return decoratorDAO.listarAtivosPorTipo("PERSONALIZACAO"); }
    public Optional<DecoratorAdicional> buscarPorId(int id) { return decoratorDAO.buscarPorId(id); }
    public void salvar(DecoratorAdicional d) { validar(d); decoratorDAO.salvar(d); }
    public void atualizar(DecoratorAdicional d) { validar(d); decoratorDAO.atualizar(d); }
    public void deletar(int id) { decoratorDAO.deletar(id); }
    private void validar(DecoratorAdicional d) {
        if (d.getNome() == null || d.getNome().trim().isEmpty()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (!"FRETE".equals(d.getTipo()) && !"GARANTIA".equals(d.getTipo()) && !"BRINDE".equals(d.getTipo()) && !"PERSONALIZACAO".equals(d.getTipo())) {
            throw new IllegalArgumentException("Tipo inválido.");
        }
        if (d.getValor() < 0) throw new IllegalArgumentException("Valor não pode ser negativo.");
    }
}
