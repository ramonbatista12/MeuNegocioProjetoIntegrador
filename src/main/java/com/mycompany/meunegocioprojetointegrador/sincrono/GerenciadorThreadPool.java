/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.sincrono;

import com.mycompany.meunegocioprojetointegrador.view.IFinalizar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

/**
 *
 * @author ramon
 */
public class GerenciadorThreadPool implements IFinalizar{
    private static GerenciadorThreadPool instancai;
    private int numeroDeNucleos =Runtime.getRuntime().availableProcessors();
    private ExecutorService executorIo = Executors.newCachedThreadPool();
    private ScheduledExecutorService executorAgendavel = Executors.newScheduledThreadPool(((numeroDeNucleos/2)==0)?2:(numeroDeNucleos/2));
    private ExecutorService defaut=Executors.newFixedThreadPool(numeroDeNucleos);
    private GerenciadorThreadPool(){}
    public static GerenciadorThreadPool getInstance(){
     if(instancai==null) instancai=new GerenciadorThreadPool();
    return instancai;
    }
    public<T> CompletableFuture<T> submeterPoolIO(Supplier s){
        System.err.println("nova tarefa submetida");    
    return CompletableFuture.supplyAsync(s,executorIo);
    }
    
    public Future<?> submeterPoolDefalt(Runnable acao){
    return defaut.submit(acao);
    }
    
    @Override
    public void finalizar() {
     executorAgendavel.shutdownNow();
     executorIo.shutdownNow();
     defaut.shutdownNow();
     executorIo=null;
     executorAgendavel=null;
     defaut=null;
     instancai=null;
    }
    
}
