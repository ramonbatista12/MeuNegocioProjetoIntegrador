/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import com.mycompany.meunegocioprojetointegrador.bd.respostas.ResultadoIo;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.ValidadorCliente;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.ValidadorEndereco;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.ValidadorTelefone;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.paineisAdicaoDeCLientes.EstadosEstagiosCadastroCliente;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeValidacaoDeDados;

/**
 *
 * @author ramon
 */
public class ControleAdicionarClientes implements IFinalizar{
    private Repositorio repositorio;
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Estado<Cliente> estadoCliente=new Estado<>(null);
    private Estado<List<Telefone>> estadoListaTelefone = new Estado<>(new ArrayList<>());
    private Estado<List<Endereco>> estadoListaEndereco = new Estado<>(new ArrayList<>());
    private Estado<EstadosEstagiosCadastroCliente> estadaDasSubTelas = new Estado<>(new EstadosEstagiosCadastroCliente.EstagioNome());
    private Estado<RespostaDeLoad<Void>> loadSalvamentoDeDados =new Estado<>(new RespostaDeLoad.OKVasio());
    private Estado<RespostaDeValidacaoDeDados> validamentoEtapaNome =new Estado<>(new RespostaDeValidacaoDeDados.NaoAvaliado());
    private Estado<RespostaDeValidacaoDeDados> validamentoEtapaEndereco =new Estado<>(new RespostaDeValidacaoDeDados.NaoAvaliado());
    private Estado<RespostaDeValidacaoDeDados> validamentoEtapaTelefone =new Estado<>(new RespostaDeValidacaoDeDados.NaoAvaliado());
    
    
    public ControleAdicionarClientes(DadosDoCliente d,GerenciadorThreadPool g,Repositorio r){
     this.repositorio=r;
     this.gerenciadorThreadPool=g;
     
     estadoCliente.notificar((d==null)? null : d.getCliente());
     
    }
    
    
    public void caregarTelefones(Long idCliente){
     var completable=   gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarTelefones(idCliente));
     completable.exceptionally((ex)->{
      ex.printStackTrace();
      return new ArrayList<>();
     });
     completable.thenAccept((l)->estadoListaTelefone.notificar((List<Telefone>) l));
    }
    
    public void CaregarEnderecos(Long idCliente){
     var futuro= gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarEnderecos(idCliente));
     futuro.exceptionally((ex)->{
      ex.printStackTrace();
      return new ArrayList<>();
     });
     futuro.thenAccept((l)->{
     estadoListaEndereco.notificar((List<Endereco>) l);
     });
    }
    
    public void prosimoSubestado(){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
    var estadoAtual =estadaDasSubTelas.getValot();
    var prosimo=estadoAtual.proximo();
    if(prosimo!=null){
     estadaDasSubTelas.notificar(prosimo);
    }
    });
    }
    
    public void subEstadoAnterior(){
     gerenciadorThreadPool.submeterPoolDefalt(()->{
     var estadoAtual =estadaDasSubTelas.getValot();
     var anterior=estadoAtual.anterior();
     if(anterior!=null){
     estadaDasSubTelas.notificar(anterior);
    }
     });
    
    }
    
    private void salvarDados(List<Endereco> e, List<Telefone> t ,Cliente c){
    var futuro =gerenciadorThreadPool.submeterPoolIO(()->repositorio.salvarDadosDeCliente(c, e, t));
    futuro.exceptionally((ex)->{
        ex.printStackTrace();
    return new RespostaDeLoad.ErroVasio<>();
    });
    futuro.thenAccept((r)->{
     if(r instanceof ResultadoIo.Erro  er){
      loadSalvamentoDeDados.notificar(new RespostaDeLoad.Erro<>(er.Mensagem()));
     }
     if(r instanceof ResultadoIo.ErroVasio  er){loadSalvamentoDeDados.notificar(new RespostaDeLoad.Erro<>("Operacao nao pode ser comcluida"));}
     if(r instanceof ResultadoIo.OK  ){
      loadSalvamentoDeDados.notificar(new RespostaDeLoad.OK(null));
     }
    });
    }
    
    private void updateDados(List<Endereco> e, List<Telefone> t ,Cliente c){
            var futuro =gerenciadorThreadPool.submeterPoolIO(()->{
                
                return repositorio.editarDadosDeCliente(c, e, t) ;
                
                
                        });
    futuro.exceptionally((ex)->{
        ex.printStackTrace();
    return new RespostaDeLoad.ErroVasio<>();
    });
    futuro.thenAccept((r)->{
        System.err.println("comsumindo o valor vindo do futuro ");  
     if(r instanceof ResultadoIo.Erro er){
      loadSalvamentoDeDados.notificar(new RespostaDeLoad.Erro<>(er.Mensagem()));
     }
     if(r instanceof ResultadoIo.ErroVasio er){loadSalvamentoDeDados.notificar(new RespostaDeLoad.Erro<>("Operacao nao pode ser comcluida"));}
     if(r instanceof ResultadoIo.OK ){
      loadSalvamentoDeDados.notificar(new RespostaDeLoad.OK<>(null));
     }
    });
    }
    public void salvar(List<Endereco> e, List<Telefone> t ,Cliente c){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
    loadSalvamentoDeDados.notificar(new RespostaDeLoad.Load<>());
    var clienteOriginal= estadoCliente.getValot();
    if(clienteOriginal==null){
        salvarDados(e, t, c);
        return;
    }    
    c.setId(clienteOriginal.getId());
    updateDados(e, t, c);
    
    
    
    });
    
    }

  
    
    public void caregarDadosIniciais(){
        
    gerenciadorThreadPool.submeterPoolDefalt(()->{
     var cliente =  estadoCliente.getValot(); 
     if(cliente!=null){   
     caregarTelefones(cliente.getId());
     CaregarEnderecos(cliente.getId());}
    });
    }
    
    public void validarDadosDoCliente(String nome,String cpf,String cnpj){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
    var validadroDados = new ValidadorCliente();
    validadroDados.getValidar(nome, cnpj, cpf);
    if(validadroDados.invalido()){
    validamentoEtapaNome.notificar(new RespostaDeValidacaoDeDados.Invalido(validadroDados.mensagem()));
    return;
    }
    validamentoEtapaNome.notificar(new RespostaDeValidacaoDeDados.Validado());
    });
    } 
    public Estado<Cliente> getEstadoCliente() {
        return estadoCliente;
    }

    public Estado<List<Telefone>> getEstadoListaTelefone() {
        return estadoListaTelefone;
    }

    public Estado<List<Endereco>> getEstadoListaEndereco() {
        return estadoListaEndereco;
    }

    public Estado<EstadosEstagiosCadastroCliente> getEstadaDasSubTelas() {
        return estadaDasSubTelas;
    }

    public Estado<RespostaDeLoad<Void>> getLoadSalvamentoDeDados() {
        return loadSalvamentoDeDados;
    }

    public Estado<RespostaDeValidacaoDeDados> getValidamentoEtapaNome() {
        return validamentoEtapaNome;
    }

    public Estado<RespostaDeValidacaoDeDados> getValidamentoEtapaEndereco() {
        return validamentoEtapaEndereco;
    }

    public Estado<RespostaDeValidacaoDeDados> getValidamentoEtapaTelefone() {
        return validamentoEtapaTelefone;
    }
    
    public void validarEndereco(String cep,String rua,String bairro,String cidade,String estado,String numero ,String complemento){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
     var validador = new ValidadorEndereco();
     validador.validar(rua, cidade, bairro, numero, estado, cep, complemento);
     if(validador.invalido())validamentoEtapaEndereco.notificar(new RespostaDeValidacaoDeDados.Invalido(validador.mensagegem()));
     else validamentoEtapaEndereco.notificar(new RespostaDeValidacaoDeDados.Validado());
    });
    }
    
    public void validarTelefone(String telefone){
    gerenciadorThreadPool.submeterPoolDefalt(()->{
    var validador = new ValidadorTelefone();
    validador.validar(telefone);
    if(validador.invalido())validamentoEtapaTelefone.notificar(new RespostaDeValidacaoDeDados.Invalido(validador.mensagem()));
    else validamentoEtapaTelefone.notificar(new RespostaDeValidacaoDeDados.Validado());
    });
    }
    @Override
    public void finalizar() {
     this.estadoCliente.clear();
     this.estadoListaEndereco.clear();
     this.estadoListaTelefone.clear();
     this.estadoCliente=null;
     this.estadoListaEndereco=null;
     this.estadoListaTelefone=null; 
     this.gerenciadorThreadPool=null;
     this.repositorio=null;
    }
}
