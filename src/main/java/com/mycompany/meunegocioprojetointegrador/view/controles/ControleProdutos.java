/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaProdutosServico;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnumPesquisaProdutos;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleProdutos implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Repositorio repositorio;
    private Estado<List<ProdutoServico>> estadoDaLista= new Estado<>(new ArrayList<>());
    private Estado<RespostaDeLoad<Void>> estadoLoadLista= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<Boolean> estadoListaAtivo=new Estado<>(false);
    public ControleProdutos(GerenciadorThreadPool gerenciadorThreadPool, Repositorio repositorio) {
        this.gerenciadorThreadPool = gerenciadorThreadPool;
        this.repositorio = repositorio;
    }
    public void pesquisar(EnumPesquisaProdutos e,String p){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
        if(p.isBlank()){caregarListaInicial();return;}
        switch (e) {
            case EnumPesquisaProdutos.Id->{
             var stringChecagem =p.replaceAll("\\D", "");
             long id =0;
             if(stringChecagem.isBlank()){
             estadoLoadLista.notificar(new RespostaDeLoad.Erro<>("Para pesquisar por id e nessesaria digitar apenas Numeros inteiros"));
             return;
             }
             try {
                   id=Long.parseLong(p);
                } catch (Exception ex) {
                    estadoLoadLista.notificar(new RespostaDeLoad.Erro<>("Para pesquisar por id e nessesaria digitar um numero Inteiro"));
                    return;
                }
             if(id<=0){
             estadoLoadLista.notificar(new RespostaDeLoad.Erro<>("Para pesquisar por id e nessesaria digitar um numero inteiro positivo"));
             return;
             }
                pesquisarProId(new PesquisaProdutosServico.Id(id));
              
            }
            case EnumPesquisaProdutos.Nome->{
                pesquisarPorNome(new PesquisaProdutosServico.Nome(p));
            }
        }
    });
    }
    
    private void pesquisarProId(PesquisaProdutosServico.Id p){
    gerenciadorThreadPool.submeterPoolIO(()->repositorio.pesquisarProduto(p))
                         .exceptionally((ex)->{
                             estadoLoadLista.notificar(new RespostaDeLoad.Erro<>("Falha ao pesquisar produto"));
                             return new ArrayList<>();
                          })
                         .thenAccept((l)->{
                          estadoDaLista.notificar((List<ProdutoServico>) l);
                          estadoLoadLista.notificar(new RespostaDeLoad.OK(null));
                         estadoListaAtivo.notificar(false);
                         });
    }
    
    private void pesquisarPorNome(PesquisaProdutosServico.Nome p){
    gerenciadorThreadPool.submeterPoolIO(()->repositorio.pesquisarProduto(p))
                         .exceptionally((ex)->{
                         estadoLoadLista.notificar(new RespostaDeLoad.Erro("Erro ao pesquisar produto"));
                         return new ArrayList<>();
                         })
                         .thenAccept((l)->{
                         estadoDaLista.notificar((List<ProdutoServico>) l);
                         estadoListaAtivo.notificar(false);
                         estadoLoadLista.notificar(new RespostaDeLoad.OK(null));
                         });
    }
    public void caregarListaInicial(){
    var futuro=gerenciadorThreadPool.submeterPoolIO(()->{
        estadoLoadLista.notificar(new RespostaDeLoad.Load<>());
        return repositorio.listarProdutos();});
    futuro.exceptionally((ex)->{
     ex.printStackTrace();
     estadoLoadLista.notificar(new RespostaDeLoad.Erro<>("Nao foi posivel caregar lista"));
     return new ArrayList<>();
    });
    futuro.thenAccept((l)->{
        estadoDaLista.notificar((List<ProdutoServico>) l);
        estadoLoadLista.notificar(new RespostaDeLoad.OK(null));
    });
    }

    public Estado<RespostaDeLoad<Void>> getEstadoLoadLista() {
        return estadoLoadLista;
    }
    
    
    public Estado<List<ProdutoServico>> getEstadoDaLista(){return estadoDaLista;}

    public Estado<Boolean> getEstadoListaAtivo() {
        return estadoListaAtivo;
    }
    
    
    @Override
    public void finalizar() {
    gerenciadorThreadPool=null;
    repositorio=null;
    estadoDaLista.clear();
    estadoDaLista=null;
    }
    
    
}
