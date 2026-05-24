/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEstado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Estado;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeEstado {
    public Estado mapearParaEntidadeParaEstado(EntidadeEstado e){
        return  new Estado(e.getId(),e.getDescricao());
    }
    
    public EntidadeEstado mapearParEntidadeEstado(Estado e){
    var entidade = new EntidadeEstado();
    entidade.setId(e.getId());
    entidade.setDescricao(e.getDescricao());
    return  entidade;
    }
}
