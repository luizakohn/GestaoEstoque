package Persistencia;
import java.sql.*;
public class BaseDAO {
    protected Connection obterConexao() throws SQLException {
        return FabricadeConexao.obterConexao();
    }
}
