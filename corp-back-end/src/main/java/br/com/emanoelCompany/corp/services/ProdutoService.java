package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.exceptions.ConversaoDataProdutoDTOExeption;
import br.com.emanoelCompany.corp.exceptions.ProdutoDTOValidationException;
import br.com.emanoelCompany.corp.exceptions.ProdutoNaoEncontradoException;
import br.com.emanoelCompany.corp.model.Produto;
import br.com.emanoelCompany.corp.repository.ProdutoRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
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
                produto.getCategoria().toString(),
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
        if (produto.preco() == null) {
            throw new ProdutoDTOValidationException("Preço do produto não pode ser nulo");
        }
        if(produto.preco() < 0){
            throw new ProdutoDTOValidationException("Preço do produto não pode ser negativo");
        }
        if(produto.preco() == 0){
            throw new ProdutoDTOValidationException("Preço do produto não pode ser zero");
        }
        if (produto.categoria() == null || produto.categoria().isEmpty() || produto.categoria().isBlank()) {
            throw new ProdutoDTOValidationException("Categoria do produto não pode ser nula ou vazia");
        }
        if (produto.quantidade() == null) {
            throw new ProdutoDTOValidationException("A quantidade do produto não pode ser nula");
        }
        if (produto.quantidade() < 0) {
            throw new ProdutoDTOValidationException("A quantidade do produto não pode ser negativa");
        }
        if (produto.dataEntrada() == null || produto.dataEntrada().isEmpty() || produto.dataEntrada().isBlank()) {
            throw new ProdutoDTOValidationException("A data de entrada não pode ser nula");
        }
        if (produto.codigoProduto() == null || produto.codigoProduto().isEmpty() || produto.codigoProduto().isBlank()) {
            throw new ProdutoDTOValidationException("O codigo do produto não pode ser nulo ou vazio");
        }
        try {
            LocalDate data = LocalDate.parse(produto.dataEntrada());
        } catch (DateTimeParseException e) {
            throw new ConversaoDataProdutoDTOExeption("Erro ao converter a data para localDate");
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

    public boolean deletar(Long id){
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if(produtoOptional.isPresent()){
            try {
                produtoRepository.delete(produtoOptional.get());
            } catch (StaleObjectStateException e) {
                throw new ProdutoNaoEncontradoException("O produto foi atualizado ou excluído por outra transação.");
            }
            return true;
        }else {
            throw new ProdutoNaoEncontradoException();
        }
    }
    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        if (produtoDTO.id() == null) {
            throw new ProdutoDTOValidationException("Informe o ID do produto para atualizar!");
        }
        if (produtoDTO.preco() == 0){
            throw new ProdutoDTOValidationException("O preço do produto não pode ser zero");
        }
        if (produtoDTO.preco() < 0){
            throw new ProdutoDTOValidationException("O preço do produto não pode ser negativo");
        }
        if (produtoDTO.quantidade() < 0){
            throw new ProdutoDTOValidationException("A quantidade do produto não pode ser negativo");
        }


        Produto produto = produtoRepository.findById(produtoDTO.id())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("O ID fornecido não condiz com nenhum produto em nosso estoque!"));

        produto.setPreco(produtoDTO.preco());
        produto.setQuantidade(produtoDTO.quantidade());
        produto.setDataEntrada(LocalDate.now());
        produto.setPrecoTotal(produto.valorTotal(produtoDTO.quantidade(), produtoDTO.preco()));
        Produto produtoAtualizado = produtoRepository.save(produto);


        return  convertToDTO(produtoAtualizado);
    }

}
