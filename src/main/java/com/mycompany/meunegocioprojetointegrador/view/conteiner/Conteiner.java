/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.conteiner;

import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author ramon
 * @param <T>
 */
public class Conteiner {
    private HashMap<Class<? extends IFinalizar>,IFinalizar> instancias = new HashMap<>();

    public Conteiner(JFrame f){
    f.addWindowListener(new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent e) {
        instancias.forEach((c,i)->{
            try {
                i.finalizar();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
            }
        
        });
        
        }
    
    
    });
    }
    
    public <T extends IFinalizar>void adicionar(Class<T> c,T f ){
     instancias.put(c, f);
    }
    
    public <T extends IFinalizar> T obter(Class<T> c){
    
        return c.cast(instancias.get(c));
    }
    
    
            
}
