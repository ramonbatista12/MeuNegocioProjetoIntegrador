/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeHistoricoRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
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
     var entitimanager =gerenciadorDeEntidades.getManager();
        try {
            System.out.println("id pasado "+idRequisicao);
            var criteria =entitimanager.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeHistoricoRequisicao.class);
            var raiz =querye.from(EntidadeHistoricoRequisicao.class);
            var predicado =criteria.equal(raiz.get("idRequisicao"),idRequisicao);
            querye.select(raiz).where(predicado);
            return entitimanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
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
