/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

import java.time.LocalDateTime;

/**
 *
 * @author ramon
 */
public class HistoricoDeMudancas {
    private Long id;
    private Long idRequisicao;
    private Estado estado;
    private LocalDateTime carimboDataEHora;

    public HistoricoDeMudancas(Long id, Long idRequisicao, Estado estado, LocalDateTime carimboDataEHora) {
        this.id = id;
        this.idRequisicao = idRequisicao;
        this.estado = estado;
        this.carimboDataEHora = carimboDataEHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(Long idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getCarimboDataEHora() {
        return carimboDataEHora;
    }

    public void setCarimboDataEHora(LocalDateTime carimboDataEHora) {
        this.carimboDataEHora = carimboDataEHora;
    }
    
    
}
