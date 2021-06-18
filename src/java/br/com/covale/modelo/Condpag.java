package br.com.covale.modelo;

public class Condpag {

    private int id_condpag;
    private String descricao;

    public Condpag() {
    }

    public Condpag(int id_condpag, String descricao) {
        this.id_condpag = id_condpag;
        this.descricao = descricao;
    }

    public int getId_condpag() {
        return id_condpag;
    }

    public void setId_condpag(int id_condpag) {
        this.id_condpag = id_condpag;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
