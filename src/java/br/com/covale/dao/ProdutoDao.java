package br.com.covale.dao;

import br.com.covale.modelo.Produto;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {
    
    private Conexao conn;
    
    private PreparedStatement stmt;
    
    public ProdutoDao() {        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Produto pd) {

        String sql = "INSERT INTO produto(nome,marca,grupo,quantidade,medida,"
                + "preco_compra,val_unit) "
                + "VALUES (?,?,?,?,?,?,?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, pd.getNome());
            stmt.setString(2, pd.getMarca());
            stmt.setString(3, pd.getGrupo());
            stmt.setString(4, pd.getQuantidade());
            stmt.setString(5, pd.getMedida());
            stmt.setString(6, pd.getPreco_compra());
            stmt.setDouble(7, pd.getVal_unit());
            stmt.execute();
            retorno = true;
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;

            return retorno;
        }
    }

    public boolean update(Produto pd) {

        String sql; 
        sql = "UPDATE produto SET nome=?, marca=?, grupo=?, quantidade=?, medida=?, "
                + "preco_compra=?, val_unit=? WHERE id_produto=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);            
            stmt.setString(1, pd.getNome());
            stmt.setString(2, pd.getMarca());
            stmt.setString(3, pd.getGrupo());
            stmt.setString(4, pd.getQuantidade());
            stmt.setString(5, pd.getMedida());
            stmt.setString(6, pd.getPreco_compra());
            stmt.setDouble(7, pd.getVal_unit());
            stmt.setInt(8, pd.getId_produto());
            stmt.execute();
            retorno = true;
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return retorno;
        }
    }

    public boolean delete(int id_produto) {

        String sql = "DELETE FROM produto WHERE id_produto=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_produto);
            stmt.execute();
            retorno = true;
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return retorno;
        }
    }

    public Produto objectFactory(ResultSet rs) {         
        Produto pd = null; 
        try {
            pd = new Produto(rs.getInt("id_produto"), rs.getString("nome"),
                    rs.getString("marca"), rs.getString("grupo"),
                    rs.getString("quantidade"), rs.getString("medida"),
                    rs.getString("preco_compra"), rs.getDouble("val_unit"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return pd;
        }
    }
        
    public Produto objectFactoryPorVenda(ResultSet rs) { 
        
        Produto pd = null; 
        try {
            pd = new Produto(rs.getInt("id_produto"), rs.getString("nome"),
                    rs.getString("marca"), rs.getString("grupo"),
                    rs.getString("quantidade"),rs.getString("qtde"), rs.getString("medida"),
                    rs.getString("preco_compra"), rs.getDouble("val_unit"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return pd;
        }
    }

    public List<Produto> selecionarTodos() {

        List<Produto> produtos = null;
        String sql = "SELECT * FROM produto order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {                
                produtos.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return produtos;
        }
    }

    public List<Produto> selecionarBusca(Produto pd) {
        List<Produto> produtos = new ArrayList<Produto>();

        String sql = "SELECT * fROM produto WHERE UPPER(nome) like ? "
                + "AND UPPER(marca) like ?  AND UPPER(grupo) like ? "
                + " AND UPPER(quantidade) like ? "
                + " AND UPPER(preco_compra) like ? order by nome asc  ";


        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + pd.getNome().toUpperCase() + "%");
            stmt.setString(2, "%" + pd.getMarca().toUpperCase() + "%");
            stmt.setString(3, "%" + pd.getGrupo().toUpperCase() + "%");
            stmt.setString(4, "%" + pd.getQuantidade().toUpperCase() + "%");
            stmt.setString(5, "%" + pd.getPreco_compra().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return produtos;
        }
    }

    public Produto selecionarUm(int id_produto) {
        Produto produto = null;
        String sql = "SELECT * FROM produto WHERE id_produto=?";

        try {        
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_produto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                produto = objectFactory(rs);
            }         
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produto;
        }
    }

    public List<Produto> selecionarPorCompra(int id) {
        List<Produto> produtos = null;
        String sql;
        sql = "SELECT * FROM produto p "
                + " INNER JOIN prod_compra c ON c.id_produto = p.id_produto "
                + "WHERE c.id_compra  = ? "
                + "order by nome asc";
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }

    public List<Produto> selecionarForaCompra(int id) {
        List<Produto> produtos = null;
        String sql = "SELECT * FROM produto "
                + "WHERE id_produto NOT IN (SELECT id_produto FROM prod_compra " 
                + "WHERE id_compra = ?)";
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }
      
    public boolean verificaCompra(int id) {
        String sql = "SELECT * FROM prod_compra WHERE id_compra=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
        }
        return retorno;
    }

    public List<Produto> selecionarPorVenda(int id) {
        List<Produto> produtos = null;
        String sql;
        sql = "SELECT * FROM produto p "
                + " INNER JOIN prod_venda ve ON ve.id_produto = p.id_produto "
                + "WHERE ve.id_venda  = ? "
                + "order by nome asc";
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactoryPorVenda(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }

    public List<Produto> selecionarForaVenda(int id) {
        List<Produto> produtos = null;
        String sql = "SELECT * FROM produto " 
                + "WHERE id_produto NOT IN (SELECT id_produto FROM prod_venda "
                + "WHERE id_venda = ?)";
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }

    public List<Produto> selecionarForaVenda(String idProdutos) {
        List<Produto> produtos = null;
        String sql = "SELECT * FROM produto "
                + "WHERE id_produto NOT IN ("+idProdutos +")"; 
        try {
            stmt = conn.getPreparedStatement(sql);
            produtos = new ArrayList<Produto>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(objectFactory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }
    
    
}
