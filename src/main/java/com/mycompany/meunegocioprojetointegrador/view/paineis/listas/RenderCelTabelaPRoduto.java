/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ramon
 */
public class RenderCelTabelaPRoduto implements TableCellRenderer{
    PainelCelulasHEader p = new PainelCelulasHEader();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        switch(column){
            case 0->{p.setWith(100);}
            case 2->{p.setWith(100);}
            case 3->{p.setWith(100);}
        }
        p.lDado.setText((String)value);
        return p;
    }
    
}
