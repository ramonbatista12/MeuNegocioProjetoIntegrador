/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

/**
 *
 * @author ramon
 */
public sealed interface PesquisaClientes {
   public record PesquisaNome(String nome) implements PesquisaClientes{}
   
   public record PesquisaCpf(String cpf) implements PesquisaClientes{}
   
   public record PesquisaCnpj(String cnpj) implements PesquisaClientes{}
   
   public record PesquisaVasia()implements PesquisaClientes{}
}
