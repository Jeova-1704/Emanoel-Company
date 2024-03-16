package br.com.emanoelCompany.corp.DTO;


public record ProdutoDTO(Long id, String nome, Double preco, String categoria, Integer quantidade, String dataEntrada, String codigoProduto) {
}
