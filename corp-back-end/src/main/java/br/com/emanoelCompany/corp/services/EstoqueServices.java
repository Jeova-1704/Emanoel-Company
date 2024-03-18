package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.DTO.EstoqueDTO;
import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueServices {

    @Autowired
    private ProdutoService produtoService;

    public EstoqueDTO dadosEstoque() {
        List<ProdutoDTO> listaProdutosEstoque = produtoService.listarProdutos();
        Double totalDinheiroEstoque = listaProdutosEstoque.stream().mapToDouble(ProdutoDTO::precoTotal).sum();
        Integer totalProdutos = listaProdutosEstoque.stream().mapToInt(ProdutoDTO::quantidade).sum();
        Integer totalUnitario = listaProdutosEstoque.size();
        return new EstoqueDTO(totalDinheiroEstoque, totalProdutos, totalUnitario);
    }

    public byte[] criarRelatorioProdutosTxt() {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        StringBuilder sb = new StringBuilder();
        sb.append("Relatório de Produtos\n\n");
        for (ProdutoDTO produto : produtos) {
            sb.append("ID: ").append(produto.id()).append(" -- ");
            sb.append("Nome: ").append(produto.nome()).append(" -- ");
            sb.append("Preço: ").append(produto.preco()).append(" -- ");
            sb.append("Categoria: ").append(produto.categoria()).append(" -- ");
            sb.append("Quantidade: ").append(produto.quantidade()).append(" -- ");
            sb.append("Data de Entrada: ").append(produto.dataEntrada()).append(" -- ");
            sb.append("Código do Produto: ").append(produto.codigoProduto()).append(" -- ");
            sb.append("Fornecedor: ").append(produto.fornecedor()).append(" -- ");
            sb.append("Valor Total: ").append(produto.precoTotal()).append(" | ");
            sb.append("\n");
        }
        return sb.toString().getBytes();
    }

}
