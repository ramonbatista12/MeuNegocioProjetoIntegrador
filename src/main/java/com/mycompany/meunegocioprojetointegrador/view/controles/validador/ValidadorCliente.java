/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles.validador;

/**
 *
 * @author ramon
 */
public class ValidadorCliente {
    private Validadora objeto;
    
   public void getValidar(String nome,String cnpj,String cpf){
   objeto=null;
   if(nome.isBlank()){ objeto= new Invalido("Nome vazio");return;}
   var cnpjVasio=true;
   var cpfVasio=true;
   if(!validarMascar(cnpj)){
    if(!cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")){objeto= new Invalido("CNPJ no formato inválido");return;}
   cnpjVasio=false;
   }
   if(!validarMascar(cpf)){
    if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")){objeto= new Invalido("CPF no formato inválido");return;}
   cpfVasio=false;
   }
   if(cnpjVasio&&cpfVasio){objeto= new Invalido("E presiso que pelomenos um docuemnto seja preenchido");return;}
   objeto= new Valido();
   }
   private boolean  validarMascar(String s){
    var string =s.replaceAll("\\D","");
    if(string.isBlank())return true;
    
    return false;
   }
   public boolean invalido(){
   if(objeto instanceof Invalido) return true;
   if(objeto instanceof Valido )return false;
   return false;
   }
   
   public String mensagem(){
   if(objeto==null) return "";
   if(objeto instanceof Valido) return "Campos validados";
   return ((Invalido) objeto).mensagem;
   }
}
