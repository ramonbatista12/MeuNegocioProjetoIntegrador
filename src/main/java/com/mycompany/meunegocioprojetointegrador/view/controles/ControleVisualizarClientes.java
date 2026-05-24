/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.controles;

import com.mycompany.meunegocioprojetointegrador.bd.dados.repositorio.Repositorio;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Cliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.Telefone;
import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import com.mycompany.meunegocioprojetointegrador.sincrono.GerenciadorThreadPool;
import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramon
 */
public class ControleVisualizarClientes implements IFinalizar{
    private Repositorio repositorio;
    private GerenciadorThreadPool gerenciadorThreadPool;
    private Estado<Cliente> estadoCliente=new Estado<>(null);
    private Estado<List<Telefone>> estadoListaTelefone = new Estado<>(new ArrayList<>());
    private Estado<List<Endereco>> estadoListaEndereco = new Estado<>(new ArrayList<>());
    public ControleVisualizarClientes(Repositorio r,GerenciadorThreadPool g,Cliente c){
    estadoCliente.notificar(c);
    this.gerenciadorThreadPool=g;
    this.repositorio=r;
    }
    public void ListarEnderecos(Long idCli){
    var completable= gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarEnderecos(idCli));
    completable.exceptionally((ex)->{
    ex.printStackTrace();
    return new ArrayList<>();
    });
    completable.thenAccept((l)->{
        System.out.println("lista de endercos "+l);   
     estadoListaEndereco.notificar((List<Endereco>) l);
    });
    }
    
    public void ListarTelefones(Long idCliente){
    var completable= gerenciadorThreadPool.submeterPoolIO(()->repositorio.listarTelefones(idCliente));
    completable.exceptionally((ex)->{
    ex.printStackTrace();
    return  new ArrayList<>();
    });
    completable.thenAccept((l)->{
    estadoListaTelefone.notificar((List<Telefone>) l);
    });
    }
    
    public Estado<List<Endereco>> getEstadoListaEnderecos(){return estadoListaEndereco;}
    public Estado<List<Telefone>> getEstadoListaTelefones(){return estadoListaTelefone;}
    public Estado<Cliente> getEstadoCliente(){return estadoCliente;}
    
    public DadosDoCliente getDadosDoCliente(){
    return new DadosDoCliente(estadoCliente.getValot(),estadoListaTelefone.getValot(),estadoListaEndereco.getValot());
    }
    @Override
    public void finalizar() {
    this.repositorio=null;
    this.gerenciadorThreadPool=null;
    this.estadoCliente.clear();
    this.estadoListaEndereco.clear();
    this.estadoListaTelefone.clear();
    this.estadoCliente=null;
    this.estadoListaEndereco=null;
    this.estadoListaTelefone=null;
    }
    
    
}
