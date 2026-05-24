/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnumPesquisaProdutos;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author ramon
 */
public class ModeloComboBoxPesquisaProdutos extends AbstractListModel<EnumPesquisaProdutos>implements ComboBoxModel<EnumPesquisaProdutos>{
    private EnumPesquisaProdutos[] pesquisas = new EnumPesquisaProdutos[]{EnumPesquisaProdutos.Nome,
                                                                          EnumPesquisaProdutos.Id};
    private EnumPesquisaProdutos itemSelecionado =null;
    @Override
    public int getSize() {
    return pesquisas.length;
    }

    @Override
    public EnumPesquisaProdutos getElementAt(int index) {
    return pesquisas[index];
    }

    @Override
    public void setSelectedItem(Object anItem) {
    itemSelecionado=(EnumPesquisaProdutos)anItem;
    }

    @Override
    public Object getSelectedItem() {
    return itemSelecionado;
            }
    
}
