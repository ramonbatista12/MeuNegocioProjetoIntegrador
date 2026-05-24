/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeHistoricoRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.HistoricoDeMudancas;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeHistoricoRequisicao {
    public HistoricoDeMudancas mapearParaHistoricoDeMudancas(EntidadeHistoricoRequisicao e){
    return new HistoricoDeMudancas(e.getId(),
                           e.getIdRequicao(),
                             new MapeadorEntidadeEstado().mapearParaEntidadeParaEstado(e.getIdEstado()),
                     e.getDataMudanca());
    }
    
    public EntidadeHistoricoRequisicao mapearParaEntidadehistoricoDeMudancas(HistoricoDeMudancas e){
     var entidade = new EntidadeHistoricoRequisicao();
     entidade.setId(e.getId());
     entidade.setIdRequicao(e.getIdRequisicao());
     entidade.setIdEstado(new MapeadorEntidadeEstado().mapearParEntidadeEstado(e.getEstado()));
     entidade.setDataMudanca(e.getCarimboDataEHora());
     return entidade;
    }
}
