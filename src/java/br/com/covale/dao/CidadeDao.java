package br.com.covale.dao;

import br.com.covale.modelo.Cidade;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDao {    
     
    private Conexao conn;     
    
    private PreparedStatement stmt;   

    public CidadeDao() {        
        
        conn = Conexao.getInstance();
        stmt = null;
                     
    }
    
    public boolean inserir(Cidade cd){
    
        String sql = "INSERT INTO cidade(nome,estado) VALUES (?,?)";
                boolean retorno = false;
                try {
                    stmt = conn.getPreparedStatement(sql);
                    stmt.setString(1, cd.getNome());
                    stmt.setString(2, cd.getEstado());
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
    
    public boolean update (Cidade cd){
        
        String sql; 
        sql = "UPDATE cidade SET nome=?, estado=? WHERE id_cidade=?";
                boolean retorno = false;
                try {
                    conn.conectar();
                    stmt = conn.getPreparedStatement(sql);
                    stmt.setString(1, cd.getNome());
                    stmt.setString(2, cd.getEstado());
                    stmt.setInt(3, cd.getId_cidade());
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
    
    public boolean delete (int id_cidade){
        
        String sql = "DELETE FROM cidade WHERE id_cidade=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_cidade);
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
    
    public Cidade objectFactory(ResultSet rs){ 
         
        Cidade cd = null;
        try {
            cd = new Cidade(rs.getInt("id_cidade"), rs.getString("nome"), 
                    rs.getString("estado"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cd;
        }
    }
    
    public List<Cidade> selecionarTodos(){
        
        List<Cidade> cidades = null;
        String sql = "SELECT * FROM cidade order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            cidades = new ArrayList<Cidade>();
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                cidades.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return cidades;
        }
    }
    
    public List<Cidade> selecionarBusca(Cidade cidade) {
        List<Cidade> cidades = new ArrayList<Cidade>();
        String sql = "SELECT * FROM cidade WHERE UPPER(nome) like ? "
                + "AND UPPER(estado) like ? order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + cidade.getNome().toUpperCase() + "%");
            stmt.setString(2, "%" + cidade.getEstado().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cidades.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return cidades;
        }
    }
    
    public Cidade selecionarUm(int id) {
        Cidade cidade = null;
        String sql = "SELECT * FROM cidade WHERE id_cidade=?";      
               
        try {         
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cidade = objectFactory(rs);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {           
            return cidade;
        }
    }
    
}
