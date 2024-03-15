package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.model.Produto;
import br.com.emanoelCompany.corp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarID(Long idProduto){
        return produtoRepository.findById(idProduto).get();
    }
    public List<Produto> buscarNome(String nome){
        return produtoRepository.buscarNome(nome.trim().toUpperCase());
    }
}
