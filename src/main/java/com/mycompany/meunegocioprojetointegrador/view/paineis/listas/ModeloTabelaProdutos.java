/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ramon
 */
public class ModeloTabelaProdutos extends AbstractTableModel{
    private String[] cabesalho = new String[]{"Id","Nome","Produto/Servico","Estatus","Preço"};
    private List<ProdutoServico> lista = new ArrayList<>();
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
        var produto =lista.get(rowIndex);
        switch(columnIndex){
            case 0->{ return produto.getId().toString();}
            case 1->{return produto.getNome();}
            case 2->{
            
            return (produto.isServico())?"Servico":"Produto";}
            case 3->{
            return (produto.isAtivo())?"Ativo":"Inativo";
            }
            case 4->{return String.format("%.2f",produto.getPreco());}
            default->{throw new IllegalAccessError("valor pasdo como indice de coluna nao pode ser usado para retornar um dos campos do objeto");}
        }
   
    }

    @Override
    public String getColumnName(int column) {
        return cabesalho[column];
    }
    
    public ProdutoServico getSelecao(int indice){
    return lista.get(indice);
    }
    
    public void updateLista(List<ProdutoServico> l){
     this.lista=l;
     fireTableDataChanged();
    }
    
}
