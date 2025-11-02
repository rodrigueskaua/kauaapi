package br.edu.infnet.kauaapi.model;

public class Aluno {

    private int matricula;
    private String nome;
    private boolean bolsista;
    private double notaFinal;

    public Aluno() {
    }

    public Aluno(int matricula, String nome, boolean bolsista, double notaFinal) {
        this.matricula = matricula;
        this.nome = nome;
        this.bolsista = bolsista;
        this.notaFinal = notaFinal;
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
        this.notaFinal = notaFinal;
    }

    public void exibirResultadoFinal() {
        String resultado = calcularResultado();
        System.out.println("\n=== Resultado Final do Aluno ===");
        System.out.println("Matrícula: " + matricula);
        System.out.println("Nome: " + nome);
        System.out.println("Bolsista: " + (bolsista ? "Sim" : "Não"));
        System.out.println("Nota Final: " + notaFinal);
        System.out.println("Situação: " + resultado);
    }

    private String calcularResultado() {
        return notaFinal >= 6 ? "Aprovado" : "Reprovado";
    }
}
