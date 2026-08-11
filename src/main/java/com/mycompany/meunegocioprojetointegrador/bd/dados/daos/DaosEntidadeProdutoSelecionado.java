/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import jakarta.persistence.criteria.CriteriaQuery;
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
    return gerenciadorDeEntidades.executar(
            escopo -> {
            var criteria = escopo.getCriteriaBuilder();
            var query =criteria.createQuery(Number.class);
            var rais=query.from(EntidadeProdutoSelecionado.class);
            var predicado = criteria.equal(rais.get("idRequisicao"), idRequisicao);
            var esprecao =criteria.prod(rais.get("preco"), rais.get("quantidade"));
            query.select(criteria.sum(esprecao).as(Double.class)).where(predicado);
            var valor =escopo. selectResultadoUnico(query);
            if(valor==null) return 0.0;
            return valor.doubleValue();
            } ,
            erro->{
            erro.printStackTrace();
            return 0.0;
            });
    
    }
    
    public List<EntidadeProdutoSelecionado> produtosSelecionados(Long idRequisicao){
        return gerenciadorDeEntidades.executar(
                escopo->{
                var criteria =escopo.getCriteriaBuilder();
                var query =criteria.createQuery(EntidadeProdutoSelecionado.class);
                var rais =query.from(EntidadeProdutoSelecionado.class);
                var predicado = criteria.equal(rais.get("idRequisicao"), idRequisicao);
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
