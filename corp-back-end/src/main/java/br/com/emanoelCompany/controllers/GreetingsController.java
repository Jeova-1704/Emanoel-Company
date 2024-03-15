package br.com.emanoelCompany.controllers;

import br.com.emanoelCompany.model.Produto;
import br.com.emanoelCompany.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping(value = "/cadastrar")
    @ResponseBody
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto){
        Produto produto1 = produtoRepository.save(produto);
        return new ResponseEntity<Produto>(produto1, HttpStatus.CREATED);
    }

}
