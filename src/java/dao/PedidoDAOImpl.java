package dao;

import model.DecoratorAdicional;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public List<Pedido> listarTodos() {
        String sql = """
                SELECT p.id AS pedido_id, p.cliente, p.data_pedido, p.total_final,
                       df.id AS frete_id, df.nome AS frete_nome, df.tipo AS frete_tipo, df.valor AS frete_valor,
                       df.percentual AS frete_percentual, df.meses AS frete_meses, df.ativo AS frete_ativo,
                       db.id AS brinde_id, db.nome AS brinde_nome, db.tipo AS brinde_tipo, db.valor AS brinde_valor,
                       db.percentual AS brinde_percentual, db.meses AS brinde_meses, db.ativo AS brinde_ativo,
                       dp.id AS personalizacao_id, dp.nome AS personalizacao_nome, dp.tipo AS personalizacao_tipo, dp.valor AS personalizacao_valor,
                       dp.percentual AS personalizacao_percentual, dp.meses AS personalizacao_meses, dp.ativo AS personalizacao_ativo,
                       ip.id AS item_id, ip.quantidade, ip.preco_unitario,
                       dg.id AS garantia_id, dg.nome AS garantia_nome, dg.tipo AS garantia_tipo, dg.valor AS garantia_valor,
                       dg.percentual AS garantia_percentual, dg.meses AS garantia_meses, dg.ativo AS garantia_ativo,
                       pr.id AS produto_id, pr.nome, pr.marca, pr.modelo
                FROM pedido p
                LEFT JOIN decorator_adicional df ON df.id = p.decorator_frete_id
                LEFT JOIN decorator_adicional db ON db.id = p.decorator_brinde_id
                LEFT JOIN decorator_adicional dp ON dp.id = p.decorator_personalizacao_id
                LEFT JOIN item_pedido ip ON ip.pedido_id = p.id
                LEFT JOIN produto pr ON pr.id = ip.produto_id
                LEFT JOIN decorator_adicional dg ON dg.id = ip.decorator_garantia_id
                ORDER BY p.id, ip.id
                """;
        Map<Integer, Pedido> pedidos = new LinkedHashMap<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int pedidoId = rs.getInt("pedido_id");
                Pedido pedido = pedidos.get(pedidoId);
                if (pedido == null) {
                    pedido = Pedido.builder()
                            .id(pedidoId)
                            .cliente(rs.getString("cliente"))
                            .data(obterData(rs.getTimestamp("data_pedido")))
                            .freteDecorator(mapearDecorator(rs, "frete"))
                            .brindeDecorator(mapearDecorator(rs, "brinde"))
                            .personalizacaoDecorator(mapearDecorator(rs, "personalizacao"))
                            .itens(new ArrayList<>())
                            .build();
                    pedidos.put(pedidoId, pedido);
                }
                int itemId = rs.getInt("item_id");
                if (!rs.wasNull()) pedido.getItens().add(mapearItem(rs, itemId));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar pedidos.", e); }
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        return listarTodos().stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public void salvar(Pedido pedido) {
        String insertPedido = "INSERT INTO pedido (cliente, data_pedido, decorator_frete_id, decorator_brinde_id, decorator_personalizacao_id, total_final) VALUES (?, ?, ?, ?, ?, ?)";
        String insertItem = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, decorator_garantia_id, valor_garantia, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pedidoStmt = conn.prepareStatement(insertPedido, Statement.RETURN_GENERATED_KEYS)) {
                preencherPedidoStatement(pedidoStmt, pedido, false);
                pedidoStmt.executeUpdate();
                try (ResultSet keys = pedidoStmt.getGeneratedKeys()) { if (keys.next()) pedido.setId(keys.getInt(1)); }
            }
            inserirItens(conn, pedido, insertItem);
            conn.commit();
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar pedido.", e); }
    }

    @Override
    public void atualizar(Pedido pedido) {
        String updatePedido = "UPDATE pedido SET cliente=?, data_pedido=?, decorator_frete_id=?, decorator_brinde_id=?, decorator_personalizacao_id=?, total_final=? WHERE id=?";
        String deleteItens = "DELETE FROM item_pedido WHERE pedido_id=?";
        String insertItem = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, decorator_garantia_id, valor_garantia, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pedidoStmt = conn.prepareStatement(updatePedido)) {
                preencherPedidoStatement(pedidoStmt, pedido, true);
                pedidoStmt.executeUpdate();
            }
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteItens)) { deleteStmt.setInt(1, pedido.getId()); deleteStmt.executeUpdate(); }
            inserirItens(conn, pedido, insertItem);
            conn.commit();
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar pedido.", e); }
    }

    private void preencherPedidoStatement(PreparedStatement stmt, Pedido pedido, boolean update) throws SQLException {
        stmt.setString(1, pedido.getCliente());
        stmt.setTimestamp(2, new Timestamp(pedido.getData().getTime()));
        setDecoratorId(stmt, 3, pedido.getFreteDecorator());
        setDecoratorId(stmt, 4, pedido.getBrindeDecorator());
        setDecoratorId(stmt, 5, pedido.getPersonalizacaoDecorator());
        stmt.setDouble(6, pedido.getTotalFinal());
        if (update) stmt.setInt(7, pedido.getId());
    }

    private void setDecoratorId(PreparedStatement stmt, int index, DecoratorAdicional d) throws SQLException {
        if (d != null) stmt.setInt(index, d.getId()); else stmt.setNull(index, Types.INTEGER);
    }

    private void inserirItens(Connection conn, Pedido pedido, String insertItem) throws SQLException {
        try (PreparedStatement itemStmt = conn.prepareStatement(insertItem)) {
            for (ItemPedido item : pedido.getItens()) {
                itemStmt.setInt(1, pedido.getId());
                itemStmt.setInt(2, item.getProduto().getId());
                itemStmt.setInt(3, item.getQuantidade());
                itemStmt.setDouble(4, item.getProduto().getPreco());
                setDecoratorId(itemStmt, 5, item.getGarantiaDecorator());
                itemStmt.setDouble(6, item.getValorGarantia());
                itemStmt.setDouble(7, item.getSubtotal());
                itemStmt.executeUpdate();
            }
        }
    }

    @Override
    public void deletar(int id) {
        String deletePedido = "DELETE FROM pedido WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(deletePedido)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao deletar pedido.", e); }
    }

    private ItemPedido mapearItem(ResultSet rs, int itemId) throws SQLException {
        Produto produto = Produto.builder()
                .id(rs.getInt("produto_id"))
                .nome(rs.getString("nome"))
                .preco(rs.getDouble("preco_unitario"))
                .marca(rs.getString("marca"))
                .modelo(rs.getString("modelo"))
                .build();
        return new ItemPedido(itemId, produto, rs.getInt("quantidade"), mapearDecorator(rs, "garantia"));
    }

    private DecoratorAdicional mapearDecorator(ResultSet rs, String prefixo) throws SQLException {
        int id = rs.getInt(prefixo + "_id");
        if (rs.wasNull()) return null;
        return new DecoratorAdicional(
                id,
                rs.getString(prefixo + "_nome"),
                rs.getString(prefixo + "_tipo"),
                rs.getDouble(prefixo + "_valor"),
                rs.getBoolean(prefixo + "_percentual"),
                rs.getInt(prefixo + "_meses"),
                rs.getBoolean(prefixo + "_ativo")
        );
    }

    private java.util.Date obterData(Timestamp timestamp) { return timestamp != null ? new java.util.Date(timestamp.getTime()) : new java.util.Date(); }
}
