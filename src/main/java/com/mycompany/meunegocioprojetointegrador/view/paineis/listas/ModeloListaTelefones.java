/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author ramon
 */
public class ModeloListaTelefones extends AbstractListModel<Telefone>{
    private List<Telefone> telefones = new ArrayList();
    @Override
    public int getSize() {
    return  telefones.size();
    }
    @Override
    public Telefone getElementAt(int index) {
     return telefones.get(index);
    }

   
    
    public void updateLista(List<Telefone> l){
     
     telefones=l;
     fireContentsChanged(this, 0,telefones.size()-1);
     }
}
