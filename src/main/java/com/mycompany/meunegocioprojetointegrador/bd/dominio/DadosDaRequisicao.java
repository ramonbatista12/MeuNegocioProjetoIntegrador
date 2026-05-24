/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

/**
 *
 * @author ramon
 */
public class DadosDaRequisicao {
    private Requisicao requisicao;
    private Estado estado;
    private Cliente cliente;

    public DadosDaRequisicao(Requisicao requisicao, Estado estado, Cliente cliente) {
        this.requisicao = requisicao;
        this.estado = estado;
        this.cliente = cliente;
    }
 
    
    
    public Requisicao getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
