/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.respostas;

/**
 *
 * @author ramon
 */
public enum RespostaDefault {
    NaoEncontrado("Nao encontrado"),
    Inesistente("Inessistente"),
    Desconhesido("Erro Desconhecido"),
    OperacaoNaoComcluida("Opercao não foi comcluida");
    private String s;
    private RespostaDefault(String s){
     this.s=s;
    }
    public String getMenssagen(){return s;}
}
