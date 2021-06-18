package br.com.covale.managedBean;

import br.com.covale.dao.ProdutoDao;
import br.com.covale.dao.ValprodDao;
import br.com.covale.modelo.Valprod;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ValprodMB {

    private Valprod valprod = new Valprod();
    private List valprods = new ArrayList();
    private int produtoId;

    public ValprodMB() {

        ValprodDao vpDao = new ValprodDao();

        FacesContext fc = FacesContext.getCurrentInstance();
        valprods = vpDao.selecionarTodos();

    }

    public Valprod getValprod() {
        return valprod;
    }

    public void setValprod(Valprod valprod) {
        this.valprod = valprod;
    }

    public List getValprods() {
        return valprods;
    }

    public void setValprods(List valprods) {
        this.valprods = valprods;
    }

    public void cadastrar(ActionEvent actionEvent) {

        if (!valprod.getData_validade().equals("")) {

            ValprodDao vpDao = new ValprodDao();
            if (vpDao.inserir(valprod)) {
                valprods = vpDao.selecionarTodos();
                valprod = new Valprod();
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
                        "É OBRIGATÓRIO informar a Data de Validade do Produto", ""));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        ValprodDao vpDao = new ValprodDao();
        if (vpDao.update(valprod)) {
            valprods = vpDao.selecionarTodos();
            valprod = new Valprod();
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
        ValprodDao vpDao = new ValprodDao();
        if (vpDao.delete(valprod.getId_val_prod())) {
            valprods = vpDao.selecionarTodos();
            valprod = new Valprod();
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
        ValprodDao vpDao = new ValprodDao();
        valprods = vpDao.selecionarBusca(valprod);
        valprod = new Valprod();
    }

    public void cancelar(ActionEvent actionEvent) {
        valprod = new Valprod();
    }

    public void limparBusca(ActionEvent actionEvent) {
        ValprodDao vpDao = new ValprodDao();
        valprods = vpDao.selecionarTodos();
    }

    public List getProduto() {
        ProdutoDao pdDao = new ProdutoDao();
        return pdDao.selecionarTodos();
    }
}
