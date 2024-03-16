package br.com.emanoelCompany.corp.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException() {
        super("produto buscado não existe");
    }

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}