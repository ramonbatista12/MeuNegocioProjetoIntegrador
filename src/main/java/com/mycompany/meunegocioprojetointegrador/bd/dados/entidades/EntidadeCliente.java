/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dados.entidades;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import java.util.List;
import javax.annotation.processing.Generated;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

/**
 *
 * @author ramon
 */
@Entity(name = "clientes")
public class EntidadeCliente {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "cnpj")
    private String cnpj;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private List<EntidadeEndereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private List<EntidadeTelefone> telefones;
            
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<EntidadeEndereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EntidadeEndereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<EntidadeTelefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<EntidadeTelefone> telefones) {
        this.telefones = telefones;
    }
    
    
    
    
}
