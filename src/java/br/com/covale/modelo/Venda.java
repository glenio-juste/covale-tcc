package br.com.covale.modelo;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    
    private int id_venda;
    private String data;
    private String valor;
    private String nfv;
    
    
    private Condpag condpag;
    private Formapag formapag;
    private Cliente cliente;
    private Vendedor vendedor;
    
    List<Produto> produtos;

    public Venda() {
        condpag = new Condpag();
        formapag = new Formapag();
        cliente = new Cliente();
        vendedor = new Vendedor();
        
        produtos = new ArrayList<Produto>();
        
    }

    public Venda(int id_venda, String data, String valor, String nfv, Condpag condpag, 
            Formapag formapag, Cliente cliente, Vendedor vendedor,
            List<Produto> produtos) {
        this.id_venda = id_venda;
        this.data = data;
        this.valor = valor;
        this.nfv = nfv;
        
        this.condpag = condpag;
        this.formapag = formapag;
        this.cliente = cliente;
        this.vendedor = vendedor;
        
        this.produtos = produtos;
        
    }
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public Condpag getCondpag() {
        return condpag;
    }

    public void setCondpag(Condpag condpag) {
        this.condpag = condpag;
    }
    
    public Formapag getFormapag() {
        return formapag;
    }
    
    public void setFormapag(Formapag formapag) {
        this.formapag = formapag;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Vendedor getVendedor() {
        return vendedor;
    }
    
    public void setVendedor (Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getNfv() {
        return nfv;
    }

    public void setNfv(String nfv) {
        this.nfv = nfv;
    }
        
    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
}
