/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.estados;

import java.awt.EventQueue;

/**
 *
 * @author ramon
 */
public class Observador<T> {
    private IComsumir<T> comsumir;
    
    public Observador(IComsumir c){
    this.comsumir=c;
    }
    
    public void notificar(T valor){
        EventQueue.invokeLater(()->{
            try {
                if(comsumir!=null){
                    comsumir.comsumir(valor);
                }else System.out.println("A notificao nao pode ser trasmitida notificacao morta ");
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
    }
    
    public void clear(){
    this.comsumir=null;
    }
}
