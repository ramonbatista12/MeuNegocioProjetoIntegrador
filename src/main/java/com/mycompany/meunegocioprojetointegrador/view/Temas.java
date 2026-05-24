/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view;

import java.awt.Color;

/**
 *
 * @author ramon
 */
public class Temas {
    public static final Color  backgraundBotoes=new Color(0x444370) ;
    public static final Color[] coresIniciais=new Color[]{new Color(0xFF0000),new Color(0x0D00FF),new Color(0xFF0088)};
    public static final Color foregraundBotoes = Color.WHITE;
    public static final Color corSelecaoImagems=new Color(0x417BC7);
   public static  final Color getCorPorInicias(char inicial){
    var numero=(short)inicial;
    var posicao=numero%3;
    return coresIniciais[posicao];
   }
}
