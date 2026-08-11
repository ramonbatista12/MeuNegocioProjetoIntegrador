/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
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
        
    return gerenciadorDeEntidades.executar(
        escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeEndereco.class);
            var raiz=query.from(EntidadeEndereco.class);
            var predicado =criteria.equal(raiz.get("idCliente"), id);
            query.select(raiz).where(predicado);
            return escopo.selectList(query);
        }, erro->{
        erro.printStackTrace();
        return new ArrayList<>();
        });
  
    }
    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
}
