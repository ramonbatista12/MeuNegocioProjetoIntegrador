/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.gerenciamentoDeEntidades;

/**
 *
 * @author ramon
 */
public sealed interface ResultadoDeOperacoes {
    record Sucesso() implements ResultadoDeOperacoes{}
    record Falha(Exception e) implements  ResultadoDeOperacoes{}
    
    
}
