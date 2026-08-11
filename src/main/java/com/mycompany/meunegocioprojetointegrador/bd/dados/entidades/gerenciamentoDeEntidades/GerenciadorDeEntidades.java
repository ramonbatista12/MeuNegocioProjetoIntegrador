/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author ramon
 */
public class GerenciadorDeEntidades {
    private static GerenciadorDeEntidades instancia;
    private static Object lock = new Object();
    private EntityManagerFactory fabricaGerencidoresDeEntidadde= null;
    
    private GerenciadorDeEntidades(){
      fabricaGerencidoresDeEntidadde=Persistence.createEntityManagerFactory("MeuNegocioProjetoIntegrador");
    }
    public static GerenciadorDeEntidades getInstancia() {
        synchronized (lock) {
            if(instancia==null){instancia = new GerenciadorDeEntidades();}}
        return instancia;
    }

    public EntityManager getManager(){
    return  fabricaGerencidoresDeEntidadde.createEntityManager();
    }
    public<R>  R executar(Function<IEscopoOperacoes,R> comando,Function<Exception,R> erro){
        var entityManager=getManager();
        var escopo=new EscopoDeOperacoes(entityManager);
        try {
          return comando.apply(escopo);
            
        } catch (Exception e) {
            return erro.apply(e);
        }finally{
        entityManager.clear();
        entityManager.close();
        escopo.apagarRederenciaAEntityManager();
        }
    
    } 
    
    public void executar(Consumer<IEscopoOperacoes> comando,Consumer<Exception> erro){
     var entityManger=getManager();
     var escopo=new EscopoDeOperacoes(entityManger);
        try {
            comando.accept(escopo);
        } catch (Exception e) {
            erro.accept(e);
        }finally{
        entityManger.clear();
        entityManger.close();
        escopo.apagarRederenciaAEntityManager();
        }
        
    
    }
    
    public<R> R executarEntransacao(Function<IEscopoOperacoes,R> comando,Function<Exception,R> erro){
    var entityManager=getManager();
    var escopo=new EscopoDeOperacoes(entityManager);
    var transacao=entityManager.getTransaction();
        try {
            transacao.begin();
            var resultado=comando.apply(escopo);
            transacao.commit();
            return resultado;
        } catch (Exception e) {
            if(transacao.isActive()){
            transacao.rollback();
            }
            return erro.apply(e);
        } finally {
            entityManager.clear();
            entityManager.close();
            escopo.apagarRederenciaAEntityManager();
        }
    }
    
    public void executarEntransacao(Consumer<IEscopoOperacoes> comando,Consumer<Exception> erro){
     var entityManager=getManager();
     var escopo=new EscopoDeOperacoes(entityManager);
     var transacao=entityManager.getTransaction();
        try {
            transacao.begin();
            comando.accept(escopo);
            transacao.commit();
        } catch (Exception e) {
            if(transacao.isActive()){
            transacao.rollback();
            }
            erro.accept(e);
        } finally {
            entityManager.clear();
            entityManager.close();
            escopo.apagarRederenciaAEntityManager();
        }
    
    }
    
    public void finalizar(){
     synchronized(lock){
       
     fabricaGerencidoresDeEntidadde.close();
     fabricaGerencidoresDeEntidadde=null;
     instancia=null;
       
       }
    
     
   }
}
