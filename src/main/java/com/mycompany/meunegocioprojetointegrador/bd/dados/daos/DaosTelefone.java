/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
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
    var entityManager = gerenciadorDeEntidades.getManager();
       try {
           var criteria =entityManager.getCriteriaBuilder();
           var querie= criteria.createQuery(EntidadeTelefone.class);
           var rais = querie.from(EntidadeTelefone.class);
           var predicado =criteria.equal(rais.get("idCliente"),idCLiente);
           querie.select(rais).where(predicado);
           return entityManager.createQuery(querie).getResultList();
       } catch (Exception e) {
           e.printStackTrace();
           return  new ArrayList<>();
       }finally{
        entityManager.clear();
        entityManager.close();
       }
   }

    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
                
}
