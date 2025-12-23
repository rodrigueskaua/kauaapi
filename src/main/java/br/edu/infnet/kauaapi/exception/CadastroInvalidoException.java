package br.edu.infnet.kauaapi.exception;

public class CadastroInvalidoException extends RuntimeException {

    public CadastroInvalidoException() {
        super("Erro ao realizar cadastro!");
    }

    public CadastroInvalidoException(String mensagem) {
        super(mensagem);
    }
}
