package br.com.covale.managedBean;

import br.com.covale.dao.CidadeDao;
import br.com.covale.dao.ClienteDao;
import br.com.covale.modelo.Cidade;
import br.com.covale.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class CidadeMB {

    private Cidade cidade = new Cidade();
    private List cidades = new ArrayList();

    public CidadeMB() {

        CidadeDao cdDao = new CidadeDao();
        cidades = cdDao.selecionarTodos();

    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List getCidades() {
        return cidades;
    }

    public void setCidades(List cidades) {
        this.cidades = cidades;
    }

    public void cadastrar(ActionEvent actionEvent) {        
        if (!cidade.getNome().equals("")) { 
            CidadeDao cdDao = new CidadeDao();
            if (cdDao.inserir(cidade)) {
                cidades = cdDao.selecionarTodos();
                cidade = new Cidade();
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
                    "O nome da cidade é obrigatorio!", "Tente novamente"));
        }

    }

    public void alterar(ActionEvent actionEvent) {
        CidadeDao cdDao = new CidadeDao();
        if (cdDao.update(cidade)) {
            cidades = cdDao.selecionarTodos();
            cidade = new Cidade();
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
        CidadeDao cdDao = new CidadeDao();

        ClienteDao cDao = new ClienteDao();
        Cliente c = new Cliente(0, "", "", "", "", "", "", "", "", "", "", "", cidade);
        if (cDao.selecionarBusca(c).isEmpty()) {

            if (cdDao.delete(cidade.getId_cidade())) {
                cidades = cdDao.selecionarTodos();
                cidade = new Cidade();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Apagado com sucesso", ""));
            } else {

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erro ao apagar", "Tente novamente"));
            }
        }
    else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao apagar", "Cidade com ligação"));
        }

    }

    public void busca(ActionEvent actionEvent) {
        CidadeDao cdDao = new CidadeDao();
        cidades = cdDao.selecionarBusca(cidade);
        cidade = new Cidade();
    }

    public void cancelar(ActionEvent actionEvent) {
        cidade = new Cidade();
    }

    public void limparBusca(ActionEvent actionEvent) {
        CidadeDao cdDao = new CidadeDao();
        cidades = cdDao.selecionarTodos();
    }
}
