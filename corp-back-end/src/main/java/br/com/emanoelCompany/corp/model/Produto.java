package br.com.emanoelCompany.corp.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private String nome;

    private String categoria;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @Column(name = "codigo_produto", nullable = false)
    private String codigoProduto;

    public Produto(Long id, Double preco, String nome, String categoria, int quantidade, LocalDate dataEntrada, String codigoProduto) {
        this.id = id;
        this.preco = preco;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.dataEntrada = dataEntrada;
        this.codigoProduto = codigoProduto;
    }

    public Produto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
}
