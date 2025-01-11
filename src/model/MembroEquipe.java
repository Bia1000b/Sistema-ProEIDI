package src.model;

import src.model.enums.*;
import src.utils.InputUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Vector;

public class MembroEquipe extends Pessoa{
    private String matricula;
    private Vector<Curso> turmas;
    private String cursoUFRN;
    private String email;
    private Cargo cargo;
    private BancoDAO banco = BancoDAO.getInstance();

    public BancoDAO getBanco() {
        return banco;
    }

    public MembroEquipe(String nome, String CPF, Genero genero, String numeroCelular, String matricula, Vector<Curso> turmas, String cursoUFRN, String email, Cargo cargo) {
        super(nome, CPF, genero, numeroCelular);
        this.matricula = matricula;
        this.turmas = turmas;
        this.cursoUFRN = cursoUFRN;
        this.email = email;
        this.cargo = cargo;
    }

    void matricularAluno(){
        System.out.println("=== Cadastro de Aluno ===");
        String nome = InputUtils.lerString("Nome: ");
        LocalDate dataNascimento = InputUtils.lerData("Nascimento");

        if(Period.between(dataNascimento, LocalDate.now()).getYears() < 60){
            System.out.println("É necessário ser idoso para participar do projeto.");
            return;
        }

        String cpf = InputUtils.lerString("CPF: ");
        Genero genero = InputUtils.lerGenero();
        String numeroCelular = InputUtils.lerString("Número de celular");
        Escolaridade escolaridade = InputUtils.lerEnum(Escolaridade.class);
        String obsSaude = InputUtils.lerString("Observação relativa à saúde");
        boolean temInternet = InputUtils.lerBool("Tem internet?");
        boolean temComputador = InputUtils.lerBool("Tem computador?");
        boolean temSmartphone = InputUtils.lerBool("Tem smartPhone?");
        SistemaOperacional sistemaOperacional = InputUtils.lerEnum(SistemaOperacional.class);

        Aluno aluno = new Aluno(nome, cpf, genero, numeroCelular, dataNascimento, escolaridade, obsSaude, temInternet, temComputador, temSmartphone, sistemaOperacional);

        for(Turma turmaDisponivel : banco.getArrayTurmas()){
            System.out.println(turmaDisponivel.getNome() + "\n");
        }

        String turmaMatricula = InputUtils.lerString("Turma: ");

        for(Turma turma : banco.getArrayTurmas()){
            if(turmaMatricula.equals(turma.getNome())){
                System.out.println("Turma válida.");
                if(turma.getNumeroVagas() > 0){
                    aluno.setCursoAtual(turma.getCurso());
                    Vector<Aluno> alunosNovo = turma.getAlunos();
                    alunosNovo.add(aluno);
                    turma.setAlunos(alunosNovo);
                    Integer numeroDeVagas = turma.getNumeroVagas();
                    turma.setNumeroVagas(numeroDeVagas--);
                    break;
                }else{
                    System.out.println("Turma cheia");
                    break;
                }
            }else{
                System.out.println("Turma válida.");
            }
        }

        if(banco.getArrayPessoas().add(aluno)){
            System.out.println("Aluno matriculado com sucesso!");
        }else{
            System.out.println("Erro ao matricular!");
        }
    }

    void cadastrarTurma(){
        Curso curso = InputUtils.lerEnum(Curso.class);
        Horario horario = InputUtils.lerEnum(Horario.class);
        Integer numeroVagas = InputUtils.lerInteger("Número de vagas");
        LocalDate dataInicio = InputUtils.lerData("Data de ínicio");
        LocalDate dataTermino = InputUtils.lerData("Data de término");

        Turma turma = new Turma(curso, horario, numeroVagas, dataInicio, dataTermino);

        if(banco.getArrayTurmas().add(turma)){
            System.out.println("Turma cadastrada com sucesso!");
        }else{
            System.out.println("Erro ao cadastrar turma!");
        }
    }

    void adicionarMembroDaEquipeATurma(Turma turma, MembroEquipe membro){
        Vector<MembroEquipe> membros = turma.getEquipe();
        if(membros.add(membro)){
            System.out.println("Membro adicionado com sucesso à turma " + turma.getNome());
        }
        turma.setEquipe(membros);
    }

    void cadastrarMembroEquipe() {
        System.out.println("=== Cadastro de Membro da Equipe ===");
        String nome = InputUtils.lerString("Nome: ");
        String cpf = InputUtils.lerString("CPF: ");
        Genero genero = InputUtils.lerGenero();
        String numeroCelular = InputUtils.lerString("Número de celular: ");
        String matricula = InputUtils.lerString("Matrícula: ");
        String cursoUFRN = InputUtils.lerString("Curso na UFRN: ");
        String email = InputUtils.lerString("E-mail: ");
        Cargo cargo = InputUtils.lerEnum(Cargo.class);

        MembroEquipe novoMembro = new MembroEquipe(nome,  cpf, genero, numeroCelular, matricula, turmas, cursoUFRN, email, cargo);

        if (banco.getArrayPessoas().add(novoMembro)) {
            System.out.println("Membro da equipe cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o membro da equipe!");
        }
    }

    void chamadaAlunos(Turma turma){
        Boolean presente = true;
        LocalDate dataAtual = LocalDate.now();
        for(Aluno aluno : turma.getAlunos()){
            presente = InputUtils.lerBool("Presente? ");
            if(!presente){
                Vector<LocalDate> faltas = aluno.getFaltas();
                faltas.add(dataAtual);
                aluno.setFaltas(faltas);
            }
        }
    }

    void chamadaMembrosEquipe(Turma turma){
        Boolean presente = true;
        LocalDate dataAtual = LocalDate.now();
        for(MembroEquipe membroEquipe : turma.getEquipe()){
            presente = InputUtils.lerBool("Presente? ");
            if(!presente){
                Vector<LocalDate> faltas = membroEquipe.getFaltas();
                faltas.add(dataAtual);
                membroEquipe.setFaltas(faltas);
            }
        }
    }


    void editarAluno() {
        System.out.println("=== Editar Aluno ===");

        // Solicita o CPF do aluno a ser editado
        String cpf = InputUtils.lerString("CPF do aluno que deseja editar: ");

        // Procura o aluno pelo CPF no banco de dados
        Aluno aluno = null;
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa instanceof Aluno && pessoa.getCPF().equals(cpf)) {
                aluno = (Aluno) pessoa;
                break;
            }
        }

        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        System.out.println("Informações atuais do aluno:");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCPF());
        System.out.println("Gênero: " + aluno.getGenero());
        System.out.println("Número de celular: " + aluno.getNumeroCelular());
        System.out.println("Data de nascimento: " + aluno.getDataNascimento());
        System.out.println("Escolaridade: " + aluno.getEscolaridade());
        System.out.println("Observação de saúde: " + aluno.getObsSaude());
        System.out.println("Tem internet? " + aluno.isTemInternet());
        System.out.println("Tem computador? " + aluno.isTemComputador());
        System.out.println("Tem smartphone? " + aluno.isTemSmartphone());
        System.out.println("Sistema operacional: " + aluno.getSistemaOperacional());
        System.out.println("Curso atual: " + aluno.getCursoAtual());

        System.out.println("\n== Atualizações ==");

        if (InputUtils.lerBool("Deseja atualizar o nome? (true/false): ")) {
            String novoNome = InputUtils.lerString("Novo nome: ");
            aluno.setNome(novoNome);
        }

        // Atualizar Número de Celular
        if (InputUtils.lerBool("Deseja atualizar o número de celular? (true/false): ")) {
            String novoNumeroCelular = InputUtils.lerString("Novo número de celular: ");
            aluno.setNumeroCelular(novoNumeroCelular);
        }

        // Atualizar Escolaridade
        if (InputUtils.lerBool("Deseja atualizar a escolaridade? (true/false): ")) {
            Escolaridade novaEscolaridade = InputUtils.lerEnum(Escolaridade.class);
            aluno.setEscolaridade(novaEscolaridade);
        }

        // Atualizar Observação de Saúde
        if (InputUtils.lerBool("Deseja atualizar a observação de saúde? (true/false): ")) {
            String novaObsSaude = InputUtils.lerString("Nova observação de saúde: ");
            aluno.setObsSaude(novaObsSaude);
        }

        // Atualizar Tem Internet
        if (InputUtils.lerBool("Deseja atualizar se tem internet? (true/false): ")) {
            boolean novoTemInternet = InputUtils.lerBool("Tem internet? (true/false): ");
            aluno.setTemInternet(novoTemInternet);
        }

        // Atualizar Tem Computador
        if (InputUtils.lerBool("Deseja atualizar se tem computador? (true/false): ")) {
            boolean novoTemComputador = InputUtils.lerBool("Tem computador? (true/false): ");
            aluno.setTemComputador(novoTemComputador);
        }

        // Atualizar Tem Smartphone
        if (InputUtils.lerBool("Deseja atualizar se tem smartphone? (true/false): ")) {
            boolean novoTemSmartphone = InputUtils.lerBool("Tem smartphone? (true/false): ");
            aluno.setTemSmartphone(novoTemSmartphone);
        }

        // Atualizar Sistema Operacional
        if (InputUtils.lerBool("Deseja atualizar o sistema operacional? (true/false): ")) {
            SistemaOperacional novoSistemaOperacional = InputUtils.lerEnum(SistemaOperacional.class);
            aluno.setSistemaOperacional(novoSistemaOperacional);
        }

        // Atualizar Curso Atual
        if (InputUtils.lerBool("Deseja atualizar o curso atual? (true/false): ")) {
            Curso novoCursoAtual = InputUtils.lerEnum(Curso.class);
            aluno.setCursoAtual(novoCursoAtual);
        }

        System.out.println("As informações do aluno foram atualizadas com sucesso!");
    }

    void editarMembroEquipe() {
        System.out.println("=== Editar Membro da Equipe ===");

        // Solicita o CPF do membro a ser editado
        String cpf = InputUtils.lerString("CPF do membro que deseja editar: ");

        // Procura o membro pelo CPF no banco de dados
        MembroEquipe membro = null;
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa instanceof MembroEquipe && pessoa.getCPF().equals(cpf)) {
                membro = (MembroEquipe) pessoa;
                break;
            }
        }

        if (membro == null) {
            System.out.println("Membro da equipe não encontrado!");
            return;
        }

        // Exibe as informações atuais do membro
        System.out.println("Informações atuais do membro:");
        System.out.println("Nome: " + membro.getNome());
        System.out.println("CPF: " + membro.getCPF());
        System.out.println("Gênero: " + membro.getGenero());
        System.out.println("Número de celular: " + membro.getNumeroCelular());
        System.out.println("Matrícula: " + membro.getMatricula());
        System.out.println("Curso na UFRN: " + membro.getCursoUFRN());
        System.out.println("E-mail: " + membro.getEmail());
        System.out.println("Cargo: " + membro.getCargo());
        System.out.println("Turmas: " + membro.getTurmas());

        // Atualiza as informações do membro com base na entrada do usuário
        System.out.println("\n== Atualizações ==");

        // Atualizar Nome
        if (InputUtils.lerBool("Deseja atualizar o nome?")) {
            String novoNome = InputUtils.lerString("Novo nome: ");
            membro.setNome(novoNome);
        }

        // Atualizar Número de Celular
        if (InputUtils.lerBool("Deseja atualizar o número de celular?")) {
            String novoNumeroCelular = InputUtils.lerString("Novo número de celular: ");
            membro.setNumeroCelular(novoNumeroCelular);
        }

        // Atualizar Matrícula
        if (InputUtils.lerBool("Deseja atualizar a matrícula?")) {
            String novaMatricula = InputUtils.lerString("Nova matrícula: ");
            membro.setMatricula(novaMatricula);
        }

        // Atualizar Curso na UFRN
        if (InputUtils.lerBool("Deseja atualizar o curso na UFRN?")) {
            String novoCursoUFRN = InputUtils.lerString("Novo curso na UFRN: ");
            membro.setCursoUFRN(novoCursoUFRN);
        }

        // Atualizar E-mail
        if (InputUtils.lerBool("Deseja atualizar o e-mail?")) {
            String novoEmail = InputUtils.lerString("Novo e-mail: ");
            membro.setEmail(novoEmail);
        }

        // Atualizar Cargo
        if (InputUtils.lerBool("Deseja atualizar o cargo?")) {
            Cargo novoCargo = InputUtils.lerEnum(Cargo.class);
            membro.setCargo(novoCargo);
        }

//        // Atualizar Turmas
//        if (InputUtils.lerBool("Deseja atualizar as turmas?")) {
//            Vector<Curso> novasTurmas = new Vector<>();
//            System.out.println("Insira as turmas (digite 'fim' para parar): ");
//            while (true) {
//                String nomeTurma = InputUtils.lerString("Nome da turma: ");
//                if (nomeTurma.equalsIgnoreCase("fim")) {
//                    break;
//                }
//                Curso curso = null;
//                for (Curso c : Curso.values()) {
//                    if (c.name().equalsIgnoreCase(nomeTurma)) {
//                        curso = c;
//                        break;
//                    }
//                }
//                if (curso != null) {
//                    novasTurmas.add(curso);
//                } else {
//                    System.out.println("Turma inválida, tente novamente.");
//                }
//            }
//            membro.setTurmas(novasTurmas);
//        }

        System.out.println("As informações do membro foram atualizadas com sucesso!");
    }



}
