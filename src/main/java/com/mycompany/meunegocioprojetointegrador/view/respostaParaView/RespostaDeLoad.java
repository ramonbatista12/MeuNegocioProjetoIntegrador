/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.respostaParaView;

/**
 *
 * @author ramon
 */
public sealed interface RespostaDeLoad<T> {
    record Load<T>() implements RespostaDeLoad{}
    record ErroVasio<T>() implements RespostaDeLoad{}
    record Erro<T>(String mensagem) implements RespostaDeLoad {}
    record OK<T>(T resposta) implements RespostaDeLoad {}
    record OKVasio() implements RespostaDeLoad {}
}
