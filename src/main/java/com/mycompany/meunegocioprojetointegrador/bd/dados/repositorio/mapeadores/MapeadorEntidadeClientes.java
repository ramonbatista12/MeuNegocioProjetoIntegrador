/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.mapeadores;

import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeEndereco;
import com.mycompany.meunegocioprojetointegrador.bd.dados.entidades.EntidadeTelefone;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import com.mysql.cj.xdevapi.Session;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class MapeadorEntidadeClientes {
    public DadosDoCliente mapearEntidadeParaDadosDoCliente(EntidadeCliente e){
    var cliente =new Cliente(e.getId(),e.getCpf(),e.getCnpj(),e.getNome());
    List<Telefone> telefones = e.getTelefones().stream().map((t)->new MapeadorEntidadeTelefone().mapearEntidadeParaTelefone(t)).toList();
    return new DadosDoCliente(cliente,telefones,new ArrayList<>());
    }
    
    public Cliente mapearEntidadeParaCliente(EntidadeCliente e){
    return new Cliente(e.getId(),e.getCpf(),e.getCnpj(),e.getNome());
    }
    
    public EntidadeCliente mapeClienteParaEntidadeCliente(Cliente e){
    var entidade= new EntidadeCliente();
    entidade.setCnpj(e.getCnpj());
    entidade.setCpf(e.getCpf());
    entidade.setNome(e.getNome());
    entidade.setId(e.getId());
    return entidade;
    }
    
}
