/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.paineisAdicaoDeCLientes;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleAdicionarClientes;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.INavegar;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.paineisAdicaoDeCLientes.PainelDadosDocliente;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeValidacaoDeDados;
import java.awt.CardLayout;
import java.util.List;

/**
 *
 * @author ramon
 */
public class PainelAdicaoDeClientes extends Painel {
    private PainelDadosDocliente paineldados ;
    private PainelEnderecoCliente painelEndereco;
    private PainelTelefone painelTelefone;
    private ControleAdicionarClientes controle;
    private InterfaceNavegacaoENtreEstagios anterior= new InterfaceNavegacaoENtreEstagios() {
        @Override
        public void navegue() {
          controle.subEstadoAnterior();}};
    private InterfaceNavegacaoENtreEstagios prosimo= new InterfaceNavegacaoENtreEstagios() {
        @Override
        public void navegue() {
          controle.prosimoSubestado(); }};
   
    /**
     * Creates new form PainelAdicaoDeClienttes
     */
    public PainelAdicaoDeClientes(ControleAdicionarClientes c,INavegar voutar) {
        this.controle=c;
        initComponents();
        paineldados=new PainelDadosDocliente(prosimo,voutar,()->{
         controle.validarDadosDoCliente(paineldados.getNome(),paineldados.getCpf(),paineldados.getCnpj());
        });
        painelEndereco= new PainelEnderecoCliente(anterior,prosimo,()->{
            
         controle.validarEndereco(painelEndereco.getCep(),painelEndereco.getRua(), painelEndereco.getBairro(),painelEndereco.getCidade(),painelEndereco.getEstado(), painelEndereco.getNumero(),painelEndereco.getComplemento());;
        });
        painelTelefone = new PainelTelefone(anterior,()->{controle.salvar(painelEndereco.getLista(),painelTelefone.getLista(),paineldados.getCliente());},()->{
         controle.validarTelefone(painelTelefone.getTelefone());
        });
        var cardlayout=(CardLayout)jPanel1.getLayout();
        jPanel1.add(paineldados,EstadosEstagiosCadastroCliente.EstagioNome.class.getSimpleName());
        jPanel1.add(painelEndereco,EstadosEstagiosCadastroCliente.EstagioEndereco.class.getSimpleName());
        jPanel1.add(painelTelefone,EstadosEstagiosCadastroCliente.EstagioTelefone.class.getSimpleName());
        controle.getEstadaDasSubTelas().observar((e)->{
            System.out.println("Valor notificadao do subestadoade tela "+e);    
        cardlayout.show(jPanel1,e.getClass().getSimpleName());
        }, this);
       controle.getEstadoCliente().observar((cli)->{
       paineldados.updateDados((Cliente) cli);
       }, this);
       controle.getEstadoListaEndereco().observar((end)->{
        painelEndereco.updateEderecos((List<Endereco>) end);
       },this);
       controle.getEstadoListaTelefone().observar((tlf)->{
       painelTelefone.updateTelefones((List<Telefone>) tlf);
       },this);
       controle.getValidamentoEtapaNome().observar((v)->{
        paineldados.respsotaInvalidacao((RespostaDeValidacaoDeDados) v);
       },this);
       controle.getValidamentoEtapaEndereco().observar((r)->painelEndereco.notificarValidacao((RespostaDeValidacaoDeDados) r),this);
       controle.getLoadSalvamentoDeDados().observar((load)->{
           System.out.println("Estado de salvamento mudou "+load);
       if(load instanceof RespostaDeLoad.Load ) painelTelefone.avisoAnimar();
       if(load instanceof RespostaDeLoad.Erro e){
       painelTelefone.avisoPararAnimacao();
       painelTelefone.avisoDeErro(e.mensagem());
       }
       if(load instanceof RespostaDeLoad.OK){
        painelTelefone.avisoPararAnimacao();
        voutar.navegar(null);
       }
       if(load instanceof RespostaDeLoad.OKVasio){
       System.out.println("vaor do load e ok vasio");
       }
       }, this);
       controle.getValidamentoEtapaTelefone().observar((r)->painelTelefone.respostaDeValidacao((RespostaDeValidacaoDeDados) r), this);
    }

    @Override
    public void iniciar() {
        super.iniciar(); 
        controle.caregarDadosIniciais();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setAlignmentX(CENTER_ALIGNMENT);
        setMaximumSize(new java.awt.Dimension(32767, 900));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setAlignmentX(CENTER_ALIGNMENT);
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(new java.awt.CardLayout());
        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    public void finalizar() {
        System.err.println("Finalizar foi chamado e vou limpar os paineis internos");
        super.finalizar();
        painelEndereco.finalizar();
        painelTelefone.finalizar();
        paineldados.finalizar();
    }
    
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
