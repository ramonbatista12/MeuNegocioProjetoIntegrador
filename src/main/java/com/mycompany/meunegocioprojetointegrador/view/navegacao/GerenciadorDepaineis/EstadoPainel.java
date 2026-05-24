/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao.GerenciadorDepaineis;

/**
 *
 * @author ramon
 */
public class EstadoPainel<T> implements ICicloDevide{
    public EnunEstadosPainel estado =EnunEstadosPainel.Inesistente;
    public IFabricaDePaineis<T> fabrica;
    public Painel painel;
    
    public EstadoPainel(IFabricaDePaineis fabrica){
    this.fabrica=fabrica;
    }
    public void mudarEstado(T rota){ 
        System.out.println("GerenciadorDepaineis.EstadoPainel.mudarEstado() +"+rota+" "+estado);    
        switch (estado) {
            case Inesistente->{
           
             this.criar(rota);
            painel.criar();
            estado=EnunEstadosPainel.Criado;
            }
            case Criado->{
                System.out.println("MAquina de estado chanso iniciar no painel "+painel);   
             painel.iniciar();
             estado=EnunEstadosPainel.Iniciado;
            }
            case Iniciado->{
            painel.parar();
            estado=EnunEstadosPainel.Parado;
            }
            case Parado->{
             painel.finalizar();
             this.estado=EnunEstadosPainel.Destruido;
            }
            case Destruido->{
             this.painel=null;
             this.estado=EnunEstadosPainel.Inesistente;
            }
               
        }
    }
    
    public void criar(T rota){
        try {
            this.painel=fabrica.fabricar(rota);
            if(painel==null)throw new IllegalAccessError("Erro ao acesar a fabrica : fabrica nao cria objetos validos valor do objeto e null");
        } catch (Exception e) {
            System.err.println("Erros ao tentar criar o panel");
            e.printStackTrace();
        }
    }
    
    public void pararPainel(){
    estado=EnunEstadosPainel.Parado;
    painel.parar();
    }
    
    public void retornarPainel(){
     estado=EnunEstadosPainel.Iniciado;
     painel.iniciar();
    }
    public void limpar(){
     this.fabrica=null;
     this.painel=null;
     estado=null;
    }
}
