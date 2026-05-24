/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.Util.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Box;
import javax.xml.crypto.dsig.spec.XPathType;

/**
 *
 * @author ramon
 */
public class FillerLinhaSeparadora  extends Box.Filler{
    
    public FillerLinhaSeparadora(Dimension min, Dimension pref, Dimension max) {
        super(min, pref, max);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if(this.getWidth()>0){
         var grafico2d =(Graphics2D)g.create();
         grafico2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
         var cor =Color.LIGHT_GRAY;
         grafico2d.setStroke(new BasicStroke(0.5f,1,1));
         var x=this.getWidth()/2;
         grafico2d.drawLine(x,1,x,this.getHeight()-1);
         grafico2d.dispose();
        }
    }
    
}
