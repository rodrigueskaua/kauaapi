package br.edu.infnet.kauaapi.exception;

public class ArquivoException extends Exception {

    public ArquivoException(String mensagem) {
        super(mensagem);
    }

    public ArquivoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
