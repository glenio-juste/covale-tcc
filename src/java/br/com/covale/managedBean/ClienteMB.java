package br.com.covale.managedBean;

import br.com.covale.dao.CidadeDao;
import br.com.covale.dao.ClienteDao;
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
public class ClienteMB {

    private Cliente cliente = new Cliente();
    private List clientes = new ArrayList();

    public ClienteMB() {

        ClienteDao cliDao = new ClienteDao();
        clientes = cliDao.selecionarTodos();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List getClientes() {
        return clientes;
    }

    public void setClientes(List clientes) {
        this.clientes = clientes;
    }

    public void cadastrar(ActionEvent actionEvent) {        
        
        if (!cliente.getNome().equals("") && !cliente.getPessoa().equals("")) { 
            ClienteDao cliDao = new ClienteDao();
            if (cliDao.inserir(cliente)) {
                clientes = cliDao.selecionarTodos();
                cliente = new Cliente();
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
                    "O nome do Cliente e o Tipo SÂO OBRIGATÓRIOS!", "Tente novamente"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        ClienteDao cliDao = new ClienteDao();
        if (cliDao.update(cliente)) {
            clientes = cliDao.selecionarTodos();
            cliente = new Cliente();
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
        ClienteDao cliDao = new ClienteDao();
        if (cliDao.delete(cliente.getId_cliente())) {
            clientes = cliDao.selecionarTodos();
            cliente = new Cliente();
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
        ClienteDao cliDao = new ClienteDao();
        clientes = cliDao.selecionarBusca(cliente);
        cliente = new Cliente();
    }

    public void cancelar(ActionEvent actionEvent) {
        cliente = new Cliente();
    }

    public void limparBusca(ActionEvent actionEvent) {
        ClienteDao cliDao = new ClienteDao();
        clientes = cliDao.selecionarTodos();
    }

    public List getCidade() {
        CidadeDao cdDao = new CidadeDao();
        return cdDao.selecionarTodos();
    }
}
