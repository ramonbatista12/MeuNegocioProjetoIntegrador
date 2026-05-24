/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.respostas;

/**
 *
 * @author ramon
 */
public sealed interface ResultadoIo<T> {
    public record OK<T>(T r) implements ResultadoIo{}
    public record Erro<T>(String Mensagem)implements ResultadoIo{};
    public record ErroVasio<T>() implements ResultadoIo{}
}
