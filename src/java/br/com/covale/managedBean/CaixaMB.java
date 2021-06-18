package br.com.covale.managedBean;

import br.com.covale.dao.CaixaDao;
import br.com.covale.modelo.Caixa;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class CaixaMB {

    private Caixa caixa = new Caixa();
    private List caixas = new ArrayList();   
    private String saldo;

    public CaixaMB() {

        CaixaDao cxDao = new CaixaDao();
        caixas = cxDao.selecionarTodos();

    }
    
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public List getCaixas() {
        return caixas;
    }

    public void setCaixas(List caixas) {
        this.caixas = caixas;
    }

    public void cadastrar(ActionEvent actionEvent) {
        CaixaDao cxDao = new CaixaDao(); 
        if (cxDao.inserir(caixa)) {            
            caixas = cxDao.selecionarTodos(); 
            caixa = new Caixa(); 
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
        CaixaDao cxDao = new CaixaDao();

        if (cxDao.update(caixa)) { 
            caixas = cxDao.selecionarTodos(); 
            caixa = new Caixa(); 
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
        CaixaDao cxDao = new CaixaDao();
        if (cxDao.delete(caixa.getId_caixa())) {
            caixas = cxDao.selecionarTodos();
            caixa = new Caixa();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Apagado com sucesso", ""));
        } else {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao apagar", "Tente novamente"));
        }
    }

    public void busca() {
        CaixaDao cxDao = new CaixaDao();
        caixas = cxDao.selecionarBusca(caixa); 
        caixa = new Caixa();
    }

    public void cancelar(ActionEvent actionEvent) {
        caixa = new Caixa(); 
    }

    public void limparBusca(ActionEvent actionEvent) {
        CaixaDao cxDao = new CaixaDao();
        caixas = cxDao.selecionarTodos(); 
    }

    public String getSaldo() {
        Double soma = 0d; 
        List<Caixa> cs = caixas;
        for (Caixa c : cs) { 
            if (c.getOp().equals("Saída")) { 
                soma = soma - Double.parseDouble(c.getValor());
            } else {
                soma = soma + Double.parseDouble(c.getValor());
            }
        }
        saldo = soma.toString(); 
        return saldo;

    }
}
