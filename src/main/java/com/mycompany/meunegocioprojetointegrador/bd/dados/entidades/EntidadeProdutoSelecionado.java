/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author ramon
 */
@Entity(name = "produto_selecionado")
public class EntidadeProdutoSelecionado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_req")
    private Long idRequisicao;
    @Column(name = "id_prod")
    private Long idProduto;
    @Column(name = "nome_momento_da_compra")
    private String nome;
    @Column(name = "preco_moomento_da_compra")
    private Float preco;
    @Column(name = "prod_ser")
    private Byte  prodServ;
    @Column(name = "quantidade")
    private Long quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Long idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Byte getProdServ() {
        return prodServ;
    }

    public void setProdServ(Byte prodServ) {
        this.prodServ = prodServ;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
