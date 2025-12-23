package br.edu.infnet.kauaapi.model;

public interface Avaliavel {

    double calcularMedia();

    SituacaoAluno obterSituacao();

    void registrarNota(double nota);
}
