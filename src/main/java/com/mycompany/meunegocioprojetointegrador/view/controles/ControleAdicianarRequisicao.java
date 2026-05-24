/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaProdutosServico;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Requisicao;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnumPesquisaProdutos;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnunPesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.Par;
import com.mycompany.meunegocioprojetointegrador.view.paineis.Util.util.EstadosEstagiosDeAdicao;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleAdicianarRequisicao implements IFinalizar{
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Repositorio repositorio;
    private Estado<DadosDaRequisicao> estadoDadosDaRequisicao= new Estado(null);
    private Estado<List<ProdutoServico>> estadoListaDeProdutos = new Estado<>(new ArrayList());
    private Estado<List<ProdutoSelecionado>> estadoProdutoSelecionadoa= new Estado(new ArrayList<>());
    private Estado<List<DadosDoCliente>> estadoListaDeCliente= new Estado<>(new ArrayList<>());
    private Estado<DadosDoCliente> estadoClienteSelecionado= new Estado<>(null);
    private Estado<Par<String,String>> estadoDescricaoObservacao = new Estado<>(null);
    private Estado<RespostaDeLoad<Void>> loadDadosRequisicao = new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>> loadListaDeProdutos= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>>loadSalvarDados= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>>loaListaProdutosSelecionado= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>>loadListaClientes= new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeLoad<Void>> loadDadosDoCliente=new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<EstadosEstagiosDeAdicao> estadoSubEstadoDeTelas=  new Estado<>(new EstadosEstagiosDeAdicao.EstadosSelecaoDeClioentes());
    public ControleAdicianarRequisicao(GerenciadorThreadPool gerenciadorThreadPool, Repositorio repositorio,DadosDaRequisicao d) {
        this.gerenciadorThreadPool = gerenciadorThreadPool;
        this.repositorio = repositorio;
        estadoDadosDaRequisicao.notificar(d);
        if(d!=null){
        estadoDescricaoObservacao.notificar(new Par(d.getRequisicao().getDescricao(),d.getRequisicao().getObservacao()));
        }
        System.out.println("Dados de d "+d);
        
        
    }
    public void caregarListaClientes(){
 gerenciadorThreadPool.submeterPoolIO(()->{
                       loadListaClientes.notificar(new RespostaDeLoad.Load<>());
                       return repositorio.listaDeClientes();})
                      .exceptionally((ex)->{
                      ex.printStackTrace();
                      loadListaClientes.notificar(new RespostaDeLoad.Erro<>("Erro ao carregar lista de clientes "));
                      return new ArrayList<>();
                       })
                      .thenAccept((l)->{
                       estadoListaDeCliente.notificar((List<DadosDoCliente>) l);
                       loadListaClientes.notificar(new RespostaDeLoad.OK<>(null));
                      });
 }
 
    public void caregarListaDeProdutos(){
 gerenciadorThreadPool.submeterPoolIO(()->{
  loadListaDeProdutos.notificar(new RespostaDeLoad.Load<>());
  return repositorio.listarProdutosAtivos();
  }).exceptionally((ex)->{
   ex.printStackTrace();
   return new ArrayList<>();
  }).thenAccept((l)->{
  loadListaDeProdutos.notificar(new RespostaDeLoad.OK<>(null));
  estadoListaDeProdutos.notificar((List<ProdutoServico>) l);
  });
 
 }
 
    public void pesquisarProduto(EnumPesquisaProdutos e,String p){
    if(p.isBlank()){caregarListaDeProdutos();return;}
        switch (e) {
            case EnumPesquisaProdutos.Id->{
            Long id;
                try {
                    id=Long.parseLong(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    loadListaDeProdutos.notificar(new RespostaDeLoad.Erro<>("Para pesquisar o id do produto e nessesario digitar um numero inteiro"));
                 return;
                }
                pesquisarProdutoPorId(new PesquisaProdutosServico.Id(id));
            }
            case EnumPesquisaProdutos.Nome->{
                pesquisarProdutoPorNome(new PesquisaProdutosServico.Nome(p));
            }
        }
    }
    
    private void pesquisarProdutoPorNome(PesquisaProdutosServico.Nome p){
    gerenciadorThreadPool.submeterPoolIO(()->repositorio.pesquisarProdutoAtivo(p))
                         .exceptionally((ex)->{
                         ex.printStackTrace();
                         return new ArrayList<>();})
                         .thenAccept((l)->estadoListaDeProdutos.notificar((List<ProdutoServico>) l));
    }
    
    private void pesquisarProdutoPorId(PesquisaProdutosServico.Id p){
       gerenciadorThreadPool.submeterPoolIO(()->repositorio.pesquisarProdutoAtivo(p))
                         .exceptionally((ex)->{
                         ex.printStackTrace();
                         return new ArrayList<>();})
                        .thenAccept((l)->estadoListaDeProdutos.notificar((List<ProdutoServico>) l));
    }
    
    public void mudarListaDeProdutosSelecionados(ProdutoSelecionado p){
 gerenciadorThreadPool.submeterPoolDefalt(()->{
 var listaAuxiliar =new ArrayList<>(estadoProdutoSelecionadoa.getValot());
 listaAuxiliar.add(p);
 estadoProdutoSelecionadoa.notificar(listaAuxiliar);
 
 });
 }
    
    public void caregarListaDeProdutosSelecionados(){
     gerenciadorThreadPool.submeterPoolIO(()->{
      var dadosDaRequisicao =estadoDadosDaRequisicao.getValot();
      if(dadosDaRequisicao==null) return new ArrayList<>();
      return repositorio.listarProdutosSelecionados(dadosDaRequisicao.getRequisicao().getId());
     }).exceptionally((ex)->{
      ex.printStackTrace();
      return new ArrayList<>();
     }).thenAccept((l)->{
     estadoProdutoSelecionadoa.notificar((List<ProdutoSelecionado>) l);
     });
    }
    
    public void caregarDadosDoCliente(){
    gerenciadorThreadPool.submeterPoolIO(()->{
      var dadosSarequisicao =estadoDadosDaRequisicao.getValot();
      if(dadosSarequisicao==null)return null;
      else return repositorio.clientePorId(dadosSarequisicao.getCliente().getId());
    }).exceptionally((ex)->{
    return  null;
    }).thenAccept((d)->estadoClienteSelecionado.notificar((DadosDoCliente) d));
    }
    
    public void encrementar(int indice){
 gerenciadorThreadPool.submeterPoolDefalt(()->{
     System.out.println("Metodo encrementar foi chamado");  
     try {
         
    
   
 var listaAuxiliar =new ArrayList<>(estadoProdutoSelecionadoa.getValot());
 var objetoAtual =listaAuxiliar.get(indice);
 var novoObjeto= new ProdutoSelecionado(objetoAtual.getId(), 
                                        objetoAtual.getIdRequisicao(), 
                                       objetoAtual.getIdProduto(),
                                       objetoAtual.getNome(),
                                       objetoAtual.isProduto(),
                                        objetoAtual.getQuantidade()+1, 
                                         objetoAtual.getPreco() );
 listaAuxiliar.set(indice, novoObjeto);
 estadoProdutoSelecionadoa.notificar(listaAuxiliar); } catch (Exception e) {
     e.printStackTrace();
     }
 });
 }
 
    public void decrementar(int indice){
      gerenciadorThreadPool.submeterPoolDefalt(()->{
      System.out.println("Metodo dencrementar foi chamado");     
 var listaAuxiliar = new ArrayList<>(estadoProdutoSelecionadoa.getValot());
 var objetoAtual =listaAuxiliar.get(indice);
 var novoObjeto= new ProdutoSelecionado(objetoAtual.getId(), 
                                        objetoAtual.getIdRequisicao(), 
                                       objetoAtual.getIdProduto(),
                                       objetoAtual.getNome(),
                                       objetoAtual.isProduto(),
                                        objetoAtual.getQuantidade()-1, 
                                         objetoAtual.getPreco() );
 try{
 if(novoObjeto.getQuantidade()==0)listaAuxiliar.remove(indice);
 else listaAuxiliar.set(indice, novoObjeto);
 estadoProdutoSelecionadoa.notificar(listaAuxiliar);
          System.out.println("notificacao enviada sobre a lista");}
 catch(Exception e){
          e.printStackTrace();
          }
 });
 }
    
    public void anterior(){
  gerenciadorThreadPool.submeterPoolDefalt(()->{
   var anterior=estadoSubEstadoDeTelas.getValot().getAnterior();
   if(anterior!=null) estadoSubEstadoDeTelas.notificar(anterior);
  });
 }
    
    public void proximo(){
 gerenciadorThreadPool.submeterPoolDefalt(()->{
  var proximo=estadoSubEstadoDeTelas.getValot().getProsimo();
  if(proximo!=null)estadoSubEstadoDeTelas.notificar(proximo);
 });
 }
    
    private void pesquisarNome(PesquisaClientes.PesquisaNome p){
 gerenciadorThreadPool.submeterPoolIO(()->{
 return  repositorio.listarPesquisaClientes(p);
 }).exceptionally((ex)->{
 ex.printStackTrace();
 return new ArrayList<>();
 }).thenAccept((l)->{
 estadoListaDeCliente.notificar((List<DadosDoCliente>) l);
 });
         ;
 }
    
    private void pesquisarCpf(PesquisaClientes.PesquisaCpf p){
 gerenciadorThreadPool.submeterPoolIO(()->{
 return  repositorio.listarPesquisaClientes(p);
 }).exceptionally((ex)->{
 ex.printStackTrace();
 return new ArrayList<>();
 }).thenAccept((l)->{
 estadoListaDeCliente.notificar((List<DadosDoCliente>) l);
 });
         ;
 }
    
    private void pesquisarCnpj(PesquisaClientes.PesquisaCnpj p){
 gerenciadorThreadPool.submeterPoolIO(()->{
 return  repositorio.listarPesquisaClientes(p);
 }).exceptionally((ex)->{
 ex.printStackTrace();
 return new ArrayList<>();
 }).thenAccept((l)->{
 estadoListaDeCliente.notificar((List<DadosDoCliente>) l);
 });
         ;
 }
    
    public void pesquisarCliente(String p,EnunPesquisaClientes e){
 gerenciadorThreadPool.submeterPoolDefalt(()->{
 if(p.isBlank()){caregarListaClientes();}
     switch (e) {
         case Nome-> pesquisarNome(new PesquisaClientes.PesquisaNome(p));
         case Cnpj-> pesquisarCnpj(new PesquisaClientes.PesquisaCnpj(p));
         case Cpf->  pesquisarCpf(new PesquisaClientes.PesquisaCpf(p));
     }
 
 });
 }
 
    private void salvarRequisicao(DadosDaRequisicao d,List<ProdutoSelecionado> l){
    gerenciadorThreadPool.submeterPoolIO(()->{
     loadSalvarDados.notificar(new RespostaDeLoad.Load<>());
     return repositorio.salvarRequisicao(d, l);
        
    })
                         .exceptionally((ex)->{
                          ex.printStackTrace();
                          return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
                         }).thenAccept((r)->{
                         if(r instanceof ResultadoIo.OK ){
                         loadSalvarDados.notificar(new RespostaDeLoad.OK<>(null));
                         }
                         if(r instanceof ResultadoIo.Erro er){
                          loadSalvarDados.notificar(new RespostaDeLoad.Erro(er.Mensagem()));
                         }
                         
                         });
    }
    
    private void editarRequisica(DadosDaRequisicao d,List<ProdutoSelecionado> l){
    gerenciadorThreadPool.submeterPoolIO(()->{
     loadSalvarDados.notificar(new RespostaDeLoad.Load<>());
     return repositorio.editarRequisicao(d, l);
        
    })
                         .exceptionally((ex)->{
                          ex.printStackTrace();
                          return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
                         }).thenAccept((r)->{
                         if(r instanceof ResultadoIo.OK ){
                         loadSalvarDados.notificar(new RespostaDeLoad.OK<>(null));
                         }
                         if(r instanceof ResultadoIo.Erro er){
                          loadSalvarDados.notificar(new RespostaDeLoad.Erro(er.Mensagem()));
                         }
                         
                         });
    }
    
    public void acaoDeSalvar(){
        System.out.println("Acao de salvar foi chamada no controle");    
    gerenciadorThreadPool.submeterPoolDefalt(()->{
        try {
    var dados=estadoDadosDaRequisicao.getValot(); 
    var clienteSelecionado=estadoClienteSelecionado.getValot();
    var listaProdutosSelecionados =estadoProdutoSelecionadoa.getValot();
     var par =estadoDescricaoObservacao.getValot();
    if(dados==null){
       
        var dadosASerenSalvos=new DadosDaRequisicao(new Requisicao(null,par.primeiro(),par.segundo()), 
                                                    null, 
                                             clienteSelecionado.getCliente());
        salvarRequisicao(dadosASerenSalvos, listaProdutosSelecionados);
        return;
    }
    dados.setCliente(clienteSelecionado.getCliente());
    dados.getRequisicao().setDescricao(par.primeiro());
    dados.getRequisicao().setObservacao(par.segundo());
    editarRequisica(dados, listaProdutosSelecionados);}catch(Exception ex){
    ex.printStackTrace();
    }
    });
    
    }
    
    public Estado<DadosDaRequisicao> getEstadoDadosDaRequisicao() {
        return estadoDadosDaRequisicao;
    }

    public Estado<List<ProdutoServico>> getEstadoListaDeProdutos() {
        return estadoListaDeProdutos;
    }

    public Estado<List<ProdutoSelecionado>> getEstadoProdutoSelecionadoa() {
        return estadoProdutoSelecionadoa;
    }

    public Estado<List<DadosDoCliente>> getEstadoListaDeCliente() {
        return estadoListaDeCliente;
    }

   
    public Estado<DadosDoCliente> getEstadoDlienteSelecionado() {
        return estadoClienteSelecionado;
    }

    public Estado<Par<String, String>> getEstadoDescricaoObservacao() {
        return estadoDescricaoObservacao;
    }

    public Estado<RespostaDeLoad<Void>> getLoadDadosRequisicao() {
        return loadDadosRequisicao;
    }

    public Estado<RespostaDeLoad<Void>> getLoadListaDeProdutos() {
        return loadListaDeProdutos;
    }

    public Estado<RespostaDeLoad<Void>> getLoadSalvarDados() {
        return loadSalvarDados;
    }

    public Estado<RespostaDeLoad<Void>> getLoaListaProdutosSelecionado() {
        return loaListaProdutosSelecionado;
    }

    public Estado<RespostaDeLoad<Void>> getLoadListaClientes() {
        return loadListaClientes;
    }

    public Estado<EstadosEstagiosDeAdicao> getEstadoSubEstadoDeTelas() {
        return estadoSubEstadoDeTelas;
    }

    public Estado<RespostaDeLoad<Void>> getLoadDadosDoCliente() {
        return loadDadosDoCliente;
    }

    public void updateEstaObservacoes(Par<String,String> p){
    estadoDescricaoObservacao.notificar(p);
    }
    public void updateCliente(DadosDoCliente d){
    estadoClienteSelecionado.notificar(d);
    }
    @Override
    public void finalizar() {
    gerenciadorThreadPool=null;
    repositorio=null;
    estadoDadosDaRequisicao.clear();
    estadoDescricaoObservacao.clear();
    estadoClienteSelecionado.clear();
    estadoListaDeCliente.clear();
    estadoListaDeProdutos.clear();
    estadoProdutoSelecionadoa.clear();
    estadoSubEstadoDeTelas.clear();//
    estadoDadosDaRequisicao=null;
    estadoDescricaoObservacao=null;
    estadoClienteSelecionado=null;
    estadoListaDeCliente=null;
    estadoListaDeProdutos=null;
    estadoProdutoSelecionadoa=null;
    estadoSubEstadoDeTelas=null;
    
    }
 
 
 
}
