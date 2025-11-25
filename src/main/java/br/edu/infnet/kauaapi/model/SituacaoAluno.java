package br.edu.infnet.kauaapi.model;

public enum SituacaoAluno {
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    EM_RECUPERACAO("Em Recuperação"),
    CURSANDO("Cursando");

    private final String descricao;

    SituacaoAluno(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
