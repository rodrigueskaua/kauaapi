package br.edu.infnet.kauaapi.util;

import br.edu.infnet.kauaapi.exception.ArquivoException;
import br.edu.infnet.kauaapi.model.Aluno;
import br.edu.infnet.kauaapi.model.Disciplina;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivos {

    private static final String DIRETORIO_DADOS = "dados/";
    private static final String ARQUIVO_ALUNOS = DIRETORIO_DADOS + "alunos.txt";
    private static final String ARQUIVO_DISCIPLINAS = DIRETORIO_DADOS + "disciplinas.txt";
    private static final String ARQUIVO_LOG = DIRETORIO_DADOS + "sistema.log";

    public GerenciadorArquivos() {
        criarDiretorio();
    }

    private void criarDiretorio() {
        File dir = new File(DIRETORIO_DADOS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void salvarAlunos(List<Aluno> alunos) throws ArquivoException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(ARQUIVO_ALUNOS));

            for (Aluno aluno : alunos) {
                String linha = String.format("%d;%s;%b;%.2f",
                        aluno.getMatricula(),
                        aluno.getNome(),
                        aluno.isBolsista(),
                        aluno.getNotaFinal());
                writer.write(linha);
                writer.newLine();
            }

            registrarLog("Alunos salvos com sucesso. Total: " + alunos.size());

        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar alunos no arquivo", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }
    }

    public List<Aluno> carregarAlunos() throws ArquivoException {
        List<Aluno> alunos = new ArrayList<>();
        BufferedReader reader = null;

        try {
            File arquivo = new File(ARQUIVO_ALUNOS);
            if (!arquivo.exists()) {
                registrarLog("Arquivo de alunos não encontrado. Retornando lista vazia.");
                return alunos;
            }

            reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            int linhaNumero = 0;

            while ((linha = reader.readLine()) != null) {
                linhaNumero++;
                try {
                    String[] dados = linha.split(";");

                    if (dados.length != 4) {
                        throw new IllegalArgumentException("Formato inválido na linha " + linhaNumero);
                    }

                    int matricula = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    boolean bolsista = Boolean.parseBoolean(dados[2]);
                    double notaFinal = Double.parseDouble(dados[3]);

                    Aluno aluno = new Aluno(matricula, nome, bolsista, notaFinal);
                    alunos.add(aluno);

                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + linhaNumero + ": " + e.getMessage());
                }
            }

            registrarLog("Alunos carregados com sucesso. Total: " + alunos.size());

        } catch (FileNotFoundException e) {
            throw new ArquivoException("Arquivo de alunos não encontrado: " + ARQUIVO_ALUNOS, e);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler arquivo de alunos", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }

        return alunos;
    }

    public void salvarDisciplinas(List<Disciplina> disciplinas) throws ArquivoException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(ARQUIVO_DISCIPLINAS));

            for (Disciplina disciplina : disciplinas) {
                String linha = String.format("%s;%s;%s;%d",
                        disciplina.getCodigo(),
                        disciplina.getNome(),
                        disciplina.getProfessor() != null ? disciplina.getProfessor() : "",
                        disciplina.getCargaHoraria());
                writer.write(linha);
                writer.newLine();
            }

            registrarLog("Disciplinas salvas com sucesso. Total: " + disciplinas.size());

        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar disciplinas no arquivo", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }
    }

    public List<Disciplina> carregarDisciplinas() throws ArquivoException {
        List<Disciplina> disciplinas = new ArrayList<>();
        BufferedReader reader = null;

        try {
            File arquivo = new File(ARQUIVO_DISCIPLINAS);
            if (!arquivo.exists()) {
                registrarLog("Arquivo de disciplinas não encontrado. Retornando lista vazia.");
                return disciplinas;
            }

            reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            int linhaNumero = 0;

            while ((linha = reader.readLine()) != null) {
                linhaNumero++;
                try {
                    String[] dados = linha.split(";");

                    if (dados.length != 4) {
                        throw new IllegalArgumentException("Formato inválido na linha " + linhaNumero);
                    }

                    String codigo = dados[0];
                    String nome = dados[1];
                    String professor = dados[2].isEmpty() ? null : dados[2];
                    int cargaHoraria = Integer.parseInt(dados[3]);

                    Disciplina disciplina = new Disciplina(codigo, nome, professor, cargaHoraria);
                    disciplinas.add(disciplina);

                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + linhaNumero + ": " + e.getMessage());
                }
            }

            registrarLog("Disciplinas carregadas com sucesso. Total: " + disciplinas.size());

        } catch (FileNotFoundException e) {
            throw new ArquivoException("Arquivo de disciplinas não encontrado: " + ARQUIVO_DISCIPLINAS, e);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler arquivo de disciplinas", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }

        return disciplinas;
    }

    public void registrarLog(String mensagem) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(ARQUIVO_LOG, true));
            String timestamp = java.time.LocalDateTime.now().toString();
            writer.write(String.format("[%s] %s", timestamp, mensagem));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao registrar log: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo de log: " + e.getMessage());
                }
            }
        }
    }

    public void exportarRelatorio(String nomeArquivo, String conteudo) throws ArquivoException {
        BufferedWriter writer = null;
        try {
            String caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
            writer = new BufferedWriter(new FileWriter(caminhoCompleto));
            writer.write(conteudo);
            registrarLog("Relatório exportado: " + nomeArquivo);
            System.out.println("Relatório salvo em: " + caminhoCompleto);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao exportar relatório", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar arquivo: " + e.getMessage());
                }
            }
        }
    }
}
