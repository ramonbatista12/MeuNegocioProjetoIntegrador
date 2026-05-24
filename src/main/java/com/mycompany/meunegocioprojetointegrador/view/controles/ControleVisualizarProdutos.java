/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;

/**
 *
 * @author ramon
 */
public class ControleVisualizarProdutos implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Repositorio repositorio;
    private Estado<ProdutoServico> estadoProduto = new Estado<>(null);
    private Estado<RespostaDeLoad<Void>> estadoLoad= new Estado<>(new RespostaDeLoad.OKVasio());
    public ControleVisualizarProdutos(GerenciadorThreadPool gerenciadorThreadPool,
                                      Repositorio repositorio,
                                      ProdutoServico produto ) {
        this.gerenciadorThreadPool = gerenciadorThreadPool;
        this.repositorio = repositorio;
        estadoProduto.notificar(produto);
    }

    public Estado<ProdutoServico> getEstadoProduto() {
        return estadoProduto;
    }

    public Estado<RespostaDeLoad<Void>> getEstadoLoad() {
        return estadoLoad;
    }
    
    public void mudarEstatus(){
    gerenciadorThreadPool.submeterPoolIO(()->{
    var produto = estadoProduto.getValot();
    var novoEstado =!produto.isAtivo();
    produto.setAtivo(novoEstado);
    return repositorio.editarProduto(produto);
    }).exceptionally((ex)->{
     ex.printStackTrace();
     return new ResultadoIo.Erro<>("Erro ao mudar o estatus do produto");
     
    }).thenAccept((r)->{
    if(r instanceof ResultadoIo.Erro er){
    estadoLoad.notificar(new RespostaDeLoad.Erro<>(er.Mensagem()));
    }
    if(r instanceof ResultadoIo.OK){
    estadoLoad.notificar(new RespostaDeLoad.OK<>(null));
    caregarProduto();
    }
    });
    
    }
    
    public void caregarProduto(){
    gerenciadorThreadPool.submeterPoolIO(()->{
    var id =estadoProduto.getValot().getId();
    return repositorio.produtoPorId(id);
    }).exceptionally((ex)->{
    ex.printStackTrace();
    return new ResultadoIo.Erro<>("Erro ao atualizar produto");
    }).thenAccept((respsota)->{
    if(respsota instanceof ResultadoIo.OK ok){
    estadoProduto.notificar((ProdutoServico) ok.r());
    }
    if(respsota instanceof ResultadoIo.ErroVasio|| respsota instanceof ResultadoIo.Erro){
    estadoLoad.notificar(new RespostaDeLoad.Erro<>("Erro ao atualizar o produto"));
    }
    
    
    });
    }

    @Override
    public void finalizar() {
    estadoLoad.clear();
    estadoProduto.clear();
    estadoLoad=null;
    estadoProduto=null;
    
    }
    
}
