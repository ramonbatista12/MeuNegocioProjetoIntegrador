/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author ramon
 */
public class RendercelTelefone implements ListCellRenderer<Telefone>{
    private PainelTelefone p = new PainelTelefone();
    @Override
    public Component getListCellRendererComponent(JList<? extends Telefone> list, Telefone value, int index, boolean isSelected, boolean cellHasFocus) {
        p.lTelefone.setText(value.getTelefone());
        p.selecionado(isSelected);
        return p;
    }
    
}
