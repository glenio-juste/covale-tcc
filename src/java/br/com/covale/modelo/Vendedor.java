package br.com.covale.modelo;

import java.io.Serializable;

public class Vendedor implements Serializable {
    
    private int id_vendedor;
    private String nome;
    private String rg;
    private String cpf;
    private String endereco;
    private String bairro;
    private String telefone;
    private String fonecel;
    private String adm;
    private String salario;
    private String email;
    
    private Cidade cidade;
    
    public Vendedor() {
        cidade = new Cidade();
    }

    public Vendedor(int id_vendedor, String nome, String rg, String cpf, String endereco, 
            String bairro, String telefone, String fonecel, String adm, String salario, 
            String email, Cidade cidade) {
        this.id_vendedor = id_vendedor;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
        this.bairro = bairro;
        this.telefone = telefone;
        this.fonecel = fonecel;
        this.adm = adm;
        this.salario = salario;
        this.email = email;
        this.cidade = cidade;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFonecel() {
        return fonecel;
    }

    public void setFonecel(String fonecel) {
        this.fonecel = fonecel;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }
    
    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
}
