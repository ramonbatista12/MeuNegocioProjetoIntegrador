/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

/**
 *
 * @author ramon
 */
public sealed interface PesquisaProdutosServico {
    record Nome(String nome)implements PesquisaProdutosServico{}
    record Id(Long id)implements PesquisaProdutosServico{}
    record Vazia()implements PesquisaProdutosServico{}
}
