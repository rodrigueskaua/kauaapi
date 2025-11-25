package br.edu.infnet.kauaapi.model;

public class Aluno {

    private int matricula;
    private String nome;
    private boolean bolsista;
    private double notaFinal;
    private SituacaoAluno situacao;

    public Aluno() {
        this.situacao = SituacaoAluno.CURSANDO;
    }

    public Aluno(int matricula, String nome) {
        this();
        this.matricula = matricula;
        this.nome = nome;
    }

    public Aluno(int matricula, String nome, boolean bolsista, double notaFinal) {
        this(matricula, nome);
        this.bolsista = bolsista;
        setNotaFinal(notaFinal);
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isBolsista() {
        return bolsista;
    }

    public void setBolsista(boolean bolsista) {
        this.bolsista = bolsista;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        if (notaFinal >= 0 && notaFinal <= 10) {
            this.notaFinal = notaFinal;
            this.situacao = calcularSituacao();
        }
    }

    public SituacaoAluno getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoAluno situacao) {
        this.situacao = situacao;
    }

    public void exibirResultadoFinal() {
        exibirResultadoFinal(false);
    }

    public void exibirResultadoFinal(boolean exibirDetalhes) {
        System.out.println("\n=== Resultado Final do Aluno ===");
        System.out.println("Matrícula: " + matricula);
        System.out.println("Nome: " + nome);
        System.out.println("Bolsista: " + (bolsista ? "Sim" : "Não"));
        System.out.printf("Nota Final: %.2f%n", notaFinal);
        System.out.println("Situação: " + situacao);

        if (exibirDetalhes && situacao == SituacaoAluno.EM_RECUPERACAO) {
            System.out.println("Você precisa de pelo menos 5.0 na prova de recuperação");
        }
    }

    private SituacaoAluno calcularSituacao() {
        if (notaFinal >= 7.0) {
            return SituacaoAluno.APROVADO;
        } else if (notaFinal >= 4.0) {
            return SituacaoAluno.EM_RECUPERACAO;
        } else {
            return SituacaoAluno.REPROVADO;
        }
    }

    public double calcularNotaNecessaria() {
        return calcularNotaNecessaria(6.0);
    }

    public double calcularNotaNecessaria(double mediaDesejada) {
        if (notaFinal >= mediaDesejada) {
            return 0.0;
        }
        return mediaDesejada - notaFinal;
    }

    @Override
    public String toString() {
        return String.format("Aluno[%d] %s - Nota: %.2f - %s%s",
                matricula, nome, notaFinal, situacao,
                bolsista ? " (Bolsista)" : "");
    }
}
