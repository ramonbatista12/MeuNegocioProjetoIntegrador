/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author ramon
 */
public class ModeloListaCOntatos extends AbstractListModel<DadosDoCliente>{
    private List<DadosDoCliente> lista = new ArrayList<>();
    @Override
    public int getSize() {
        return lista.size();
    }

    @Override
    public DadosDoCliente getElementAt(int index) {
        return lista.get(index);
    }

   public void adicionarLista(List<DadosDoCliente> l){
    
    this.lista=l;
       fireContentsChanged(this, 0,lista.size());
    
  }
    
    public DadosDoCliente getItemSelecionado(int indice){
        
    return lista.get(indice);
        
    }

    @Override
    public void addListDataListener(ListDataListener l) {
       super.addListDataListener(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
     super.removeListDataListener(l);
    }
}
