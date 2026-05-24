/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosEntidadeProdutoSelecionado implements IFinalizar{
    private GerenciadorDeEntidades gerenciadorDeEntidades;

    public GerenciadorDeEntidades getGerenciadorDeEntidades() {
        return gerenciadorDeEntidades;
    }

    public DaosEntidadeProdutoSelecionado(GerenciadorDeEntidades gerenciadorDeEntidades) {
        this.gerenciadorDeEntidades = gerenciadorDeEntidades;
    }
    
    public void setGerenciadorDeEntidades(GerenciadorDeEntidades gerenciadorDeEntidades) {
        this.gerenciadorDeEntidades = gerenciadorDeEntidades;
    }
    
    public Double getTotal(Long idRequisicao){
    var entitimanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria = entitimanager.getCriteriaBuilder();
            var querye =criteria.createQuery(Number.class);
            var rais=querye.from(EntidadeProdutoSelecionado.class);
            var predicado = criteria.equal(rais.get("idRequisicao"), idRequisicao);
            var esprecao =criteria.prod(rais.get("preco"), rais.get("quantidade"));
            querye.select(criteria.sum(esprecao).as(Double.class)).where(predicado);
            var valor =entitimanager.createQuery(querye).getSingleResult();
            return valor.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }finally{
         entitimanager.clear();
         entitimanager.close();
        }
    }
    
    public List<EntidadeProdutoSelecionado> produtosSelecionados(Long idRequisicao){
        System.out.println("id pesquisado para o total "+idRequisicao);
      var entitimanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitimanager.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeProdutoSelecionado.class);
            var rais =querye.from(EntidadeProdutoSelecionado.class);
            var predicado = criteria.equal(rais.get("idRequisicao"), idRequisicao);
            querye.select(rais).where(predicado);
            return  entitimanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return  new ArrayList<>();
            
        } finally {
         entitimanager.clear();
         entitimanager.close();
        }
    }

    @Override
    public void finalizar() {
    this.gerenciadorDeEntidades=null;
    }
}
