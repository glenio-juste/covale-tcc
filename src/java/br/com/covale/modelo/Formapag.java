package br.com.covale.modelo;

public class Formapag {
    
    private int id_formapag;
    private String descricao;

    public Formapag() {
    }

    public Formapag(int id_formapag, String descricao) {
        this.id_formapag = id_formapag;
        this.descricao = descricao;
    }

    public int getId_formapag() {
        return id_formapag;
    }

    public void setId_formapag(int id_formapag) {
        this.id_formapag = id_formapag;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
