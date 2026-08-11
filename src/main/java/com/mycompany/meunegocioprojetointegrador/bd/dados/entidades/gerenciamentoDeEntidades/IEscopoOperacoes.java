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

/**
 *
 * @author ramon
 */
public interface IEscopoOperacoes {
    public CriteriaBuilder getCriteriaBuilder();
    public<R> R selectResultadoUnico(CriteriaQuery<R> query);
    public<R> List<R> selectList(CriteriaQuery<R> query);
    public void merge(Object o);
    public void persist(Object o);
    public<R> void deletCriteria(CriteriaDelete<R> delete );
    public<R> void updateCriteria(CriteriaUpdate<R> update);
    public EntityManager getEntityManager();
    public void nativeQuery(String query,List<Par<String,Object>> argumentos);
    public void remove(Object o);
}
