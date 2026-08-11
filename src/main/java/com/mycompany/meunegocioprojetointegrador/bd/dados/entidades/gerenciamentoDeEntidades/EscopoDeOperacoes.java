/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author ramon
 */
public class EscopoDeOperacoes implements IEscopoOperacoes{

    private EntityManager entitiManger;

    public EscopoDeOperacoes(EntityManager entitiManger) {
        this.entitiManger = entitiManger;
    }
    
    public CriteriaBuilder getCriteriaBuilder(){return entitiManger.getCriteriaBuilder();}
    
    public<R> R selectResultadoUnico(CriteriaQuery<R> query){
    return entitiManger.createQuery(query).getSingleResultOrNull();
    };
    
    public<R> List<R> selectList(CriteriaQuery<R> query){
    var resultadoOpercao=entitiManger.createQuery(query)
                                     .getResultList();
    return resultadoOpercao;
    } 
    
    public void merge(Object o){
    entitiManger.merge(o);
    }
    
    public void persist(Object o){
    entitiManger.persist(o);
    }
    
    public<R> void deletCriteria(CriteriaDelete<R> delete ){
    entitiManger.createQuery(delete).executeUpdate();
    } 
    
    public<R> void updateCriteria(CriteriaUpdate<R> update){
    entitiManger.createQuery(update).executeUpdate();
    }
    
    public EntityManager getEntityManager(){return entitiManger;}
    
    public void nativeQuery(String query,List<Par<String,Object>> argumentos){
    var queryNativa= entitiManger.createNativeQuery(query);
    argumentos.forEach(par->{
    queryNativa.setParameter(par.primeiro(),par.segundo());
    });
    queryNativa.executeUpdate();
    }
    
    public void apagarRederenciaAEntityManager(){
    entitiManger=null;
    }

    @Override
    public void remove(Object o) {
    entitiManger.remove(o);
    }
}
