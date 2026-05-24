/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 *
 * @author ramon
 */
@Entity(name = "requisicao")
public class EntidadeRequisicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "observacao")
    private String Observacao;
    @ManyToOne
    @JoinColumn(name = "id_est")
    private EntidadeEstado estado;
    @ManyToOne
    @JoinColumn(name = "id_cli")
    private EntidadeCliente cliente;

    public EntidadeEstado getEstado() {
        return estado;
    }

    public void setEstado(EntidadeEstado estado) {
        this.estado = estado;
    }

    public EntidadeCliente getCliente() {
        return cliente;
    }

    public void setCliente(EntidadeCliente cliente) {
        this.cliente = cliente;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String Observacao) {
        this.Observacao = Observacao;
    }
    
    
}
