/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

/**
 *
 * @author ramon
 */
public sealed interface PesquisaRequisicoes {
    record NomeCliente(String nome)implements PesquisaRequisicoes{}
    record ID(Long id) implements PesquisaRequisicoes{}
    record Estado(String estado) implements PesquisaRequisicoes{}
}
