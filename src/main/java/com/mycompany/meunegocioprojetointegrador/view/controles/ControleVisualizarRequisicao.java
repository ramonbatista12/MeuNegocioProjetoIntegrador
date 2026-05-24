/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.EnunEstados;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.HistoricoDeMudancas;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarRequisicoa.SubEstadosListaInternasVisualizarRequisicoes;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleVisualizarRequisicao implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private  Repositorio repositorio;
    private  Estado<Double> estadoTotal= new Estado<>(0.0);
    private  Estado<List<ProdutoSelecionado>> estadoListaProdutos = new Estado<>(new ArrayList<>());
    private  Estado<List<HistoricoDeMudancas>> estadoHistoricoDeMudancas = new Estado<>(new ArrayList<>());
    private  Estado<DadosDaRequisicao> estadoDadosDaRequisicao = new Estado<>(null);
    private Estado<SubEstadosListaInternasVisualizarRequisicoes> listaVisualizada = new Estado<>(new SubEstadosListaInternasVisualizarRequisicoes.ListaDeProdutos());
    private Estado<RespostaDeLoad<Void>> loadOperacoes = new Estado<>(new RespostaDeLoad.OKVasio());
    public ControleVisualizarRequisicao(GerenciadorThreadPool g,Repositorio r,DadosDaRequisicao d){
    gerenciadorThreadPool=g;
    repositorio=r;
    estadoDadosDaRequisicao.notificar(d);
    }
    
    public void MudarLista(){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
     var  prosimo = listaVisualizada.getValot().getProsimo();
     listaVisualizada.notificar(prosimo);
    });
    }
    public void caregarListaDeProdutos(){
        var  id =estadoDadosDaRequisicao.getValot().getRequisicao().getId();
    var futuro =gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarProdutosSelecionados(id));
    futuro.exceptionally((ex)->{
    ex.printStackTrace();
    return  new ArrayList<>();
    });
    futuro.thenAccept((l)->{
    System.out.println("o valor da lista "+l);        
    estadoListaProdutos.notificar((List<ProdutoSelecionado>) l);
    });
    }
    public void  caregarTotal(){
        var  id =estadoDadosDaRequisicao.getValot().getRequisicao().getId();
    var futuro =gerenciadorThreadPool.submeterPoolIO(()->repositorio.totalProdutosSelecionados(id));
    futuro.exceptionally((ex)->{
    ex.printStackTrace();
    return  0.0;
    });
    futuro.thenAccept((d)->{
        System.out.println("o valor do double "+d);    
    estadoTotal.notificar((Double)d);
    });
    }
    
    public void caregarListaDoHistoricoDeMudancas(){
    var  id =estadoDadosDaRequisicao.getValot().getRequisicao().getId();
    var futuro =gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarHistoicoDeMudancasParaRequisicao(id));
    futuro.exceptionally((ex)->{
    ex.printStackTrace();
    return  new ArrayList<>();
    });
    futuro.thenAccept((l)->{
           
    estadoHistoricoDeMudancas.notificar((List<HistoricoDeMudancas>) l);
    });
    
    }
    
    public void caregarEstadoDadosDaRequisicao(){
    gerenciadorThreadPool.submeterPoolIO(()->{
    var id =estadoDadosDaRequisicao.getValot().getRequisicao().getId();
    return repositorio.requisicaoPorId(id);
    }).exceptionally((ex)->{
    ex.printStackTrace();
    return new ResultadoIo.Erro<>("Erro ao atualizar Requisicao");
    }).thenAccept((resposta)->{
    if(resposta instanceof ResultadoIo.OK ok){
    estadoDadosDaRequisicao.notificar((DadosDaRequisicao) ok.r());
    }
    if(resposta instanceof ResultadoIo.Erro er){
    loadOperacoes.notificar(new RespostaDeLoad.Erro<>("Erro ao Atualizar os dados da Requisicao"));
    }
    if(resposta instanceof  ResultadoIo.ErroVasio){
    loadOperacoes.notificar(new RespostaDeLoad.Erro<>("Erro ao Atualizar os dados da Requisicao"));
    
    }
    });
    }
    public void updateEstado(EnunEstados e){
    gerenciadorThreadPool.submeterPoolIO(()->{
        System.out.println("iniciando auteracao do estado "+e);
        loadOperacoes.notificar(new RespostaDeLoad.Load<>());
       
     var dadoRequiscaoAuxiliar =estadoDadosDaRequisicao.getValot();
     dadoRequiscaoAuxiliar.setEstado(new com.mycompany.meunegocioprojetointegrador.bd.dominio.Estado(e.getId(),e.getDescricao()));
     return repositorio.editarRequisicao(dadoRequiscaoAuxiliar);
    }).exceptionally((ex)->{
    ex.printStackTrace();
    return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
    }).thenAccept((r)->{
        System.out.println("retorna da computacao"+r);
    if(r instanceof ResultadoIo.OK){
        System.out.println("retorna da computacao e uma instancia de OK<>");
        loadOperacoes.notificar(new RespostaDeLoad.OK<>(null));
        caregarEstadoDadosDaRequisicao();
        caregarListaDoHistoricoDeMudancas();
    }
    if(r instanceof ResultadoIo.ErroVasio){
        loadOperacoes.notificar(new RespostaDeLoad.Erro<>("Erro ao mudar estado da requisicao"));
    }
    if(r instanceof ResultadoIo.Erro er){
        loadOperacoes.notificar(new RespostaDeLoad.Erro<>("Erro ao mudar estado da requisicao"));
    }
    
    });
    }
    
    public Estado<Double> getEstadoTotal() {
        return estadoTotal;
    }

    public Estado<List<ProdutoSelecionado>> getEstadoListaProdutos() {
        return estadoListaProdutos;
    }

    public Estado<DadosDaRequisicao> getEstadoDadosDaRequisicao() {
        return estadoDadosDaRequisicao;
    }

    public Estado<List<HistoricoDeMudancas>> getEstadoHistoricoDeMudancas() {
        return estadoHistoricoDeMudancas;
    }
    
    public ControleVisualizarRequisicao(GerenciadorThreadPool g,Repositorio r){
    this.gerenciadorThreadPool=g;
    this.repositorio=r;
    }

    public Estado<SubEstadosListaInternasVisualizarRequisicoes> getListaVisualizada() {
        return listaVisualizada;
    }

    public Estado<RespostaDeLoad<Void>> getLoadOperacoes() {
        return loadOperacoes;
    }
    
    
    @Override
    public void finalizar() {
     
     this.estadoListaProdutos.clear();
     this.estadoDadosDaRequisicao.clear();
     this.estadoTotal.clear();
     this.listaVisualizada.clear();
     this.loadOperacoes.clear();
     this.estadoListaProdutos=null;
     this.estadoDadosDaRequisicao=null;
     this.listaVisualizada=null;
     this.estadoTotal=null;
     loadOperacoes=null;
     gerenciadorThreadPool=null;
     repositorio=null;
    }
}
