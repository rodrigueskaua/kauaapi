package br.edu.infnet.kauaapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.edu.infnet.kauaapi.model.Aluno;

import java.util.Scanner;

@SpringBootApplication
public class KauaapiApplication {

    public static void main(String[] args) {

        SpringApplication.run(KauaapiApplication.class, args);

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema Escolar - Cadastro de Aluno ===");

        System.out.print("Informe a matrícula do aluno: ");
        int matricula = sc.nextInt();
        sc.nextLine();

        System.out.print("Informe o nome do aluno: ");
        String nome = sc.nextLine();

        System.out.print("O aluno é bolsista? (true/false): ");
        boolean bolsista = sc.nextBoolean();

        System.out.print("Informe a nota final do aluno: ");
        double notaFinal = sc.nextDouble();

        Aluno aluno = new Aluno(matricula, nome, bolsista, notaFinal);

        aluno.exibirResultadoFinal();

        sc.close();
    }

}
