/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles.validador;

/**
 *
 * @author ramon
 */
public class ValidadorEndereco {
    private Validadora objeto;
    
public void validar(String rua,String cidade,String bairro,String numero,String estado,String cep,String complemento){
if(rua.isBlank()){objeto= new Invalido("Campo Rua vazio"); return;}
if(bairro.isBlank()){objeto= new Invalido("Campo Bairro vazio");return;}
if(numero.isBlank()){objeto= new Invalido("Campo Numerro vazio");return;}
if(estado.isBlank()){objeto= new Invalido("Campo Estado vasio");return;}
if(checarcepVazio(cep)){objeto= new Invalido("Campo Cep vazio");return;}
objeto= new Valido();
}
private boolean checarcepVazio(String s){
 if((s.replaceAll("\\D", "")).isBlank()) return true;
 return false;
}

public boolean invalido(){
if(objeto instanceof Invalido v)return true;
else return false;
}

public String mensagegem(){
if(objeto instanceof Invalido i)return i.mensagem;
else return "";
}
}
