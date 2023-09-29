package Persistencia;
import java.sql.*;
import Entidade.Produto;
import java.time.*;

public class EstoqueDAO extends BaseDAO{
    public void criarTabela(){
        String sql = """
                CREATE TABLE IF NOT EXISTS produtos (
                     id_produto integer primary key autoincrement,
                     nome VARCHAR(100) NOT NULL,
                     descricao VARCHAR(255),
                     preco DECIMAL(10, 2) NOT NULL,
                     quantidade integer,
                     ativo boolean
                );
                """;
        try(PreparedStatement p = obterConexao().prepareStatement(sql)){
            p.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao criar tabela Produto. ");
            e.printStackTrace();
        }
        //fazendo o prepared statment no try, ele fecha automáticamente
    }

    public void inserir(Produto produto){
        try(Connection c = obterConexao()){
            String sql = """
                    insert into produto(nome,descricao,preco,quantidade,ativo)
                    values(?,?,?,?,?,true)
                    """;
            try(PreparedStatement p = c.prepareStatement(sql)){
                p.setString(1, produto.getNome());
                p.setString(2,produto.getDescricao());
                p.setDouble(3,produto.getPreco());
                p.setDate(4, produto.getDataAteracao());
                p.setInt(5,produto.getQtdeEstoque());
                p.executeUpdate();
            }catch (SQLException e){
                System.out.println("Erro ao inserir Produto. "+ produto);
                e.printStackTrace();
            }
        }catch (SQLException e){
            System.out.println("Erro na conexão.");
            e.printStackTrace();
        }

    }
    public void desativar(long id){
        String sql = """
                    update ativo from produtos where id_produto = ?
                    """;
        try(PreparedStatement p = obterConexao().prepareStatement(sql)){
            p.setLong(1,id);
            p.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao desativar produto "+id);
            e.printStackTrace();
        }
    }

    public void atualizarEstoque(long id, int qtde, boolean diminuir){
        if (diminuir){
            qtde = qtde * (-1);
        }
        String sql = """
                select quantidade from produtos where id_produto = ?
                """;
        int qtdeEstoque = 0;
        try(PreparedStatement p = obterConexao().prepareStatement(sql)){
            p.setLong(1, id);
            ResultSet rs = p.executeQuery();
            if (rs.next()){
                qtdeEstoque = rs.getInt("quantidade");
            }
        }catch (SQLException e){
            System.out.println("Erro ao consultar produto "+ id);
            e.printStackTrace();
            return;
        }
        qtde = qtde + qtde;
                sql = """
                    update produtos set quantidade = ? where id_produto = ?
                    """;
        try(PreparedStatement p = obterConexao().prepareStatement(sql)){
            p.setInt(1, qtde);
            p.setLong(2, id);
            p.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao desativar produto "+ id);
            e.printStackTrace();
        }
    }
}
