/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles.validador;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;

/**
 *
 * @author ramon
 */
public class ValidadorProdutos {
    private Validadora objeto;
   
    private String nome,descricao;
    private boolean produto;
    private float   preco;
    
    public void validar(String nome,String preco,String descricao,boolean produto){
        
        if(nome.isBlank()){
        objeto=new Invalido("Nome vasio");
        return;
        }
        if(descricao.isBlank()){
        objeto= new Invalido("Descricao vasia");
        return;
        }
        if(!preco.matches(("\\d{1,},\\d{2}"))){
        objeto=new Invalido("Preco no formato invalido");
        return;
        }
        try{
            var strigsenvirgula =preco.replace(',', '.');
            this.preco=Float.parseFloat(strigsenvirgula);
            if(this.preco<=0.0){
             objeto= new Invalido("Preço nao posui um valor definido");   
            return;
            }
        }catch(Exception e){e.printStackTrace(); objeto=new Invalido("Erroa ao comverter para numero"); return; }
       this.nome=nome;
       this.produto=produto;
       this.descricao=descricao;
       
    }
    
    public ProdutoServico montartObjeto(){
    return new ProdutoServico(0l, produto, nome, descricao, preco, true);
    }
    
    public boolean invalido(){
    if(objeto==null)return false;
    if(objeto instanceof Invalido) return true;
    else return false ;
    }
    
    public String mensagem(){
    if(objeto==null) return "" ;  
    if(objeto instanceof Invalido i) return i.mensagem;
    
    return null;
    }
}
