/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.bd.dominio;

/**
 *
 * @author ramon
 */
public enum EnunEstados {
    Camcelado(1L,"Cancelado"),
    Pendendte(2L,"Pendente"),
    Entreguq(3L,"Entreque"),
    Aprovado(4L,"Aprovado"),
    ;
    private String descricao;
    private Long id;

    private EnunEstados( Long id,String descricao) {
        this.descricao = descricao;
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId() {
        return id;
    }

    public static class Pendendte {

        public Pendendte() {
        }
    }
    
    /*public static EnunEstados getEstadoPorId(long id){
        switch (id) {
            case 1l ->{return Camcelado;}
            case 2l->{return  Pendendte;}
            case 3l->{return Entreguq;}
            case 4l->{return Aprovado;}
            default->{throw new IllegalArgumentException();}
        }
    }*/
    
    public static EnunEstados getEstado(long id){
    for(var en :values()){
        if(en.getId()==id)return en;
    }
    
    return null;
    }
    
}
