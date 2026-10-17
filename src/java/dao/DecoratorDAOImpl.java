package dao;

import model.DecoratorAdicional;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecoratorDAOImpl implements DecoratorDAO {
    @Override
    public List<DecoratorAdicional> listarTodos() {
        String sql = "SELECT * FROM decorator_adicional ORDER BY tipo, id";
        return listar(sql);
    }

    @Override
    public List<DecoratorAdicional> listarAtivosPorTipo(String tipo) {
        String sql = "SELECT * FROM decorator_adicional WHERE ativo = TRUE AND tipo = ? ORDER BY id";
        List<DecoratorAdicional> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar decorators.", e); }
        return lista;
    }

    @Override
    public Optional<DecoratorAdicional> buscarPorId(int id) {
        String sql = "SELECT * FROM decorator_adicional WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return Optional.of(mapear(rs)); }
        } catch (SQLException e) { throw new RuntimeException("Erro ao buscar decorator.", e); }
        return Optional.empty();
    }

    @Override
    public void salvar(DecoratorAdicional d) {
        String sql = "INSERT INTO decorator_adicional (nome, tipo, valor, percentual, meses, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencher(stmt, d, false);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar decorator.", e); }
    }

    @Override
    public void atualizar(DecoratorAdicional d) {
        String sql = "UPDATE decorator_adicional SET nome=?, tipo=?, valor=?, percentual=?, meses=?, ativo=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencher(stmt, d, true);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar decorator.", e); }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM decorator_adicional WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Não foi possível excluir. O decorator pode estar em uso em algum pedido.", e); }
    }

    private List<DecoratorAdicional> listar(String sql) {
        List<DecoratorAdicional> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar decorators.", e); }
        return lista;
    }

    private void preencher(PreparedStatement stmt, DecoratorAdicional d, boolean update) throws SQLException {
        stmt.setString(1, d.getNome());
        stmt.setString(2, d.getTipo());
        stmt.setDouble(3, d.getValor());
        stmt.setBoolean(4, d.isPercentual());
        stmt.setInt(5, d.getMeses());
        stmt.setBoolean(6, d.isAtivo());
        if (update) stmt.setInt(7, d.getId());
    }

    private DecoratorAdicional mapear(ResultSet rs) throws SQLException {
        return new DecoratorAdicional(
            rs.getInt("id"), rs.getString("nome"), rs.getString("tipo"),
            rs.getDouble("valor"), rs.getBoolean("percentual"), rs.getInt("meses"), rs.getBoolean("ativo")
        );
    }
}
