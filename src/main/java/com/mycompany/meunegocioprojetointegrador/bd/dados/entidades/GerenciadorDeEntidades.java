/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author ramon
 */
public class GerenciadorDeEntidades {
    private static GerenciadorDeEntidades instancia;
    private static Object lock = new Object();
    private EntityManagerFactory fabricaGerencidoresDeEntidadde= null;
    
    private GerenciadorDeEntidades(){
      fabricaGerencidoresDeEntidadde=Persistence.createEntityManagerFactory("MeuNegocioProjetoIntegrador");
    }
    public static GerenciadorDeEntidades getInstancia() {
        synchronized (lock) {
            if(instancia==null){instancia = new GerenciadorDeEntidades();}}
        return instancia;
    }
    
    public EntityManager getManager(){
     return fabricaGerencidoresDeEntidadde.createEntityManager();
    }
    
   public void finalizar(){
     fabricaGerencidoresDeEntidadde.close();
     fabricaGerencidoresDeEntidadde=null;
     instancia=null;
     
   }
}
