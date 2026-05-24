/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.listas;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis.Painel;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author ramon
 */
public class RencerCellCLiente implements ListCellRenderer<DadosDoCliente>{
     PainelParaListaDeClientes p = new PainelParaListaDeClientes();
     String[] iniciaismock=new String[]{"A","b","c","D","N"};

    @Override
    public Component getListCellRendererComponent(JList<? extends DadosDoCliente> list, DadosDoCliente value, int index, boolean isSelected, boolean cellHasFocus) {
        var ponteiroCliente =value.getCliente();
        String  documento=checardocumento(ponteiroCliente);
        
        
        p.lDoc.setText(documento);
        p.lNome.setText(ponteiroCliente.getNome());
        p.lTelefone.setText(value.getTelefones().get(0).getTelefone());
        p.selecionado(isSelected);
        p.setIniciais(criarIniciais(ponteiroCliente.getNome()));
        
        return p;
    }

 private String criarIniciais(String nome){
     var split=nome.split("\\s");
     String inicial=null;
        if(split.length>0 && !split[0].isBlank()){
            inicial=""+split[0].charAt(0);
        }else if(split.length>1&& !split[1].isBlank()){
            inicial=""+split[0].charAt(0)+split[1].charAt(0);
          
        }
        return inicial;
 } 
    
 private String checardocumento(Cliente ponteiroCliente){
     String documento;
     if(ponteiroCliente.getCnpj()==null&&ponteiroCliente.getCpf()==null)documento="Nao informado";
else if(ponteiroCliente.getCnpj()!=null&&ponteiroCliente.getCpf()!=null)
                documento="Cpf : "+ponteiroCliente.getCpf()+" Cnpj : "+ponteiroCliente.getCnpj();
else if(ponteiroCliente.getCnpj()==null)documento="Cpf : "+ponteiroCliente.getCpf();
     else documento="Cnpj : "+ponteiroCliente.getCnpj();
    return documento;
 }
   
    
}
