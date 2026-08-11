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

    public void adicionarPainel(Painel p){
     System.err.println("Adicionando painel "+p);   
     this.removeAll();
     this.add(p);
     this.revalidate();
     this.doLayout();
     this.repaint();
     
    }

    



}
