package br.com.emanoelCompany.corp.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConversaoDataProdutoDTOExeption extends RuntimeException{
    public ConversaoDataProdutoDTOExeption(String msg) {
        super(msg);
    }
}
