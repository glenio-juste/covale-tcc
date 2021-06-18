package br.com.covale.managedBean;

import br.com.covale.dao.FormapagDao;
import br.com.covale.modelo.Formapag;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class FormapagMB {

    private Formapag formapag = new Formapag();
    private List formapags = new ArrayList();

    public FormapagMB() {

        FormapagDao fpDao = new FormapagDao();
        formapags = fpDao.selecionarTodos();

    }

    public Formapag getFormapag() {
        return formapag;
    }

    public void setFormapag(Formapag formapag) {
        this.formapag = formapag;
    }

    public List getFormapags() {
        return formapags;
    }

    public void setFormapags(List formapags) {
        this.formapags = formapags;
    }

    public void cadastrar(ActionEvent actionEvent) {
        FormapagDao fpDao = new FormapagDao();
        if (fpDao.inserir(formapag)) {
            formapags = fpDao.selecionarTodos();
            formapag = new Formapag();
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
        FormapagDao fpDao = new FormapagDao();
        if (fpDao.update(formapag)) {
            formapags = fpDao.selecionarTodos();
            formapag = new Formapag();
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
        FormapagDao fpDao = new FormapagDao();
        if (fpDao.delete(formapag.getId_formapag())) {
            formapags = fpDao.selecionarTodos();
            formapag = new Formapag();
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
        FormapagDao fpDao = new FormapagDao();
        formapags = fpDao.selecionarBusca(formapag);
        formapag = new Formapag();
    }

    public void cancelar(ActionEvent actionEvent) {
        formapag = new Formapag();
    }

    public void limparBusca(ActionEvent actionEvent) {
        FormapagDao fpDao = new FormapagDao();
        formapags = fpDao.selecionarTodos();
    }
}
