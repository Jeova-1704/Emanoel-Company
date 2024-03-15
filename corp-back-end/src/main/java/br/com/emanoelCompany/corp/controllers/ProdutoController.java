package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.model.Produto;
import br.com.emanoelCompany.corp.repository.ProdutoRepository;
import br.com.emanoelCompany.corp.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto){
        Produto produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> produtoList = produtoService.listarProdutos();
        return ResponseEntity.ok(produtoList);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarID(@PathVariable Long id){
        Produto produto = produtoService.buscarID(id);
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/{nome}")
    public ResponseEntity<List<Produto>> filtrarNome(@PathVariable String nome){
        List<Produto> listaNomes = produtoService.buscarNome(nome);
        return ResponseEntity.ok(listaNomes);
    }

    @DeleteMapping("/deletarProduto/{id}")
    public ResponseEntity<String> deletarID(@PathVariable Long id){
        boolean deletado = produtoService.deletar(id);
        if(deletado){
            return ResponseEntity.ok("Produto deletado!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }






}
