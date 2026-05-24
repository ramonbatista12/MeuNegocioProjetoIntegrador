/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ramon
 */
public class GerenciadorDePaineis<T> {
   private PainelGerenciavel painelGerenciavel;
   private HashMap<Class,EstadoPainel> rotas = new HashMap<>();
   private EstadoPainel estadoDoPainelAtual;
   private Class classeAtual=null;
   private Stack<EstadoPainel> pilha= new Stack();
   private INavegar listenerFimDePilha;
    public GerenciadorDePaineis(JFrame frame,PainelGerenciavel p){
     frame.addWindowListener(new WindowAdapter(){
         @Override
         public void windowClosing(WindowEvent e) {
             
         }
     
     });
     painelGerenciavel=p;
    }
   public void navegar(T objeto){
    
    var prosimoEstado=rotas.get(objeto.getClass());
    if(classeAtual!=null&&classeAtual==objeto.getClass())return;
    if(prosimoEstado==null) throw new IllegalArgumentException("Foi emitida a pasagem de uma rota que nao foi adicionada ");
    System.err.println("criando o prosimo estado");
       while (prosimoEstado.estado!=EnunEstadosPainel.Iniciado)prosimoEstado.mudarEstado(objeto);
    System.err.println(" prosimo estado criado "+prosimoEstado.estado);
    painelGerenciavel.adicionarPainel(prosimoEstado.painel);
    if(estadoDoPainelAtual!=null)
    if(estadoDoPainelAtual.estado!=EnunEstadosPainel.Inesistente){
    while(estadoDoPainelAtual.estado!=EnunEstadosPainel.Inesistente){
          
      estadoDoPainelAtual.mudarEstado(objeto);
      
     }
    }
    estadoDoPainelAtual=prosimoEstado;
    classeAtual=objeto.getClass();
       
    
    
    
   }
   
   public void adicionarRota(Class classeDoObjeto,IFabricaDePaineis fabrica){
       if(classeDoObjeto==null)throw new IllegalArgumentException("A classe pasada como paramentro nao pode ser nula");
       if(fabrica==null)throw  new IllegalArgumentException("A fabrica pasada como parametro nao pode ser nula");
       var estadopainel = new EstadoPainel(fabrica);
       rotas.put(classeDoObjeto, estadopainel);
   }
   
   public void navegarComPilhaDeRetorno(T objeto){
       var estadoAuxiliar = rotas.get(objeto.getClass());
       while(estadoAuxiliar.estado!=EnunEstadosPainel.Iniciado) estadoAuxiliar.mudarEstado(objeto);
       
       if(estadoAuxiliar==null)throw new IllegalArgumentException("O objeto pasado nao faz parte das rotas pasadas para serem gerenciadas");
       if(estadoDoPainelAtual!=null){
       estadoDoPainelAtual.pararPainel();
       estadoDoPainelAtual.painel.setVisible(false);
       pilha.push(estadoDoPainelAtual);
       }
       painelGerenciavel.adicionarPainel(estadoAuxiliar.painel);
       estadoDoPainelAtual=estadoAuxiliar;
       System.out.println("Pilha de retorno "+pilha+" painel atual "+estadoDoPainelAtual);
   }
   
   public void popPilhaDeRetorno(){
   if(pilha.empty()){return;}
   if(pilha.size()==1)listenerFimDePilha.navegar(null);
    var estadoAuxiliar=pilha.pop();
    
     while(estadoDoPainelAtual.estado!=EnunEstadosPainel.Inesistente){
       estadoDoPainelAtual.mudarEstado(null);
     }
     estadoAuxiliar.retornarPainel();
     estadoAuxiliar.painel.setVisible(true);
     painelGerenciavel.adicionarPainel(estadoAuxiliar.painel);
     
     estadoDoPainelAtual=estadoAuxiliar;
   }
   
  
   public void listenerFimDePilha(INavegar n){
   listenerFimDePilha=n;
   }
   
   public void destruir(){
    System.out.println("com.mycompany.meunegocioprojetointegrador.view.navegacao.paineis.GerenciadorDePaineis.destruir()");
    rotas.forEach((c,e)->{
        while (e.estado!=EnunEstadosPainel.Inesistente){ e.mudarEstado(null);}
    });
    rotas.clear();
    pilha.forEach((e)->{while (e.estado!=EnunEstadosPainel.Inesistente){ e.mudarEstado(null);}});
    pilha.clear();
    painelGerenciavel=null;
    estadoDoPainelAtual=null;
    listenerFimDePilha=null;
    System.out.println("com.mycompany.meunegocioprojetointegrador.view.navegacao.paineis.GerenciadorDePaineis.destruir() finalizado");
   }
   
   
}
