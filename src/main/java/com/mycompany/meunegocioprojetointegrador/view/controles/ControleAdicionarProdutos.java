/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.ValidadorProdutos;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;

/**
 *
 * @author ramon
 */
public class ControleAdicionarProdutos implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Repositorio repositorio;
    
    private Estado<ProdutoServico> estadoProdutoServico= new Estado<>(null);
    private Estado<RespostaDeLoad<Void>> estadoDeSalvamento = new Estado<>(null);
    public ControleAdicionarProdutos(GerenciadorThreadPool gerenciadorThreadPool, Repositorio repositorio,ProdutoServico ps) {
        this.gerenciadorThreadPool = gerenciadorThreadPool;
        this.repositorio = repositorio;
        this.estadoProdutoServico.notificar(ps);
    }
    
    private void salvarProduto(ProdutoServico p){
     p.setId(null);
     var futuro=gerenciadorThreadPool.submeterPoolIO(()->repositorio.salvarProduto(p));
     futuro.exceptionally((ex)->{
     ex.printStackTrace();
     return new ResultadoIo.Erro<>(RespostaDefault.Desconhesido.getMenssagen());
     });
     futuro.thenAccept((r)->{
     if(r instanceof ResultadoIo.Erro err){
     estadoDeSalvamento.notificar(new RespostaDeLoad.Erro<>("Erro ao salvar produto "+err.Mensagem()));
     }
     if(r instanceof  ResultadoIo.ErroVasio ){
        estadoDeSalvamento.notificar(new RespostaDeLoad.Erro("Erro ao salvar"));
     }
     if(r instanceof ResultadoIo.OK ){
      estadoDeSalvamento.notificar(new RespostaDeLoad.OKVasio());
     }
     });
    }
    
    private  void editarProduto(ProdutoServico p){
     var id =estadoProdutoServico.getValot().getId();
     p.setId(id);
     var futuro=gerenciadorThreadPool.submeterPoolIO(()->repositorio.editarProduto(p));
     futuro.exceptionally((ex)->{
     ex.printStackTrace();
     return new ResultadoIo.Erro<>(RespostaDefault.Desconhesido.getMenssagen());
     });
     futuro.thenAccept((r)->{
     if(r instanceof ResultadoIo.Erro err){
     estadoDeSalvamento.notificar(new RespostaDeLoad.Erro<>("Erro ao Editar produto "+err.Mensagem()));
     }
     if(r instanceof  ResultadoIo.ErroVasio ){
        estadoDeSalvamento.notificar(new RespostaDeLoad.Erro("Erro ao Editar produto"));
     }
     if(r instanceof ResultadoIo.OK ){
      estadoDeSalvamento.notificar(new RespostaDeLoad.OK(null));
     }
     });
    
    }
    
    public void operacaoDeSalvar(String nome,String descricao,String preco,boolean produto){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
    estadoDeSalvamento.notificar(new RespostaDeLoad.Load<>());
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
        }
    var validador = new ValidadorProdutos();
    var ponteiroParaProduto=estadoProdutoServico.getValot();
    var salvar=(ponteiroParaProduto==null)?true : false;
    validador.validar(nome, preco, descricao, produto);
    if(validador.invalido()){
      var mensagem =validador.mensagem();
     estadoDeSalvamento.notificar(new RespostaDeLoad.Erro<>("Erro "+mensagem));
     return;
    }
    var produtoMontado =validador.montartObjeto();
    if(salvar)salvarProduto(produtoMontado);
    else editarProduto(produtoMontado);
    });
    }

    public Estado<ProdutoServico> getEstadoProdutoServico() {
        return estadoProdutoServico;
    }

    public Estado<RespostaDeLoad<Void>> getEstadoDeSalvamento() {
        return estadoDeSalvamento;
    }

    @Override
    public void finalizar() {
    estadoDeSalvamento.clear();
    estadoProdutoServico.clear();
    estadoDeSalvamento=null;
    estadoProdutoServico=null;
    gerenciadorThreadPool=null;
    repositorio=null;
    }
    
}
