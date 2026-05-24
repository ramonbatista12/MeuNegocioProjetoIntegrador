/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

/**
 *
 * @author ramon
 */
public class Telefone {
    private Long id,idCli;
    private String telefone;

    public Telefone(Long id, Long idCli, String telefone) {
        this.id = id;
        this.idCli = idCli;
        this.telefone = telefone;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCli() {
        return idCli;
    }

    public void setIdCli(Long idCli) {
        this.idCli = idCli;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
