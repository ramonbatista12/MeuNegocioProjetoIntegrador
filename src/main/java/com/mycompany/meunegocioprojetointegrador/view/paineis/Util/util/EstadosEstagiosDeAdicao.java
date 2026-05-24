/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.Util.util;

/**
 *
 * @author ramon
 */
public sealed interface EstadosEstagiosDeAdicao {
    public record EstadosSelecaoDeClioentes () implements EstadosEstagiosDeAdicao{

        @Override
        public EstadosEstagiosDeAdicao getProsimo() {
            return new EstadosSelecaoDeProdutos() ; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosDeAdicao getAnterior() {
            return null; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
    public record EstadosSelecaoDeProdutos()implements EstadosEstagiosDeAdicao{

        @Override
        public EstadosEstagiosDeAdicao getProsimo() {
            return new EstadosEstagioDeDescricao(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosDeAdicao getAnterior() {
            return new EstadosSelecaoDeClioentes(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
    public record EstadosEstagioDeDescricao()implements EstadosEstagiosDeAdicao{

        @Override
        public EstadosEstagiosDeAdicao getProsimo() {
            return null; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public EstadosEstagiosDeAdicao getAnterior() {
            return new EstadosSelecaoDeProdutos(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    
    }
            
    default public String getClassName(){
    return this.getClass().getSimpleName();
    }
    public EstadosEstagiosDeAdicao getProsimo();
    public EstadosEstagiosDeAdicao getAnterior();
    
}
