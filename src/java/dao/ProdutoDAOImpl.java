package dao;

import model.Produto;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, preco, marca, modelo, processador, memoria_ram, armazenamento, cor, voltagem, garantia_meses FROM produto ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos.", e);
        }

        return produtos;
    }

    @Override
    public Optional<Produto> buscarPorId(int id) {
        String sql = "SELECT id, nome, preco, marca, modelo, processador, memoria_ram, armazenamento, cor, voltagem, garantia_meses FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearProduto(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID.", e);
        }

        return Optional.empty();
    }

    @Override
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, marca, modelo, processador, memoria_ram, armazenamento, cor, voltagem, garantia_meses) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherStatement(stmt, produto);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto.", e);
        }
    }

    @Override
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, marca = ?, modelo = ?, processador = ?, memoria_ram = ?, armazenamento = ?, cor = ?, voltagem = ?, garantia_meses = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherStatement(stmt, produto);
            stmt.setInt(11, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto.", e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto.", e);
        }
    }

    private void preencherStatement(PreparedStatement stmt, Produto produto) throws SQLException {
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setString(3, produto.getMarca());
        stmt.setString(4, produto.getModelo());
        stmt.setString(5, produto.getProcessador());
        stmt.setString(6, produto.getMemoriaRam());
        stmt.setString(7, produto.getArmazenamento());
        stmt.setString(8, produto.getCor());
        stmt.setString(9, produto.getVoltagem());
        stmt.setInt(10, produto.getGarantiaMeses());
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        return Produto.builder()
                .id(rs.getInt("id"))
                .nome(rs.getString("nome"))
                .preco(rs.getDouble("preco"))
                .marca(rs.getString("marca"))
                .modelo(rs.getString("modelo"))
                .processador(rs.getString("processador"))
                .memoriaRam(rs.getString("memoria_ram"))
                .armazenamento(rs.getString("armazenamento"))
                .cor(rs.getString("cor"))
                .voltagem(rs.getString("voltagem"))
                .garantiaMeses(rs.getInt("garantia_meses"))
                .build();
    }
}
