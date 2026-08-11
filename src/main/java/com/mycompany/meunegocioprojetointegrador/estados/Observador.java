/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.estados;

import java.awt.EventQueue;
import java.lang.ref.WeakReference;

/**
 *
 * @author ramon
 */
public class Observador<T> {
    private IComsumir<T> comsumir;
    private WeakReference<Object> objetoNotificavel;
    private WeakReference<IComsumir<T>> referenciaFracaConsumir;
    public Observador(IComsumir c,WeakReference<Object> referenciaFraca){
    referenciaFracaConsumir=new WeakReference<>(c);
    comsumir=c;
    this.objetoNotificavel=referenciaFraca;
    }
    
    public void notificar(T valor){
        
        System.err.println("Agendando notificacap na evt");
        EventQueue.invokeLater(()->{
            System.err.println("Notificacao disparada checando valores ");
            try {
               var objetoNotificavelAux=objetoNotificavel.get();
               if(objetoNotificavelAux!=null){
                   System.err.println("Notificador esta visivel");
                var refererwenciaConsumirAux=referenciaFracaConsumir.get();
                if(refererwenciaConsumirAux!=null){
                    System.err.println("A funcao esta disponivel vou notificar");
                    refererwenciaConsumirAux.comsumir(valor);}
               }
               else{
                   comsumir=null;
                   System.err.println("A funcao nao esta disponivel");
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
    }
    
    public void clear(){
    this.comsumir=null;
    }
}
