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

    public static void criarAlunoPadrao(Turma turma) {
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

        adicionarFalta(alunoPadrao);

        alunoPadrao.setCursosFeitos(cursos);

        alunoPadrao.setCursoAtual(Curso.SMARTPHONE_AVANCADO);

        adicionarAlunoATurma(turma, alunoPadrao);

        banco.getArrayPessoas().add(alunoPadrao);

        System.out.println("Aluno padrão criado e adicionado ao banco com sucesso!");
    }

    //ESSA FUNÇÃO NÃO É DAQUI!
    public static void adicionarAlunoATurma(Turma turma, Aluno aluno){
        if(turma.getNumeroVagas() > 0){
            aluno.setCursoAtual(turma.getCurso());
            aluno.setCodigoTurma(turma.getCodigo());
            Vector<Aluno> alunos = turma.getAlunos();
            if(alunos.add(aluno)){
                System.out.println("Membro adicionado com sucesso à turma " + turma.getNome());
            }
            turma.setAlunos(alunos);
            int numeroDeVagas = turma.getNumeroVagas() - 1;
            turma.setNumeroVagas(numeroDeVagas);
        }else{
            System.out.println("Turma cheia");
        }
    }

    //ESSA FUNÇÃO NÃO É DAQUI!
    public static void adicionarFalta(Aluno aluno){
        LocalDate dataAtual = LocalDate.now();
        Vector<LocalDate> faltas = aluno.getFaltas();
        faltas.add(dataAtual);
        aluno.setFaltas(faltas);
    }


    public static Turma criarTurmaPadrao() {
        // Valores padrão para inicialização
        String nomePadrao = "Turma de Pensamento Computacional";
        Curso cursoPadrao = Curso.PENSAMENTO_COMPUTACIONAL_I; // Exemplo: Enum para cursos
        Horario horarioPadrao = Horario.DEZ_E_TRINTA; // Classe para horário
        Integer numeroVagasPadrao = 20;
        LocalDate dataInicioPadrao = LocalDate.of(2025, 2, 1); // Data de início padrão
        LocalDate dataTerminoPadrao = LocalDate.of(2025, 6, 30); // Data de término padrão

        // Criando uma turma com os valores padrão
        Turma turmaPadrao = new Turma(
                nomePadrao,
                cursoPadrao,
                horarioPadrao,
                numeroVagasPadrao,
                dataInicioPadrao,
                dataTerminoPadrao
        );

        // Adicionando alguns alunos e membros da equipe à turma
        Aluno aluno1 = new Aluno("João Silva", "12345678901", Genero.MASC, "(84) 91234-5678",
                LocalDate.of(2000, 1, 1), Escolaridade.MEDIO_COMPLETO, "Nenhuma observação.",
                true, true, true, SistemaOperacional.ANDROID);
        Aluno aluno2 = new Aluno("Maria Oliveira", "98765432109", Genero.FEM, "(84) 98765-4321",
                LocalDate.of(1998, 5, 20), Escolaridade.SUPERIOR_INCOMPLETO, "Alérgica a amendoim.",
                true, true, false, SistemaOperacional.IOS);
        turmaPadrao.getAlunos().add(aluno1);
        turmaPadrao.getAlunos().add(aluno2);

        MembroEquipe professor = new MembroEquipe("Carlos Andrade", "22233344455", Genero.MASC,
                "(84) 91111-2222", "1", "BTI", "carlos@gmail.com", Cargo.PROFESSOR);
        turmaPadrao.getEquipe().add(professor);

        MembroEquipe professor2 = new MembroEquipe("Joana Silva", "12345665433", Genero.FEM,
                "(84) 91111-2222", "22", "BCC", "joana@gmail.com", Cargo.PROFESSOR);
        turmaPadrao.getEquipe().add(professor2);

        // Adicionando a turma ao banco
        BancoDAO.getInstance().getArrayTurmas().add(turmaPadrao);

        System.out.println("Turma padrão criada e adicionada ao banco com sucesso!");

        return turmaPadrao;
    }


    public static Pessoa buscarPessoa(String CPF) {
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa.getCPF().equals(CPF)) {
                if (pessoa instanceof Aluno) {
                    Aluno aluno = (Aluno) pessoa;
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

    public static Turma buscarTurma(String codigo){
        for (Turma turma : banco.getArrayTurmas()) {
            if (turma.getCodigo().equals(codigo)) {
                turma.detalharTurma();
                return turma;
            }
        }

        System.out.println("Nenhuma turma com esse código foi encontrada...");
        return null;
    }
}
