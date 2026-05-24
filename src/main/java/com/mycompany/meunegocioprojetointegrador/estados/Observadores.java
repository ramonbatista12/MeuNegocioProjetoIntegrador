/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.estados;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author ramon
 */
public class Observadores<T> {
    private CopyOnWriteArrayList<Observador> observadores = new CopyOnWriteArrayList<>();
    
    public Observadores(){}
    
    public void adicionarObservador(Observador o){
     observadores.add(o);
    }
    public  void removerObservador(Observador o){
    observadores.remove(o);
    o.clear();
    }
    public void notificar(T valor){
        System.out.println("observadores para os dados "+observadores);    
    observadores.forEach((o)->o.notificar(valor));
    }
    public void clear(){
        System.out.println("limpando observadores ");
     observadores.forEach((o)->{
         o.clear();});
     observadores.clear();
    }
}
