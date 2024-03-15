package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.model.Produto;
import br.com.emanoelCompany.corp.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping(value = "/cadastrar")
    @ResponseBody
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto){
        Produto produto1 = produtoRepository.save(produto);
        return new ResponseEntity<Produto>(produto1, HttpStatus.CREATED);
    }

}
