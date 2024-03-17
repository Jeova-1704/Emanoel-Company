package br.com.emanoelCompany.corp.exceptions;

public class QuantidadeInsuficienteException extends RuntimeException {

    public QuantidadeInsuficienteException() {
        super("O produto que você está tentando vender acabou");
    }

    public QuantidadeInsuficienteException(String mensagem) {
        super(mensagem);
    }
}