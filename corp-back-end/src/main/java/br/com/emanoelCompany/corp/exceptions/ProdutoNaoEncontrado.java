package br.com.emanoelCompany.corp.exceptions;

public class ProdutoNaoEncontrado extends RuntimeException {

    public ProdutoNaoEncontrado() {
        super("produto buscado não existe");
    }

    public ProdutoNaoEncontrado(String mensagem){
        super(mensagem);
    }

}
