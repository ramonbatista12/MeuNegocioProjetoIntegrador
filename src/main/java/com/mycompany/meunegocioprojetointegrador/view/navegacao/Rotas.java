/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.meunegocioprojetointegrador.view.navegacao;

import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDaRequisicao;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.DadosDoCliente;
import com.mycompany.meunegocioprojetointegrador.bd.dominio.ProdutoServico;
import java.awt.image.RasterOp;

/**
 *
 * @author ramon
 */
public sealed interface Rotas  {
    public record  Clientes()implements Rotas{}
    public record  Requisicoes()implements Rotas{}
    public record  Produtos()implements Rotas{}
    public record  AdicionarProduto(ProdutoServico ps) implements Rotas {}
    public record AdicionarCliente(DadosDoCliente dados)implements Rotas{}
    public record AdicionarRequisicao(DadosDaRequisicao dados) implements  Rotas{}
    public record VisualizarCliente(DadosDoCliente dados)implements   Rotas{}
    public record VisualizarRequisicao(DadosDaRequisicao dados)implements Rotas{}
    public record VisualizarProdutoServico(ProdutoServico prosuto) implements Rotas{}
    
}
