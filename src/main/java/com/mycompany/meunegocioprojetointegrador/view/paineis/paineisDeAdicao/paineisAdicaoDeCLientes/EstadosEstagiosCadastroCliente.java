/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeAdicao.paineisAdicaoDeCLientes;

/**
 *
 * @author ramon
 */
public sealed interface EstadosEstagiosCadastroCliente {
    record EstagioNome()implements EstadosEstagiosCadastroCliente{
         
        @Override
        public EstadosEstagiosCadastroCliente anterior() {
            return null; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosCadastroCliente proximo() {
            return new  EstagioEndereco(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        @Override
        public String getRota(){
        return this.getClass().getSimpleName();
        } 
        
    
    }
    record EstagioEndereco() implements EstadosEstagiosCadastroCliente{
        
        
        @Override
        public EstadosEstagiosCadastroCliente anterior() {
            return new EstagioNome(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosCadastroCliente proximo() {
            return new EstagioTelefone(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        @Override
        public String getRota(){
        return this.getClass().getSimpleName();
        } 
        
        }
    record EstagioTelefone() implements EstadosEstagiosCadastroCliente{
         
        @Override
        public EstadosEstagiosCadastroCliente anterior() {
            return new EstagioEndereco(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosCadastroCliente proximo() {
            return null; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        @Override
        public String getRota(){
        return this.getClass().getSimpleName();
        }    
    }
    
    public String getRota();
    public EstadosEstagiosCadastroCliente anterior();
    public EstadosEstagiosCadastroCliente proximo();
}
