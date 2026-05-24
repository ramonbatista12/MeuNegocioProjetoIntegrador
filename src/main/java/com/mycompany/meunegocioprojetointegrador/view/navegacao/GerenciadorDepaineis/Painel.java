/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis;

import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

/**
 *
 * @author ramon
 */
public abstract class Painel extends JPanel implements ICicloDevide{
 private CopyOnWriteArrayList<IObservadorDosicloDeVida>observadores= new CopyOnWriteArrayList<>();
    @Override
    public void finalizar() {
        observadores.forEach((o)->{
            try {
               o.finalizar(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        observadores.clear();
    }

    @Override
    public void parar() {
                observadores.forEach((o)->{
            try {
               o.parar(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
    }

    @Override
    public void iniciar() {
            observadores.forEach((o)->{
            try {
               o.iniciar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
    }
        

    @Override
    public void criar() {
                observadores.forEach((o)->{
            try {
               o.criar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
    }
    
    public void registraObservadores(IObservadorDosicloDeVida o){
     observadores.add(o);
    }
    public void desregistraObservador(IObservadorDosicloDeVida o){
     observadores.remove(o);
    }
}
