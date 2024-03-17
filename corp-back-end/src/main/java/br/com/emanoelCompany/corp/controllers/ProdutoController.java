package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoDTO produto){
        ProdutoDTO produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoDTO>> listarProdutos(){
        List<ProdutoDTO> produtoList = produtoService.listarProdutos();
        return ResponseEntity.ok(produtoList);

    }
    @GetMapping("/buscarID/{id}")
    public ResponseEntity<ProdutoDTO> buscarID(@PathVariable Long id){
        ProdutoDTO produto = produtoService.buscarID(id);
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/filtrar/{nome}")
    public ResponseEntity<List<ProdutoDTO>> filtrarNome(@PathVariable String nome){
        List<ProdutoDTO> listaNomes = produtoService.buscarNome(nome);
        return ResponseEntity.ok(listaNomes);
    }

    @GetMapping("/buscarCat/{categoria}")
    public ResponseEntity<List<ProdutoDTO>> buscarCategoria(@PathVariable String categoria){
        List<ProdutoDTO> listaProdutos = produtoService.buscarCategoria(categoria);
        return ResponseEntity.ok(listaProdutos);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarID(@PathVariable Long id){
        produtoService.deletar(id);
        return ResponseEntity.ok("Produto deletado!");

    }
    @PutMapping("/atualizar")
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody ProdutoDTO produto){

        ProdutoDTO produtoAtualizado =produtoService.atualizar(produto);

        return ResponseEntity.ok(produtoAtualizado);
    }

    @PutMapping ("/vender")
    public ResponseEntity<String> vender(@RequestBody Map<Long,Integer> vendaMap ){

        produtoService.vender(vendaMap);

        return ResponseEntity.ok("Produto vendido com sucesso!");
    }

}