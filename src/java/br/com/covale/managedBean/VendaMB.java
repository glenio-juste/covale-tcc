package br.com.covale.managedBean;

import br.com.covale.dao.CaixaDao;
import br.com.covale.dao.ClienteDao;
import br.com.covale.dao.CondpagDao;
import br.com.covale.dao.FormapagDao;
import br.com.covale.dao.ProdutoDao;
import br.com.covale.dao.VendaDao;
import br.com.covale.dao.VendedorDao;
import br.com.covale.modelo.Caixa;
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
public class VendaMB {

    private Venda venda = new Venda();
    private List vendas = new ArrayList();
    private Produto produto = new Produto();
    private List<Produto> produtos = new ArrayList<Produto>();
    private List<Produto> vaux = new ArrayList<Produto>();
    private Double dinheiro;
    private Double troco;

    public VendaMB() {
 
        VendaDao veDao = new VendaDao();
        vendas = veDao.selecionarTodos();

        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();

        dinheiro = new Double(0.0);
        troco = new Double(0.0);

    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List getVendas() {
        return vendas;
    }

    public void setVendas(List vendas) {
        this.vendas = vendas;
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

    public List<Produto> getVaux() {
        return vaux;
    }

    public void setVaux(List<Produto> vaux) {
        this.vaux = vaux;
    }

    public Double getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(Double dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public void cadastrar(ActionEvent actionEvent) { 

        VendaDao veDao = new VendaDao();
        if (veDao.inserir(venda)) { 
            vendas = veDao.selecionarTodos();
            ProdutoDao pdDao = new ProdutoDao();
            produtos = pdDao.selecionarTodos();

            Caixa caixa = new Caixa(-1, venda.getData(), "Venda", "Entrada", venda.getValor());
            CaixaDao cxDao = new CaixaDao();
            cxDao.inserir(caixa);

            venda = new Venda();

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
        VendaDao veDao = new VendaDao();
        if (veDao.update(venda, vaux)) {
            vendas = veDao.selecionarTodos();
            venda = new Venda();

            ProdutoDao pdDao = new ProdutoDao();
            produtos = pdDao.selecionarTodos();
            vaux = new ArrayList<Produto>();

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
        VendaDao veDao = new VendaDao();

        ProdutoDao pdDao = new ProdutoDao();

        if (veDao.delete(venda.getId_venda())) {
            vendas = veDao.selecionarTodos();
            venda = new Venda();
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

        VendaDao veDao = new VendaDao();
        vendas = veDao.selecionarBusca(venda);
        venda = new Venda();

    }

    public void cancelar(ActionEvent actionEvent) {
        venda = new Venda();

        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarTodos();
        vaux = new ArrayList<Produto>();

    }

    public void limparBusca(ActionEvent actionEvent) {
        VendaDao veDao = new VendaDao();
        vendas = veDao.selecionarTodos();
    }

    public List getCondpag() {
        CondpagDao veDao = new CondpagDao();
        return veDao.selecionarTodos();
    }

    public List getFormapag() {
        FormapagDao veDao = new FormapagDao();
        return veDao.selecionarTodos();
    }

    public List getCliente() {
        ClienteDao veDao = new ClienteDao();
        return veDao.selecionarTodos();
    }

    public List getVendedor() {
        VendedorDao veDao = new VendedorDao();
        return veDao.selecionarTodos();
    }

    public void removeProduto(Produto pd) {
        venda.getProdutos().remove(pd);
        produtos.add(pd);
        vaux.add(pd);
    }

    public void addProduto(ActionEvent actionEvent) {

        if (Double.parseDouble(produto.getQuantidade()) - Double.parseDouble(produto.getQuantidadeVenda()) < 0.0) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Não há quantidade de produto suficiente no estoque", ""));
        } else {

            venda.getProdutos().add(produto);

            try {
                Double valor = Double.parseDouble(venda.getValor())
                        + (produto.getVal_unit() * Double.parseDouble(produto.getQuantidadeVenda()));

                venda.setValor(valor.toString());                
            } catch (NumberFormatException e) {
                Double valor = 0 + (produto.getVal_unit() * Double.parseDouble(produto.getQuantidadeVenda()));

                venda.setValor(valor.toString());
            }

            produto = new Produto();

        }

    }

    public void TrocoVenda(ActionEvent actionEvent) {
        troco = dinheiro - Double.parseDouble(venda.getValor());
    }

    public void produtosNotVen(Venda ven) {
        ProdutoDao pdDao = new ProdutoDao();
        produtos = pdDao.selecionarForaVenda(ven.getId_venda());
    }
 
    public void produtosNotVen() {
        ProdutoDao pdDao = new ProdutoDao(); 
        String ids = ""; 
 
        for (Produto p : venda.getProdutos()) {
            ids += (ids.length() > 0 ? "," : "") + p.getId_produto();
        }
    
        if (ids.length() > 0) {
            produtos = pdDao.selecionarForaVenda(ids); 
        } else {
            produtos = pdDao.selecionarTodos(); 
        }
    }

    public void edit() {
        venda = new Venda();
        dinheiro = new Double(0.0);
        troco = new Double(0.0);
    }
}
