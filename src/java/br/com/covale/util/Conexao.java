package br.com.covale.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    private static Conexao conexoes = null; 
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static final String DRIVER = "org.postgresql.Driver";
    //private static final String IP = "127.0.0.1";
    private static final String IP = "localhost";
    private static final String PostgreSQL = "jdbc:postgresql://";
    private static final String database = "covale";
    private static final String user = "postgres";
    private static final String password = "0000";

    private Conexao() {
    }
    
    // Padrão Singleton
    public static Conexao getInstance() {        
        if (conexoes == null) {            
            conexoes = new Conexao();
        }        
        return conexoes;
    }
   
    public void conectar() {
        if (conn == null) {
            try {
                String dbPostgree = PostgreSQL + IP + "/" + database;
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(dbPostgree, user, password);
                conn.setAutoCommit(true);
                System.out.println("Conectou!!");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println(conn + " já esta conectado!!!");
        }
    }
    
    public void desconectar() {
        if (conn != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                conn.close();
                conn = null;
                stmt = null;
                System.out.println("Desconectou!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(conn + " esta desconectado!!!");
        }
    }
    
    public PreparedStatement getPreparedStatement(String statement) {
        try {
            if (conn == null) {
                conectar();
            }
            stmt = conn.prepareStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return stmt;
        }
    }
}
