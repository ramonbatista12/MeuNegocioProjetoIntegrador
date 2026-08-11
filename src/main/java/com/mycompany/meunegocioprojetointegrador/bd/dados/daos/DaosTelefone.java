/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosTelefone implements IFinalizar{
    private GerenciadorDeEntidades gerenciadorDeEntidades;
    
   public DaosTelefone(GerenciadorDeEntidades g){
   this.gerenciadorDeEntidades=g;
   }
   
   public List<EntidadeTelefone> getTelefonesPorCliente(Long idCLiente){
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query= criteria.createQuery(EntidadeTelefone.class);
            var rais = query.from(EntidadeTelefone.class);
            var predicado =criteria.equal(rais.get("idCliente"),idCLiente);
            query.select(rais).where(predicado);
            return escopo.selectList(query);
            
            },
            erro->{
            erro.printStackTrace();
            return  new ArrayList<>();
            });
    
   }

    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
                
}
