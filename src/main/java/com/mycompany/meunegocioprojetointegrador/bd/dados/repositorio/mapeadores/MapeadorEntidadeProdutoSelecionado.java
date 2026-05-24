/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeProdutoSelecionado {
    public ProdutoSelecionado mapearEntidadeParaProdutoSelecionado(EntidadeProdutoSelecionado e){
     return new ProdutoSelecionado(e.getId(),e.getIdRequisicao(),e.getIdProduto(),e.getNome(),(e.getProdServ()>0)?true:false,e.getQuantidade(),e.getPreco());
    }
    
    public EntidadeProdutoSelecionado maoerarProdutoParaEntidade(ProdutoSelecionado e){
     var entidade = new EntidadeProdutoSelecionado();
     Double preco=e.getPreco();
    byte produto;
    if(e.isProduto())produto=1;
    else produto=0;
     entidade.setId(e.getId());
     entidade.setIdProduto(e.getIdProduto());
     entidade.setIdRequisicao(e.getIdRequisicao());
     entidade.setNome(e.getNome());
     entidade.setPreco(preco.floatValue());
     entidade.setQuantidade(e.getQuantidade());
     entidade.setProdServ(produto);
     return entidade;
    }
}
