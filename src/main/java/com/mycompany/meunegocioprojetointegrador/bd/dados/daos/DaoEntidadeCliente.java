/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.Par;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.ResultadoDeOperacoes;
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
       return g.executar(escopo->{
           var criteria= escopo.getCriteriaBuilder();
           var querye=criteria.createQuery(EntidadeCliente.class);
           var raiz =querye.from(EntidadeCliente.class);
           var predicado= criteria.like(raiz.get("nome"), "%"+nome+"%");
           raiz.fetch("telefones", JoinType.LEFT);
           querye.select(raiz).where(predicado);
           return escopo.selectList(querye) ;
       }, erro->{
        erro.printStackTrace();
        return new ArrayList<>();
       });
    /*var entitimanager = g.getManager();
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
        }*/
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
        return  g.executar(escopo->{
               var criteria=escopo.getCriteriaBuilder();
               var querye=criteria.createQuery(EntidadeCliente.class);
               var raiz =querye.from(EntidadeCliente.class);
               var predicado= criteria.like(raiz.get("cnpj"), "%"+cnpj+"%");
               raiz.fetch("telefones", JoinType.LEFT);
               querye.select(raiz).where(predicado);
               return escopo.selectList(querye);},
               erro->{
               erro.printStackTrace();
               return new ArrayList<>();});
    /*var entitimanager = g.getManager();
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
        }*/
    }
    
    public List<EntidadeCliente> listaDeClientes(){
        
       return g.executar(
         escopo->{
            System.out.println("caregando lista de clientes");
            var criteria=escopo.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            raiz.fetch("telefones", JoinType.LEFT);
            querye.select(raiz);
            return escopo.selectList(querye);
        },
        erro->{
         erro.printStackTrace();
         return  new ArrayList<>();
        });
        /*var entitiManager =g.getManager();
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
        */
        
    }
    
    public ResultadoIo<EntidadeCliente> obterCliente(Long id){
      return g.executar(
              escopo->{
                 var criteria=escopo.getCriteriaBuilder();
                 var query=criteria.createQuery(EntidadeCliente.class);
                 var raiz =query.from(EntidadeCliente.class);
                 var predicado=criteria.equal(raiz.get("id"),id);
                 query.select(raiz).where(predicado);
                 var cliente= escopo.selectResultadoUnico(query);
                 if (cliente==null) return  new ResultadoIo.Erro<>("Cliente nao encontrado");
                 else return new ResultadoIo.OK(cliente);
        }, erro->{
        erro.printStackTrace();
        return new ResultadoIo.Erro(RespostaDefault.NaoEncontrado.getMenssagen());
        });
        

    }
    
    public ResultadoIo<Void> salvarCliente(EntidadeCliente entidadeCliente,List<EntidadeEndereco>listaEntidadeEndereco,List<EntidadeTelefone>listaEntidadeCliente){
        return g.executarEntransacao(escopo->{
        listaEntidadeEndereco.forEach((e)->{
            e.setIdCliente(entidadeCliente.getId());
            
            });
            listaEntidadeCliente.forEach((t)->{
            t.setIdCliente(entidadeCliente.getId());
            
            });
            entidadeCliente.setEnderecos(listaEntidadeEndereco);
            entidadeCliente.setTelefones(listaEntidadeCliente);
            escopo.persist(entidadeCliente)  ;
            return new ResultadoIo.OK(null);
        },e->{
        e.printStackTrace();
        return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        });
        

    }
    
    public ResultadoIo<Void> editarCliente(EntidadeCliente entidadeCliente,List<EntidadeEndereco>listaEntidfadeEndereco,List<EntidadeTelefone> listaEntidadeTelefone){
    
       return g.executarEntransacao(escopo->{
                listaEntidfadeEndereco.forEach((e)->{
                e.setIdCliente(entidadeCliente.getId()); });
                listaEntidadeTelefone.forEach((t)->{
                t.setIdCliente(entidadeCliente.getId());});
                entidadeCliente.setTelefones(listaEntidadeTelefone);
                entidadeCliente.setEnderecos(listaEntidfadeEndereco);
                escopo.merge(entidadeCliente);
                return new ResultadoIo.OK<>(null);
                }, 
                erro->{
                 erro.printStackTrace();   
                 return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        
                });
        
        
        
    }
   
    public EntidadeCliente clientePorId(Long id){
        return  g.executar(escopo->{
            var criteria=escopo.getCriteriaBuilder();
            var querye =criteria.createQuery(EntidadeCliente.class);
            var raiz =querye.from(EntidadeCliente.class);
            raiz.fetch("telefones", JoinType.LEFT);
            var predicado =criteria.equal(raiz.get("id"),id);
            querye.select(raiz).where(predicado);
            return escopo.selectResultadoUnico(querye);
        }, erro->{
        erro.printStackTrace();
        return null;
        });
    
    }/*
    
              escopo.nativeQuery("delete from historicorequisicoes where id_req in (select id from requisicao where id_cli =:id)",
                                 new Par<String,Object>[]{new Par<>("id",e.getId())});
              escopo.nativeQuery("delete from requisicao where id_cli =:id",
                                 new Par<String,Object>[]{mew Par<>("id",e.getId())}); },*/
    public ResultadoIo<Void> apagarCliente(EntidadeCliente e){
    
    return g.executarEntransacao(
              escopo->{
              List<Par<String,Object>> argumento= new ArrayList<Par<String,Object>>();
              argumento.add(new Par<>("id",e.getId()));
              escopo.nativeQuery("delete from produto_selecionado where id_req in (select id from requisicao where id_cli =:id)", 
                                 argumento);
              escopo.nativeQuery("delete from historicorequisicoes where id_req in (select id from requisicao where id_cli =:id)", 
                                 argumento);
              escopo.nativeQuery("delete from requisicao where id_cli =:id", argumento);
              
              var cliente=escopo.getEntityManager().find(EntidadeCliente.class, e.getId());
              escopo.remove(cliente);
              return new ResultadoIo.OK<>(null);
              },
             
              erro->{
              erro.printStackTrace();
              return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        
              });
        
   
    }
    
    @Override
    public void finalizar() {
    this.g=null;
    }
}
