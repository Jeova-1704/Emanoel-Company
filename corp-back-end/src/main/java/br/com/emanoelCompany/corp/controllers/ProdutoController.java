package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoDTO produto){
        ProdutoDTO produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(){
        List<ProdutoDTO> produtoList = produtoService.listarProdutos();
        return ResponseEntity.ok(produtoList);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ProdutoDTO> buscarID(@PathVariable Long id){
        ProdutoDTO produto = produtoService.buscarID(id);
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoDTO>> filtrarNome(@PathVariable String nome){
        List<ProdutoDTO> listaNomes = produtoService.buscarNome(nome);
        return ResponseEntity.ok(listaNomes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarID(@PathVariable Long id){

        produtoService.deletar(id);
        return ResponseEntity.ok("Produto deletado!");

    }
}
