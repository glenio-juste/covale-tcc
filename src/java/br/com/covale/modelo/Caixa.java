package br.com.covale.modelo;

public class Caixa {
    
    private int id_caixa;
    private String data;
    private String tipo;
    private String op;
    private String valor;
    

    public Caixa() {
    }

    public Caixa(int id_caixa, String data, String tipo, String op, String valor) {
        this.id_caixa = id_caixa;
        this.data = data;
        this.tipo = tipo;
        this.op = op;
        this.valor = valor;
        
    }

    public int getId_caixa() {
        return id_caixa;
    }

    public void setId_caixa(int id_caixa) {
        this.id_caixa = id_caixa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
    
}
