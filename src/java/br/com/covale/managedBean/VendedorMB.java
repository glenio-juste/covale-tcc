package br.com.covale.managedBean;

import br.com.covale.dao.CidadeDao;
import br.com.covale.dao.VendedorDao;
import br.com.covale.modelo.Vendedor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class VendedorMB {

    private Vendedor vendedor = new Vendedor();
    private List vendedores = new ArrayList();

    public VendedorMB() {

        VendedorDao venDao = new VendedorDao();
        vendedores = venDao.selecionarTodos();
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List getVendedores() {
        return vendedores;
    }

    public void setVendedores(List vendedores) {
        this.vendedores = vendedores;
    }

    public void cadastrar(ActionEvent actionEvent) {
        VendedorDao venDao = new VendedorDao();
        if (!vendedor.getNome().equals("") && !vendedor.getFonecel().equals("")) {
            if (venDao.inserir(vendedor)) {
                vendedores = venDao.selecionarTodos();
                vendedor = new Vendedor();
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
                    "Nome e Fone Cel é preenchimento obrigatório", "Tente novamente"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        VendedorDao venDao = new VendedorDao();
        if (venDao.update(vendedor)) {
            vendedores = venDao.selecionarTodos();
            vendedor = new Vendedor();
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
        VendedorDao venDao = new VendedorDao();
        if (venDao.delete(vendedor.getId_vendedor())) {
            vendedores = venDao.selecionarTodos();
            vendedor = new Vendedor();
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
        VendedorDao venDao = new VendedorDao();
        vendedores = venDao.selecionarBusca(vendedor);
        vendedor = new Vendedor();
    }

    public void cancelar(ActionEvent actionEvent) {
        vendedor = new Vendedor();
    }

    public void limparBusca(ActionEvent actionEvent) {
        VendedorDao venDao = new VendedorDao();
        vendedores = venDao.selecionarTodos();
    }

    public List getCidade() {
        CidadeDao cdDao = new CidadeDao();
        return cdDao.selecionarTodos();
    }
}
