package br.com.covale.dao;

import br.com.covale.modelo.Vendedor;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendedorDao {
    
    private Conexao conn;
   
    private PreparedStatement stmt;
    
    public VendedorDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Vendedor v) {

        String sql = "INSERT INTO vendedor(nome,rg,cpf,endereco,bairro,telefone,"
                + "fonecel,adm,salario,email,id_cidade) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, v.getNome());
            stmt.setString(2, v.getRg());
            stmt.setString(3, v.getCpf());
            stmt.setString(4, v.getEndereco());
            stmt.setString(5, v.getBairro());
            stmt.setString(6, v.getTelefone());
            stmt.setString(7, v.getFonecel());
            stmt.setString(8, v.getAdm());
            stmt.setString(9, v.getSalario());
            stmt.setString(10, v.getEmail());
            stmt.setInt(11, v.getCidade().getId_cidade());
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

    public boolean update(Vendedor v) {

        String sql; 
        sql = "UPDATE vendedor SET nome=?, rg=?, cpf=?, endereco=?, bairro=?, telefone=?, "
                + "fonecel=?, adm=?, salario=?, email=?, id_cidade=? WHERE id_vendedor=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, v.getNome());
            stmt.setString(2, v.getRg());
            stmt.setString(3, v.getCpf());
            stmt.setString(4, v.getEndereco());
            stmt.setString(5, v.getBairro());
            stmt.setString(6, v.getTelefone());
            stmt.setString(7, v.getFonecel());
            stmt.setString(8, v.getAdm());
            stmt.setString(9, v.getSalario());
            stmt.setString(10, v.getEmail());
            stmt.setInt(11, v.getCidade().getId_cidade());
            stmt.setInt(12, v.getId_vendedor());
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

    public boolean delete(int id_vendedor) {

        String sql = "DELETE FROM vendedor WHERE id_vendedor=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_vendedor);
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

    public Vendedor objectFactory(ResultSet rs) {

        Vendedor v = null;
        CidadeDao cidadeDao = new CidadeDao();
        try {
            v = new Vendedor(rs.getInt("id_vendedor"), rs.getString("nome"), rs.getString("rg"),
                    rs.getString("cpf"), rs.getString("endereco"), rs.getString("bairro"),
                    rs.getString("telefone"), rs.getString("fonecel"), rs.getString("adm"),
                    rs.getString("salario"), rs.getString("email"),
                    cidadeDao.selecionarUm(rs.getInt("id_cidade")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return v;
        }
    }

    public List<Vendedor> selecionarTodos() {

        List<Vendedor> vendedores = null;
        String sql = "SELECT * FROM vendedor order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            vendedores = new ArrayList<Vendedor>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vendedores.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return vendedores;
        }
    }

    public List<Vendedor> selecionarBusca(Vendedor v) {

        List<Vendedor> vendedores = new ArrayList<Vendedor>();
        String sql = "SELECT v.id_vendedor, v.nome, v.rg, v.cpf, v.endereco, v.bairro, "
                + "v.telefone, v.fonecel, v.adm, v.salario, v.email, "
                + "v.id_cidade FROM vendedor v "
                + "INNER JOIN cidade ON cidade.id_cidade = v.id_cidade "
                + "WHERE UPPER(v.nome) like ? AND UPPER(v.rg) like ? "
                + "AND UPPER(v.cpf) like ? AND UPPER(v.endereco) like ? "
                + "AND UPPER(v.bairro) like ? AND UPPER(v.telefone) like ? "
                + "AND UPPER(v.fonecel) like ? AND UPPER(v.adm) like ? AND UPPER(v.salario) like ? "
                + "AND UPPER(v.email) like ? "
                + "AND UPPER(cidade.nome) like ? ";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + v.getNome().toUpperCase() + "%");
            stmt.setString(2, "%" + v.getRg().toUpperCase() + "%");
            stmt.setString(3, "%" + v.getCpf().toUpperCase() + "%");
            stmt.setString(4, "%" + v.getEndereco().toUpperCase() + "%");
            stmt.setString(5, "%" + v.getBairro().toUpperCase() + "%");
            stmt.setString(6, "%" + v.getTelefone().toUpperCase() + "%");
            stmt.setString(7, "%" + v.getFonecel().toUpperCase() + "%");
            stmt.setString(8, "%" + v.getAdm().toUpperCase() + "%");
            stmt.setString(9, "%" + v.getSalario().toUpperCase() + "%");
            stmt.setString(10, "%" + v.getEmail().toUpperCase() + "%");
            stmt.setString(11, "%" + v.getCidade().getNome().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vendedores.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return vendedores;
        }
    }

    public Vendedor selecionarUm(int id) {
        Vendedor vendedor = null;
        String sql = "SELECT * FROM vendedor WHERE id_vendedor=?";

        try {
            //conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vendedor = objectFactory(rs);
            }
            //stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            return vendedor;
        }
    }
}
