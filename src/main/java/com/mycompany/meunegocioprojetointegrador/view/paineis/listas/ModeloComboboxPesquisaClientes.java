/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.view.controles.validador.EnunPesquisaClientes;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author ramon
 */
public class ModeloComboboxPesquisaClientes extends AbstractListModel<EnunPesquisaClientes> implements ComboBoxModel<EnunPesquisaClientes>{
    private EnunPesquisaClientes[] pesquisa= new EnunPesquisaClientes[]{EnunPesquisaClientes.Nome,
                                                                        EnunPesquisaClientes.Cpf,
                                                                        EnunPesquisaClientes.Cnpj};
    private EnunPesquisaClientes itemselecionado=null;
    
    @Override
    public int getSize() {
    return pesquisa.length;
    }

    @Override
    public EnunPesquisaClientes getElementAt(int index) {
    return pesquisa[index];}

    @Override
    public void setSelectedItem(Object anItem) {
        itemselecionado=(EnunPesquisaClientes)anItem;
        fireContentsChanged(this, -1, -1);
     }

    @Override
    public Object getSelectedItem() {
    return itemselecionado;
    }
    
}
