package br.edu.infnet.kauaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.edu.infnet.kauaapi.model.Aluno;
import br.edu.infnet.kauaapi.model.Disciplina;
import br.edu.infnet.kauaapi.model.SituacaoAluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class KauaapiApplication {

    public static void main(String[] args) {

        SpringApplication.run(KauaapiApplication.class, args);

        Scanner sc = new Scanner(System.in);
        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();

        int opcao;

        do {
            System.out.println("\n=== Sistema Escolar ===");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos cadastrados");
            System.out.println("3. Calcular média da turma");
            System.out.println("4. Buscar aluno por matrícula");
            System.out.println("5. Cadastrar disciplina");
            System.out.println("6. Listar disciplinas");
            System.out.println("7. Matricular aluno em disciplina");
            System.out.println("8. Exibir relatório de disciplina");
            System.out.println("9. Sair");
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
                    System.out.print("Informe o código da disciplina: ");
                    String codigo = sc.nextLine();

                    boolean codigoExiste = false;
                    for (Disciplina d : disciplinas) {
                        if (d.getCodigo().equalsIgnoreCase(codigo)) {
                            System.out.println("Código de disciplina já cadastrado!");
                            codigoExiste = true;
                            break;
                        }
                    }
                    if (codigoExiste) break;

                    System.out.print("Informe o nome da disciplina: ");
                    String nomeDisciplina = sc.nextLine();

                    System.out.print("Informe o nome do professor: ");
                    String professor = sc.nextLine();

                    System.out.print("Informe a carga horária: ");
                    int cargaHoraria = sc.nextInt();
                    sc.nextLine();

                    Disciplina disciplina = new Disciplina(codigo, nomeDisciplina, professor, cargaHoraria);
                    disciplinas.add(disciplina);

                    System.out.println("Disciplina cadastrada com sucesso!");
                    System.out.println(disciplina);
                    break;

                case 6:
                    if (disciplinas.isEmpty()) {
                        System.out.println("Nenhuma disciplina cadastrada.");
                    } else {
                        System.out.println("\n=== Lista de Disciplinas ===");
                        for (Disciplina d : disciplinas) {
                            System.out.println(d);
                        }
                    }
                    break;

                case 7:
                    if (disciplinas.isEmpty()) {
                        System.out.println("Nenhuma disciplina cadastrada.");
                        break;
                    }
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                        break;
                    }

                    System.out.println("\n=== Disciplinas disponíveis ===");
                    for (int i = 0; i < disciplinas.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, disciplinas.get(i));
                    }

                    System.out.print("Escolha o número da disciplina: ");
                    int numDisciplina = sc.nextInt();
                    sc.nextLine();

                    if (numDisciplina < 1 || numDisciplina > disciplinas.size()) {
                        System.out.println("Disciplina inválida!");
                        break;
                    }

                    Disciplina disciplinaSelecionada = disciplinas.get(numDisciplina - 1);

                    System.out.println("\n=== Alunos disponíveis ===");
                    for (int i = 0; i < alunos.size(); i++) {
                        Aluno a = alunos.get(i);
                        System.out.printf("%d. %s (Matrícula: %d)%n", i + 1, a.getNome(), a.getMatricula());
                    }

                    System.out.print("Escolha o número do aluno: ");
                    int numAluno = sc.nextInt();
                    sc.nextLine();

                    if (numAluno < 1 || numAluno > alunos.size()) {
                        System.out.println("Aluno inválido!");
                        break;
                    }

                    Aluno alunoSelecionado = alunos.get(numAluno - 1);
                    disciplinaSelecionada.matricularAluno(alunoSelecionado);
                    break;

                case 8:
                    if (disciplinas.isEmpty()) {
                        System.out.println("Nenhuma disciplina cadastrada.");
                        break;
                    }

                    System.out.println("\n=== Disciplinas disponíveis ===");
                    for (int i = 0; i < disciplinas.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, disciplinas.get(i));
                    }

                    System.out.print("Escolha o número da disciplina para exibir o relatório: ");
                    int numDisciplinaRelatorio = sc.nextInt();
                    sc.nextLine();

                    if (numDisciplinaRelatorio < 1 || numDisciplinaRelatorio > disciplinas.size()) {
                        System.out.println("Disciplina inválida!");
                        break;
                    }

                    Disciplina disciplinaRelatorio = disciplinas.get(numDisciplinaRelatorio - 1);

                    System.out.print("Deseja incluir a lista de alunos no relatório? (true/false): ");
                    boolean incluirAlunos = sc.nextBoolean();
                    sc.nextLine();

                    disciplinaRelatorio.exibirRelatorio(incluirAlunos);
                    break;

                case 9:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha entre 1 e 9.");
            }

        } while (opcao != 9);

        sc.close();
    }
}
