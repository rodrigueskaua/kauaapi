package br.edu.infnet.kauaapi.exception;

public class NotaInvalidaException extends Exception {

    public NotaInvalidaException() {
        super("Nota inválida! Deve estar entre 0 e 10.");
    }

    public NotaInvalidaException(String mensagem) {
        super(mensagem);
    }

    public NotaInvalidaException(double nota) {
        super(String.format("Nota %.2f é inválida! Deve estar entre 0 e 10.", nota));
    }
}
