package br.com.covale.managedBean;

import br.com.covale.dao.CidadeDao;
import br.com.covale.dao.FornecedorDao;
import br.com.covale.modelo.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class FornecedorMB {

    private Fornecedor fornecedor = new Fornecedor();
    private List fornecedores = new ArrayList();

    public FornecedorMB() {

        FornecedorDao forDao = new FornecedorDao();
        fornecedores = forDao.selecionarTodos();
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List fornecedores) {
        this.fornecedores = fornecedores;
    }

    public void cadastrar(ActionEvent actionEvent) {
        FornecedorDao forDao = new FornecedorDao();
        if (!fornecedor.getNome().equals("") && !fornecedor.getTelefone().equals("")) {

            if (forDao.inserir(fornecedor)) {
                fornecedores = forDao.selecionarTodos();
                fornecedor = new Fornecedor();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Inserido com sucesso", ""));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erro ao inserir", "Tente mais Tarde"));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao inserir", "Preenchimento do Nome e Fone Com é obrigatório"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        FornecedorDao forDao = new FornecedorDao();
        if (forDao.update(fornecedor)) {
            fornecedores = forDao.selecionarTodos();
            fornecedor = new Fornecedor();
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
        FornecedorDao forDAO = new FornecedorDao();
        if (forDAO.delete(fornecedor.getId_fornecedor())) {
            fornecedores = forDAO.selecionarTodos();
            fornecedor = new Fornecedor();
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
        FornecedorDao forDao = new FornecedorDao();
        fornecedores = forDao.selecionarBusca(fornecedor);
        fornecedor = new Fornecedor();
    }

    public void cancelar(ActionEvent actionEvent) {
        fornecedor = new Fornecedor();
    }

    public void limparBusca(ActionEvent actionEvent) {
        FornecedorDao forDao = new FornecedorDao();
        fornecedores = forDao.selecionarTodos();
    }

    public List getCidade() {
        CidadeDao cdDao = new CidadeDao();
        return cdDao.selecionarTodos();
    }
}
