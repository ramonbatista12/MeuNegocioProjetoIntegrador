/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarRequisicoa;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.EnunEstados;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.HistoricoDeMudancas;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.view.Temas;
import com.mycompany.meunegocioprojetointegrador.view.controles.ControleVisualizarRequisicao;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import com.mycompany.meunegocioprojetointegrador.view.paineis.Util.PainelLoadBarraCircular;
import com.mycompany.meunegocioprojetointegrador.view.paineis.listas.ModeloProdutoSelecionado;
import com.mycompany.meunegocioprojetointegrador.view.paineis.Util.util.PainelComBorda;
import com.mycompany.meunegocioprojetointegrador.view.respostaParaView.RespostaDeLoad;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ramon
 */
public class PainelVisualizarRequisicao extends Painel {
    private ControleVisualizarRequisicao controle;
    private PainelListaDeProdutosSelecionados produtosSelecionados;
    private PainelHistoricoRequisicao historicoDaRequisicao;
    private Runnable voutar;
    private NavegarParaEdicao editar;
    private boolean atualizado=false;
    /**
     * Creates new form PainelVisualizarRequisicao
     */
    public PainelVisualizarRequisicao(ControleVisualizarRequisicao controle,Runnable v,NavegarParaEdicao editar) {
        this.controle=controle;
        voutar=v;
        this.editar=editar;
        initComponents();
        produtosSelecionados = new PainelListaDeProdutosSelecionados();
        historicoDaRequisicao= new PainelHistoricoRequisicao();
        jPListas.add(produtosSelecionados,SubEstadosListaInternasVisualizarRequisicoes.ListaDeProdutos.class.getSimpleName());
        jPListas.add(historicoDaRequisicao,SubEstadosListaInternasVisualizarRequisicoes.ListaDeMudancas.class.getSimpleName());
        controle.getEstadoDadosDaRequisicao().observar((dados)->{
            if(dados!=null)
            caregarDados((DadosDaRequisicao) dados);
        }, this);
        controle.getEstadoListaProdutos().observar((l)->{
         produtosSelecionados.updateLista((List<ProdutoSelecionado>) l);
        }, this);
        controle.getEstadoTotal().observar((total)->{
            String string ;
            if(total==null)string="0,0"; else string=String.format("%.02f",(Double)total);
         jLabelTotal.setText(string);
        }, this);
        controle.getEstadoHistoricoDeMudancas().observar((l)->{
         historicoDaRequisicao.updateLista((List<HistoricoDeMudancas>) l);
        }, this);
        controle.getListaVisualizada().observar((l)->{
         if(l instanceof SubEstadosListaInternasVisualizarRequisicoes.ListaDeProdutos lp){
         jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/history_23dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
         jButton7.setText("Historico");
         var cardlayou=(CardLayout)jPListas.getLayout();
         cardlayou.show(jPListas,lp.getClass().getSimpleName());
         }
         if(l instanceof SubEstadosListaInternasVisualizarRequisicoes.ListaDeMudancas lm){
         jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shoppingmode_25dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
         jButton7.setText("Produtos");
         var cardlayou=(CardLayout)jPListas.getLayout();
         cardlayou.show(jPListas,lm.getClass().getSimpleName());
         }
        },this);
        controle.getEstadoListaProdutos().observar((l)->{
            System.out.println("Notificacao da lista de produto foi recebi");
            ((List)l).forEach((i)->{
                System.out.println("items na lista "+i);
            });
        produtosSelecionados.updateLista((List<ProdutoSelecionado>) l);
        },this);
        controle.getLoadOperacoes().observar((r)->{
        if(r instanceof RespostaDeLoad.OKVasio){
         jPAnimacao.setVisible(false);
        }
        if(r instanceof  RespostaDeLoad.OK ){
        jPAnimacao.setVisible(false);
        }
        if(r instanceof RespostaDeLoad.Load){
        jPAnimacao.setVisible(true);
        }
        if(r instanceof  RespostaDeLoad.Erro er){
        jPAnimacao.setVisible(false);
            JOptionPane.showMessageDialog(jPanel1,er.mensagem());
        }
        },this);
    }
    public void caregarDados(DadosDaRequisicao d){
     String decricao= (d.getRequisicao().getDescricao()==null||d.getRequisicao().getDescricao().isBlank())?"Nao informado"  :d.getRequisicao().getDescricao();
     String observacao=(d.getRequisicao().getObservacao()==null||d.getRequisicao().getObservacao().isBlank())?"Nao informado ":d.getRequisicao().getObservacao();
     labelCliente.setText(d.getCliente().getNome());
     labelEstado.setText(d.getEstado().getDescricao());
     labelNumero.setText(d.getRequisicao().getId().toString());
     jLabelDescricao.setText(decricao);
     jLabelObservacao.setText(observacao);
        switch (EnunEstados.getEstado(d.getEstado().getId())) {
            case EnunEstados.Aprovado ->{
             jBPedidoConfirmado.setVisible(false);
            }
            case EnunEstados.Camcelado->{
            jPBotoesDeEstado.setVisible(false);
            }
            case EnunEstados.Entreguq->{
            jPBotoesDeEstado.setVisible(false);
            }
            case EnunEstados.Pendendte->{}
           
        }
     
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        labelNumero = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        labelCliente = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        labelEstado = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabelDescricao = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabelObservacao = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabelTotal = new javax.swing.JLabel();
        jPBotoesDeEstado = new javax.swing.JPanel();
        jButonCancelarPedido = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jBPedidoEntreque = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jBPedidoConfirmado = new javax.swing.JButton();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPAnimacao = new PainelLoadBarraCircular();
        jPListas = new PainelComBorda();
        jPanel9 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButton5 = new javax.swing.JButton();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButton6 = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButton7 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(32767, 900));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel2.setMinimumSize(new java.awt.Dimension(800, 70));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 70));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(300, 60));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Requisicao N° ");
        jLabel1.setAlignmentX(0.5F);
        jPanel3.add(jLabel1);
        jPanel3.add(filler1);

        labelNumero.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelNumero.setText("1");
        labelNumero.setMaximumSize(new java.awt.Dimension(50, 32));
        jPanel3.add(labelNumero);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2);

        jPanel4.setMaximumSize(new java.awt.Dimension(600, 700));
        jPanel4.setMinimumSize(new java.awt.Dimension(600, 700));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(600, 30));
        jPanel5.setMinimumSize(new java.awt.Dimension(600, 30));
        jPanel5.setPreferredSize(new java.awt.Dimension(30059, 30));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Cliente :");
        jPanel5.add(jLabel3);
        jPanel5.add(filler2);

        labelCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelCliente.setText("jLabel4");
        labelCliente.setMaximumSize(new java.awt.Dimension(30000, 20));
        labelCliente.setMinimumSize(new java.awt.Dimension(30000, 20));
        labelCliente.setPreferredSize(new java.awt.Dimension(30000, 20));
        jPanel5.add(labelCliente);

        jPanel4.add(jPanel5);

        jPanel6.setMaximumSize(new java.awt.Dimension(600, 30));
        jPanel6.setMinimumSize(new java.awt.Dimension(600, 30));
        jPanel6.setPreferredSize(new java.awt.Dimension(30058, 30));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Estado :");
        jPanel6.add(jLabel5);
        jPanel6.add(filler3);

        labelEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelEstado.setText("jLabel4");
        labelEstado.setMaximumSize(new java.awt.Dimension(30000, 20));
        labelEstado.setMinimumSize(new java.awt.Dimension(30000, 20));
        labelEstado.setPreferredSize(new java.awt.Dimension(30000, 20));
        jPanel6.add(labelEstado);

        jPanel4.add(jPanel6);

        jPanel10.setMaximumSize(new java.awt.Dimension(600, 30));
        jPanel10.setMinimumSize(new java.awt.Dimension(600, 30));
        jPanel10.setPreferredSize(new java.awt.Dimension(30047, 30));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Desc :");
        jPanel10.add(jLabel8);
        jPanel10.add(filler10);

        jLabelDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelDescricao.setMaximumSize(new java.awt.Dimension(30000, 20));
        jLabelDescricao.setMinimumSize(new java.awt.Dimension(30000, 20));
        jLabelDescricao.setPreferredSize(new java.awt.Dimension(30000, 20));
        jPanel10.add(jLabelDescricao);

        jPanel4.add(jPanel10);

        jPanel11.setMaximumSize(new java.awt.Dimension(600, 30));
        jPanel11.setMinimumSize(new java.awt.Dimension(600, 30));
        jPanel11.setPreferredSize(new java.awt.Dimension(30047, 30));
        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Obs :");
        jPanel11.add(jLabel9);
        jPanel11.add(filler11);

        jLabelObservacao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelObservacao.setText("jLabel4");
        jLabelObservacao.setMaximumSize(new java.awt.Dimension(30000, 20));
        jLabelObservacao.setMinimumSize(new java.awt.Dimension(30000, 20));
        jLabelObservacao.setPreferredSize(new java.awt.Dimension(30000, 20));
        jPanel11.add(jLabelObservacao);

        jPanel4.add(jPanel11);

        jPanel7.setMaximumSize(new java.awt.Dimension(600, 30));
        jPanel7.setMinimumSize(new java.awt.Dimension(600, 30));
        jPanel7.setPreferredSize(new java.awt.Dimension(30047, 30));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Total :");
        jPanel7.add(jLabel7);
        jPanel7.add(filler4);

        jLabelTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelTotal.setText("jLabel4");
        jLabelTotal.setMaximumSize(new java.awt.Dimension(30000, 20));
        jLabelTotal.setMinimumSize(new java.awt.Dimension(30000, 20));
        jLabelTotal.setPreferredSize(new java.awt.Dimension(30000, 20));
        jPanel7.add(jLabelTotal);

        jPanel4.add(jPanel7);

        jPBotoesDeEstado.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPBotoesDeEstado.setLayout(new javax.swing.BoxLayout(jPBotoesDeEstado, javax.swing.BoxLayout.LINE_AXIS));

        jButonCancelarPedido.setBackground(Temas.backgraundBotoes);
        jButonCancelarPedido.setForeground(Temas.foregraundBotoes);
        jButonCancelarPedido.setText("Cancelar pedido");
        jButonCancelarPedido.setMaximumSize(new java.awt.Dimension(126, 30));
        jButonCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButonCancelarPedidoActionPerformed(evt);
            }
        });
        jPBotoesDeEstado.add(jButonCancelarPedido);
        jPBotoesDeEstado.add(filler5);

        jBPedidoEntreque.setText("Pedido Entregue");
        jBPedidoEntreque.setMaximumSize(new java.awt.Dimension(126, 30));
        jBPedidoEntreque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPedidoEntrequeActionPerformed(evt);
            }
        });
        jPBotoesDeEstado.add(jBPedidoEntreque);
        jBPedidoEntreque.setBackground(Temas.backgraundBotoes);

        jBPedidoEntreque.setForeground(Temas.foregraundBotoes);
        jPBotoesDeEstado.add(filler6);

        jBPedidoConfirmado.setText("Comfirmar Pedido");
        jBPedidoConfirmado.setMaximumSize(new java.awt.Dimension(136, 30));
        jBPedidoConfirmado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPedidoConfirmadoActionPerformed(evt);
            }
        });
        jPBotoesDeEstado.add(jBPedidoConfirmado);
        jBPedidoConfirmado.setBackground(Temas.backgraundBotoes);

        jBPedidoConfirmado.setForeground(Temas.foregraundBotoes);
        jPBotoesDeEstado.add(filler12);

        jPAnimacao.setMaximumSize(new java.awt.Dimension(30, 30));
        jPAnimacao.setMinimumSize(new java.awt.Dimension(30, 30));
        jPAnimacao.setPreferredSize(new java.awt.Dimension(30, 30));
        jPBotoesDeEstado.add(jPAnimacao);

        jPanel4.add(jPBotoesDeEstado);

        jPListas.setMaximumSize(new java.awt.Dimension(32767, 200));
        jPListas.setLayout(new java.awt.CardLayout());
        jPanel4.add(jPListas);

        jPanel9.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jButton4.setBackground(Temas.backgraundBotoes);
        jButton4.setForeground(Temas.foregraundBotoes);
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/arrow_back_23dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        jButton4.setText("voutar");
        jButton4.setMaximumSize(new java.awt.Dimension(126, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton4);
        jPanel9.add(filler7);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture_as_pdf_23dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        jButton5.setText("Pdf");
        jButton5.setMaximumSize(new java.awt.Dimension(126, 30));
        jButton5.setBackground(Temas.backgraundBotoes);
        jButton5.setForeground(Temas.foregraundBotoes);
        jPanel9.add(jButton5);
        jBPedidoEntreque.setBackground(Temas.backgraundBotoes);

        jBPedidoEntreque.setForeground(Temas.foregraundBotoes);
        jPanel9.add(filler8);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit_16dp_FFFFFF_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton6.setText("Editar");
        jButton6.setMaximumSize(new java.awt.Dimension(136, 30));
        jButton6.setBackground(Temas.backgraundBotoes);
        jButton6.setForeground(Temas.foregraundBotoes);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton6);
        jBPedidoConfirmado.setBackground(Temas.backgraundBotoes);

        jBPedidoConfirmado.setForeground(Temas.foregraundBotoes);
        jPanel9.add(filler9);

        jButton7.setBackground(Temas.backgraundBotoes);
        jButton7.setForeground(Temas.foregraundBotoes);
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/history_23dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        jButton7.setText("Historico");
        jButton7.setMaximumSize(new java.awt.Dimension(136, 30));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton7);
        jBPedidoConfirmado.setBackground(Temas.backgraundBotoes);

        jBPedidoConfirmado.setForeground(Temas.foregraundBotoes);

        jPanel4.add(jPanel9);

        jPanel1.add(jPanel4);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      voutar.run();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        controle.MudarLista();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButonCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButonCancelarPedidoActionPerformed
        controle.updateEstado(EnunEstados.Camcelado);
    }//GEN-LAST:event_jButonCancelarPedidoActionPerformed

    private void jBPedidoEntrequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPedidoEntrequeActionPerformed
        controle.updateEstado(EnunEstados.Entreguq);
    }//GEN-LAST:event_jBPedidoEntrequeActionPerformed

    private void jBPedidoConfirmadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPedidoConfirmadoActionPerformed
       controle.updateEstado(EnunEstados.Aprovado);
    }//GEN-LAST:event_jBPedidoConfirmadoActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       if(editar!=null){
       atualizado=true;
       editar.editar(controle.getEstadoDadosDaRequisicao().getValot());
       }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jBPedidoConfirmado;
    private javax.swing.JButton jBPedidoEntreque;
    private javax.swing.JButton jButonCancelarPedido;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelObservacao;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPAnimacao;
    private javax.swing.JPanel jPBotoesDeEstado;
    private javax.swing.JPanel jPListas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelNumero;
    // End of variables declaration//GEN-END:variables

        @Override
    public void iniciar() {
        super.iniciar();
        controle.caregarListaDeProdutos();
        controle.caregarTotal();
        controle.caregarListaDoHistoricoDeMudancas();
        if(atualizado){
        controle.caregarEstadoDadosDaRequisicao();
        atualizado=false;
        }
    }
    @Override
    public void finalizar() {
        super.finalizar(); 
        controle.finalizar();
        historicoDaRequisicao=null;
        produtosSelecionados=null;
        voutar=null;
        this.editar=null;
        controle=null;
        
    }


    
public interface NavegarParaEdicao{
public void editar(DadosDaRequisicao d);
}

}
