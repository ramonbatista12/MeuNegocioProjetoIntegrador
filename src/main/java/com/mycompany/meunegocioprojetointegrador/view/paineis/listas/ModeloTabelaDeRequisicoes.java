/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;


import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Estado;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Requisicao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ramon
 */
public class ModeloTabelaDeRequisicoes extends AbstractTableModel{
    private String[] cabesalhoDeRequisicoes = new String[]{"Req","Cliente","Estado"};
    private  List<DadosDaRequisicao> lista=new ArrayList<>();

    public ModeloTabelaDeRequisicoes() {
       
    }
    @Override
    public int getRowCount() {
    return lista.size();
    }

    @Override
    public String getColumnName(int column) {
        return cabesalhoDeRequisicoes[column];
    }
    
    @Override
    public int getColumnCount() {
        return cabesalhoDeRequisicoes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var dados=lista.get(rowIndex);
        switch(columnIndex){
        case 0->{return dados.getRequisicao().getId().toString();}
        case 1->{return dados.getCliente().getNome();}
        case 2->{return dados.getEstado().getDescricao();}
        }
        return lista.get(rowIndex);
    }
    
    public DadosDaRequisicao getItemSelecionado(int index){
    return lista.get(index);
    }
    
    public void updadteLista(List<DadosDaRequisicao> l){
     this.lista=l;
     fireTableDataChanged();
    }
    
}
