package br.com.covale.modelo;

public class Produto {

    private int id_produto;
    private String nome;
    private String marca;
    private String grupo;    
    private String quantidade;    
    private String quantidadeVenda;
    private String medida;
    private String preco_compra;
    private Double val_unit;

    public Produto() {
    }

    public Produto(int id_produto, String nome, String marca, String grupo,
            String quantidade, String medida, String preco_compra, Double val_unit) {

        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.grupo = grupo;
        this.quantidade = quantidade;
        this.medida = medida;
        this.preco_compra = preco_compra;
        this.val_unit = val_unit;
        
    }

    public Produto(int id_produto, String nome, String marca, String grupo, String quantidade, String quantidadeVenda, String medida, String preco_compra, Double val_unit) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.grupo = grupo;
        this.quantidade = quantidade;
        this.quantidadeVenda = quantidadeVenda;
        this.medida = medida;
        this.preco_compra = preco_compra;
        this.val_unit = val_unit;
    }
    
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
    
    public String getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(String preco_compra) {
        this.preco_compra = preco_compra;
    }

    public Double getVal_unit() {
        return val_unit;
    }

    public void setVal_unit(Double val_unit) {
        this.val_unit = val_unit;
    }

    public String getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(String quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }
    

}
