package br.edu.infnet.kauaapi.model;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa implements Relatoravel {

    private String especialidade;
    private final List<Disciplina> disciplinasLecionadas;
    private static final int MAX_DISCIPLINAS = 5;

    public Professor() {
        super();
        this.disciplinasLecionadas = new ArrayList<>();
    }

    public Professor(int id, String nome) {
        super(id, nome);
        this.disciplinasLecionadas = new ArrayList<>();
    }

    public Professor(int id, String nome, String email, String telefone, String especialidade) {
        super(id, nome, email, telefone);
        this.especialidade = especialidade;
        this.disciplinasLecionadas = new ArrayList<>();
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Disciplina> getDisciplinasLecionadas() {
        return disciplinasLecionadas;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (disciplinasLecionadas.size() >= MAX_DISCIPLINAS) {
            throw new IllegalStateException("Professor já atingiu o número máximo de disciplinas (" + MAX_DISCIPLINAS + ")");
        }
        if (disciplina != null) {
            disciplinasLecionadas.add(disciplina);
            disciplina.setProfessor(getNome());
            System.out.println("Disciplina " + disciplina.getNome() + " atribuída ao professor " + getNome());
        }
    }

    public void removerDisciplina(Disciplina disciplina) {
        if (disciplinasLecionadas.remove(disciplina)) {
            System.out.println("Disciplina " + disciplina.getNome() + " removida do professor " + getNome());
        }
    }

    public int getTotalAlunos() {
        int total = 0;
        for (Disciplina disciplina : disciplinasLecionadas) {
            total += disciplina.getAlunosMatriculados().size();
        }
        return total;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("\n=== Informações do Professor ===");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + (email != null ? email : "N/A"));
        System.out.println("Telefone: " + (telefone != null ? telefone : "N/A"));
        System.out.println("Especialidade: " + (especialidade != null ? especialidade : "N/A"));
        System.out.println("Disciplinas lecionadas: " + disciplinasLecionadas.size());
        System.out.println("Total de alunos: " + getTotalAlunos());
    }

    @Override
    public String getTipo() {
        return "Professor";
    }

    @Override
    public void exibirRelatorio() {
        exibirRelatorio(false);
    }

    @Override
    public void exibirRelatorio(boolean detalhado) {
        System.out.println("\n=== RELATÓRIO DO PROFESSOR ===");
        System.out.println("Nome: " + getNome());
        System.out.println("Especialidade: " + (especialidade != null ? especialidade : "N/A"));
        System.out.println("Total de Disciplinas: " + disciplinasLecionadas.size());
        System.out.println("Total de Alunos: " + getTotalAlunos());

        if (detalhado && !disciplinasLecionadas.isEmpty()) {
            System.out.println("\nDisciplinas Lecionadas:");
            for (Disciplina disciplina : disciplinasLecionadas) {
                System.out.println("  - " + disciplina.getNome() +
                        " (Código: " + disciplina.getCodigo() +
                        ", Alunos: " + disciplina.getAlunosMatriculados().size() + ")");
            }
        }
    }

    @Override
    public String gerarResumo() {
        return String.format("Prof. %s - Especialidade: %s - %d disciplinas - %d alunos",
                getNome(),
                especialidade != null ? especialidade : "N/A",
                disciplinasLecionadas.size(),
                getTotalAlunos());
    }

    @Override
    public String toString() {
        return String.format("Professor[%d] %s - Especialidade: %s - %d disciplinas",
                getId(), getNome(),
                especialidade != null ? especialidade : "N/A",
                disciplinasLecionadas.size());
    }
}
