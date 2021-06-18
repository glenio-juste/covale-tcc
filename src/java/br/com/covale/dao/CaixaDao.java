package br.com.covale.dao;

import br.com.covale.modelo.Caixa;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaixaDao {

    private Conexao conn;    
    private PreparedStatement stmt; 
   
    public CaixaDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Caixa cx) {
       
        String sql = "INSERT INTO caixa(data,tipo,op,valor) VALUES (?,?,?,?)";
        boolean retorno = false;
        try {            
            stmt = conn.getPreparedStatement(sql);  
            stmt.setString(1, cx.getData());
            stmt.setString(2, cx.getTipo()); 
            stmt.setString(3, cx.getOp());
            stmt.setString(4, cx.getValor());

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

    public boolean update(Caixa cx) {

        String sql;        
        sql = "UPDATE caixa SET data=?, tipo=?, op=?, valor=? WHERE id_caixa=?"; 
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, cx.getData());
            stmt.setString(2, cx.getTipo());
            stmt.setString(3, cx.getOp());
            stmt.setString(4, cx.getValor());
            stmt.setInt(5, cx.getId_caixa());
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

    public boolean delete(int id_caixa) {

        String sql = "DELETE FROM caixa WHERE id_caixa=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_caixa);
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

    public Caixa objectFactory(ResultSet rs) {        

        Caixa cx = null;
        try {
            cx = new Caixa(rs.getInt("id_caixa"), rs.getString("data"),
                    rs.getString("tipo"), rs.getString("op"), rs.getString("valor"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cx;
        }
    }

    public List<Caixa> selecionarTodos() { 

        List<Caixa> caixas = null;        
        String sql = "SELECT * FROM caixa order by data asc"; 
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            caixas = new ArrayList<Caixa>();
            
            ResultSet rs = stmt.executeQuery(); 

            while (rs.next()) { 
                caixas.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return caixas;
        }
    }

    public List<Caixa> selecionarBusca(Caixa cx) { 
        List<Caixa> caixas = new ArrayList<Caixa>(); 
        String sql = "SELECT * FROM caixa WHERE UPPER(data) like ? " 
                + "AND UPPER(tipo) like ? AND UPPER(op) like ? AND UPPER(valor) like ? "
                + "order by data asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + cx.getData().toUpperCase() + "%"); 
            stmt.setString(2, "%" + cx.getTipo().toUpperCase() + "%"); 
            stmt.setString(3, "%" + cx.getOp().toUpperCase() + "%"); 
            stmt.setString(4, "%" + cx.getValor().toUpperCase() + "%"); 

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                caixas.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return caixas;
        }
    }
   
    public Caixa selecionarUm(int id) { 
        Caixa caixa = null;
        String sql = "SELECT * FROM caixa WHERE id_caixa=?";

        try {            
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) {                  
                caixa = objectFactory(rs);
            }
            
        } catch (SQLException e) {  
            e.printStackTrace();
        } finally {           
            return caixa; 
        }
    }
}
