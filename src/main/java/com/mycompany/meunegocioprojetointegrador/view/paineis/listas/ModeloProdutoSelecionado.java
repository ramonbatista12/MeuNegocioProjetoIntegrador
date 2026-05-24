/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoSelecionado;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ramon
 */
public class ModeloProdutoSelecionado extends AbstractTableModel{
    private String[] cabesalho =new String[]{"Nome","Prod/Serv","Quant","Preco"};
    private List<ProdutoSelecionado> lista = new ArrayList<>();
    @Override
    public int getRowCount() {
       return lista.size();
    }

    @Override
    public int getColumnCount() {
        return cabesalho.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var produto=lista.get(rowIndex);
        switch (columnIndex) {
            case 0->{return produto.getNome() ;}
            case 1->{return produto.getProdutoServico();}
            case 2->{return produto.getQuantidade();}
            case 3->{return produto.getPrecoComoString();}
            default->{throw new IllegalAccessError("Indice invalido");}
        }
    }

    @Override
    public String getColumnName(int column) {
        return cabesalho[column];
    }
    
    public ProdutoSelecionado getItemSelecionado(int indice){
    return lista.get(indice);
    }
    
    public void updateLista(List<ProdutoSelecionado> l){
     lista=l;
     fireTableDataChanged();
    }
    
    public boolean  listavasia(){return lista.isEmpty();}
}
