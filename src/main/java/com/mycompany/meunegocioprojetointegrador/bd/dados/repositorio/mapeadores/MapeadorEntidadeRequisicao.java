/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Estado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Requisicao;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeRequisicao {
    public DadosDaRequisicao maperEntidadeParaDadosDaRequisicao(EntidadeRequisicao e){
     return new DadosDaRequisicao(new Requisicao(e.getId(),e.getObservacao(),e.getDescricao()),
                                  new MapeadorEntidadeEstado().mapearParaEntidadeParaEstado(e.getEstado()),
                                  new MapeadorEntidadeClientes().mapearEntidadeParaCliente(e.getCliente()));
    }
    
    public EntidadeRequisicao mapearparaEntidadeRequisicao(DadosDaRequisicao e){
    var entidade = new EntidadeRequisicao();
    entidade.setId(e.getRequisicao().getId());
    entidade.setObservacao(e.getRequisicao().getObservacao());
    entidade.setDescricao(e.getRequisicao().getDescricao());
    entidade.setEstado(new MapeadorEntidadeEstado().mapearParEntidadeEstado(e.getEstado()));
    entidade.setCliente(new MapeadorEntidadeClientes().mapeClienteParaEntidadeCliente(e.getCliente()));
    return entidade;
    }
}
