package br.com.covale.managedBean;

import br.com.covale.dao.CondpagDao;
import br.com.covale.modelo.Condpag;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class CondpagMB {

    private Condpag condpag = new Condpag();
    private List condpags = new ArrayList();

    public CondpagMB() {

        CondpagDao cpDao = new CondpagDao();
        condpags = cpDao.selecionarTodos();

    }

    public Condpag getCondpag() {
        return condpag;
    }

    public void setCondpag(Condpag condpag) {
        this.condpag = condpag;
    }

    public List getCondpags() {
        return condpags;
    }

    public void setCondpags(List condpags) {
        this.condpags = condpags;
    }

    public void cadastrar(ActionEvent actionEvent) {
        CondpagDao cpDao = new CondpagDao();
        if (cpDao.inserir(condpag)) {
            condpags = cpDao.selecionarTodos();
            condpag = new Condpag();
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
        CondpagDao cpDao = new CondpagDao();
        if (cpDao.update(condpag)) {
            condpags = cpDao.selecionarTodos();
            condpag = new Condpag();
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
        CondpagDao cpDao = new CondpagDao();
        if (cpDao.delete(condpag.getId_condpag())) {
            condpags = cpDao.selecionarTodos();
            condpag = new Condpag();
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
        CondpagDao cpDao = new CondpagDao();
        condpags = cpDao.selecionarBusca(condpag);
        condpag = new Condpag();
    }

    public void cancelar(ActionEvent actionEvent) {
        condpag = new Condpag();
    }

    public void limparBusca(ActionEvent actionEvent) {
        CondpagDao cpDao = new CondpagDao();
        condpags = cpDao.selecionarTodos();
    }
}
