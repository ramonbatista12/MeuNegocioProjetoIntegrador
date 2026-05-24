/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.respostaParaView;

/**
 *
 * @author ramon
 */
public sealed interface RespostaDeValidacaoDeDados {
   public record Validado() implements RespostaDeValidacaoDeDados{}
   public record Invalido(String mensagem) implements RespostaDeValidacaoDeDados{}
   public record NaoAvaliado() implements RespostaDeValidacaoDeDados{}
   
}
