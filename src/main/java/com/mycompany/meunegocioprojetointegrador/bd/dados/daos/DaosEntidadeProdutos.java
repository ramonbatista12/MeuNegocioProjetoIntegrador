/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaosEntidadeProdutos implements IFinalizar{
    private GerenciadorDeEntidades gerenciadorDeEntidades;
    
    public DaosEntidadeProdutos(GerenciadorDeEntidades g){
     this.gerenciadorDeEntidades=g;
    }
    
    public List<EntidadeProdutoServico> listartTodosOSProsutosServicos(){
     var entitimanager = gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitimanager.getCriteriaBuilder();
            var qurye =criteria.createQuery(EntidadeProdutoServico.class);
            var rais =qurye.from(EntidadeProdutoServico.class);
            qurye.select(rais);
            return entitimanager.createQuery(qurye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
         entitimanager.clear();
         entitimanager.close();
        }
    }
     
    public List<EntidadeProdutoServico> listarProdutosAtivos(){
    var entitimanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitimanager.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz =query.from(EntidadeProdutoServico.class);
            var predicado1=criteria.equal(raiz.get("ativo"),1 );
            query.select(raiz).where(predicado1);
            return entitimanager.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitimanager.clear();
            entitimanager.close();
        }
    
    }
    
    public List<EntidadeProdutoServico> listarProdutosPorNome(String nome){
    var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitymanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz =querye.from(EntidadeProdutoServico.class);
            var predicadao=criteria.like(raiz.get("nome"),"%"+nome+"%");
            querye.select(raiz).where(predicadao);
            return entitymanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    }
    
    public List<EntidadeProdutoServico> listarProdutosAtivosPorNome(String nome){
    var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitymanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz =querye.from(EntidadeProdutoServico.class);
            var predicadao=criteria.like(raiz.get("nome"),"%"+nome+"%");
            var predicado2=criteria.equal(raiz.get("ativo"),1);
            querye.select(raiz).where(predicadao,predicado2);
            return entitymanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    }
    

    

    public List<EntidadeProdutoServico> listarProdutoPorId(Long id){
    var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria=entitymanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz = querye.from(EntidadeProdutoServico.class);
            var predicadp=criteria.equal(raiz.get("id"), id);
            querye.select(raiz).where(predicadp);
            return entitymanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    
    }
    
    public List<EntidadeProdutoServico> listarProdutosAtivoPorId(Long id){
    var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            var criteria=entitymanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz = querye.from(EntidadeProdutoServico.class);
            var predicadp=criteria.equal(raiz.get("id"), id);
            var predicado2=criteria.equal(raiz.get("ativo"),1);
            querye.select(raiz).where(predicadp,predicado2);
            return entitymanager.createQuery(querye).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    
    }
    public ResultadoIo<Boolean> salvarProduto(EntidadeProdutoServico p){
     var entitymanager =gerenciadorDeEntidades.getManager();
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(p);
            entitymanager.getTransaction().commit();
            return new ResultadoIo.OK<>(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        } finally {
            entitymanager.clear();
            entitymanager.close();
        }
    }
    
    public ResultadoIo<Boolean> editarProduto(EntidadeProdutoServico p){
     var entitymanagager =gerenciadorDeEntidades.getManager();
        try {
            entitymanagager.getTransaction().begin();
            entitymanagager.merge(p);
            entitymanagager.getTransaction().commit();
            return new ResultadoIo.OK<>(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        } finally {
            entitymanagager.clear();
            entitymanagager.close();
        }
    }
    
    public ResultadoIo<EntidadeProdutoServico> produtoproId(Long id){
    var entitimangar =gerenciadorDeEntidades.getManager();
        try {
            var criteria =entitimangar.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeProdutoServico.class);
            var raiz=querye.from(EntidadeProdutoServico.class);
            var predicado =criteria.equal(raiz.get("id"),id);
            querye.select(raiz).where(predicado);
            var reusultado =entitimangar.createQuery(querye).getSingleResultOrNull();
            if(reusultado!=null)return new ResultadoIo.OK<>(reusultado);
            
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            
        } finally {
        entitimangar.clear();
        entitimangar.close();
        }
    }
    
   
    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
}
