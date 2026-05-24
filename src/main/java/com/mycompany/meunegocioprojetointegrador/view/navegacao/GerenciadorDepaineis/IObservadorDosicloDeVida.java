/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis;

/**
 *
 * @author ramon
 */
public interface IObservadorDosicloDeVida {
    default public void criar(){}
    default public void iniciar(){}
    default  public void parar(){}
    default public void finalizar(){}
}
