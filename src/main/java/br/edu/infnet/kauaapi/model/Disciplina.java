package br.edu.infnet.kauaapi.model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {

    private String codigo;
    private String nome;
    private String professor;
    private int cargaHoraria;
    private List<Aluno> alunosMatriculados;

    public Disciplina() {
        this.alunosMatriculados = new ArrayList<>();
    }

    public Disciplina(String codigo, String nome) {
        this();
        this.codigo = codigo;
        this.nome = nome;
    }

    public Disciplina(String codigo, String nome, String professor, int cargaHoraria) {
        this(codigo, nome);
        this.professor = professor;
        this.cargaHoraria = cargaHoraria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public void matricularAluno(Aluno aluno) {
        if (aluno != null) {
            alunosMatriculados.add(aluno);
            System.out.println("Aluno " + aluno.getNome() + " matriculado na disciplina " + nome);
        }
    }

    public void matricularAluno(List<Aluno> alunos) {
        for (Aluno aluno : alunos) {
            matricularAluno(aluno);
        }
    }

    public void matricularAluno(int matricula, String nome, boolean bolsista, double notaFinal) {
        Aluno novoAluno = new Aluno(matricula, nome, bolsista, notaFinal);
        matricularAluno(novoAluno);
    }

    public double calcularMediaDisciplina() {
        if (alunosMatriculados.isEmpty()) {
            return 0.0;
        }

        double soma = 0;
        for (Aluno aluno : alunosMatriculados) {
            soma += aluno.getNotaFinal();
        }

        return soma / alunosMatriculados.size();
    }

    public int contarAlunosPorSituacao(SituacaoAluno situacao) {
        int contador = 0;
        for (Aluno aluno : alunosMatriculados) {
            if (aluno.getSituacao() == situacao) {
                contador++;
            }
        }
        return contador;
    }

    public void exibirRelatorio() {
        System.out.println("\n=== RELATÓRIO DA DISCIPLINA ===");
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Professor: " + professor);
        System.out.println("Carga Horária: " + cargaHoraria + "h");
        System.out.println("Total de Alunos: " + alunosMatriculados.size());
        System.out.printf("Média da Disciplina: %.2f%n", calcularMediaDisciplina());
        System.out.println("Aprovados: " + contarAlunosPorSituacao(SituacaoAluno.APROVADO));
        System.out.println("Reprovados: " + contarAlunosPorSituacao(SituacaoAluno.REPROVADO));
        System.out.println("Em Recuperação: " + contarAlunosPorSituacao(SituacaoAluno.EM_RECUPERACAO));
    }

    public void exibirRelatorio(boolean incluirAlunos) {
        exibirRelatorio();

        if (incluirAlunos && !alunosMatriculados.isEmpty()) {
            System.out.println("\nAlunos Matriculados:");
            for (Aluno aluno : alunosMatriculados) {
                System.out.println("  " + aluno);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Disciplina[%s] %s - Prof. %s (%dh) - %d alunos",
                codigo, nome, professor, cargaHoraria, alunosMatriculados.size());
    }
}
