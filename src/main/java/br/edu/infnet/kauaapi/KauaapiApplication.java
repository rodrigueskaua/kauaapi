package br.edu.infnet.kauaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.edu.infnet.kauaapi.model.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class KauaapiApplication {

    public static void main(String[] args) {

        SpringApplication.run(KauaapiApplication.class, args);

        Scanner sc = new Scanner(System.in);
        List<Aluno> alunos = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== Sistema Escolar ===");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos cadastrados");
            System.out.println("3. Calcular média da turma");
            System.out.println("4. Buscar aluno por matrícula");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Informe a matrícula do aluno: ");
                    int matricula = sc.nextInt();
                    sc.nextLine();

                    boolean existe = false;
                    for (Aluno a : alunos) {
                        if (a.getMatricula() == matricula) {
                            System.out.println("Matrícula já cadastrada!");
                            existe = true;
                            break;
                        }
                    }
                    if (existe) break;

                    System.out.print("Informe o nome do aluno: ");
                    String nome = sc.nextLine();

                    System.out.print("O aluno é bolsista? (true/false): ");
                    boolean bolsista = sc.nextBoolean();

                    System.out.print("Informe a nota final do aluno (0 a 10): ");
                    double notaFinal = sc.nextDouble();

                    if (notaFinal < 0 || notaFinal > 10) {
                        System.out.println("Nota inválida. Deve estar entre 0 e 10.");
                        break;
                    }

                    Aluno aluno = new Aluno(matricula, nome, bolsista, notaFinal);
                    alunos.add(aluno);

                    System.out.println("Aluno cadastrado com sucesso!");
                    aluno.exibirResultadoFinal();
                    break;

                case 2:
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                    } else {
                        System.out.println("\n=== Lista de Alunos ===");
                        for (Aluno a : alunos) {
                            System.out.printf("Matrícula: %d | Nome: %s | Nota: %.2f | Bolsista: %s%n",
                                    a.getMatricula(), a.getNome(), a.getNotaFinal(), a.isBolsista());
                        }
                    }
                    break;

                case 3:
                    if (alunos.isEmpty()) {
                        System.out.println("Cadastre alunos antes de calcular a média.");
                    } else {
                        double soma = 0;
                        int qtd = 0;
                        for (Aluno a : alunos) {
                            if (a.getNotaFinal() < 0) continue;
                            soma += a.getNotaFinal();
                            qtd++;
                        }
                        double media = soma / qtd;
                        System.out.printf("Média da turma: %.2f%n", media);
                    }
                    break;

                case 4:
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                    } else {
                        System.out.print("Informe a matrícula para busca: ");
                        int buscaMatricula = sc.nextInt();
                        boolean encontrado = false;
                        for (Aluno a : alunos) {
                            if (a.getMatricula() == buscaMatricula) {
                                System.out.println("Aluno encontrado!");
                                a.exibirResultadoFinal();
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Aluno não encontrado.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha entre 1 e 5.");
            }

        } while (opcao != 5);

        sc.close();
    }
}
