/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.meunegocioprojetointegrador;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades.GerenciadorDeEntidades;
import com.mycompany.meunegocioprojetointegrador.view.FramePrincipal;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author ramon
 */
public class MeuNegocioProjetoIntegrador {

    public static void main(String[] args) {
        
        try {
          UIManager.setLookAndFeel(new FlatLightLaf());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(()->{
         var frame =new FramePrincipal();
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
        });
    }
}
