/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeClientes;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaoEntidadeCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosEntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosEntidadeProdutos;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosEntidadeRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosHistoricoDeMudancas;
import com.mycompany.meunegocioprojetointegrador.bd.dados.daos.DaosTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeEnderecos;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeHistoricoRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.MapeadorEntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.HistoricoDeMudancas;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaProdutosServico;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores.PesquisaRequisicoes;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.EnunEstados;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Estado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Requisicao;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.RespostaDefault;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class Repositorio implements IFinalizar{
    private static Repositorio instanci;
    private static Object lock = new Object();
    private GerenciadorDeEntidades gerenciadorDeEntidades;
    private DaoEntidadeCliente daoEntidadeCliente;
    private DaosEndereco daosEndereco ;
    private DaosTelefone daosTelefone;
    private DaosEntidadeRequisicao daosReqisicao;
    private DaosEntidadeProdutos  daosProdutos;
    private DaosEntidadeProdutoSelecionado daoProdutoSelecionado;
    private DaosHistoricoDeMudancas daoHistoricoDeMudancas;
    private Repositorio(){
      gerenciadorDeEntidades = GerenciadorDeEntidades.getInstancia();
      daoEntidadeCliente= new DaoEntidadeCliente(gerenciadorDeEntidades);
      daosEndereco = new DaosEndereco(gerenciadorDeEntidades);
      daosTelefone= new DaosTelefone(gerenciadorDeEntidades);
      daosReqisicao= new DaosEntidadeRequisicao(gerenciadorDeEntidades);
      daosProdutos= new DaosEntidadeProdutos(gerenciadorDeEntidades);
      daoProdutoSelecionado= new DaosEntidadeProdutoSelecionado(gerenciadorDeEntidades);
      daoHistoricoDeMudancas= new DaosHistoricoDeMudancas(gerenciadorDeEntidades);
    }
    
    public List<DadosDoCliente> listaDeClientes(){
    var mapeador = new MapeadorEntidadeClientes();
    return daoEntidadeCliente.listaDeClientes()
                             .stream()
                             .map((e)->mapeador.mapearEntidadeParaDadosDoCliente(e))
                             .toList();
    }
    
    public List<Telefone> listarTelefones(Long idCliente){
     var mapeador=   new MapeadorEntidadeTelefone();
     return daosTelefone.getTelefonesPorCliente(idCliente)
                        .stream()
                        .map((e)->mapeador.mapearEntidadeParaTelefone(e))
                        .toList();
    }
    
    public List<Endereco> listarEnderecos(Long idCliente){
     var mapeador =   new MapeadorEntidadeEnderecos();
     return daosEndereco.getEnderecosPorCLiente(idCliente)
                        .stream()
                        .map((e)->mapeador.mapearEntidadeEnderecoParaEndereco(e))
                        .toList();
    }
    
    public List<DadosDaRequisicao> listarRequisicoes(){
     var mapeador =   new MapeadorEntidadeRequisicao();
     return  daosReqisicao.listarTodasAsRequisicoes()
                          .stream()
                          .map((e)->mapeador.maperEntidadeParaDadosDaRequisicao(e))
                          .toList();
    }
    
    public List<DadosDoCliente> listarPesquisaClientes(PesquisaClientes p){
        var mapeador= new MapeadorEntidadeClientes();
        if(p instanceof PesquisaClientes.PesquisaNome n){
        return daoEntidadeCliente.pesquisarClientePorNome(n.nome())
                                 .stream()
                                 .map((e)->mapeador.mapearEntidadeParaDadosDoCliente(e))
                                 .toList();
        }
        if(p instanceof PesquisaClientes.PesquisaCpf c){
        return daoEntidadeCliente.pesquisarClientePorCpf(c.cpf())
                                 .stream()
                                 .map((e)->mapeador.mapearEntidadeParaDadosDoCliente(e))
                                 .toList();
        }
         if(p instanceof PesquisaClientes.PesquisaCnpj c){
        return daoEntidadeCliente.pesquisarClientePorCnpj(c.cnpj())
                                 .stream()
                                 .map((e)->mapeador.mapearEntidadeParaDadosDoCliente(e))
                                 .toList();
        }
        
        if(p instanceof PesquisaClientes.PesquisaVasia){
        return  listaDeClientes();
        }
        return listaDeClientes();
    }
    
    public DadosDoCliente clientePorId(Long id){
     var dado =daoEntidadeCliente.clientePorId(id);
     if(dado==null)return null;
     return new MapeadorEntidadeClientes().mapearEntidadeParaDadosDoCliente(dado);
    }
    
    public List<ProdutoSelecionado> listarProdutosSelecionados(Long idRequisicao){
     var mapeador = new MapeadorEntidadeProdutoSelecionado();
    return  daoProdutoSelecionado.produtosSelecionados(idRequisicao)
                                 .stream()
                                 .map((p)->mapeador.mapearEntidadeParaProdutoSelecionado(p))
                                 .toList();
    }
    
    public Double totalProdutosSelecionados(Long idRequisicao){
    return daoProdutoSelecionado.getTotal(idRequisicao);
    }
    public List<HistoricoDeMudancas> listarHistoicoDeMudancasParaRequisicao(Long idRequisicao){
        var mapeador = new MapeadorEntidadeHistoricoRequisicao();
        return daoHistoricoDeMudancas.listarHistorico(idRequisicao)
                                     .stream()
                                     .map((h)->mapeador.mapearParaHistoricoDeMudancas(h))
                                     .toList();
    }
  
    public List<ProdutoServico> listarProdutos(){
    var maperador = new MapeadorEntidadeProdutoServico();
    return daosProdutos.listartTodosOSProsutosServicos()
                       .stream()
                       .map((p)->maperador.maperarEntidadeParaPRoduto(p))
                       .toList();
    }
    
    public List<ProdutoServico> listarProdutosAtivos(){
    var mapeador = new MapeadorEntidadeProdutoServico();
    return daosProdutos.listarProdutosAtivos().stream()
                                              .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                                              .toList();
    }
    
    public List<ProdutoServico> pesquisarProduto(PesquisaProdutosServico p){
    if(p==null)throw new IllegalArgumentException("Presquisa nao pode ser null");
    var mapeador = new MapeadorEntidadeProdutoServico();
    if(p instanceof PesquisaProdutosServico.Nome n){
    return  daosProdutos.listarProdutosPorNome(n.nome())
                        .stream()
                        .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                        .toList();
    }
    if(p instanceof PesquisaProdutosServico.Id i){
    return daosProdutos.listarProdutoPorId(i.id())
                       .stream()
                       .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                       .toList();
    }
    if(p instanceof PesquisaProdutosServico.Vazia)return listarProdutos();
    
    return null;
    }
    
    public List<ProdutoServico> pesquisarProdutoAtivo(PesquisaProdutosServico p){
        var mapeador = new MapeadorEntidadeProdutoServico();
        if(p instanceof PesquisaProdutosServico.Id id){
            return daosProdutos.listarProdutosAtivoPorId(id.id())
                               .stream()
                               .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                               .toList();
        }
        if(p instanceof PesquisaProdutosServico.Nome n){
        return daosProdutos.listarProdutosAtivosPorNome(n.nome())
                           .stream()
                           .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                           .toList();
        }
      return daosProdutos.listarProdutosAtivos()
                         .stream()
                         .map((e)->mapeador.maperarEntidadeParaPRoduto(e))
                         .toList();
    }
    
    public ResultadoIo<Boolean> salvarProduto(ProdutoServico p){
    var mapeador = new MapeadorEntidadeProdutoServico();
    return daosProdutos.salvarProduto(mapeador.mapearParaEntidadeProdutoServico(p));
    }
    
    public ResultadoIo<Boolean> editarProduto(ProdutoServico p){
    var mapeador = new MapeadorEntidadeProdutoServico();
    return daosProdutos.editarProduto(mapeador.mapearParaEntidadeProdutoServico(p));
    }
    
    public ResultadoIo<Void> salvarDadosDeCliente(Cliente c,List<Endereco> le,List<Telefone>lt){
    var mapeadorCliente=new MapeadorEntidadeClientes();
    var mapeadorEndereco=new MapeadorEntidadeEnderecos();
    var mapeadorTelefone = new MapeadorEntidadeTelefone();
    var listaEntidadeEndereco =le.stream().map((e)->mapeadorEndereco.mapeEnderecoParaEntidade(e)).toList();
    var listaEntidadeTelefone =lt.stream().map((t)->mapeadorTelefone.mapearTelefoneParEntidadeTelefone(t)).toList();
    return daoEntidadeCliente.salvarCliente(mapeadorCliente.mapeClienteParaEntidadeCliente(c),listaEntidadeEndereco,listaEntidadeTelefone);
    }
   
    public ResultadoIo<Void> editarDadosDeCliente(Cliente c,List<Endereco> le,List<Telefone>lt){
     var mapeadorCliente=new MapeadorEntidadeClientes();
    var mapeadorEndereco=new MapeadorEntidadeEnderecos();
    var mapeadorTelefone = new MapeadorEntidadeTelefone();
    var listaEntidadeEndereco =le.stream().map((e)->mapeadorEndereco.mapeEnderecoParaEntidade(e)).toList();
    var listaEntidadeTelefone =lt.stream().map((t)->mapeadorTelefone.mapearTelefoneParEntidadeTelefone(t)).toList();
    return daoEntidadeCliente.editarCliente(mapeadorCliente.mapeClienteParaEntidadeCliente(c),listaEntidadeEndereco,listaEntidadeTelefone);
    }
   
    public static  Repositorio getInstacia(){
        synchronized(lock){
            if(instanci==null)instanci = new Repositorio();
        }
        
    return instanci;
    }
    
    public ResultadoIo<Void> salvarRequisicao(DadosDaRequisicao d ,List<ProdutoSelecionado>l){
        d.setEstado(new Estado(EnunEstados.Pendendte.getId(),EnunEstados.Pendendte.getDescricao()));
        var entidadeRequisicao =new MapeadorEntidadeRequisicao().mapearparaEntidadeRequisicao(d);
        var mapeador =new MapeadorEntidadeProdutoSelecionado();
        var listaMapeada=l.stream()
                          .map((p)->mapeador.maoerarProdutoParaEntidade(p))
                          .toList();
       
        return daosReqisicao.salvarRequisicao(entidadeRequisicao, listaMapeada);
    
    }
  
    public ResultadoIo<Void> editarRequisicao(DadosDaRequisicao d,List<ProdutoSelecionado> l){
  
        var entidadeRequisicao =new MapeadorEntidadeRequisicao().mapearparaEntidadeRequisicao(d);
        var mapeador =new MapeadorEntidadeProdutoSelecionado();
        var listaMapeada=l.stream()
                          .map((p)->mapeador.maoerarProdutoParaEntidade(p))
                          .toList();
       
        return daosReqisicao.updateRequisicao(entidadeRequisicao, listaMapeada);    
    }
    
    public ResultadoIo<Void> editarRequisicao(DadosDaRequisicao d){
    
    return daosReqisicao.updateRequisicao(new MapeadorEntidadeRequisicao().mapearparaEntidadeRequisicao(d));
    }
    
    public ResultadoIo<DadosDaRequisicao> requisicaoPorId(Long id){
    var resultado = daosReqisicao.requisicaoPorId(id);
    if(resultado instanceof ResultadoIo.OK ok){
    return new ResultadoIo.OK<>(new MapeadorEntidadeRequisicao().maperEntidadeParaDadosDaRequisicao((EntidadeRequisicao) ok.r()));
    }
    if(resultado instanceof ResultadoIo.Erro er){
    return new ResultadoIo.Erro<>(er.Mensagem());
    }
    return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
    }
    
    public ResultadoIo<ProdutoServico> produtoPorId(Long id){
    var resultado = daosProdutos.produtoproId(id);
    if(resultado instanceof ResultadoIo.OK ok){
    return new ResultadoIo.OK<>(new MapeadorEntidadeProdutoServico().maperarEntidadeParaPRoduto((EntidadeProdutoServico) ok.r()));
    }
    return new ResultadoIo.Erro<>(RespostaDefault.OperacaoNaoComcluida.getMenssagen());
    }
    
    public List<DadosDaRequisicao> pesquisaRequisicao(PesquisaRequisicoes p){
    var mapeador = new MapeadorEntidadeRequisicao();
    return pesquisaRequisicaoAuxiliar(p).stream()
                                        .map((r)->mapeador.maperEntidadeParaDadosDaRequisicao(r))
                                        .toList();
    }
    private List<EntidadeRequisicao> pesquisaRequisicaoAuxiliar(PesquisaRequisicoes p){
    if(p instanceof PesquisaRequisicoes.ID id){
    return daosReqisicao.listarequisicaoPorId(id.id());
    }
    if(p instanceof PesquisaRequisicoes.NomeCliente n){
    return daosReqisicao.requisicoesPorCliente(n.nome());
    }
    if(p instanceof PesquisaRequisicoes.Estado e){
    return daosReqisicao.requisicaoPorEstado(e.estado());
    }
    return new ArrayList<>();
    }
    
    public ResultadoIo<Void> excluirRequisicao(DadosDaRequisicao d){
    var mapeador =new MapeadorEntidadeRequisicao();
    var entidade =mapeador.mapearparaEntidadeRequisicao(d);
    return daosReqisicao.excluirRequisicao(entidade);
    }
    
    public ResultadoIo<Void> apagarCliente(DadosDoCliente d){
    return daoEntidadeCliente.apagarCliente(new MapeadorEntidadeClientes().mapeClienteParaEntidadeCliente(d.getCliente()));
    }
    
    @Override
    public void finalizar() {
    synchronized (this) {
            daoEntidadeCliente.finalizar();
            daosEndereco.finalizar();
            daosTelefone.finalizar();
            daosReqisicao.finalizar();
            daosProdutos.finalizar();
            daoProdutoSelecionado.finalizar();
            daoHistoricoDeMudancas.finalizar();
            gerenciadorDeEntidades.finalizar();
            daoEntidadeCliente=null;
            daosEndereco=null;
            daosReqisicao=null;
            daosTelefone=null;
            daoProdutoSelecionado=null;
            daoHistoricoDeMudancas=null;
            gerenciadorDeEntidades=null;
            daosProdutos=null;
            instanci = null;
            
        }
    }
}
