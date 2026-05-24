/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarRequisicoa;

/**
 *
 * @author ramon
 */
public sealed interface SubEstadosListaInternasVisualizarRequisicoes {
    public record ListaDeProdutos()implements SubEstadosListaInternasVisualizarRequisicoes{

        @Override
        public SubEstadosListaInternasVisualizarRequisicoes getProsimo() {
            return new  ListaDeMudancas(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    public record ListaDeMudancas()implements SubEstadosListaInternasVisualizarRequisicoes{

        @Override
        public SubEstadosListaInternasVisualizarRequisicoes getProsimo() {
            return new ListaDeProdutos(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
    
    public SubEstadosListaInternasVisualizarRequisicoes getProsimo();
    
    
}
