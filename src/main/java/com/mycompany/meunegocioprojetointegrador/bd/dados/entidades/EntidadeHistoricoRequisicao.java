/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades;

import com.mycompany.meunegocioprojetointegrador.estados.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

/**
 *
 * @author ramon
 */
@Entity(name = "historicorequisicoes")
public class EntidadeHistoricoRequisicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_req")
    private Long idRequisicao;
    @ManyToOne
    @JoinColumn(name = "id_est")
    private EntidadeEstado idEstado;
    @Column(name = "datamudanca")
    private LocalDateTime dataMudanca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequicao() {
        return idRequisicao;
    }

    public void setIdRequicao(Long idRequicao) {
        this.idRequisicao = idRequicao;
    }

    public EntidadeEstado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EntidadeEstado idEstado) {
        this.idEstado = idEstado;
    }

    

    public LocalDateTime getDataMudanca() {
        return dataMudanca;
    }

    public void setDataMudanca(LocalDateTime dataMudanca) {
        this.dataMudanca = dataMudanca;
    }
    
    
}
