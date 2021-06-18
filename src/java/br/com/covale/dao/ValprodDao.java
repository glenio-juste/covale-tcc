package br.com.covale.dao;

import br.com.covale.modelo.Valprod;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ValprodDao {

    private Conexao conn;
    private PreparedStatement stmt;

    public ValprodDao() {

        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Valprod vp) {

        String sql = "INSERT INTO val_prod(data_compra,lote,data_validade,"
                + "verifica, id_produto) VALUES (?,?,?,?,?)";

        boolean retorno = false;

        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, vp.getData_compra());
            stmt.setString(2, vp.getLote());
            stmt.setString(3, vp.getData_validade());
            stmt.setString(4, vp.getVerifica());
            stmt.setInt(5, vp.getProduto().getId_produto());

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

    public boolean update(Valprod vp) {

        String sql;
        sql = "UPDATE val_prod SET data_compra=?, lote=?, data_validade=?, "
                + "verifica=?, id_produto=? WHERE id_val_prod=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, vp.getData_compra());
            stmt.setString(2, vp.getLote());
            stmt.setString(3, vp.getData_validade());
            stmt.setString(4, vp.getVerifica());
            stmt.setInt(5, vp.getProduto().getId_produto());
            stmt.setInt(6, vp.getId_val_prod());
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

    public boolean delete(int id_val_prod) {

        String sql = "DELETE FROM val_prod WHERE id_val_prod=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_val_prod);
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

    public Valprod objectFactory(ResultSet rs) { 

        Valprod vp = null;
        ProdutoDao produtoDao = new ProdutoDao();
        try {
            vp = new Valprod(rs.getInt("id_val_prod"), rs.getString("data_compra"),
                    rs.getString("lote"), rs.getString("data_validade"),
                    rs.getString("verifica"),
                    produtoDao.selecionarUm(rs.getInt("id_produto")));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return vp;
        }
    }

    public List<Valprod> selecionarTodos() {

        List<Valprod> valprods = null;
        String sql = "SELECT * FROM val_prod order by lote asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            valprods = new ArrayList<Valprod>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                valprods.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return valprods;
        }
    }

    public List<Valprod> selecionarBusca(Valprod valprod) {

        List<Valprod> valprods = new ArrayList<Valprod>();

        String sql = "SELECT vp.id_val_prod, vp.data_compra, vp.lote, "
                + "vp.data_validade, vp.id_produto, vp.verifica FROM val_prod vp "
                + "INNER JOIN produto ON produto.id_produto = vp.id_produto "
                + "WHERE UPPER(vp.data_compra) like ? "
                + "AND UPPER(vp.lote) like ? AND UPPER(vp.data_validade) like ? "
                + "AND UPPER(vp.verifica) like ? "
                + "AND UPPER(produto.nome) like ? "
                + "order by data_compra asc";

        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + valprod.getData_compra().toUpperCase() + "%");
            stmt.setString(2, "%" + valprod.getLote().toUpperCase() + "%");
            stmt.setString(3, "%" + valprod.getData_validade().toUpperCase() + "%");
            stmt.setString(4, "%" + valprod.getVerifica().toUpperCase() + "%");
            stmt.setString(5, "%" + valprod.getProduto().getNome().toUpperCase() + "%");
            System.out.println(valprod.getProduto().getNome());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                valprods.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return valprods;
        }
    }

    public Valprod selecionarUm(int id) {
        Valprod valprod = null;
        String sql = "SELECT * FROM val_prod WHERE id_val_prod=?";
       
        try {            
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                valprod = objectFactory(rs);
            }
            //stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            return valprod;
        }
    }
    
    public List<Valprod> selecionarTodos(int id) {
        List<Valprod> listValprod = null;
        String sql = "SELECT * FROM val_prod WHERE id_val_prod=?";

        try {            
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                listValprod.add(objectFactory(rs));
            }
            //stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return listValprod;
        }
    }
}
