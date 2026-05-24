/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeProdutoServico {
    public ProdutoServico maperarEntidadeParaPRoduto(EntidadeProdutoServico e){
        
    return new ProdutoServico(e.getId(),
                             (e.getProdutoServico()>0)?true : false,
                             e.getNome(),
                             e.getDescricao(),e.getPreco(),
                             (e.getAtivo()>0)?true:false);
    }
    public EntidadeProdutoServico mapearParaEntidadeProdutoServico(ProdutoServico e){
        var entidade = new EntidadeProdutoServico();
        byte produt;
        if(e.isServico())produt=1;
        else produt=0;
        byte ativo;
        if(e.isAtivo())ativo=1;
        else ativo=0;
        entidade.setId(e.getId());
        entidade.setDescricao(e.getDescricao());
        entidade.setNome(e.getNome());
        entidade.setPreco(e.getPreco());
        entidade.setProdutoServico(produt);
        entidade.setAtivo(ativo);
        return entidade;
    }
}
