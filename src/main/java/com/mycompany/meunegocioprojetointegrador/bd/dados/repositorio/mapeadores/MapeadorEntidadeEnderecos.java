/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeEnderecos {
     public Endereco mapearEntidadeEnderecoParaEndereco(EntidadeEndereco e){
    return new Endereco(e.getRua(),
                    e.getCep(),
                  e.getCidade(),
                  e.getEstado(),
                  e.getNumero(),
              e.getComplemento()   ,
                     e.getId(),
                   e.getIdCliente(),
                   e.getBairro());
     
     
    }
     
     public EntidadeEndereco mapeEnderecoParaEntidade(Endereco e){
      var entidade =new EntidadeEndereco();
      entidade.setBairro(e.getBairo());
      entidade.setCep(e.getCep());
      entidade.setCidade(e.getCidade());
      entidade.setEstado(e.getEstado());
      entidade.setRua(e.getRua());
      entidade.setComplemento(e.getComplemento());
      entidade.setId(e.getId());
      entidade.setIdCliente(e.getIdCli());
      entidade.setNumero(e.getNumero());
             
      return entidade; 
              
     }
}
