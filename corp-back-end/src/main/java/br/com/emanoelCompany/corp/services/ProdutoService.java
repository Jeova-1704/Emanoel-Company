package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.exceptions.ConversaoDataProdutoDTOExeption;
import br.com.emanoelCompany.corp.exceptions.ProdutoDTOValidationException;
import br.com.emanoelCompany.corp.exceptions.ProdutoNaoEncontradoException;
import br.com.emanoelCompany.corp.exceptions.QuantidadeInsuficienteException;
import br.com.emanoelCompany.corp.model.Produto;
import br.com.emanoelCompany.corp.repository.ProdutoRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    private ProdutoDTO convertToDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria(),
                produto.getQuantidade(),
                produto.getDataEntrada().toString(),
                produto.getCodigoProduto(),
                produto.getPrecoTotal()
        );
    }

    public ProdutoDTO salvar(ProdutoDTO produto) {
        if (produto.nome() == null || produto.nome().isEmpty() || produto.nome().isBlank()) {
            throw new ProdutoDTOValidationException("Nome do produto não pode ser nulo ou vazio");
        }
        if (produto.preco() == null || produto.preco() <= 0) {
            throw new ProdutoDTOValidationException("Preço do produto não pode ser nulo, negativo ou zero");
        }

        if (produto.categoria() == null || produto.categoria().isEmpty() || produto.categoria().isBlank()) {
            throw new ProdutoDTOValidationException("Categoria do produto não pode ser nula ou vazia");
        }
        if (produto.quantidade() == null || produto.quantidade() <= 0) {
            throw new ProdutoDTOValidationException("A quantidade do produto não pode ser nula, negativa ou zero");
        }

        if (produto.dataEntrada() == null || produto.dataEntrada().isEmpty() || produto.dataEntrada().isBlank()) {
            throw new ProdutoDTOValidationException("A data de entrada não pode ser nula");
        }
        if (produto.codigoProduto() == null || produto.codigoProduto().isEmpty() || produto.codigoProduto().isBlank()) {
            throw new ProdutoDTOValidationException("O codigo do produto não pode ser nulo ou vazio");
        }

        Produto prod = new Produto(produto);
        prod.setPrecoTotal(prod.valorTotal(prod.getQuantidade(), prod.getPreco()));
        Produto produtoSalvo = produtoRepository.save(prod);

        return convertToDTO(produtoSalvo);
    }

    public List<ProdutoDTO> listarProdutos() {
        List<Produto> produtoList = produtoRepository.findAll();
        return produtoList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarID(Long idProduto){
        if (produtoRepository.findById(idProduto).isPresent()) {
            Produto produto = produtoRepository.findById(idProduto).get();
            return convertToDTO(produto);
        } else {
            throw new ProdutoNaoEncontradoException();
        }
    }
    public List<ProdutoDTO> buscarNome(String nome){
        List<Produto> produtoList = produtoRepository.buscarNome(nome.trim().toUpperCase());

        if(produtoList.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }else {
            return produtoList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<ProdutoDTO> buscarCategoria(String categoria){
        List<Produto> produtoList = produtoRepository.findProdutoByCategoria(categoria.trim().toUpperCase());

        if (produtoList.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        } else {
            return produtoList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public void deletar(Long id){
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if(produtoOptional.isPresent()){
            try {
                produtoRepository.delete(produtoOptional.get());
            } catch (StaleObjectStateException e) {
                throw new ProdutoNaoEncontradoException("O produto foi atualizado ou excluído por outra transação.");
            }
        }else {
            throw new ProdutoNaoEncontradoException();
        }
    }
    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        if (produtoDTO.id() == null) {
            throw new ProdutoDTOValidationException("Informe o ID do produto para atualizar!");
        }

        if (produtoDTO.quantidade() == null || produtoDTO.quantidade() <= 0){
            throw new ProdutoDTOValidationException("A quantidade do produto não pode ser nulo, negativo ou zero");
        }

        if (produtoDTO.nome() == null || produtoDTO.nome().isEmpty() || produtoDTO.nome().isBlank()) {
            throw new ProdutoDTOValidationException("Nome do produto não pode ser nulo ou vazio");
        }
        if (produtoDTO.preco() == null || produtoDTO.preco() <= 0) {
            throw new ProdutoDTOValidationException("Preço do produto não pode ser nulo, negativo ou zero ");
        }
        if (produtoDTO.categoria() == null || produtoDTO.categoria().isEmpty() || produtoDTO.categoria().isBlank()) {
            throw new ProdutoDTOValidationException("Categoria do produto não pode ser nula, vazia, negativa ou zero");
        }
        if (produtoDTO.dataEntrada() == null || produtoDTO.dataEntrada().isEmpty() || produtoDTO.dataEntrada().isBlank()) {
            throw new ProdutoDTOValidationException("A data de entrada não pode ser nula");
        }
        if (produtoDTO.codigoProduto() == null || produtoDTO.codigoProduto().isEmpty() || produtoDTO.codigoProduto().isBlank()) {
            throw new ProdutoDTOValidationException("O codigo do produto não pode ser nulo ou vazio");
        }
        Produto produto = produtoRepository.findById(produtoDTO.id()).orElseThrow(() -> new ProdutoNaoEncontradoException("O ID fornecido não condiz com nenhum produto em nosso estoque!"));


        produto.setNome(produtoDTO.nome());
        produto.setCodigoProduto(produtoDTO.codigoProduto());
        produto.setPreco(produtoDTO.preco());
        produto.setQuantidade(produtoDTO.quantidade());
        produto.setDataEntrada(LocalDate.now());
        produto.setCategoria(produtoDTO.categoria());
        produto.setPrecoTotal(produto.valorTotal(produtoDTO.quantidade(), produtoDTO.preco()));
        Produto produtoAtualizado = produtoRepository.save(produto);

        return  convertToDTO(produtoAtualizado);
    }
    public void vender(Map<Long,Integer> vendaMap){

        for (Long id : vendaMap.keySet()){
            int quantidade = vendaMap.get(id);
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("O ID fornecido não condiz com nenhum produto em estoque "));

            int quantidadeAtualizada =  (produto.getQuantidade()-quantidade);
            if(quantidadeAtualizada < 0){
                throw new QuantidadeInsuficienteException();
            }

            produto.setQuantidade(quantidadeAtualizada);
            produtoRepository.save(produto);
        }
    }



}
