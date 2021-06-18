package br.com.covale.modelo;

import java.io.Serializable;

public class Cliente implements Serializable{
    
    private int id_cliente;
    private String nome;
    private String pessoa;
    private String doc;
    private String tipo;
    private String endereco;
    private String bairro;
    private String cep;
    private String fonecom;
    private String fonecel;
    private String telefone;
    private String email;
    
    private Cidade cidade;  
    
    public Cliente() {
        cidade = new Cidade();
    }

    public Cliente(int id_cliente, String nome, String pessoa, String doc,String tipo, 
            String endereco, String bairro,String cep, String telefone, 
            String fonecom,String fonecel, String email, Cidade cidade) {
        
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.pessoa = pessoa;
        this.doc = doc;
        this.tipo = tipo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.telefone = telefone;
        this.fonecom = fonecom;
        this.fonecel = fonecel;
        this.email = email;
        
        this.cidade = cidade;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    
    
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }
    
    public String getDoc(){
        return doc;
    }
    
    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getBairro(){
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
    
    public String getFonecom(){
        return fonecom;
    }
    
    public void setFonecom(String fonecom) {
        this.fonecom = fonecom;
    }
    
    public String getFonecel(){
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
    
}
