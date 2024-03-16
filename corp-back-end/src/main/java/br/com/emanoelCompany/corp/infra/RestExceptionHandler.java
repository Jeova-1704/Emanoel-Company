package br.com.emanoelCompany.corp.infra;

import br.com.emanoelCompany.corp.exceptions.ProdutoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontrado.class)
    private ResponseEntity<String> produtoNaoEncontradoHandler(ProdutoNaoEncontrado exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado !");
    }
}
