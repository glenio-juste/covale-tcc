package br.com.covale.managedBean;

import br.com.covale.dao.CaixaDao;
import br.com.covale.dao.CompraDao;
import br.com.covale.dao.CondpagDao;
import br.com.covale.dao.FormapagDao;
import br.com.covale.dao.FornecedorDao;
import br.com.covale.dao.ProdutoDao;
import br.com.covale.modelo.Caixa;
import br.com.covale.modelo.Compra;
import br.com.covale.modelo.Produto;
import br.com.covale.modelo.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class CompraMB {

    private Compra compra = new Compra();
    private List compras = new ArrayList();
    private Produto produto = new Produto();
    private List<Produto> produtos = new ArrayList<Produto>();
    private List<Produto> caux = new ArrayList<Produto>();
    private Venda venda;

    public CompraMB() {

        CompraDao cpDao = new CompraDao();
        compras = cpDao.selecionarTodos();

        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();

    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List getCompras() {
        return compras;
    }

    public void setCompras(List compras) {
        this.compras = compras;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getCaux() {
        return caux;
    }

    public void setCaux(List<Produto> caux) {
        this.caux = caux;
    }

    public void cadastrar(ActionEvent actionEvent) {
        
        if (!compra.getData().equals("")) {
            
            CompraDao cpDao = new CompraDao();
            
            if (cpDao.inserir(compra)) {             
                compras = cpDao.selecionarTodos(); 
                ProdutoDao pdDao = new ProdutoDao();
                produtos = pdDao.selecionarTodos();              
               
                Caixa caixa = new Caixa(-1, compra.getData(), "Compra", "Saída", compra.getValor());
                CaixaDao cxDao = new CaixaDao(); 
                cxDao.inserir(caixa); 

                compra = new Compra(); 
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Inserido com sucesso", ""));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erro ao inserir", "Tente novamente"));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Insira a Data (obrigatório) ", "Tente novamente"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        CompraDao cpDao = new CompraDao();
        if (cpDao.update(compra, caux)) {
            compras = cpDao.selecionarTodos();
            compra = new Compra();
            ProdutoDao pdDao = new ProdutoDao();
            produtos = pdDao.selecionarTodos();
            caux = new ArrayList<Produto>();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Alterado com sucesso", ""));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao alterar", "Tente novamente"));
        }
    }

    public void apagar(ActionEvent actionEvent) {
        CompraDao cpDao = new CompraDao();

        ProdutoDao pdDao = new ProdutoDao();

        if (pdDao.verificaCompra(compra.getId_compra())) { 

            if (cpDao.delete(compra.getId_compra())) { 
                compras = cpDao.selecionarTodos();
                compra = new Compra();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Apagado com sucesso", ""));
            }
        } else {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao apagar", "Tente novamente"));
        }
    }

    public void busca(ActionEvent actionEvent) {
        CompraDao cpDao = new CompraDao();
        compras = cpDao.selecionarBusca(compra);
        compra = new Compra();
    }

    public void cancelar(ActionEvent actionEvent) {
        compra = new Compra();

        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();
        caux = new ArrayList<Produto>();

    }

    public void limparBusca(ActionEvent actionEvent) {
        CompraDao cpDao = new CompraDao();
        compras = cpDao.selecionarTodos();
    }

    public List getCondpag() {
        CondpagDao cpDao = new CondpagDao();
        return cpDao.selecionarTodos();
    }

    public List getFormapag() {
        FormapagDao cpDao = new FormapagDao();
        return cpDao.selecionarTodos();
    }

    public List getFornecedor() {
        FornecedorDao cpDao = new FornecedorDao();
        return cpDao.selecionarTodos();
    }
 
    public void removeProduto(Produto pd) {    
        compra.getProdutos().remove(pd);   
        produtos.add(pd); 
        caux.add(pd); 

        produto = new Produto(); 

    }
 
    public void addProduto(ActionEvent actionEvent) {        
        compra.getProdutos().add(produto);

        try {            
            
            Double valor = Double.parseDouble(compra.getValor()) + (Double.parseDouble(produto.getPreco_compra()) * Double.parseDouble(produto.getQuantidade()));
            compra.setValor(valor.toString()); 
        } catch (NumberFormatException e) {
            Double valor = 0 + (Double.parseDouble(produto.getPreco_compra()) * Double.parseDouble(produto.getQuantidade()));
            compra.setValor(valor.toString());
        }
        produto = new Produto();
    }

    public void produtosNotCom(Compra com) {
        ProdutoDao pdDao = new ProdutoDao();         
        produtos = pdDao.selecionarForaCompra(com.getId_compra());
    }
}
