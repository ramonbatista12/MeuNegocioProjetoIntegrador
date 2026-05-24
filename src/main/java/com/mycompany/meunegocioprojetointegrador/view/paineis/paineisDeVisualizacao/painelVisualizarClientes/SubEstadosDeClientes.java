/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.paineis.paineisDeVisualizacao.painelVisualizarClientes;

/**
 *
 * @author ramon
 */
public sealed interface SubEstadosDeClientes {
    public record Telefone()implements SubEstadosDeClientes{

        @Override
        public SubEstadosDeClientes prosimo() {
            return new Endereco(); 
        }
    }
    public record Endereco()implements SubEstadosDeClientes{

        @Override
        public SubEstadosDeClientes prosimo() {
            return new Telefone(); 
        }
    }
     
    public SubEstadosDeClientes prosimo();
            
}
