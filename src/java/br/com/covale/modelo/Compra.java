package br.com.covale.modelo;

import java.util.ArrayList;
import java.util.List;

public class Compra {
    
    private int id_compra;
    private String data;
    private String valor;
    private String nf;
    
    private Condpag condpag;
    private Formapag formapag;
    private Fornecedor fornecedor;
    
    List<Produto> produtos;

    public Compra() {
        condpag = new Condpag();
        formapag = new Formapag();
        fornecedor = new Fornecedor();
        
        produtos = new ArrayList<Produto>();
        
    }

    public Compra(int id_compra, String data, String valor, String nf, 
            Condpag condpag, Formapag formapag, Fornecedor fornecedor, 
            List<Produto> produtos) {
        
        this.id_compra = id_compra;
        this.data = data;
        this.valor = valor;
        this.nf = nf;
        
        this.condpag = condpag;
        this.formapag = formapag;
        this.fornecedor = fornecedor;
        
        this.produtos = produtos;
        
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
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

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
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
    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
}
