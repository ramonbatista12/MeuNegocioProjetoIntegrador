/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import jakarta.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class DaoEntidadeCliente implements IFinalizar{
    private GerenciadorDeEntidades g;
    
    public DaoEntidadeCliente(GerenciadorDeEntidades g){
     this.g =g;
    }
    
    public List<EntidadeCliente> pesquisarClientePorNome(String nome){
    var entitimanager = g.getManager();
        try {
            var criteria=entitimanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            var predicado= criteria.like(raiz.get("nome"), "%"+nome+"%");
            raiz.fetch("telefones", JoinType.LEFT);
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
    
    public List<EntidadeCliente> pesquisarClientePorCpf(String cpf){
    var entitimanager = g.getManager();
        try {
            var criteria=entitimanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            var predicado= criteria.like(raiz.get("cpf"), "%"+cpf+"%");
            raiz.fetch("telefones", JoinType.LEFT);
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
    
    public List<EntidadeCliente> pesquisarClientePorCnpj(String cnpj){
    var entitimanager = g.getManager();
        try {
            var criteria=entitimanager.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            var predicado= criteria.like(raiz.get("cnpj"), "%"+cnpj+"%");
            raiz.fetch("telefones", JoinType.LEFT);
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
    
    public List<EntidadeCliente> listaDeClientes(){
        var entitiManager =g.getManager();
        try {
            System.out.println("caregando lista de clientes");
            var criteria =entitiManager.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            raiz.fetch("telefones", JoinType.LEFT);
            querye.select(raiz);
            return entitiManager.createQuery(querye).getResultList();
            
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            entitiManager.clear();
            entitiManager.clear();
        }
        
        
    }
    
    public ResultadoIo<EntidadeCliente> obterCliente(Long id){
        var entitymanger=g.getManager();
        try {
            var criteria=entitymanger.getCriteriaBuilder();
            var querye=criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            var predicado=criteria.equal(raiz.get("id"),id);
            querye.select(raiz).where(predicado);
            var cliente = entitymanger.createQuery(querye).getSingleResultOrNull();
            if(cliente==null)return new ResultadoIo.Erro<>(RespostaDefault.Inesistente.getMenssagen());
            return new ResultadoIo.OK<>(cliente);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.Desconhesido.getMenssagen());
        }finally{
         entitymanger.clear();
         entitymanger.close();
        }
    }
    
    public ResultadoIo<Void> salvarCliente(EntidadeCliente c,List<EntidadeEndereco>le,List<EntidadeTelefone>lt){
    var  entitimanager = g.getManager();
        try {
            entitimanager.getTransaction().begin();
           
            
            le.forEach((e)->{
            e.setIdCliente(c.getId());
            
            });
            lt.forEach((t)->{
            t.setIdCliente(c.getId());
            
            });
            c.setEnderecos(le);
            c.setTelefones(lt);
            entitimanager.persist(c);
            entitimanager.getTransaction().commit();
            return new ResultadoIo.OK<>(null);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ouve um erro ");
            entitimanager.getTransaction().rollback();
            return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        } finally {
            entitimanager.clear();
            entitimanager.close();
        }
    }
    
    public ResultadoIo<Void> editarCliente(EntidadeCliente c,List<EntidadeEndereco>le,List<EntidadeTelefone> lt){
    var entitimanager = g.getManager();
        try {
            entitimanager.getTransaction().begin();
            
           
           
            le.forEach((e)->{
             e.setIdCliente(c.getId());
            });
            lt.forEach((t)->{
             
             t.setIdCliente(c.getId());
             
            });
            c.setTelefones(lt);
            c.setEnderecos(le);
            entitimanager.merge(c);
            entitimanager.getTransaction().commit();
            return new ResultadoIo.OK<>(null);
        } catch (Exception e) {
            e.printStackTrace();
            entitimanager.getTransaction().rollback();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        } finally {
            entitimanager.clear();
            entitimanager.close();
            System.err.println("finalizamento do entitimanfer isso signid=fic aqu eo metodo ja retorno");
        }
    }
   
    public EntidadeCliente clientePorId(Long id){
            var entitiManager =g.getManager();
        try {
            System.out.println("caregando lista de clientes");
            var criteria =entitiManager.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            raiz.fetch("telefones", JoinType.LEFT);
            var predicado =criteria.equal(raiz.get("id"),id);
            querye.select(raiz).where(predicado);
            return entitiManager.createQuery(querye).getSingleResultOrNull();
            
            
        }catch(Exception e){
            e.printStackTrace();
           return null;
        } finally {
            entitiManager.clear();
            entitiManager.clear();
        }
    }
    
    public ResultadoIo<Void> apagarCliente(EntidadeCliente e){
    var entitimanager=  g.getManager();
        try {
          entitimanager.getTransaction().begin();
          var query1=entitimanager.createNativeQuery("delete from produto_selecionado where id_req in (select id from requisicao where id_cli =:id)");
          query1.setParameter("id", e.getId()).executeUpdate();
          var querye2=entitimanager.createNativeQuery("delete from historicorequisicoes where id_req in (select id from requisicao where id_cli =:id)");
          querye2.setParameter("id", e.getId()).executeUpdate();
          var querye3=entitimanager.createNativeQuery("delete from requisicao where id_cli =:id");
          querye3.setParameter("id",e.getId()).executeUpdate();
         var entidade=entitimanager.find(EntidadeCliente.class, e.getId());
         entitimanager.remove(entidade);
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
    this.g=null;
    }
}
