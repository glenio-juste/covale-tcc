package br.com.covale.modelo;

import java.io.Serializable;

public class Fornecedor implements Serializable{

    private int id_fornecedor;         
    private String nome;
    private String cnpj;
    private String endereco;
    private String bairro;
    private String cep;
    private String telefone;
    private String fonecom;
    private String fonecel;
    private String email;
        
    private Cidade cidade;    
    
    public Fornecedor() {
        cidade = new Cidade();
    }
    
    public Fornecedor(int id_fornecedor, String nome, String cnpj, String endereco, String bairro, 
            String cep, String telefone, String fonecom, String fonecel, String email, Cidade cidade) {
        this.id_fornecedor = id_fornecedor;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.telefone = telefone;
        this.fonecom = fonecom;
        this.fonecel = fonecel;
        this.email = email;
    
        this.cidade = cidade;
    }
         
    public int getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
    
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

   public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFonecom() {
        return fonecom;
    }

    public void setFonecom(String fonecom) {
        this.fonecom = fonecom;
    }

    public String getFonecel() {
        return fonecel;
    }

    public void setFonecel(String fonecel) {
        this.fonecel = fonecel;
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
