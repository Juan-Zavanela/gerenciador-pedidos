package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/gerenciador__de_pedidos?useSSL=false&serverTimezone=America/Sao_Paulo&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco. Verifique URL, usuário, senha e o mysql-connector.", e);
        }
    }
}
