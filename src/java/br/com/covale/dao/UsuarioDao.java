package br.com.covale.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.covale.modelo.Usuario;

public class UsuarioDao {

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/covale", "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Usuario getUsuario(String login, String senha) {
        Connection c = this.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement("select id, nome from usuario where login = ? and senha = ?");
            ps.setString(1, login);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setLogin(login);
                user.setSenha(senha);
                user.setNome(rs.getString("nome"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {;
                }
                rs = null;
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {;
                }
                ps = null;
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {;
                }
                c = null;
            }
        }
        return null;
    }
}
