package br.com.covale.dao;

import br.com.covale.modelo.Formapag;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormapagDao {
    
    private Conexao conn;
    
    private PreparedStatement stmt;
    
    public FormapagDao() {

        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Formapag fp) {

        String sql = "INSERT INTO formapag(descricao) VALUES (?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, fp.getDescricao());
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

    public boolean update(Formapag fp) {

        String sql; 
        sql = "UPDATE formapag SET descricao=? WHERE id_formapag=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, fp.getDescricao());
            stmt.setInt(2, fp.getId_formapag());
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

    public boolean delete(int id_formapag) {

        String sql = "DELETE FROM formapag WHERE id_formapag=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_formapag);
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

    public Formapag objectFactory(ResultSet rs) { 

        Formapag fp = null;
        try {
            fp = new Formapag(rs.getInt("id_formapag"), rs.getString("descricao"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return fp;
        }
    }

    public List<Formapag> selecionarTodos() {

        List<Formapag> formapags = null;
        String sql = "SELECT * FROM formapag order by descricao asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            formapags = new ArrayList<Formapag>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                formapags.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return formapags;
        }
    }

    public List<Formapag> selecionarBusca(Formapag formapag) {
        List<Formapag> formapags = new ArrayList<Formapag>();
        String sql = "SELECT * FROM formapag WHERE UPPER(descricao) like ? "
                + "order by descricao asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + formapag.getDescricao().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                formapags.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return formapags;
        }
    }

    public Formapag selecionarUm(int id) {
        Formapag formapag = null;
        String sql = "SELECT * FROM formapag WHERE id_formapag=?";        

        try {           
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                formapag = objectFactory(rs);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            return formapag;
        }
    }
}
