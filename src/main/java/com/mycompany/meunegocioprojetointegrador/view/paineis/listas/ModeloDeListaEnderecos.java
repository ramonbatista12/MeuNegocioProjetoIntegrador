/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author ramon
 */
public class ModeloDeListaEnderecos extends AbstractListModel<Endereco>{
    private List<Endereco> enderecos= new ArrayList();
    @Override
    public int getSize() {
     return enderecos.size();
    }

    @Override
    public Endereco getElementAt(int index) {
    return enderecos.get(index);
    }
    
    public void adicionarLista(List<Endereco> l){
    
    enderecos=l;
    fireContentsChanged(this, 0,enderecos.size()-1);
    }
    
}
