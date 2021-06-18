package br.com.covale.dao;

import br.com.covale.managedBean.VendaMB;
import br.com.covale.modelo.Produto;
import br.com.covale.modelo.Venda;
import br.com.covale.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDao {

    private Conexao conn;
    
    private PreparedStatement stmt;
    
    public VendaDao() {
        
        conn = Conexao.getInstance();
        stmt = null; 

    }

    public boolean inserir(Venda ve) { 

        String sql = "INSERT INTO venda(data,valor,nfv,id_condpag,id_formapag,"
                + "id_cliente,id_vendedor) VALUES (?,?,?,?,?,?,?) " 
                + "returning id_venda "; 
        boolean retorno = false;
        try {
            stmt = conn.getPreparedStatement(sql); 
            stmt.setString(1, ve.getData()); 
            stmt.setString(2, ve.getValor());
            stmt.setString(3, ve.getNfv());
            stmt.setInt(4, ve.getCondpag().getId_condpag());
            stmt.setInt(5, ve.getFormapag().getId_formapag());
            stmt.setInt(6, ve.getCliente().getId_cliente());
            stmt.setInt(7, ve.getVendedor().getId_vendedor());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { 
                for (int i = 0; i < ve.getProdutos().size(); i++) {
                    String sql2 = "INSERT INTO prod_venda(id_venda, id_produto, qtde) VALUES(?,?,?);";
                    stmt = conn.getPreparedStatement(sql2);
                    stmt.setInt(1, rs.getInt("id_venda"));
                    stmt.setInt(2, ve.getProdutos().get(i).getId_produto());
                    stmt.setString(3, ve.getProdutos().get(i).getQuantidadeVenda());
                    stmt.execute();
                    subQtde(ve.getProdutos().get(i)); 
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

    public boolean update(Venda ve, List<Produto> pd) { 

        apagarProduto(ve.getId_venda(), pd);
        String sql; 
        sql = "UPDATE venda SET data=?, valor=?, nfv=?, id_condpag=?, " 
                + "id_formapag=?, id_cliente=?, id_vendedor=? WHERE id_venda=?";
        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql); 
            stmt.setString(1, ve.getData());
            stmt.setString(2, ve.getValor());
            stmt.setString(3, ve.getNfv());
            stmt.setInt(4, ve.getCondpag().getId_condpag());
            stmt.setInt(5, ve.getFormapag().getId_formapag());
            stmt.setInt(6, ve.getCliente().getId_cliente());
            stmt.setInt(7, ve.getVendedor().getId_vendedor());
            stmt.setInt(8, ve.getId_venda());
            stmt.execute();

            for (Produto p : ve.getProdutos()) {
                String sql2 = "insert into prod_compra (id_compra, id_produto, qtde) "
                        + "select ?,?,? where not exists (select 1 from prod_compra "
                        + "where id_compra = ? and id_produto = ?)";

                stmt = conn.getPreparedStatement(sql2);
                stmt.setInt(1, ve.getId_venda());
                stmt.setInt(2, p.getId_produto());
                stmt.setString(3, p.getQuantidadeVenda());
                stmt.setInt(4, ve.getId_venda());
                stmt.setInt(5, p.getId_produto());
                stmt.execute();

                subQtde(p);

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

    public boolean delete(int id_venda) {

        String sql = "DELETE FROM venda WHERE id_venda=?";
        String sql1 = "DELETE FROM prod_venda WHERE id_venda = ?";

        boolean retorno = false;
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id_venda);
            stmt.execute();

            stmt = conn.getPreparedStatement(sql1);
            stmt.setInt(1, id_venda);
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

    public Venda objectFactory(ResultSet rs) { 

        Venda ve = null;
        CondpagDao condpagDao = new CondpagDao();
        FormapagDao formapagDao = new FormapagDao();
        ClienteDao clienteDao = new ClienteDao();
        VendedorDao vendedorDao = new VendedorDao();

        ProdutoDao veDao = new ProdutoDao();

        try {
            
            ve = new Venda(rs.getInt("id_venda"), rs.getString("data"),
                    rs.getString("valor"), rs.getString("nfv"),                    
                    condpagDao.selecionarUm(rs.getInt("id_condpag")),
                    formapagDao.selecionarUm(rs.getInt("id_formapag")),
                    clienteDao.selecionarUm(rs.getInt("id_cliente")),
                    vendedorDao.selecionarUm(rs.getInt("id_vendedor")),
                    veDao.selecionarPorVenda(rs.getInt("id_venda")));             

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return ve;
        }
    }

    public List<Venda> selecionarTodos() {

        List<Venda> vendas = null;
        String sql = "SELECT * FROM venda order by data asc";
        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            vendas = new ArrayList<Venda>(); 
            ResultSet rs = stmt.executeQuery(); 

            while (rs.next()) {
                vendas.add(objectFactory(rs)); 
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return vendas;
        }
    }

    public List<Venda> selecionarBusca(Venda ve) {
        List<Venda> vendas = new ArrayList<Venda>();
        String sql = "SELECT ve.id_venda, ve.data, ve.valor, ve.nfv, "
                + "ve.id_condpag, ve.id_formapag, ve.id_cliente, "
                + "ve.id_vendedor FROM venda ve "
                + "INNER JOIN condpag ON condpag.id_condpag = ve.id_condpag "
                + "INNER JOIN formapag ON formapag.id_formapag = ve.id_formapag "
                + "INNER JOIN cliente ON cliente.id_cliente = ve.id_cliente " 
                + "INNER JOIN vendedor ON vendedor.id_vendedor = ve.id_vendedor "
                + "WHERE UPPER(ve.data) like ? AND UPPER(ve.valor) like ? "
                + "AND UPPER(ve.nfv) like ? "
                + "AND UPPER(condpag.descricao) like ? "
                + "AND UPPER(formapag.descricao) like ? "
                + "AND UPPER(cliente.nome) like ? "
                + "AND UPPER(vendedor.nome) like ? "
                + " order by data asc";

        try {
            conn.conectar();
            stmt = conn.getPreparedStatement(sql);
            stmt.setString(1, "%" + ve.getData().toUpperCase() + "%");
            stmt.setString(2, "%" + ve.getValor().toUpperCase() + "%");
            stmt.setString(3, "%" + ve.getNfv().toUpperCase() + "%");
            stmt.setString(4, "%" + ve.getCondpag().getDescricao().toUpperCase() + "%");
            stmt.setString(5, "%" + ve.getFormapag().getDescricao().toUpperCase() + "%");
            stmt.setString(6, "%" + ve.getCliente().getNome().toUpperCase() + "%");
            stmt.setString(7, "%" + ve.getVendedor().getNome().toUpperCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vendas.add(objectFactory(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.desconectar();
            stmt = null;
            return vendas;
        }
    }

    public Venda selecionarUm(int id) {
        Venda venda = null;
        String sql = "SELECT * FROM venda WHERE id_venda=?";       

        try {            
            stmt = conn.getPreparedStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { 
                venda = objectFactory(rs);
            }
            //stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {            
            return venda;
        }
    }

    public boolean apagarProduto(int id_produto, List<Produto> pd) {
        boolean retorno = false;
        try {
            conn.conectar();
            for (Produto p : pd) {   
                String sql = "DELETE FROM prod_venda WHERE id_venda = ? and id_produto = ?";
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
	
    public boolean subQtde(Produto p) { 
        boolean returno = false; 

        Produto prod = new Produto();
        ProdutoDao pdDao = new ProdutoDao(); 
        prod = pdDao.selecionarUm(p.getId_produto()); 
        Double d1 = Double.parseDouble(prod.getQuantidade());
        Double d2 = Double.parseDouble(p.getQuantidadeVenda());
        d1 = d1 - d2;
        p.setQuantidade(d1.toString());
        pdDao.update(p);
        return returno;
    }
}
