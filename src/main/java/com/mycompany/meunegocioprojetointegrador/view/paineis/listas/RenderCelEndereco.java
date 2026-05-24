/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author ramon
 */
public class RenderCelEndereco implements ListCellRenderer<Endereco>{
    private PainelEndereco p= new PainelEndereco();
    @Override
    public Component getListCellRendererComponent(JList<? extends Endereco> list, Endereco value, int index, boolean isSelected, boolean cellHasFocus) {
        p.updateEndereco(value);
        p.selecionadao(isSelected);
        return p;
    }
    
}
