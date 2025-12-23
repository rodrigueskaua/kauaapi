package br.edu.infnet.kauaapi.model;

public interface Relatoravel {

    void exibirRelatorio();

    void exibirRelatorio(boolean detalhado);

    String gerarResumo();
}
