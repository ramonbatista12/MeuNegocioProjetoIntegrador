/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaRequisicoes;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnumPesquisaRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleRequisicoes implements IFinalizar{
    private GerenciadorThreadPool gerenciadorDeThreads;
    private Repositorio repositorio;
    private Estado<List<DadosDaRequisicao>> estadoDaLista = new Estado<>(new ArrayList<>());
    private Estado<RespostaDeLoad<Void>> estadoLoadListas= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>> estadoLoadExclusao= new Estado<>(new RespostaDeLoad.OKVasio());
    public ControleRequisicoes(GerenciadorThreadPool g,Repositorio r){
     this.gerenciadorDeThreads=g;
     this.repositorio=r;
    }
    
    public void caregarListaInical(){
    var futuro = gerenciadorDeThreads.submeterPoolIO(()->repositorio.listarRequisicoes());
    futuro.exceptionally((ex)->{
     ex.printStackTrace();
     return new ArrayList<>();
    });
    futuro.thenAccept((l)->estadoDaLista.notificar((List<DadosDaRequisicao>) l));
            
    }
    public Estado<List<DadosDaRequisicao>> getEstadoDaLista(){return estadoDaLista;}

    public Estado<RespostaDeLoad<Void>> getEstadoLoadListas() {
        return estadoLoadListas;
    }

    public ControleRequisicoes() {
    }

    public Estado<RespostaDeLoad<Void>> getEstadoLoadExclusao() {
        return estadoLoadExclusao;
    }
    private void pesquisarPorRequisicao(PesquisaRequisicoes.ID pesquisa){
    gerenciadorDeThreads.submeterPoolIO(()->repositorio.pesquisaRequisicao(pesquisa))
                        .exceptionally((ex)->{
                        ex.printStackTrace();
                        estadoLoadListas.notificar(new RespostaDeLoad.Erro<>("Erro ao pesquisar Pela Requisicao"));
                        return new ArrayList<>();
                        })
                        .thenAccept((l)->{
                        estadoDaLista.notificar((List<DadosDaRequisicao>) l);
                        estadoLoadListas.notificar(new RespostaDeLoad.OK<>(null));
                        });
    }
   
    private void pesquisarPorCliente(PesquisaRequisicoes.NomeCliente pesquisa){
    gerenciadorDeThreads.submeterPoolIO(()->repositorio.pesquisaRequisicao(pesquisa))
                        .exceptionally((ex)->{
                        ex.printStackTrace();
                        estadoLoadListas.notificar(new RespostaDeLoad.Erro<>("Erro ao pesquisar Pelo Nome do cliente"));
                        return new ArrayList<>();
                        })
                        .thenAccept((l)->{
                        estadoDaLista.notificar((List<DadosDaRequisicao>) l);
                        estadoLoadListas.notificar(new RespostaDeLoad.OK<>(null));
                        });
    }
    
    private void pesquisarPorEstado(PesquisaRequisicoes.Estado pesquisa){
    gerenciadorDeThreads.submeterPoolIO(()->repositorio.pesquisaRequisicao(pesquisa))
                        .exceptionally((ex)->{
                        ex.printStackTrace();
                        estadoLoadListas.notificar(new RespostaDeLoad.Erro<>("Erro ao pesquisar Pelo estado"));
                        return new ArrayList<>();
                        })
                        .thenAccept((l)->{
                        estadoDaLista.notificar((List<DadosDaRequisicao>) l);
                        estadoLoadListas.notificar(new RespostaDeLoad.OK<>(null));
                        });
    }
    
    public void pesquisar(EnumPesquisaRequisicao e,String p ){
    gerenciadorDeThreads.submeterPoolDefalt(()->{
    estadoLoadListas.notificar(new RespostaDeLoad.Load<>());
    if(p.isBlank()){
        estadoLoadListas.notificar(new RespostaDeLoad.OK<>(null));
        caregarListaInical();
        return;}
        switch (e) {
            case EnumPesquisaRequisicao.Cliente->{
                pesquisarPorCliente(new PesquisaRequisicoes.NomeCliente(p));
            }
            case EnumPesquisaRequisicao.Estado->{
                pesquisarPorEstado(new PesquisaRequisicoes.Estado(p));
            }
            case EnumPesquisaRequisicao.Requisicao->{
            Long id;
                try {
                    id = Long.parseLong(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    estadoLoadListas.notificar(new RespostaDeLoad.Erro ("Para pesquisar por requisicao e nessesario digitar um numero inteiro "));
                    return;
                }
                pesquisarPorRequisicao(new PesquisaRequisicoes.ID(id));
                
            }
        }
    });
        
    }
    
    public void Escluir(DadosDaRequisicao d){
    gerenciadorDeThreads.submeterPoolIO(()->{
                         estadoLoadExclusao.notificar(new RespostaDeLoad.Load<>());
                         return repositorio.excluirRequisicao(d);})
                        .exceptionally((ex)->{
                        ex.printStackTrace();
                        return new ResultadoIo.Erro<>("Erro ao excluir requisicao ");
                        }).thenAccept((r)->{
                        if(r instanceof ResultadoIo.Erro er){
                        estadoLoadExclusao.notificar(new RespostaDeLoad.Erro<>(er.Mensagem()));
                        }
                        if(r instanceof ResultadoIo.OK){
                        estadoLoadExclusao.notificar(new RespostaDeLoad.OK<>(null));
                        caregarListaInical();
                        }
                        });
    }
    
    @Override
    public void finalizar() {
    estadoDaLista.clear();
    estadoDaLista=null;
    this.gerenciadorDeThreads=null;
    repositorio=null;
    
    }
    
}
