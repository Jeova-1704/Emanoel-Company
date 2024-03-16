package br.com.emanoelCompany.corp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoDTOValidationException extends RuntimeException{
    public ProdutoDTOValidationException(String msg) {
        super(msg);
    }
}
