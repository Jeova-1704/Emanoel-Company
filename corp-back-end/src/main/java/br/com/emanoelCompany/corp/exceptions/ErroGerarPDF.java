package br.com.emanoelCompany.corp.exceptions;

public class ErroGerarPDF extends RuntimeException {

    public ErroGerarPDF() {
        super("Erro ao gerar PDF");
    }

    public ErroGerarPDF(String mensagem) {
        super(mensagem);
    }
}