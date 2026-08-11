/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeHistoricoRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosHistoricoDeMudancas implements IFinalizar{
    private GerenciadorDeEntidades gerenciadorDeEntidades;
    
    public DaosHistoricoDeMudancas(GerenciadorDeEntidades g){
    gerenciadorDeEntidades=g;
    }
    public List<EntidadeHistoricoRequisicao> listarHistorico(Long idRequisicao){
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query =criteria.createQuery(EntidadeHistoricoRequisicao.class);
            var raiz =query.from(EntidadeHistoricoRequisicao.class);
            var predicado =criteria.equal(raiz.get("idRequisicao"),idRequisicao);
            query.select(raiz).where(predicado);
            return escopo.selectList(query);
            }, 
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
     
    }

    @Override
    public void finalizar() {
    this.gerenciadorDeEntidades=null;
    }
}
