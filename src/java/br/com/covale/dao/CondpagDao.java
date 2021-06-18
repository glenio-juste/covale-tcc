package br.com.covale.dao;

import br.com.covale.modelo.Condpag;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CondpagDao {
    
    private Conexao conn;
    
    private PreparedStatement stmt;
    
    public CondpagDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Condpag cp) {

        String sql = "INSERT INTO condpag(descricao) VALUES (?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, cp.getDescricao());
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

    public boolean update(Condpag cp) {

        String sql; 
        sql = "UPDATE condpag SET descricao=? WHERE id_condpag=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, cp.getDescricao());
            stmt.setInt(2, cp.getId_condpag());
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

    public boolean delete(int id_condpag) {

        String sql = "DELETE FROM condpag WHERE id_condpag=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_condpag);
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

    public Condpag objectFactory(ResultSet rs) { 

        Condpag cp = null;
        try {
            cp = new Condpag(rs.getInt("id_condpag"), rs.getString("descricao"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cp;
        }
    }

    public List<Condpag> selecionarTodos() {

        List<Condpag> condpags = null;
        String sql = "SELECT * FROM condpag order by descricao asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            condpags = new ArrayList<Condpag>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                condpags.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return condpags;
        }
    }

    public List<Condpag> selecionarBusca(Condpag condpag) {
        List<Condpag> condpags = new ArrayList<Condpag>();
        String sql = "SELECT * FROM condpag WHERE UPPER(descricao) like ? "
                + "order by descricao asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + condpag.getDescricao().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                condpags.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return condpags;
        }
    }

    public Condpag selecionarUm(int id) {
        Condpag condpag = null;
        String sql = "SELECT * FROM condpag WHERE id_condpag=?";

        try {            
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                condpag = objectFactory(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            return condpag;
        }
    }
}
