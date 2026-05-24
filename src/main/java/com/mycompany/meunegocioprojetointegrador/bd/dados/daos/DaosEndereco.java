/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosEndereco implements IFinalizar{
    private  GerenciadorDeEntidades gerenciadorDeEntidades;
    public DaosEndereco(GerenciadorDeEntidades g){
     gerenciadorDeEntidades=g;
    }
    public List<EntidadeEndereco> getEnderecosPorCLiente(Long id){
    var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria=entitymanager.getCriteriaBuilder();
            var querie=criteria.createQuery(EntidadeEndereco.class);
            var rais=querie.from(EntidadeEndereco.class);
            var predicado =criteria.equal(rais.get("idCliente"), id);
            querie.select(rais).where(predicado);
            return entitymanager.createQuery(querie).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    }
    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
}
