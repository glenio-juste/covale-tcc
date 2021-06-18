package br.com.covale.modelo;

public class Valprod {

    private int id_val_prod;
    private String data_compra;
    private String lote;
    private String data_validade;
    private String verifica;
    private Produto produto;

    public Valprod() {
        produto = new Produto();
    }

    public Valprod(int id_val_prod, String data_compra, String lote,
            String data_validade, String verifica, Produto produto) {
        
        this.id_val_prod = id_val_prod;
        this.data_compra = data_compra;
        this.lote = lote;
        this.data_validade = data_validade;
        this.verifica = verifica;

        this.produto = produto;

    }

    public int getId_val_prod() {
        return id_val_prod;
    }

    public void setId_val_prod(int id_val_prod) {
        this.id_val_prod = id_val_prod;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getData_validade() {
        return data_validade;
    }

    public void setData_validade(String data_validade) {
        this.data_validade = data_validade;
    }

    public String getVerifica() {
        return verifica;
    }

    public void setVerifica(String verifica) {
        this.verifica = verifica;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}