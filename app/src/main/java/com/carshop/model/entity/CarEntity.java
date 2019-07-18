package com.carshop.model.entity;

public class CarEntity {

    private String id;
    private String nome;
    private String descricao;
    private String marca;
    private String quantidade;
    private String preco;
    private String imagem;

    public CarEntity(){
    }

    public CarEntity(String id, String nome, String descricao, String marca, String quantidade, String preco, String imagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.quantidade = quantidade;
        this.preco = preco;
        this.imagem = imagem;
    }

    public int getIdInt() {
        int getIdInt = 0;
        try {
            getIdInt = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getIdInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getQuantidadeInt() {
        int quantidadeInt = 0;
        try {
            quantidadeInt = Integer.parseInt(quantidade);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quantidadeInt;
    }
}
