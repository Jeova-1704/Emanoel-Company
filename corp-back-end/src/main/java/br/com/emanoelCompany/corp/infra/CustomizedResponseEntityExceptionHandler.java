package br.com.emanoelCompany.corp.infra;

import br.com.emanoelCompany.corp.exceptions.ConversaoDataProdutoDTOExeption;
import br.com.emanoelCompany.corp.exceptions.ProdutoDTOValidationException;
import br.com.emanoelCompany.corp.exceptions.ProdutoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConversaoDataProdutoDTOExeption.class)
    public final ResponseEntity<ExceptionResponse> handleConversaoException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProdutoDTOValidationException.class)
    public final ResponseEntity<ExceptionResponse> handleValidationException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    private ResponseEntity<String> produtoNaoEncontradoHandler(ProdutoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado !");
    }
}
