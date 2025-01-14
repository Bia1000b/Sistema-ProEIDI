package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Gerenciador {

    private static BancoDAO banco = BancoDAO.getInstance();
    private static final String ARQUIVO_BINARIO = "banco.bin";
    private static final Scanner scanner = new Scanner(System.in);

    public static void salvarBinario() {
        ArrayList<Pessoa> pessoas = banco.getArrayPessoas();
        ArrayList<Turma> turmas = banco.getArrayTurmas();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_BINARIO))) {
            // Salvando os arrays de pessoas e turmas
            oos.writeObject(pessoas);
            oos.writeObject(turmas);
            System.out.println("Dados salvos com sucesso no arquivo binário!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void carregarBinario() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_BINARIO))) {
            // Carregando os arrays de pessoas e turmas
            ArrayList<Pessoa> pessoas = (ArrayList<Pessoa>) ois.readObject();
            ArrayList<Turma> turmas = (ArrayList<Turma>) ois.readObject();

            // Atualizando os arrays no BancoDAO
            banco.getArrayPessoas().clear();
            banco.getArrayPessoas().addAll(pessoas);

            banco.getArrayTurmas().clear();
            banco.getArrayTurmas().addAll(turmas);

            System.out.println("Dados carregados com sucesso do arquivo binário!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo binário não encontrado. Ele será criado ao salvar os dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    public static void criarMembroPadrao() {
        // Valores padrão para inicialização
        String nomePadrao = "João Silva";
        String cpfPadrao = "12345678900";
        Genero generoPadrao = Genero.MASC;
        String numeroCelularPadrao = "(84) 99999-9999";
        String matriculaPadrao = "20230001";
        String cursoPadrao = "Engenharia de Computação";
        String emailPadrao = "joao.silva@ufrn.br";
        Cargo cargoPadrao = Cargo.PROFESSOR;

        // Criando um objeto MembroEquipe com os valores padrão
        MembroEquipe membroPadrao = new MembroEquipe(
                nomePadrao,
                cpfPadrao,
                generoPadrao,
                numeroCelularPadrao,
                matriculaPadrao,
                cursoPadrao,
                emailPadrao,
                cargoPadrao
        );
        banco.getArrayPessoas().add(membroPadrao);
    }

    public static void criarAlunoPadrao() {
        // Valores padrão para inicialização
        String nomePadrao = "Victor Aguiar Mendes Gomes Costa Baiano Cauã Leite Silva";
        String cpfPadrao = "2";
        Genero generoPadrao = Genero.FEM; // Exemplo: Enum para gênero
        String numeroCelularPadrao = "(84) 91234-1234";
        LocalDate dataNascimento = LocalDate.of(1920, 1, 1); // Data de nascimento padrão
        Escolaridade escolaridade = Escolaridade.SUPERIOR_COMPLETO; // Exemplo: Enum para escolaridade
        String obsSaude = "Viciado em TikTok.";
        boolean temInternet = true;
        boolean temComputador = true;
        boolean temSmartphone = true;
        SistemaOperacional sistemaOperacional = SistemaOperacional.ANDROID; // Exemplo: Enum para SO

        // Criando um objeto Aluno com os valores padrão
        Aluno alunoPadrao = new Aluno(
                nomePadrao,
                cpfPadrao,
                generoPadrao,
                numeroCelularPadrao,
                dataNascimento,
                escolaridade,
                obsSaude,
                temInternet,
                temComputador,
                temSmartphone,
                sistemaOperacional
        );

        Vector<Curso> cursos = new Vector<Curso>();

        cursos.add(Curso.PENSAMENTO_COMPUTACIONAL_I);
        cursos.add(Curso.COMPUTADOR);
        cursos.add(Curso.SMARTPHONE_BASICO);

        alunoPadrao.setCursosFeitos(cursos);

        banco.getArrayPessoas().add(alunoPadrao);

        System.out.println("Aluno padrão criado e adicionado ao banco com sucesso!");
    }



    public static Pessoa buscarPessoa(String CPF) {
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa.getCPF().equals(CPF)) {
                if (pessoa instanceof Aluno) {
                    Aluno aluno = (Aluno) pessoa;
                    aluno.detalharAluno();
                } else if (pessoa instanceof MembroEquipe) {
                    MembroEquipe membro = (MembroEquipe) pessoa;
                    membro.detalharMembroEquipe();
                }
                return pessoa;
            }
        }

        System.out.println("Nenhuma pessoa com esse CPF foi encontrada...");
        return null;
    }

    public void buscarTurma(String nome){
        for (Turma turma : banco.getArrayTurmas()) {
            if (turma.getNome().equals(nome)) {
                turma.detalharTurma();
                return;
            }
        }

        System.out.println("Nenhuma turma corresponde a esse nome...");
    }
}
