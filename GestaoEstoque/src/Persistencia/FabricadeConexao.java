package Persistencia;
import java.sql.*;
class FabricadeConexao {

    private final static String URL = "jdbc:sqlite:estoque.sqlite";

    // Padrão de Projeto : Method Factory / Fábrica por método
    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
