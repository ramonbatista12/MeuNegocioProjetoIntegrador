/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

import java.util.List;

/**
 *
 * @author ramon
 */
public class DadosDoCliente {
    private Cliente cliente;
    private List<Telefone> telefones;
    private List<Endereco> enderecos;

    public DadosDoCliente(Cliente cliente, List<Telefone> telefones, List<Endereco> enderecos) {
        this.cliente = cliente;
        this.telefones = telefones;
        this.enderecos = enderecos;
    }
   
    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
}
