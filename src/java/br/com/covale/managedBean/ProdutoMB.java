package br.com.covale.managedBean;

import br.com.covale.dao.CompraDao;
import br.com.covale.dao.ProdutoDao;
import br.com.covale.dao.VendaDao;
import br.com.covale.modelo.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ProdutoMB {

    private Produto produto = new Produto();
    private List produtos = new ArrayList();

    public ProdutoMB() {

        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();

    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List getProdutos() {
        return produtos;
    }

    public void setProdutos(List produtos) {
        this.produtos = produtos;
    }

    public void cadastrar(ActionEvent actionEvent) {        
        
        ProdutoDao pdDao = new ProdutoDao();        
        
        if (produto.getQuantidade().equals("")) {            
            produto.setQuantidade("0");
        }        
        
        if (pdDao.inserir(produto)) {            
            produtos = pdDao.selecionarTodos();            
            produto = new Produto();            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Inserido com sucesso", ""));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao inserir", "Tente novamente"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        ProdutoDao pdDao = new ProdutoDao();
        if (pdDao.update(produto)) {
            produtos = pdDao.selecionarTodos();
            produto = new Produto();
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

        ProdutoDao pdDao = new ProdutoDao();
        if (pdDao.delete(produto.getId_produto())) {
            produtos = pdDao.selecionarTodos();
            produto = new Produto();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Apagado com sucesso", ""));
        } else {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao apagar", "Tente novamente"));
        }
    }

    public void busca(ActionEvent actionEvent) {
        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarBusca(produto);
        produto = new Produto();
    }

    public void cancelar(ActionEvent actionEvent) {
        produto = new Produto();
    }

    public void limparBusca(ActionEvent actionEvent) {
        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();
    }

    public List getCompra() {
        CompraDao cpDao = new CompraDao();
        return cpDao.selecionarTodos();
    }
    
    public List getVenda() {        
        VendaDao veDao = new VendaDao();        
        return veDao.selecionarTodos();

    }
}
