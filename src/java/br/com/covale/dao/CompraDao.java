package br.com.covale.dao;

import br.com.covale.modelo.Compra;
import br.com.covale.modelo.Produto;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDao {
   
    private Conexao conn;
   
    private PreparedStatement stmt;
    
    public CompraDao() {
        
        conn = Conexao.getInstance();
        stmt = null;

    }

    public boolean inserir(Compra cp) {
        String sql = "INSERT INTO compra(data,valor,nf, id_condpag, id_formapag, "
                + "id_fornecedor) VALUES (?,?,?,?,?,?) returning id_compra ";
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, cp.getData());
            stmt.setString(2, cp.getValor());
            stmt.setString(3, cp.getNf());
            stmt.setInt(4, cp.getCondpag().getId_condpag());
            stmt.setInt(5, cp.getFormapag().getId_formapag());
            stmt.setInt(6, cp.getFornecedor().getId_fornecedor());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                for (int i = 0; i < cp.getProdutos().size(); i++) {
                    String sql2 = "INSERT INTO prod_compra(id_compra, id_produto) VALUES(?,?);";
                    stmt = conn.getPreparedStatement(sql2);
                    stmt.setInt(1, rs.getInt("id_compra"));
                    stmt.setInt(2, cp.getProdutos().get(i).getId_produto());
                    stmt.execute();
                    adcQtde(cp.getProdutos().get(i));
                }
            }


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

    public boolean update(Compra cp, List<Produto> pd) {

        apagarProduto(cp.getId_compra(), pd);
        String sql; 
        sql = "UPDATE compra SET data=?, valor=?, nf=?, id_condpag=?, "
                + "id_formapag=?, id_fornecedor=? WHERE id_compra=?";
        boolean retorno = false;  
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, cp.getData());
            stmt.setString(2, cp.getValor());
            stmt.setString(3, cp.getNf());
            stmt.setInt(4, cp.getCondpag().getId_condpag());
            stmt.setInt(5, cp.getFormapag().getId_formapag());
            stmt.setInt(6, cp.getFornecedor().getId_fornecedor());
            stmt.setInt(7, cp.getId_compra());
            stmt.execute();

            for (Produto p : cp.getProdutos()) {
                String sql2 = "insert into prod_compra (id_compra, id_produto) "
                        + "select ?,? where not exists (select 1 from prod_compra "
                        + "where id_compra = ? and id_produto = ?)";

                stmt = conn.getPreparedStatement(sql2);
                stmt.setInt(1, cp.getId_compra());
                stmt.setInt(2, p.getId_produto());
                stmt.setInt(3, cp.getId_compra());
                stmt.setInt(4, p.getId_produto());
                stmt.execute();

                adcQtde(p);
            }

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

    public boolean delete(int id_compra) {

        String sql = "DELETE FROM compra WHERE id_compra=?";
        String sql1 = "DELETE FROM prod_compra WHERE id_compra=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_compra);
            stmt.execute();

            stmt = conn.getPreparedStatement(sql1);
            stmt.setInt(1, id_compra);
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

    public Compra objectFactory(ResultSet rs) { 

        Compra cp = null;
        CondpagDao condpagDao = new CondpagDao();
        FormapagDao formapagDao = new FormapagDao();
        FornecedorDao fornecedorDao = new FornecedorDao();

        ProdutoDao pdDao = new ProdutoDao();

        try {

            cp = new Compra(rs.getInt("id_compra"), rs.getString("data"),
                    rs.getString("valor"), rs.getString("nf"),
                    condpagDao.selecionarUm(rs.getInt("id_condpag")),
                    formapagDao.selecionarUm(rs.getInt("id_formapag")),
                    fornecedorDao.selecionarUm(rs.getInt("id_fornecedor")),
                    pdDao.selecionarPorCompra(rs.getInt("id_compra")));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cp;
        }
    }

    public List<Compra> selecionarTodos() {

        List<Compra> compras = null;
        String sql = "SELECT * FROM compra order by data asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            compras = new ArrayList<Compra>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                compras.add(objectFactory(rs));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return compras;
        }
    }

    public Compra selecionarUm(int id_compra) {
        Compra compra = null;
        String sql = "SELECT * FROM compra WHERE id_compra=?";

        try {           
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_compra);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                compra = objectFactory(rs);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {           
            return compra;
        }
    }

    public List<Compra> selecionarBusca(Compra cp) {
        List<Compra> compras = new ArrayList<Compra>();
        String sql = "SELECT cp.id_compra, cp.data, cp.valor, cp.nf, "
                + "cp.id_condpag, cp.id_formapag, cp.id_fornecedor FROM compra cp "
                + "INNER JOIN condpag ON condpag.id_condpag = cp.id_condpag "
                + "INNER JOIN formapag ON formapag.id_formapag = cp.id_formapag "
                + "INNER JOIN fornecedor ON fornecedor.id_fornecedor = cp.id_fornecedor "
                + " WHERE UPPER(cp.data) like ? AND UPPER(cp.valor) like ? "
                + "AND UPPER(cp.nf) like ? "
                + "AND UPPER(condpag.descricao) like ? "
                + "AND UPPER(formapag.descricao) like ? "
                + "AND UPPER(fornecedor.nome) like ? "
                + "order by data asc";

        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + cp.getData().toUpperCase() + "%");
            stmt.setString(2, "%" + cp.getValor().toUpperCase() + "%");
            stmt.setString(3, "%" + cp.getNf().toUpperCase() + "%");
            stmt.setString(4, "%" + cp.getCondpag().getDescricao().toUpperCase() + "%");
            stmt.setString(5, "%" + cp.getFormapag().getDescricao().toUpperCase() + "%");
            stmt.setString(6, "%" + cp.getFornecedor().getNome().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                compras.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return compras;
        }
    }

    public boolean apagarProduto(int id_produto, List<Produto> pd) {
        boolean retorno = false;
        try {
            conn.conectar();
            for (Produto p : pd) {    
                String sql = "DELETE FROM prod_compra WHERE id_compra = ? and id_produto = ?";
                stmt = conn.getPreparedStatement(sql);
                stmt.setInt(1, id_produto);
                stmt.setInt(2, p.getId_produto());
                stmt.execute();
            }
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
	
    public boolean adcQtde(Produto p) {
        boolean returno = false;

        Produto prod = new Produto();
        ProdutoDao pdDao = new ProdutoDao();
        prod = pdDao.selecionarUm(p.getId_produto());
        Double d1 = Double.parseDouble(prod.getQuantidade());
        Double d2 = Double.parseDouble(p.getQuantidade());
        d1 = d1 + d2;
        p.setQuantidade(d1.toString());
        pdDao.update(p);
        return returno;
    }
}
