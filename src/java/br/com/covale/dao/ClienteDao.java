package br.com.covale.dao;

import br.com.covale.modelo.Cliente;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    
    private Conexao conn; 
   
    private PreparedStatement stmt;
    
    public ClienteDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Cliente c) {

        String sql = "INSERT INTO cliente(nome,pessoa,doc,tipo,endereco,bairro,cep,"
                + "telefone,fonecom,fonecel,email,id_cidade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getPessoa());
            stmt.setString(3, c.getDoc());
            stmt.setString(4, c.getTipo());
            stmt.setString(5, c.getEndereco());
            stmt.setString(6, c.getBairro());
            stmt.setString(7, c.getCep());
            stmt.setString(8, c.getTelefone());
            stmt.setString(9, c.getFonecom());
            stmt.setString(10, c.getFonecel());
            stmt.setString(11, c.getEmail());
            stmt.setInt(12, c.getCidade().getId_cidade());
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

    public boolean update(Cliente c) {

        String sql; 
        sql = "UPDATE cliente SET nome=?, pessoa=?, doc=?, tipo=?, endereco=?, bairro=?, cep=?, "
                + "telefone=?, fonecom=?, fonecel=?, email=?, id_cidade=? WHERE id_cliente=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getPessoa());
            stmt.setString(3, c.getDoc());
            stmt.setString(4, c.getTipo());
            stmt.setString(5, c.getEndereco());
            stmt.setString(6, c.getBairro());
            stmt.setString(7, c.getCep());
            stmt.setString(8, c.getTelefone());
            stmt.setString(9, c.getFonecom());
            stmt.setString(10, c.getFonecel());
            stmt.setString(11, c.getEmail());
            stmt.setInt(12, c.getCidade().getId_cidade());
            stmt.setInt(13, c.getId_cliente());
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

    public boolean delete(int id_cliente) {

        String sql = "DELETE FROM cliente WHERE id_cliente=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_cliente);
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

    public Cliente objectFactory(ResultSet rs) {

        Cliente c = null;
        CidadeDao cidadeDao = new CidadeDao();
        try {
            c = new Cliente(rs.getInt("id_cliente"), rs.getString("nome"),
                    rs.getString("pessoa"), rs.getString("doc"), rs.getString("tipo"),
                    rs.getString("endereco"), rs.getString("bairro"), rs.getString("cep"),
                    rs.getString("telefone"), rs.getString("fonecom"),
                    rs.getString("fonecel"), rs.getString("email"),
                    cidadeDao.selecionarUm(rs.getInt("id_cidade")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return c;
        }
    }

    public List<Cliente> selecionarTodos() {

        List<Cliente> clientes = null;
        String sql = "SELECT * FROM cliente order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            clientes = new ArrayList<Cliente>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clientes.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return clientes;
        }
    }

    public List<Cliente> selecionarBusca(Cliente c) {

        List<Cliente> clientes = new ArrayList<Cliente>();
        String sql = "SELECT c.id_cliente, c.nome, c.pessoa, c.doc, c.tipo, "
                + "c.endereco, c.bairro, c.cep, c.telefone, c.fonecom, c.fonecel, "
                + "c.email,  c.id_cidade FROM cliente c "
                + "INNER JOIN cidade ON cidade.id_cidade = c.id_cidade "
                + "WHERE UPPER(c.nome) like ?  AND UPPER(c.pessoa) like ? "
                + "AND UPPER(c.doc) like ? AND UPPER(c.tipo) like ? "
                + "AND UPPER(c.endereco) like ?  AND UPPER(c.bairro) like ?"
                + "AND UPPER(c.cep) like ? AND UPPER(c.telefone) like ? "
                + "AND UPPER(c.fonecom) like ? AND UPPER(c.fonecel) like ? "
                + "AND UPPER(c.email) like ? "
                + "AND UPPER(cidade.nome) like ? ";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + c.getNome().toUpperCase() + "%");
            stmt.setString(2, "%");
            stmt.setString(3, "%" + c.getDoc().toUpperCase() + "%");
            stmt.setString(4, "%" + c.getTipo().toUpperCase() + "%");
            stmt.setString(5, "%" + c.getEndereco().toUpperCase() + "%");
            stmt.setString(6, "%" + c.getBairro().toUpperCase() + "%");
            stmt.setString(7, "%" + c.getCep().toUpperCase() + "%");
            stmt.setString(8, "%" + c.getTelefone().toUpperCase() + "%");
            stmt.setString(9, "%" + c.getFonecom().toUpperCase() + "%");
            stmt.setString(10, "%" + c.getFonecel().toUpperCase() + "%");
            stmt.setString(11, "%" + c.getEmail().toUpperCase() + "%");
            stmt.setString(12, "%" + c.getCidade().getNome().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return clientes;
        }
    }

    public Cliente selecionarUm(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id_cliente=?";

        try {           
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = objectFactory(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {           
            return cliente;
        }
    }
}
