/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

/**
 *
 * @author ramon
 */
public class ProdutoSelecionado {
    private Long id;
    private Long idRequisicao;
    private Long idProduto;
    private String nome;
    private Boolean produtoServico;
    private Long quantidade;
    private double preco;

    public ProdutoSelecionado(Long id,Long idRequisicao,Long idProduto, String nome, Boolean produtoServico, Long quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.produtoServico = produtoServico;
        this.quantidade = quantidade;
        this.preco = preco;
        this.idRequisicao=idRequisicao;
        this.idProduto=idProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Long idRequisicao) {
        this.idRequisicao = idRequisicao;
    }
    
    public String getPrecoComoString(){
     return String.format("%.2f", preco);
    }

    public double getPreco() {
        return preco;
    }
    
    public String getProdutoServico(){
    return (produtoServico)?"Produto":"Servico";
    }
    public String quantidadeToString(){
     return String.valueOf(quantidade);
    }
    
    public Long getQuantidade(){
     return quantidade;
    }

    public Long getIdproduto() {
        return idProduto;
    }

    public void setIdproduto(Long idproduto) {
        this.idProduto = idproduto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProdutoServico(Boolean produtoServico) {
        this.produtoServico = produtoServico;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }
    public boolean  isProduto(){
    return this.produtoServico;
    }
}
