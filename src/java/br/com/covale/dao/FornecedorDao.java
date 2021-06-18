package br.com.covale.dao;

import br.com.covale.modelo.Fornecedor;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao {
    
    private Conexao conn;
    
    private PreparedStatement stmt;

    public FornecedorDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Fornecedor f) {

        String sql = "INSERT INTO fornecedor(nome,cnpj,endereco,bairro,cep,"
                + "telefone,fonecom,fonecel,email, id_cidade) VALUES (?,?,?,?,?,?,?,?,?,?)";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getEndereco());
            stmt.setString(4, f.getBairro());
            stmt.setString(5, f.getCep());
            stmt.setString(6, f.getTelefone());
            stmt.setString(7, f.getFonecom());
            stmt.setString(8, f.getFonecel());
            stmt.setString(9, f.getEmail());
            stmt.setInt(10, f.getCidade().getId_cidade());
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

    public boolean update(Fornecedor f) {

        String sql; 
        sql = "UPDATE fornecedor SET nome=?, cnpj=?, endereco=?, bairro=?, cep=?, "
                + "telefone=?, fonecom=?, fonecel=?, email=?, id_cidade=? WHERE id_fornecedor=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getEndereco());
            stmt.setString(4, f.getBairro());
            stmt.setString(5, f.getCep());
            stmt.setString(6, f.getTelefone());
            stmt.setString(7, f.getFonecom());
            stmt.setString(8, f.getFonecel());
            stmt.setString(9, f.getEmail());
            stmt.setInt(10, f.getCidade().getId_cidade());
            stmt.setInt(11, f.getId_fornecedor());
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

    public boolean delete(int id_fornecedor) {

        String sql = "DELETE FROM fornecedor WHERE id_fornecedor=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_fornecedor);
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

    public Fornecedor objectFactory(ResultSet rs) {

        Fornecedor f = null;
        CidadeDao cidadeDao = new CidadeDao();
        try {
            f = new Fornecedor(rs.getInt("id_fornecedor"), rs.getString("nome"),
                    rs.getString("cnpj"), rs.getString("endereco"), rs.getString("bairro"),
                    rs.getString("cep"), rs.getString("telefone"), rs.getString("fonecom"),
                    rs.getString("fonecel"), rs.getString("email"),
                    cidadeDao.selecionarUm(rs.getInt("id_cidade")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return f;
        }
    }

    public List<Fornecedor> selecionarTodos() {

        List<Fornecedor> fornecedores = null;
        String sql = "SELECT * FROM fornecedor order by nome asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            fornecedores = new ArrayList<Fornecedor>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                fornecedores.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return fornecedores;
        }
    }

    public List<Fornecedor> selecionarBusca(Fornecedor f) {

        List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
        String sql = "SELECT f.id_fornecedor, f.nome, f.cnpj, f.endereco, "
                + "f.bairro, f.cep, f.telefone, f.fonecom, f.fonecel, f.email,  "
                + "f.id_cidade FROM fornecedor f "
                + "INNER JOIN cidade ON cidade.id_cidade = f.id_cidade "
                + "WHERE UPPER(f.nome) like ? "
                + "AND UPPER(f.cnpj) like ? AND UPPER(f.endereco) like ? "
                + "AND UPPER(f.bairro) like ?"
                + "AND UPPER(f.cep) like ? AND UPPER(f.telefone) like ? "
                + "AND UPPER(f.fonecom) like ? AND UPPER(f.fonecel) like ? "
                + "AND UPPER(f.email) like ? "
                + "AND UPPER(cidade.nome) like ? ";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + f.getNome().toUpperCase() + "%");
            stmt.setString(2, "%" + f.getCnpj().toUpperCase() + "%");
            stmt.setString(3, "%" + f.getEndereco().toUpperCase() + "%");
            stmt.setString(4, "%" + f.getBairro().toUpperCase() + "%");
            stmt.setString(5, "%" + f.getCep().toUpperCase() + "%");
            stmt.setString(6, "%" + f.getTelefone().toUpperCase() + "%");
            stmt.setString(7, "%" + f.getFonecom().toUpperCase() + "%");
            stmt.setString(8, "%" + f.getFonecel().toUpperCase() + "%");
            stmt.setString(9, "%" + f.getEmail().toUpperCase() + "%");
            stmt.setString(10, "%" + f.getCidade().getNome().toUpperCase() + "%");
            System.out.println(f.getCidade().getNome());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fornecedores.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return fornecedores;
        }
    }

    public Fornecedor selecionarUm(int id) {
        Fornecedor fornecedor = null;
        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor=?";

        try {
            //conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fornecedor = objectFactory(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            return fornecedor;
        }
    }
}
