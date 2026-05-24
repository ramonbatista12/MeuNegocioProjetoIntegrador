/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.HistoricoDeMudancas;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ramon
 */
public class ModeloTabelaHistoricoDeMudancas extends AbstractTableModel{
    private final String[] cabesalho =new String[]{"Data-Hora","Estado"};
    private List<HistoricoDeMudancas> lista = new ArrayList<>();
    @Override
    public int getRowCount() {
        return lista.size();}

    @Override
    public int getColumnCount() {
      return cabesalho.length;  }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var obejeto=lista.get(rowIndex);
        switch (columnIndex) {
            case 0 ->{return obejeto.getCarimboDataEHora().toString();}
            case 1->{return  obejeto.getEstado().getDescricao();}
            default->{ return null;}
        }
    }

    @Override
    public String getColumnName(int column) {
    return  cabesalho[column];
    }
    
    public void updateLista(List<HistoricoDeMudancas> l){
    this.lista=l;
    fireTableDataChanged();
    }
    
}
