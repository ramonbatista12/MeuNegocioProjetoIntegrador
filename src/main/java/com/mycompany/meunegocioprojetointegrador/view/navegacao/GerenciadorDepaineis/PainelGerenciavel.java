/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ramon
 */
public class PainelGerenciavel extends JPanel{
    private Timer animacaoTransparencia ;
    private Float transparencia=1f;
    public void adicionarPainel(Painel p){
        System.err.println("Adicionando painel "+p);   
     this.removeAll();
     this.add(p);
     this.revalidate();
     this.doLayout();
     this.repaint();
     
    }

    @Override
    protected void paintChildren(Graphics g) {
        var grafico2d = (Graphics2D)g.create();
        grafico2d.rotate(Math.toRadians(90.0));
        super.paintChildren(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        grafico2d.dispose();
    }

    @Override
    public void removeNotify() {
        super.removeNotify(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void addNotify() {
        super.addNotify(); 
     animacaoTransparencia= new Timer(16,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
            
            }
     });
    }
    
}
