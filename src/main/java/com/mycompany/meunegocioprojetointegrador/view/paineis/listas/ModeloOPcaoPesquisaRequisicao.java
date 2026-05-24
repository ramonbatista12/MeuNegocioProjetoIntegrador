/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnumPesquisaRequisicao;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author ramon
 */
public class ModeloOPcaoPesquisaRequisicao extends AbstractListModel<EnumPesquisaRequisicao> implements ComboBoxModel<EnumPesquisaRequisicao>{
    public EnumPesquisaRequisicao[] opcoes=EnumPesquisaRequisicao.values();
    private EnumPesquisaRequisicao itemSelecionado;
    @Override
    public int getSize() {
    return opcoes.length;
    }

    @Override
    public EnumPesquisaRequisicao getElementAt(int index) {
    return opcoes[index];
    }

    @Override
    public void setSelectedItem(Object anItem) {
    itemSelecionado=(EnumPesquisaRequisicao) anItem;
    }

    @Override
    public Object getSelectedItem() {
     
    return itemSelecionado;
    }
    
    
}
