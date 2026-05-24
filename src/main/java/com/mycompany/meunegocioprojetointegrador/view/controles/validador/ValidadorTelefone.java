/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles.validador;

/**
 *
 * @author ramon
 */
public class ValidadorTelefone {
    private Validadora objeto;
    
    public void validar(String telefone){
     if(!telefone.matches("\\d{2} \\d{5}-\\d{4}")){objeto= new Invalido("Telefone nao e valido");return;}
     
     objeto= new Valido();
    }
    public String mensagem(){
    if(objeto instanceof Invalido i)return i.mensagem;
    else return "";
    }
    
    public boolean invalido(){
    if(objeto instanceof Invalido)return true;
    else return false;
    }
}
