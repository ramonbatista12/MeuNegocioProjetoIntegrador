/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.estados;

import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.IObservadorDosicloDeVida;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFrame;

/**
 *
 * @author ramon
 */
public class Estado<T> {
    private AtomicReference<T> estado = new AtomicReference(null);
    private Observadores observadores = new Observadores();
    private int subEstado=0;
    private final int notificavel=1;
    private final int parado=2;
    public Estado(T valorInicial){
    estado.set(valorInicial);
    subEstado=notificavel;
    }
    
    public synchronized  void observar(IComsumir c,JFrame f){
     var observador = new Observador(c);
     observador.notificar(estado.get());
     observadores.adicionarObservador(observador);
     f.addWindowListener(new WindowAdapter(){
         @Override
         public void windowClosing(WindowEvent e) {
             observadores.removerObservador(observador);
         }
     
     });
     }
    
    public synchronized void observar(IComsumir c,Painel p){
     var observador = new Observador(c);
     observador.notificar(estado.get());
     observadores.adicionarObservador(observador);
     p.registraObservadores(new IObservadorDosicloDeVida(){
         @Override
         public void finalizar() {
             System.out.println("Metodo de leimpesa automatico chamadoa ao finalizar ciclo de vida");
             observadores.removerObservador(observador);
         }

         @Override
         public void parar() {
             subEstado=parado;
          }

         @Override
         public void iniciar() {
             subEstado=notificavel;
          }
       
     });
    
    }
    
    public synchronized void notificar(T valor){
        System.out.println("notificando observadores da ocorencia do evento");    
    estado.set(valor);
    observadores.notificar(valor);
    
    }
    
    public T getValot(){
    return estado.get();
    }
    private synchronized void atualizarObservadores(){
     observadores.notificar(estado.get());
    }
    
    public synchronized void clear(){
    observadores.clear();
    observadores=null;
    estado.set(null);
    estado=null;
    }
}
