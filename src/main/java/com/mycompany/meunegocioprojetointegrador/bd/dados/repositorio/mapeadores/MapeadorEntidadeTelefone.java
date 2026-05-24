/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeTelefone {
    public Telefone mapearEntidadeParaTelefone(EntidadeTelefone e){
    
    return  new Telefone(e.getId(),e.getIdCliente(), e.getTelefone());
    }
    
    public EntidadeTelefone mapearTelefoneParEntidadeTelefone(Telefone e){
    var entidade= new EntidadeTelefone();
    entidade.setId(e.getId());
    entidade.setIdCliente(e.getIdCli());
    entidade.setTelefone(e.getTelefone());
    return entidade;
    }
}
