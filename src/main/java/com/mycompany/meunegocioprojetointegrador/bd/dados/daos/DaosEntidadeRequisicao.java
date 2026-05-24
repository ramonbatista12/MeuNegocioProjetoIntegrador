/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEstado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeHistoricoRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.EnunEstados;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.dto.JuncaoEntidadeRequisicaoClienteEstado;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.Rotas;
import jakarta.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosEntidadeRequisicao implements IFinalizar{
   private GerenciadorDeEntidades gerenciadorDeEntidade;
   
   public DaosEntidadeRequisicao(GerenciadorDeEntidades g){
   this.gerenciadorDeEntidade=g;
   }
   
   public List<EntidadeRequisicao> listarTodasAsRequisicoes(){
    var entityManger =gerenciadorDeEntidade.getManager();
       try {
           var criteria=entityManger.getCriteriaBuilder();
           var querye= criteria.createQuery(EntidadeRequisicao.class);
           var rais=querye.from(EntidadeRequisicao.class);
           rais.fetch("estado", JoinType.LEFT);
           rais.fetch("cliente", JoinType.LEFT);
           querye.select(rais);
           return entityManger.createQuery(querye).getResultList();
       } catch (Exception e) {
           e.printStackTrace();
           return new ArrayList<>();
       } finally {
           entityManger.clear();
           entityManger.close();
       }
   }
   public ResultadoIo<Void> salvarRequisicao(EntidadeRequisicao e,List<EntidadeProdutoSelecionado> l){
   var entityManager = gerenciadorDeEntidade.getManager();
       try{
           entityManager.getTransaction().begin();
           var estado=new EntidadeEstado();
           estado.setId(EnunEstados.Pendendte.getId());
           estado.setDescricao(EnunEstados.Pendendte.getDescricao());
           e.setEstado(estado);
           System.out.println("Estado escolhido "+estado.getId()+" decricao "+estado.getDescricao());
           entityManager.persist(e);
           l.forEach((p)->{
           p.setIdRequisicao(e.getId());
           entityManager.persist(p);
           });
           entityManager.getTransaction().commit();
           return new  ResultadoIo.OK(null);
           
       } 
       catch (Exception ex) {
           ex.printStackTrace();
           entityManager.getTransaction().rollback();
           return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } 
       finally {
          entityManager.clear();
          entityManager.close();
       }
   }
   
   public ResultadoIo<Void> updateRequisicao(EntidadeRequisicao e){
     var entityManager = gerenciadorDeEntidade.getManager();
       try {
           entityManager.getTransaction().begin();
           entityManager.merge(e);
           entityManager.getTransaction().commit();
           System.out.println("update ocoreu");
           return new  ResultadoIo.OK(null);
           
       } catch (Exception ex) {
           ex.printStackTrace();
           entityManager.getTransaction().rollback();
           return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } finally {
          entityManager.clear();
          entityManager.close();
       }
   }
   
   public ResultadoIo<Void> updateRequisicao(EntidadeRequisicao e,List<EntidadeProdutoSelecionado>l){
     var entityManager = gerenciadorDeEntidade.getManager();
       try {
           entityManager.getTransaction().begin();
           var criteriaBuilder= entityManager.getCriteriaBuilder();
           var querieDelete =criteriaBuilder.createCriteriaDelete(EntidadeProdutoSelecionado.class);
           var raiz =querieDelete.from(EntidadeProdutoSelecionado.class);
           var predicado=criteriaBuilder.equal(raiz.get("idRequisicao"),e.getId());
           querieDelete.where(predicado);
           var linhas =entityManager.createQuery(querieDelete).executeUpdate();
           
           entityManager.merge(e);
           l.forEach((p)->{
           p.setId(null);
           p.setIdRequisicao(e.getId());
               System.out.println("o id setado em p "+e.getId());
           entityManager.merge(p);
           });
           entityManager.getTransaction().commit();
           return new  ResultadoIo.OK(null);
           
       } catch (Exception ex) {
           ex.printStackTrace();
           entityManager.getTransaction().rollback();
           return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } finally {
          entityManager.clear();
          entityManager.close();
       }
   
   
   }
   
   public ResultadoIo<EntidadeRequisicao> requisicaoPorId(Long id){
         var entityManger =gerenciadorDeEntidade.getManager();
       try {
           var criteria=entityManger.getCriteriaBuilder();
           var querye= criteria.createQuery(EntidadeRequisicao.class);
           var rais=querye.from(EntidadeRequisicao.class);
           rais.fetch("estado", JoinType.LEFT);
           rais.fetch("cliente", JoinType.LEFT);
           var presicado=criteria.equal(rais.get("id"),id);
           querye.select(rais).where(presicado);
           var resultado =entityManger.createQuery(querye).getSingleResultOrNull();
          if(resultado!=null)return new ResultadoIo.OK<>(resultado);
           return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } catch (Exception e) {
           e.printStackTrace();
           return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } finally {
           entityManger.clear();
           entityManger.close();
       }
   
   }
   
   public List<EntidadeRequisicao> requisicoesPorCliente(String nome){
   var entitimanager = gerenciadorDeEntidade.getManager();
       try {
           var criteria =entitimanager.getCriteriaBuilder();
           var querye =criteria.createQuery(EntidadeRequisicao.class);
           var raiz =querye.from(EntidadeRequisicao.class);
           raiz.fetch("estado", JoinType.LEFT);
           raiz.fetch("cliente", JoinType.LEFT);
           var join =raiz.join("cliente",JoinType.LEFT);
           var predicado=criteria.like(join.get("nome"),"%"+nome+"%");
           querye.select(raiz).where(predicado);
           return entitimanager.createQuery(querye).getResultList();
           
       } 
       catch (Exception e) {
           e.printStackTrace();
           return new ArrayList<>();
       } 
       finally {
           entitimanager.clear();
           entitimanager.close();
           
       }
       
   
   }
   
   public List<EntidadeRequisicao> requisicaoPorEstado(String estado){
      var entitimanager = gerenciadorDeEntidade.getManager();
       try {
           var criteria =entitimanager.getCriteriaBuilder();
           var querye =criteria.createQuery(EntidadeRequisicao.class);
           var raiz =querye.from(EntidadeRequisicao.class);
           raiz.fetch("estado", JoinType.LEFT);
           raiz.fetch("cliente", JoinType.LEFT);
           var join =raiz.join("estado",JoinType.LEFT);
           var predicado=criteria.like(join.get("descricao"),"%"+estado+"%");
           querye.select(raiz).where(predicado);
           return entitimanager.createQuery(querye).getResultList();
           
       } 
       catch (Exception e) {
           e.printStackTrace();
           return new ArrayList<>();
       } 
       finally {
           entitimanager.clear();
           entitimanager.close();
           
       }
   }
   
   public List<EntidadeRequisicao> listarequisicaoPorId(Long id){
      var entitimanager = gerenciadorDeEntidade.getManager();
       try {
           var criteria =entitimanager.getCriteriaBuilder();
           var querye =criteria.createQuery(EntidadeRequisicao.class);
           var raiz =querye.from(EntidadeRequisicao.class);
           raiz.fetch("estado", JoinType.LEFT);
           raiz.fetch("cliente", JoinType.LEFT);
           var predicado =criteria.equal(raiz.get("id"), id);
           querye.select(raiz).where(predicado);
           return entitimanager.createQuery(querye).getResultList();
           
       } 
       catch (Exception e) {
           e.printStackTrace();
           return new ArrayList<>();
       } 
       finally {
           entitimanager.clear();
           entitimanager.close();
           
       }
   
   
   }
   
   public ResultadoIo<Void> excluirRequisicao(EntidadeRequisicao e){
   var entitimanager =gerenciadorDeEntidade.getManager();
       try {
           entitimanager.getTransaction().begin();
           var criteria=entitimanager.getCriteriaBuilder();
           var criteriaDelete1=criteria.createCriteriaDelete(EntidadeHistoricoRequisicao.class);
           var raiz1=criteriaDelete1.from(EntidadeHistoricoRequisicao.class);
           var predicado1=criteria.equal(raiz1.get("idRequisicao"),e.getId());
           criteriaDelete1.where(predicado1);
           entitimanager.createQuery(criteriaDelete1).executeUpdate();
           
          
           
           var criteriaDelete2=criteria.createCriteriaDelete(EntidadeProdutoSelecionado.class);
           var raiz2=criteriaDelete2.from(EntidadeProdutoSelecionado.class);
           var predicado2=criteria.equal(raiz2.get("idRequisicao"), e.getId());
           criteriaDelete2.where(predicado2);
           entitimanager.createQuery(criteriaDelete2).executeUpdate();
           
           var criteriaDelete3=criteria.createCriteriaDelete(EntidadeRequisicao.class);
           var raiz3=criteriaDelete3.from(EntidadeRequisicao.class);
           var predicado3=criteria.equal(raiz3.get("id"),e.getId());
           criteriaDelete3.where(predicado3);
           entitimanager.createQuery(criteriaDelete3).executeUpdate();
           
           entitimanager.getTransaction().commit();
           return new ResultadoIo.OK<>(null);
       } catch (Exception ex) {
           ex.printStackTrace();
           entitimanager.getTransaction().rollback();
           return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       } finally {
           entitimanager.clear();
           entitimanager.close();
       }
   }
    @Override
    public void finalizar() {
     gerenciadorDeEntidade=null;
    }
}
