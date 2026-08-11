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
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.IEscopoOperacoes;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.EnunEstados;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.dto.JuncaoEntidadeRequisicaoClienteEstado;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.Rotas;
import jakarta.persistence.criteria.CriteriaQuery;
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
    return gerenciadorDeEntidade.executar(
            escopo->{
            var criteria=escopo.getCriteriaBuilder();
            var query= criteria.createQuery(EntidadeRequisicao.class);
            var rais=query.from(EntidadeRequisicao.class);
            rais.fetch("estado", JoinType.LEFT);
            rais.fetch("cliente", JoinType.LEFT);
            query.select(rais);
            return escopo.selectList(query);
            
            },
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
   
   }
   public ResultadoIo<Void> salvarRequisicao(EntidadeRequisicao e,List<EntidadeProdutoSelecionado> l){
   
    return gerenciadorDeEntidade.executarEntransacao(
            escopo->{
            var estado=new EntidadeEstado();
            estado.setId(EnunEstados.Pendendte.getId());
            estado.setDescricao(EnunEstados.Pendendte.getDescricao());
            e.setEstado(estado);
            System.out.println("Estado escolhido "+estado.getId()+" decricao "+estado.getDescricao());
            escopo.persist(e);
            l.forEach((p)->{
            p.setIdRequisicao(e.getId());
            escopo.persist(p);});
            return new  ResultadoIo.OK(null);
            },
            erro->{
            erro.printStackTrace();
            return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       
            });
       
   }
   
   public ResultadoIo<Void> updateRequisicao(EntidadeRequisicao e){
    
    return gerenciadorDeEntidade.executarEntransacao(
            escopo->{
            escopo.merge(e);
            System.out.println("update ocoreu");
            return new  ResultadoIo.OK(null);
            },
            erro->{ 
            erro.printStackTrace();
            return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       
            });
    
   }
   
   public ResultadoIo<Void> updateRequisicao(EntidadeRequisicao entidadeRequisicao,List<EntidadeProdutoSelecionado>listaEntidadeProduto){
    
    return gerenciadorDeEntidade.executarEntransacao(escopo->{
            var criteriaBuilder= escopo.getCriteriaBuilder();
            var queryDelete =criteriaBuilder.createCriteriaDelete(EntidadeProdutoSelecionado.class);
            var raiz =queryDelete.from(EntidadeProdutoSelecionado.class);
            var predicado=criteriaBuilder.equal(raiz.get("idRequisicao"),entidadeRequisicao.getId());
            queryDelete.where(predicado);
            escopo.deletCriteria(queryDelete );
            escopo.merge(entidadeRequisicao);
            listaEntidadeProduto.forEach((p)->{
                       p.setId(null);
                       p.setIdRequisicao(entidadeRequisicao.getId());
                       System.out.println("o id setado em p "+entidadeRequisicao.getId());
                       escopo.merge(p);});
           
           return new  ResultadoIo.OK(null);   
            },
            erro->{ 
            erro.printStackTrace();
            return new ResultadoIo.Erro(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            });
      
   
   }
   
   public ResultadoIo<EntidadeRequisicao> requisicaoPorId(Long id){
       
    return gerenciadorDeEntidade.executar(
            escopo->{
            var criteria=escopo.getCriteriaBuilder();
            var query= criteria.createQuery(EntidadeRequisicao.class);
            var rais=query.from(EntidadeRequisicao.class);
            rais.fetch("estado", JoinType.LEFT);
            rais.fetch("cliente", JoinType.LEFT);
            var presicado=criteria.equal(rais.get("id"),id);
            query.select(rais).where(presicado);
            var resultado = escopo.selectResultadoUnico( query);
            if(resultado!=null)return new ResultadoIo.OK<>(resultado);   
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
      
            },
            erro->{
            erro.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
       
            });
        
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
    return gerenciadorDeEntidade.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query =criteria.createQuery(EntidadeRequisicao.class);
            var raiz =query.from(EntidadeRequisicao.class);
            raiz.fetch("estado", JoinType.LEFT);
            raiz.fetch("cliente", JoinType.LEFT);
            var join =raiz.join("estado",JoinType.LEFT);
            var predicado=criteria.like(join.get("descricao"),"%"+estado+"%");
            query.select(raiz).where(predicado);
            return escopo.selectList(query);    
            },
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();    
            });
     
   }
   
   public List<EntidadeRequisicao> listarequisicaoPorId(Long id){
    return gerenciadorDeEntidade.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query =criteria.createQuery(EntidadeRequisicao.class);
            var raiz =query.from(EntidadeRequisicao.class);
            raiz.fetch("estado", JoinType.LEFT);
            raiz.fetch("cliente", JoinType.LEFT);
            var predicado =criteria.equal(raiz.get("id"), id);
            query.select(raiz).where(predicado);
            return escopo.selectList( query);
            },
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
      
    
   
   }
   
   public ResultadoIo<Void> excluirRequisicao(EntidadeRequisicao e){
    return gerenciadorDeEntidade.executarEntransacao(
            escopo->{
            this.excluirHistoricoDeRequisicao(escopo,e.getId());
            this.excluirProdutosRequisitados(escopo,e.getId());
            this.excluirRequisicao(escopo,e.getId());
            return new ResultadoIo.OK<>(null);
            }, 
            erro->{
            erro.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            });
   
   }
   
   private void excluirHistoricoDeRequisicao(IEscopoOperacoes escopo,Long id){
           var criteria=escopo.getCriteriaBuilder();
           var criteriaDelete=criteria.createCriteriaDelete(EntidadeHistoricoRequisicao.class);
           var raiz1=criteriaDelete.from(EntidadeHistoricoRequisicao.class);
           var predicado1=criteria.equal(raiz1.get("idRequisicao"),id);
           criteriaDelete.where(predicado1);
           escopo.deletCriteria(criteriaDelete);
       
   }
   
   private void excluirProdutosRequisitados(IEscopoOperacoes escopo,Long id){ 
           var criteria=escopo.getCriteriaBuilder();
           var criteriaDelete=criteria.createCriteriaDelete(EntidadeProdutoSelecionado.class);
           var raiz=criteriaDelete.from(EntidadeProdutoSelecionado.class);
           var predicado=criteria.equal(raiz.get("idRequisicao"), id);
           criteriaDelete.where(predicado);
           escopo.deletCriteria(criteriaDelete);
           
   }
   
   private void excluirRequisicao(IEscopoOperacoes escopo,Long id){
           var criteria=escopo.getCriteriaBuilder();
           var criteriaDelete=criteria.createCriteriaDelete(EntidadeRequisicao.class);
           var raiz=criteriaDelete.from(EntidadeRequisicao.class);
           var predicado=criteria.equal(raiz.get("id"),id);
           criteriaDelete.where(predicado);
           escopo.deletCriteria(criteriaDelete);
           
   
   }
    @Override
    public void finalizar() {
     gerenciadorDeEntidade=null;
    }
}
