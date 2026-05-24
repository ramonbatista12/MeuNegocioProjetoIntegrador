/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.conteiner.Conteiner;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleAdicianarRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleAdicionarClientes;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleAdicionarProdutos;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleClientes;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleProdutos;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleRequisicoes;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleVisualizarClientes;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleVisualizarProdutos;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleVisualizarRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.BaraLateral;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.Rotas;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.GerenciadorDePaineis;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.PainelGerenciavel;

import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizacaoProdutos.PainelVisualizarProdutos;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.painelAdicaoProdutos.PainelAdicionarProdutos;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.painelAdicaoRequisicao.PainelAdicaoRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisPrincipais.painelClientes.PainelDeClientes;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisPrincipais.painelProdutos.PainelProdutos;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisPrincipais.painelRequisicoes.PainelRequisicoes;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarClientes.PainelVisualizarCliente;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarRequisicoa.PainelVisualizarRequisicao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;
import com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.paineisAdicaoDeCLientes.PainelAdicaoDeClientes;

/**
 *
 * @author ramon
 */
public class FramePrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FramePrincipal.class.getName());
    private GerenciadorDePaineis<Rotas> gerenciadorDePaineis= null;
    private Conteiner conteiner;
    private BaraLateral baraNavegacao;
    /**
     * Creates new form FremePrincipal
     */
    public FramePrincipal() {
        baraNavegacao=new BaraLateral((r)->{gerenciadorDePaineis.navegar(r);});
        baraNavegacao.setMinimumSize(new Dimension(60,700));
        baraNavegacao.setPreferredSize(new Dimension(60,700));
        baraNavegacao.setMaximumSize(new Dimension(60,32767));
        this.add(baraNavegacao);
        initComponents();
        
        gerenciadorDePaineis= new GerenciadorDePaineis<>(this,(PainelGerenciavel)jPanel1);
        iniciarCOnteiner();
        iniciarRotas();
        gerenciadorDePaineis.navegar(new Rotas.Requisicoes());
        baraNavegacao.setIconeSelecionado(1);
        
        
    }
    
    private void iniciarRotas(){
       gerenciadorDePaineis.adicionarRota(Rotas.Clientes.class,(r)->{
           // EventQueue.invokeLater(()->{baraNavegacao.setVisible(true);});
           baraNavegacao.apartecer();
            return new PainelDeClientes((r2)->gerenciadorDePaineis.navegarComPilhaDeRetorno(r2),
                                         new ControleClientes((GerenciadorThreadPool)conteiner.obter(GerenciadorThreadPool.class),
                                                               (Repositorio) conteiner.obter(Repositorio.class)));
        });
       
        gerenciadorDePaineis.adicionarRota(Rotas.Requisicoes.class,(rota)->{
        //EventQueue.invokeLater(()->{baraNavegacao.setVisible(true);});    
        baraNavegacao.apartecer();
        return new PainelRequisicoes(new ControleRequisicoes(conteiner.obter(GerenciadorThreadPool.class),
                                                             conteiner.obter(Repositorio.class)),
                                    (r2)->gerenciadorDePaineis.navegarComPilhaDeRetorno(r2));
        });
        
        gerenciadorDePaineis.adicionarRota(Rotas.Produtos.class,(r)->{
         //EventQueue.invokeLater(()->{baraNavegacao.setVisible(true);});
         baraNavegacao.apartecer();
         return new PainelProdutos(new ControleProdutos(conteiner.obter(GerenciadorThreadPool.class),conteiner.obter(Repositorio.class) ),
                                  (rota)->gerenciadorDePaineis.navegarComPilhaDeRetorno(rota));
        });
        
        gerenciadorDePaineis.adicionarRota(Rotas.AdicionarProduto.class,(r)->{
           //EventQueue.invokeLater(()->{baraNavegacao.setVisible(false);}); 
           baraNavegacao.desaparecer();
            var rota=(Rotas.AdicionarProduto)r;
            return new PainelAdicionarProdutos(new ControleAdicionarProdutos(conteiner.obter(GerenciadorThreadPool.class),conteiner.obter(Repositorio.class),rota.ps()),
                                              (r1)->gerenciadorDePaineis.popPilhaDeRetorno());});
        
        gerenciadorDePaineis.adicionarRota(Rotas.AdicionarCliente.class,(r)->{
         /*EventQueue.invokeLater(()->{
          baraNavegacao.setVisible(false);});*/
         baraNavegacao.desaparecer();
         var rota =(Rotas.AdicionarCliente) r;
         
         return new PainelAdicaoDeClientes(new ControleAdicionarClientes(rota.dados(),conteiner.obter(GerenciadorThreadPool.class),conteiner.obter(Repositorio.class)),
                                           (r2)->gerenciadorDePaineis.popPilhaDeRetorno());
        });
        
        gerenciadorDePaineis.adicionarRota(Rotas.AdicionarRequisicao.class,(r)->{
            var rota =(Rotas.AdicionarRequisicao)r;
            
            //EventQueue.invokeLater(()->{baraNavegacao.setVisible(false);});
            baraNavegacao.desaparecer();
            return new PainelAdicaoRequisicao(()->gerenciadorDePaineis.popPilhaDeRetorno(),
                                              ()->gerenciadorDePaineis.navegarComPilhaDeRetorno(new Rotas.AdicionarCliente(null)),
                                              new ControleAdicianarRequisicao(conteiner.obter(GerenciadorThreadPool.class),
                                                                              conteiner.obter(Repositorio.class),rota.dados()));
        });
        
        gerenciadorDePaineis.adicionarRota(Rotas.VisualizarCliente.class,(r)->{
        //EventQueue.invokeLater(()->baraNavegacao.setVisible(false));
        baraNavegacao.desaparecer();
        var rota=(Rotas.VisualizarCliente)r;
        return new PainelVisualizarCliente(new ControleVisualizarClientes(conteiner.obter(Repositorio.class),
                                                                          conteiner.obter(GerenciadorThreadPool.class),
                                                                          rota.dados().getCliente()),
                                           ()->gerenciadorDePaineis.popPilhaDeRetorno(),
                                            (d)->gerenciadorDePaineis.navegarComPilhaDeRetorno(new Rotas.AdicionarCliente(d)));
        });
        
        gerenciadorDePaineis.adicionarRota(Rotas.VisualizarRequisicao.class,(r)->{
            //EventQueue.invokeLater(()->{baraNavegacao.setVisible(false);});
            baraNavegacao.desaparecer();
            var rota =(Rotas.VisualizarRequisicao)r;
            return new PainelVisualizarRequisicao(new ControleVisualizarRequisicao(conteiner.obter(GerenciadorThreadPool.class),
                                                                                   conteiner.obter(Repositorio.class),
                                                                                   rota.dados()),
                                                 ()->gerenciadorDePaineis.popPilhaDeRetorno(),
                                                  (d)->gerenciadorDePaineis.navegarComPilhaDeRetorno(new Rotas.AdicionarRequisicao(d)));
        });
        gerenciadorDePaineis.adicionarRota(Rotas.VisualizarProdutoServico.class,(r)->{
            baraNavegacao.desaparecer();
         var rota=(Rotas.VisualizarProdutoServico)r;
         return new PainelVisualizarProdutos(new ControleVisualizarProdutos(conteiner.obter(GerenciadorThreadPool.class),conteiner.obter(Repositorio.class),rota.prosuto()),
                                             ()->gerenciadorDePaineis.popPilhaDeRetorno(),
                                              (p)->gerenciadorDePaineis.navegarComPilhaDeRetorno(new Rotas.AdicionarProduto(p)));
        });
        gerenciadorDePaineis.listenerFimDePilha((n)->baraNavegacao.apartecer());
    }
    
    private void iniciarCOnteiner(){
    conteiner= new Conteiner(this);
    conteiner.adicionar(Repositorio.class,Repositorio.getInstacia());
    conteiner.adicionar(GerenciadorThreadPool.class,GerenciadorThreadPool.getInstance());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new PainelGerenciavel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setPreferredSize(new java.awt.Dimension(1000, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setAlignmentX(CENTER_ALIGNMENT);
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 700));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 700));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
