/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.painelAdicaoRequisicao;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleAdicianarRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnunPesquisaClientes;
import com.mycompany.meunegocioprojetointegrador.view.controles.validador.Par;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.INavegar;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.Rotas;
import com.mycompany.meunegocioprojetointegrador.view.paineis.Util.util.EstadosEstagiosDeAdicao;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import com.mysql.cj.xdevapi.Client;
import java.awt.CardLayout;
import java.util.List;

/**
 *
 * @author ramon
 */
public class PainelAdicaoRequisicao extends Painel {
    private PainelSelecaoDeCliente selecaoDeCliente ;
    private PainelSelecaoDeprodutos selecaoDeprodutos;
    private PainelDeObservacoes observacoes;
    private ControleAdicianarRequisicao controle;
    
    private Runnable voutar=()->controle.anterior();
    private Runnable proximo=()->controle.proximo();
    private Runnable voutarParaTelaAnterior;
    /**
     * Creates new form PainelAdicaoRequisicao
     */
    public PainelAdicaoRequisicao(Runnable voutarParaTelaAnterior,Runnable adicionarClientes,ControleAdicianarRequisicao c) {
        this.controle=c;
        this.voutarParaTelaAnterior=voutarParaTelaAnterior;
        initComponents();
        selecaoDeCliente= new PainelSelecaoDeCliente(this.voutarParaTelaAnterior,
                                                     proximo,
                                                     ()->controle.pesquisarCliente(selecaoDeCliente.getPesquis(),selecaoDeCliente.getTipoPesquisa()),
                                                      adicionarClientes,
                                                     (d)->controle.updateCliente(d));
        selecaoDeprodutos= new PainelSelecaoDeprodutos(voutar,
                                                       proximo,
                                                       ()->controle.mudarListaDeProdutosSelecionados(selecaoDeprodutos.getProdutoSelecionado()),
                                                       ()->controle.encrementar(selecaoDeprodutos.indiceProdutoSelecionado()),
                                                       ()->controle.decrementar(selecaoDeprodutos.indiceProdutoSelecionado()),
                                                       ()->controle.pesquisarProduto(selecaoDeprodutos.getTipoPesquisaProdutos(),selecaoDeprodutos.getPesquisa()));
        observacoes=new PainelDeObservacoes(voutar,
                                            ()->controle.acaoDeSalvar(),
                                            ()->controle.updateEstaObservacoes(observacoes.getDados())
        );
        
        jPanel1.add(selecaoDeCliente,EstadosEstagiosDeAdicao.EstadosSelecaoDeClioentes.class.getSimpleName());
        jPanel1.add(selecaoDeprodutos,EstadosEstagiosDeAdicao.EstadosSelecaoDeProdutos.class.getSimpleName());
        jPanel1.add(observacoes,EstadosEstagiosDeAdicao.EstadosEstagioDeDescricao .class.getSimpleName());
        
        
        controle.getEstadoSubEstadoDeTelas().observar((est)->{
        var cardlayout= (CardLayout)jPanel1.getLayout();
        cardlayout.show(jPanel1,est.getClass().getSimpleName());
        },this);
        controle.getEstadoListaDeCliente().observar((l)->{
         selecaoDeCliente.updateLista((List<DadosDoCliente>)  l);
        },this);
        
        controle.getLoadListaDeProdutos().observar((load)->{},this);
        controle.getEstadoListaDeProdutos().observar((l)->{
        selecaoDeprodutos.updateProdutos((List<ProdutoServico>) l);
        },this);
        controle.getEstadoProdutoSelecionadoa().observar((l)->{
            System.out.println("Aviso com a nova lista");    
        selecaoDeprodutos.updateProdutosSelecionados((List<ProdutoSelecionado>) l);
        },this);
        controle.getLoadSalvarDados().observar((load)->{
        if(load instanceof RespostaDeLoad.Load){
        observacoes.iniciarAnimacao();
        }
        if(load instanceof RespostaDeLoad.OK){
        observacoes.pararAnimacao();
        voutarParaTelaAnterior.run();
        }
        if(load instanceof RespostaDeLoad.Erro er){
        observacoes.pararAnimacao();
        observacoes.avisoErro(er.mensagem());
        }
        
        },this);
        controle.getEstadoDlienteSelecionado().observar((d)->selecaoDeCliente.setClienteSelecionado((DadosDoCliente) d),this);
        controle.getEstadoDescricaoObservacao().observar((p)->{
        observacoes.setarDados((Par<String, String>) p);
        },this);
    }

    @Override
    public void iniciar() {
        super.iniciar(); 
       controle.caregarListaClientes();
       controle.caregarListaDeProdutos();
       controle.caregarDadosDoCliente();
       controle.caregarListaDeProdutosSelecionados();
    }

    
    @Override
    public void finalizar() {
        super.finalizar(); 
        selecaoDeCliente.finalizar();
        selecaoDeprodutos.finalizar();
        
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

        setMaximumSize(new java.awt.Dimension(32202, 900));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(new java.awt.CardLayout());
        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
