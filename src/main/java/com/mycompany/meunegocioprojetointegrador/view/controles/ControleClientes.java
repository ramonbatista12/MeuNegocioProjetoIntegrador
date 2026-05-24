/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;

import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnunPesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import com.mysql.cj.xdevapi.Client;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleClientes implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Repositorio repositorio;
    private Estado<List<DadosDoCliente>> estadoDaLista = new Estado<>(new ArrayList<>());
    private Estado<RespostaDeLoad<Void>> estadoLoad = new Estado<>(new RespostaDeLoad.OKVasio());
    public ControleClientes(GerenciadorThreadPool g,Repositorio r){
    repositorio=r;
    gerenciadorThreadPool=g;
    }
    
    public void caregarListaInicial(){
        System.err.println("mandadndo tarefa para o ezecutor caregar listaInical");  
     var completable =gerenciadorThreadPool.submeterPoolIO(()->{
         estadoLoad.notificar(new RespostaDeLoad.Load<>());
         return repositorio.listaDeClientes();
     });
     completable.exceptionally((ex)->{
         ex.printStackTrace();
         return  new ArrayList<>();
     }).thenAcceptAsync((l)->{
         System.out.println("lisata caregada "+l);
         estadoDaLista.notificar((List<DadosDoCliente>) l);
         estadoLoad.notificar(new RespostaDeLoad.OK<>(null));
     });
    }
    private void pesquisar(PesquisaClientes p){
    gerenciadorThreadPool.submeterPoolIO(()->{
     estadoLoad.notificar(new RespostaDeLoad.Load<>());
     return repositorio.listarPesquisaClientes(p);
    }).exceptionally((ex)->{
     ex.printStackTrace();
     return new ArrayList<>();
    }).thenAccept((l)->{
     estadoDaLista.notificar((List<DadosDoCliente>) l);
     estadoLoad.notificar(new RespostaDeLoad.OK<>(null));
    });
            ;
    }
    
    public void pesquisar(String pesquisa ,EnunPesquisaClientes tipo){
        if(pesquisa.isBlank()){
            pesquisar(new PesquisaClientes.PesquisaVasia());
            return;
        }
        switch (tipo) {
            case EnunPesquisaClientes.Nome->{pesquisar(new PesquisaClientes.PesquisaNome(pesquisa));}
            case EnunPesquisaClientes.Cnpj->{pesquisar(new PesquisaClientes.PesquisaCnpj(pesquisa));}
            case EnunPesquisaClientes.Cpf->{pesquisar(new PesquisaClientes.PesquisaCpf(pesquisa));}
            
        }
       
    }
    
    public void apagarCliente(DadosDoCliente d){
    gerenciadorThreadPool.submeterPoolIO(()->repositorio.apagarCliente(d))
                         .exceptionally((ex)->{
                          ex.printStackTrace();
                          return new ResultadoIo.Erro<>("Erro ao apagar dados do cliente");
                         }).thenAccept((r)->{
                         if(r instanceof ResultadoIo.OK){
                         caregarListaInicial();
                         }
                         if(r instanceof ResultadoIo.Erro er){
                         estadoLoad.notificar(new RespostaDeLoad.Erro<>(er.Mensagem()));
                         }
                         
                         })
            ;
    
    }
    
    public Estado<List<DadosDoCliente>> getLista(){
     return estadoDaLista;
    }

    public Estado<RespostaDeLoad<Void>> getEstadoLoad() {
        return estadoLoad;
    }
    
    @Override
    public void finalizar() {
        estadoDaLista.clear();
        estadoDaLista=null;
        this.gerenciadorThreadPool=null;
        this.repositorio=null;
    }
}
