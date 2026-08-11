/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.daos;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import jakarta.persistence.criteria.CriteriaQuery;
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
        
    return gerenciadorDeEntidades.executar(
             escopo->{
             var criteria =escopo.getCriteriaBuilder();
             var query =criteria.createQuery(EntidadeProdutoServico.class);
             var rais =query.from(EntidadeProdutoServico.class);
             query.select(rais);
             return escopo.selectList(query);
             },
             erro->{
             erro.printStackTrace();
             return new ArrayList<>();
             });
    
    }
     
    public List<EntidadeProdutoServico> listarProdutosAtivos(){
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz =query.from(EntidadeProdutoServico.class);
            var predicado1=criteria.equal(raiz.get("ativo"),1 );
            query.select(raiz).where(predicado1);
            return escopo.selectList(query);
            },
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });

    
    }
    
    public List<EntidadeProdutoServico> listarProdutosPorNome(String nome){
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz =query.from(EntidadeProdutoServico.class);
            var predicadao=criteria.like(raiz.get("nome"),"%"+nome+"%");
            query.select(raiz).where(predicadao);
            return escopo.selectList(query);
            },
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
  
    }
    
    public List<EntidadeProdutoServico> listarProdutosAtivosPorNome(String nome){
     return gerenciadorDeEntidades.executar(
             escopo->{
             var criteria =escopo.getCriteriaBuilder();
             var query=criteria.createQuery(EntidadeProdutoServico.class);
             var raiz =query.from(EntidadeProdutoServico.class);
             var predicadao=criteria.like(raiz.get("nome"),"%"+nome+"%");
             var predicado2=criteria.equal(raiz.get("ativo"),1);
             query.select(raiz).where(predicadao,predicado2);
             return escopo.selectList(query);
             }, 
             erro->{
             erro.printStackTrace();
             return new ArrayList<>();
             });
    
    }
    

    

    public List<EntidadeProdutoServico> listarProdutoPorId(Long id){
    
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria=escopo.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz = query.from(EntidadeProdutoServico.class);
            var predicadp=criteria.equal(raiz.get("id"), id);
            query.select(raiz).where(predicadp);
            return  escopo.selectList(query);
            }, 
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
    
    
    }
    
    public List<EntidadeProdutoServico> listarProdutosAtivoPorId(Long id){
    
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria=escopo.getCriteriaBuilder();
            var query=criteria.createQuery(EntidadeProdutoServico.class);
            var raiz = query.from(EntidadeProdutoServico.class);
            var predicadp=criteria.equal(raiz.get("id"), id);
            var predicado2=criteria.equal(raiz.get("ativo"),1);
            query.select(raiz).where(predicadp,predicado2);
            return escopo.selectList(query);
            }, 
            erro->{
            erro.printStackTrace();
            return new ArrayList<>();
            });
    
    }
    public ResultadoIo<Boolean> salvarProduto(EntidadeProdutoServico p){
      return gerenciadorDeEntidades.executar(
                escopo->{
                escopo.persist(p);
                return new ResultadoIo.OK<>(true);
                }, 
                erro->{
                erro.printStackTrace();
                return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
        
                });
     
    }
    
    public ResultadoIo<Boolean> editarProduto(EntidadeProdutoServico p){
     
    return  gerenciadorDeEntidades.executarEntransacao(
            escopo->{
            escopo.merge(p);
            return new ResultadoIo.OK<>(true);
            }, 
            erro->{
            erro.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            
            });
     
    }
    
    public ResultadoIo<EntidadeProdutoServico> produtoproId(Long id){
     
    return gerenciadorDeEntidades.executar(
            escopo->{
            var criteria =escopo.getCriteriaBuilder();
            var query =criteria.createQuery(EntidadeProdutoServico.class);
            var raiz=query.from(EntidadeProdutoServico.class);
            var predicado =criteria.equal(raiz.get("id"),id);
            query.select(raiz).where(predicado);
            var reusultado = escopo.selectResultadoUnico(query);
            if(reusultado!=null)return new ResultadoIo.OK<>(reusultado);
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            
            },
            erro->{
            erro.printStackTrace();
            return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
            
            });
    
    }
    
   
    @Override
    public void finalizar() {
     this.gerenciadorDeEntidades=null;
    }
}
