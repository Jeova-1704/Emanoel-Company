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


}
