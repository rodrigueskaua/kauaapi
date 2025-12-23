package br.edu.infnet.kauaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.edu.infnet.kauaapi.model.*;
import br.edu.infnet.kauaapi.exception.*;
import br.edu.infnet.kauaapi.util.GerenciadorArquivos;
import br.edu.infnet.kauaapi.exemplo.ExemploPolimorfismo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

@SpringBootApplication
public class KauaapiApplication {

    private static GerenciadorArquivos gerenciador;

    public static void main(String[] args) {

        SpringApplication.run(KauaapiApplication.class, args);

        gerenciador = new GerenciadorArquivos();
        Scanner sc = new Scanner(System.in);
        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Professor> professores = new ArrayList<>();

        try {
            alunos = gerenciador.carregarAlunos();
            disciplinas = gerenciador.carregarDisciplinas();
            System.out.println("Dados carregados com sucesso!");
        } catch (ArquivoException e) {
            System.out.println("Aviso: " + e.getMessage());
        }

        int opcao;

        do {
            System.out.println("\n=== Sistema Escolar - Feature 4 ===");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos cadastrados");
            System.out.println("3. Calcular média da turma");
            System.out.println("4. Buscar aluno por matrícula");
            System.out.println("5. Cadastrar disciplina");
            System.out.println("6. Listar disciplinas");
            System.out.println("7. Matricular aluno em disciplina");
            System.out.println("8. Exibir relatório de disciplina");
            System.out.println("9. Cadastrar professor");
            System.out.println("10. Listar professores");
            System.out.println("11. Atribuir disciplina a professor");
            System.out.println("12. Demonstrar polimorfismo");
            System.out.println("13. Demonstrar arrays e coleções");
            System.out.println("14. Salvar dados");
            System.out.println("15. Carregar dados");
            System.out.println("16. Exportar relatório geral");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite um número.");
                sc.nextLine();
                opcao = -1;
                continue;
            }

            try {
                switch (opcao) {
                    case 1:
                        cadastrarAluno(sc, alunos);
                        break;

                    case 2:
                        listarAlunos(alunos);
                        break;

                    case 3:
                        calcularMediaTurma(alunos);
                        break;

                    case 4:
                        buscarAluno(sc, alunos);
                        break;

                    case 5:
                        cadastrarDisciplina(sc, disciplinas);
                        break;

                    case 6:
                        listarDisciplinas(disciplinas);
                        break;

                    case 7:
                        matricularAlunoEmDisciplina(sc, alunos, disciplinas);
                        break;

                    case 8:
                        exibirRelatorioDisciplina(sc, disciplinas);
                        break;

                    case 9:
                        cadastrarProfessor(sc, professores);
                        break;

                    case 10:
                        listarProfessores(professores);
                        break;

                    case 11:
                        atribuirDisciplinaAProfessor(sc, professores, disciplinas);
                        break;

                    case 12:
                        ExemploPolimorfismo.demonstrarPolimorfismo();
                        break;

                    case 13:
                        ExemploPolimorfismo.demonstrarArraysEColecoes();
                        break;

                    case 14:
                        salvarDados(alunos, disciplinas);
                        break;

                    case 15:
                        carregarDados(alunos, disciplinas);
                        break;

                    case 16:
                        exportarRelatorioGeral(alunos, disciplinas, professores);
                        break;

                    case 0:
                        System.out.println("Deseja salvar os dados antes de sair? (s/n): ");
                        String resposta = sc.nextLine();
                        if (resposta.equalsIgnoreCase("s")) {
                            salvarDados(alunos, disciplinas);
                        }
                        System.out.println("Encerrando o sistema.");
                        break;

                    default:
                        System.out.println("Opção inválida! Escolha entre 0 e 16.");
                }
            } catch (CadastroInvalidoException e) {
                System.out.println("Erro de cadastro: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("Erro de estado: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void cadastrarAluno(Scanner sc, List<Aluno> alunos) {
        try {
            System.out.print("Informe a matrícula do aluno: ");
            int matricula = sc.nextInt();
            sc.nextLine();

            for (Aluno a : alunos) {
                if (a.getMatricula() == matricula) {
                    throw new CadastroInvalidoException("Matrícula já cadastrada!");
                }
            }

            System.out.print("Informe o nome do aluno: ");
            String nome = sc.nextLine();

            if (nome == null || nome.trim().isEmpty()) {
                throw new CadastroInvalidoException("Nome não pode ser vazio!");
            }

            System.out.print("Informe o email do aluno: ");
            String email = sc.nextLine();

            System.out.print("Informe o telefone do aluno: ");
            String telefone = sc.nextLine();

            System.out.print("O aluno é bolsista? (true/false): ");
            boolean bolsista = sc.nextBoolean();

            System.out.print("Informe a nota final do aluno (0 a 10): ");
            double notaFinal = sc.nextDouble();

            if (notaFinal < 0 || notaFinal > 10) {
                throw new CadastroInvalidoException("Nota deve estar entre 0 e 10!");
            }

            Aluno aluno = new Aluno(matricula, nome, email, telefone, bolsista);
            aluno.registrarNota(notaFinal);
            alunos.add(aluno);

            System.out.println("Aluno cadastrado com sucesso!");
            aluno.exibirResultadoFinal();

        } catch (InputMismatchException e) {
            sc.nextLine();
            throw new CadastroInvalidoException("Entrada inválida! Verifique os dados informados.");
        }
    }

    private static void listarAlunos(List<Aluno> alunos) {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n=== Lista de Alunos ===");
            for (Aluno a : alunos) {
                System.out.println(a);
            }
        }
    }

    private static void calcularMediaTurma(List<Aluno> alunos) {
        if (alunos.isEmpty()) {
            System.out.println("Cadastre alunos antes de calcular a média.");
        } else {
            double soma = 0;
            for (Aluno a : alunos) {
                soma += a.getNotaFinal();
            }
            double media = soma / alunos.size();
            System.out.printf("Média da turma: %.2f%n", media);
        }
    }

    private static void buscarAluno(Scanner sc, List<Aluno> alunos) {
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
    }

    private static void cadastrarDisciplina(Scanner sc, List<Disciplina> disciplinas) {
        System.out.print("Informe o código da disciplina: ");
        String codigo = sc.nextLine();

        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                throw new CadastroInvalidoException("Código de disciplina já cadastrado!");
            }
        }

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
    }

    private static void listarDisciplinas(List<Disciplina> disciplinas) {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            System.out.println("\n=== Lista de Disciplinas ===");
            for (Disciplina d : disciplinas) {
                System.out.println(d);
            }
        }
    }

    private static void matricularAlunoEmDisciplina(Scanner sc, List<Aluno> alunos, List<Disciplina> disciplinas) {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("\n=== Disciplinas disponíveis ===");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, disciplinas.get(i));
        }

        System.out.print("Escolha o número da disciplina: ");
        int numDisciplina = sc.nextInt();
        sc.nextLine();

        if (numDisciplina < 1 || numDisciplina > disciplinas.size()) {
            throw new IllegalArgumentException("Disciplina inválida!");
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
            throw new IllegalArgumentException("Aluno inválido!");
        }

        Aluno alunoSelecionado = alunos.get(numAluno - 1);
        disciplinaSelecionada.matricularAluno(alunoSelecionado);
    }

    private static void exibirRelatorioDisciplina(Scanner sc, List<Disciplina> disciplinas) {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        System.out.println("\n=== Disciplinas disponíveis ===");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, disciplinas.get(i));
        }

        System.out.print("Escolha o número da disciplina para exibir o relatório: ");
        int numDisciplinaRelatorio = sc.nextInt();
        sc.nextLine();

        if (numDisciplinaRelatorio < 1 || numDisciplinaRelatorio > disciplinas.size()) {
            throw new IllegalArgumentException("Disciplina inválida!");
        }

        Disciplina disciplinaRelatorio = disciplinas.get(numDisciplinaRelatorio - 1);

        System.out.print("Deseja incluir a lista de alunos no relatório? (true/false): ");
        boolean incluirAlunos = sc.nextBoolean();
        sc.nextLine();

        disciplinaRelatorio.exibirRelatorio(incluirAlunos);
    }

    private static void cadastrarProfessor(Scanner sc, List<Professor> professores) {
        System.out.print("Informe o ID do professor: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Professor p : professores) {
            if (p.getId() == id) {
                throw new CadastroInvalidoException("ID já cadastrado!");
            }
        }

        System.out.print("Informe o nome do professor: ");
        String nome = sc.nextLine();

        System.out.print("Informe o email do professor: ");
        String email = sc.nextLine();

        System.out.print("Informe o telefone do professor: ");
        String telefone = sc.nextLine();

        System.out.print("Informe a especialidade do professor: ");
        String especialidade = sc.nextLine();

        Professor professor = new Professor(id, nome, email, telefone, especialidade);
        professores.add(professor);

        System.out.println("Professor cadastrado com sucesso!");
        System.out.println(professor);
    }

    private static void listarProfessores(List<Professor> professores) {
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
        } else {
            System.out.println("\n=== Lista de Professores ===");
            for (Professor p : professores) {
                System.out.println(p);
            }
        }
    }

    private static void atribuirDisciplinaAProfessor(Scanner sc, List<Professor> professores, List<Disciplina> disciplinas) {
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        System.out.println("\n=== Professores disponíveis ===");
        for (int i = 0; i < professores.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, professores.get(i));
        }

        System.out.print("Escolha o número do professor: ");
        int numProf = sc.nextInt();
        sc.nextLine();

        if (numProf < 1 || numProf > professores.size()) {
            throw new IllegalArgumentException("Professor inválido!");
        }

        Professor professorSelecionado = professores.get(numProf - 1);

        System.out.println("\n=== Disciplinas disponíveis ===");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, disciplinas.get(i));
        }

        System.out.print("Escolha o número da disciplina: ");
        int numDisc = sc.nextInt();
        sc.nextLine();

        if (numDisc < 1 || numDisc > disciplinas.size()) {
            throw new IllegalArgumentException("Disciplina inválida!");
        }

        Disciplina disciplinaSelecionada = disciplinas.get(numDisc - 1);
        professorSelecionado.adicionarDisciplina(disciplinaSelecionada);
    }

    private static void salvarDados(List<Aluno> alunos, List<Disciplina> disciplinas) {
        try {
            gerenciador.salvarAlunos(alunos);
            gerenciador.salvarDisciplinas(disciplinas);
            System.out.println("Dados salvos com sucesso!");
        } catch (ArquivoException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void carregarDados(List<Aluno> alunos, List<Disciplina> disciplinas) {
        try {
            alunos.clear();
            disciplinas.clear();

            alunos.addAll(gerenciador.carregarAlunos());
            disciplinas.addAll(gerenciador.carregarDisciplinas());

            System.out.println("Dados carregados com sucesso!");
            System.out.println("Alunos: " + alunos.size());
            System.out.println("Disciplinas: " + disciplinas.size());
        } catch (ArquivoException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void exportarRelatorioGeral(List<Aluno> alunos, List<Disciplina> disciplinas, List<Professor> professores) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("========== RELATÓRIO GERAL DO SISTEMA ESCOLAR ==========\n\n");

        relatorio.append("=== ALUNOS CADASTRADOS ===\n");
        relatorio.append("Total: ").append(alunos.size()).append("\n\n");
        for (Aluno a : alunos) {
            relatorio.append(a.toString()).append("\n");
        }

        relatorio.append("\n=== DISCIPLINAS CADASTRADAS ===\n");
        relatorio.append("Total: ").append(disciplinas.size()).append("\n\n");
        for (Disciplina d : disciplinas) {
            relatorio.append(d.gerarResumo()).append("\n");
        }

        relatorio.append("\n=== PROFESSORES CADASTRADOS ===\n");
        relatorio.append("Total: ").append(professores.size()).append("\n\n");
        for (Professor p : professores) {
            relatorio.append(p.gerarResumo()).append("\n");
        }

        relatorio.append("\n========== FIM DO RELATÓRIO ==========\n");

        try {
            gerenciador.exportarRelatorio("relatorio_geral.txt", relatorio.toString());
        } catch (ArquivoException e) {
            System.out.println("Erro ao exportar relatório: " + e.getMessage());
        }
    }
}
