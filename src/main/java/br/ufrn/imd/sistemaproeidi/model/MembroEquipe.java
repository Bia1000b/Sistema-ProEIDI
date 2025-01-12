package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.*;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Vector;

public class MembroEquipe extends Pessoa{
    private String matricula;
    private String cursoUFRN;
    private String email;
    private Cargo cargo;
    private BancoDAO banco = BancoDAO.getInstance();

    public BancoDAO getBanco() {
        return banco;
    }

    public MembroEquipe(String nome, String CPF, Genero genero, String numeroCelular, String matricula, String cursoUFRN, String email, Cargo cargo) {
        super(nome, CPF, genero, numeroCelular);
        this.matricula = matricula;
        this.cursoUFRN = cursoUFRN;
        this.email = email;
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getEmail() {
        return email;
    }

    public String getCursoUFRN() {
        return cursoUFRN;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCursoUFRN(String cursoUFRN) {
        this.cursoUFRN = cursoUFRN;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(Cargo cargo) {
        if(this.cargo != Cargo.PROFESSOR){
            System.out.println("Você não tem permissão para cadatrar um membro da equipe.");
        }else{
            this.cargo = cargo;
        }
    }

    public void matricularAluno(){
        System.out.println("=== Cadastro de Aluno ===");
        String nome = InputUtils.lerString("Nome: ");
        LocalDate dataNascimento = InputUtils.lerData("Nascimento");

        if(Period.between(dataNascimento, LocalDate.now()).getYears() < 60){
            System.out.println("É necessário ser idoso para participar do projeto.");
            return;
        }

        String cpf = InputUtils.lerString("CPF: ");
        Genero genero = InputUtils.lerEnum(Genero.class);
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


    public void cadastrarMembroEquipe() {
        if(this.cargo != Cargo.PROFESSOR){
            System.out.println("Você não tem permissão para cadatrar um membro da equipe.");
        }else{
            System.out.println("=== Cadastro de Membro da Equipe ===");
            String nome = InputUtils.lerString("Nome: ");
            String cpf = InputUtils.lerString("CPF: ");
            Genero genero = InputUtils.lerEnum(Genero.class);
            String numeroCelular = InputUtils.lerString("Número de celular: ");
            String matricula = InputUtils.lerString("Matrícula: ");
            String cursoUFRN = InputUtils.lerString("Curso na UFRN: ");
            String email = InputUtils.lerString("E-mail: ");
            Cargo cargo = InputUtils.lerEnum(Cargo.class);

            MembroEquipe novoMembro = new MembroEquipe(nome,  cpf, genero, numeroCelular, matricula, cursoUFRN, email, cargo);

            if (banco.getArrayPessoas().add(novoMembro)) {
                System.out.println("Membro da equipe cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar o membro da equipe!");
            }
        }
    }

    private void cadastrarTurma(){
        String nome = InputUtils.lerString("Nome da turma: ");
        Curso curso = InputUtils.lerEnum(Curso.class);
        Horario horario = InputUtils.lerEnum(Horario.class);
        Integer numeroVagas = InputUtils.lerInteger("Número de vagas");
        LocalDate dataInicio = InputUtils.lerData("Data de ínicio");
        LocalDate dataTermino = InputUtils.lerData("Data de término");

        Turma turma = new Turma(nome, curso, horario, numeroVagas, dataInicio, dataTermino);

        if(banco.getArrayTurmas().add(turma)){
            System.out.println("Turma cadastrada com sucesso!");
        }else{
            System.out.println("Erro ao cadastrar turma!");
        }
    }

    public void adicionarMembroDaEquipeATurma(Turma turma, MembroEquipe membro){
        Vector<MembroEquipe> membros = turma.getEquipe();
        if(membros.add(membro)){
            System.out.println("Membro adicionado com sucesso à turma " + turma.getNome());
        }
        turma.setEquipe(membros);
    }

    public void chamadaAlunos(Turma turma){
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

    private void chamadaMembrosEquipe(Turma turma){
        if(this.cargo != Cargo.PROFESSOR){
            System.out.println("Você não tem permissão para cadatrar um membro da equipe.");
        }else {
            boolean presente = true;
            LocalDate dataAtual = LocalDate.now();
            for (MembroEquipe membroEquipe : turma.getEquipe()) {
                presente = InputUtils.lerBool("Presente? ");
                if (!presente) {
                    Vector<LocalDate> faltas = membroEquipe.getFaltas();
                    faltas.add(dataAtual);
                    membroEquipe.setFaltas(faltas);
                }
            }
        }
    }

    public Pessoa buscarPessoa(String CPF) {
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

    public void listarTurmas(){
        for (Turma turma : banco.getArrayTurmas()) {
            turma.detalharTurma();
        }
    }

    public void editarAluno(Aluno aluno) {
        System.out.println("=== Editar Aluno ===");

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

        if (InputUtils.lerBool("Deseja atualizar o nome?")) {
            String novoNome = InputUtils.lerString("Novo nome: ");
            aluno.setNome(novoNome);
        }

        // Atualizar Número de Celular
        if (InputUtils.lerBool("Deseja atualizar o número de celular?")) {
            String novoNumeroCelular = InputUtils.lerString("Novo número de celular: ");
            aluno.setNumeroCelular(novoNumeroCelular);
        }

        // Atualizar Escolaridade
        if (InputUtils.lerBool("Deseja atualizar a escolaridade?")) {
            Escolaridade novaEscolaridade = InputUtils.lerEnum(Escolaridade.class);
            aluno.setEscolaridade(novaEscolaridade);
        }

        // Atualizar Observação de Saúde
        if (InputUtils.lerBool("Deseja atualizar a observação de saúde?")) {
            String novaObsSaude = InputUtils.lerString("Nova observação de saúde: ");
            aluno.setObsSaude(novaObsSaude);
        }

        // Atualizar Tem Internet
        if (InputUtils.lerBool("Deseja atualizar se tem internet?")) {
            boolean novoTemInternet = InputUtils.lerBool("Tem internet? (true/false): ");
            aluno.setTemInternet(novoTemInternet);
        }

        // Atualizar Tem Computador
        if (InputUtils.lerBool("Deseja atualizar se tem computador?")) {
            boolean novoTemComputador = InputUtils.lerBool("Tem computador? (true/false): ");
            aluno.setTemComputador(novoTemComputador);
        }

        // Atualizar Tem Smartphone
        if (InputUtils.lerBool("Deseja atualizar se tem smartphone?")) {
            boolean novoTemSmartphone = InputUtils.lerBool("Tem smartphone? (true/false): ");
            aluno.setTemSmartphone(novoTemSmartphone);
        }

        if (InputUtils.lerBool("Deseja atualizar o sistema operacional?")) {
            SistemaOperacional novoSistemaOperacional = InputUtils.lerEnum(SistemaOperacional.class);
            aluno.setSistemaOperacional(novoSistemaOperacional);
        }

        if (InputUtils.lerBool("Deseja atualizar o curso atual?")) {
            Curso novoCursoAtual = InputUtils.lerEnum(Curso.class);
            aluno.setCursoAtual(novoCursoAtual);
        }

        System.out.println("As informações do aluno foram atualizadas com sucesso!");
    }

    public void editarMembroEquipe(MembroEquipe membro) {
        System.out.println("=== Editar Membro da Equipe ===");

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

        System.out.println("\n== Atualizações ==");

        if (InputUtils.lerBool("Deseja atualizar o nome?")) {
            String novoNome = InputUtils.lerString("Novo nome: ");
            membro.setNome(novoNome);
        }

        if (InputUtils.lerBool("Deseja atualizar o número de celular?")) {
            String novoNumeroCelular = InputUtils.lerString("Novo número de celular: ");
            membro.setNumeroCelular(novoNumeroCelular);
        }

        if (InputUtils.lerBool("Deseja atualizar a matrícula?")) {
            String novaMatricula = InputUtils.lerString("Nova matrícula: ");
            membro.setMatricula(novaMatricula);
        }

        if (InputUtils.lerBool("Deseja atualizar o curso na UFRN?")) {
            String novoCursoUFRN = InputUtils.lerString("Novo curso na UFRN: ");
            membro.setCursoUFRN(novoCursoUFRN);
        }

        if (InputUtils.lerBool("Deseja atualizar o e-mail?")) {
            String novoEmail = InputUtils.lerString("Novo e-mail: ");
            membro.setEmail(novoEmail);
        }

        if (InputUtils.lerBool("Deseja atualizar o cargo?")) {
            Cargo novoCargo = InputUtils.lerEnum(Cargo.class);
            membro.setCargo(novoCargo);
        }

        if (InputUtils.lerBool("Deseja remover este membro de alguma turma?")) {
            String nomeTurma = InputUtils.lerString("Nome da turma: ");

            for(Turma turma : banco.getArrayTurmas()){
                if(nomeTurma.equals(turma.getNome())){
                    for(MembroEquipe membroDaTurma : turma.getEquipe()){
                        if(membroDaTurma.getCPF().equals(membro.getCPF())){
                            turma.getEquipe().remove(membro);
                        }

                    }
                }
            }
        }

        System.out.println("As informações do membro foram atualizadas com sucesso!");
    }

    private void detalharMembroEquipe() {
        System.out.println("=== Detalhes do Membro da Equipe ===");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCPF());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Número de Celular: " + getNumeroCelular());
        System.out.println("Matrícula: " + matricula);
        System.out.println("Curso na UFRN: " + cursoUFRN);
        System.out.println("E-mail: " + email);
        System.out.println("Cargo: " + cargo);
    }

    public void editarTurma(Turma turma) {
        if(this.cargo != Cargo.PROFESSOR){
            System.out.println("Você não tem permissão para remover alguém da turma.");
        }else {
            if (turma == null) {
                System.out.println("Turma inválida!");
                return;
            }

            System.out.println("=== Editar Turma: " + turma.getNome() + " ===");

            if (InputUtils.lerBool("Deseja alterar o nome da turma?")) {
                String novoNome = InputUtils.lerString("Novo nome: ");
                turma.setNome(novoNome);
            }

            if (InputUtils.lerBool("Deseja alterar o curso associado?")) {
                Curso novoCurso = InputUtils.lerEnum(Curso.class);
                turma.setCurso(novoCurso);
            }

            // Editar o horário da turma
            if (InputUtils.lerBool("Deseja alterar o horário?")) {
                Horario novoHorario = InputUtils.lerEnum(Horario.class);
                turma.setHorario(novoHorario);
            }

            if (InputUtils.lerBool("Deseja alterar o número de vagas?")) {
                Integer novasVagas = InputUtils.lerInteger("Novo número de vagas: ");
                if (novasVagas < turma.getAlunos().size()) {
                    System.out.println("O número de vagas não pode ser menor que o número de alunos já matriculados (" + turma.getAlunos().size() + ").");
                } else {
                    turma.setNumeroVagas(novasVagas);
                }
            }

            if (InputUtils.lerBool("Deseja alterar a data de início?")) {
                LocalDate novaDataInicio = InputUtils.lerData("Nova data de início: ");
                if (turma.getDataTermino() != null && novaDataInicio.isAfter(turma.getDataTermino())) {
                    System.out.println("A data de início não pode ser posterior à data de término.");
                } else {
                    turma.setDataInicio(novaDataInicio);
                }
            }

            if (InputUtils.lerBool("Deseja alterar a data de término?")) {
                LocalDate novaDataTermino = InputUtils.lerData("Nova data de término: ");
                if (turma.getDataInicio() != null && novaDataTermino.isBefore(turma.getDataInicio())) {
                    System.out.println("A data de término não pode ser anterior à data de início.");
                } else {
                    turma.setDataTermino(novaDataTermino);
                }
            }

            System.out.println("Edição da turma concluída!");
        }
    }

    public void removerPessoaDaTurma(Pessoa pessoa, Turma turma){
        if(this.cargo != Cargo.PROFESSOR){
            System.out.println("Você não tem permissão para remover alguém da turma.");
        }else{
            if(pessoa instanceof Aluno){
                Aluno aluno = (Aluno) pessoa;
                turma.getAlunos().remove(aluno);
                System.out.println("Aluno removido com sucesso");
            }
            if(pessoa instanceof MembroEquipe){
                MembroEquipe membroEquipe = (MembroEquipe) pessoa;
                turma.getAlunos().remove(membroEquipe);
                System.out.println("Membro da equipe removido com sucesso");
            }
        }

    }
}
