package br.com.covale.modelo;

public class Cidade {
    
    private int id_cidade;
    private String nome;
    private String estado;

    public Cidade() {
    }

    public Cidade(int id_cidade, String nome, String estado) {
        this.id_cidade = id_cidade;
        this.nome = nome;
        this.estado = estado;
    }

    public int getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(int id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
     
}
