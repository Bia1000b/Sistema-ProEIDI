package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.SistemaApplication;
import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.model.enums.*;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

public class PrincipalEquipeController {

    @FXML private Tab perfilTab, turmasTab, buscarTab,cadastrarAlunoTab,cadastrarEquipeTab,cadastrarTurmaTab, pessoasTab;
    @FXML private TabPane tabPane;
    @FXML private VBox VBoxListaDeTurmas, VBoxListaDePessoas;
    @FXML private Button btn_menu_perfil, btn_menu_perfil1, btn_menu_perfil11, btn_menu_perfil111, btn_menu_perfil1111, btn_menu_perfil11111;
    @FXML private Button btn_menu_turma, btn_menu_turma1, btn_menu_turma11, btn_menu_turma111, btn_menu_turma1111, btn_menu_turma11111;
    @FXML private Button btn_Tab_cadastrarAluno, btn_Tab_cadastrarEquipe;
    @FXML private Button btn_menu_pessoas, btn_menu_pessoas1, btn_menu_pessoas11, btn_menu_pessoas111, btn_menu_pessoas1111, btn_menu_pessoas11111;
    @FXML private Button btn_menu_buscar, btn_menu_buscar1, btn_menu_buscar11, btn_menu_buscar111, btn_menu_buscar1111, btn_menu_buscar11111;
    @FXML private TextField cadastroAlunoCPF, cadastroAlunoNome, cadastroAlunoObsSaude, cadastroAlunoTelefone, cadastroEquipeCPF, cadastroEquipeCursoUFRN, cadastroEquipeEmail, cadastroEquipeMatricula, cadastroEquipeNome, cadastroEquipeTelefone;
    @FXML private TextField cadastroTurmaNome, cadastroTurmaVagas;
    @FXML private DatePicker cadastroAlunoDataNascimento, cadastroTurmaDataInicio, cadastroTurmaDataTermino;
    @FXML private ChoiceBox<Curso> cadastroTurmaCurso;
    @FXML private ChoiceBox<Genero> cadastroAlunoGenero, cadastroEquipeGenero;
    @FXML private ChoiceBox<SistemaOperacional> cadastroAlunoSO;
    @FXML private ChoiceBox<Escolaridade> cadastroAlunoEscolaridade;
    @FXML private ChoiceBox<Cargo>  cadastroEquipeCargo;
    @FXML private ChoiceBox<Turma>  cadastroAlunoTurmaDisponiveis;
    @FXML private ChoiceBox<Horario> cadastroTurmaHorario;
    @FXML private CheckBox checkAlunoComputador, checkAlunoInternet, checkAlunoSmartphone;
    @FXML private Label nomeUsuario, cursoUFRN, email, faltas, matricula, numeroDeTelefone;


    private MembroEquipe membroEquipe;
    private ArrayList<Pessoa> pessoas = BancoDAO.getInstance().getArrayPessoas();
    private ArrayList<Turma> turmas = BancoDAO.getInstance().getArrayTurmas();

    private Stage principalSceneEquipe;

    public void setPrincipalSceneEquipe(Stage stage) {
        this.principalSceneEquipe = stage;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Equipe carregada!");
        cadastroAlunoGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        cadastroEquipeGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        cadastroAlunoSO.setItems(FXCollections.observableArrayList(SistemaOperacional.values()));
        cadastroAlunoEscolaridade.setItems(FXCollections.observableArrayList(Escolaridade.values()));
        cadastroAlunoTurmaDisponiveis.setItems(FXCollections.observableArrayList(turmas));
        cadastroEquipeCargo.setItems(FXCollections.observableArrayList(Cargo.values()));
        cadastroTurmaCurso.setItems(FXCollections.observableArrayList(Curso.values()));
        cadastroTurmaHorario.setItems(FXCollections.observableArrayList(Horario.values()));
        cadastroAlunoDataNascimento.setValue(LocalDate.of(1964,06,20));
        carregarTurmas();
        carregarPessoas();

    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public void setMembroEquipe(MembroEquipe membroEquipe) {
        this.membroEquipe = membroEquipe;
        membroEquipe.detalharMembroEquipe(); //OPCIONAL
        carregarDadosMembroEquipe();
//        carregarFaltas();
    }

    public void carregarDadosMembroEquipe(){
        if(membroEquipe != null){
            nomeUsuario.setText(membroEquipe.getNome());
            cursoUFRN.setText(membroEquipe.getCursoUFRN());
            email.setText(membroEquipe.getEmail());
            faltas.setText(Integer.toString(membroEquipe.getFaltas().size()));
            matricula.setText(membroEquipe.getMatricula());
            numeroDeTelefone.setText(membroEquipe.getNumeroCelular());
        }
    }

    @FXML
    public void clicarBtnCadastrarAlunoFinal(ActionEvent event) {
        System.out.println("Botão CADASTRAR ALUNO clicado.");

        try {
            String nome = InputUtils.validarNome(cadastroAlunoNome.getText());
            String dataNascimentoTexto = cadastroAlunoDataNascimento.getEditor().getText();
            System.out.println(dataNascimentoTexto);
            LocalDate dataNascimento = InputUtils.validarData(dataNascimentoTexto);
            String cpf = InputUtils.validarCPF(cadastroAlunoCPF.getText());
            Genero genero = (Genero) cadastroAlunoGenero.getValue();
            String numeroCelular = InputUtils.validarTelefone(cadastroAlunoTelefone.getText());
            Escolaridade escolaridade = (Escolaridade) cadastroAlunoEscolaridade.getValue();
            Turma turma = (Turma) cadastroAlunoTurmaDisponiveis.getValue();
            String obsSaude = cadastroAlunoObsSaude.getText();
            boolean temInternet = checkAlunoInternet.isSelected();
            boolean temComputador = checkAlunoComputador.isSelected();
            boolean temSmartphone = checkAlunoSmartphone.isSelected();
            SistemaOperacional sistemaOperacional = (SistemaOperacional) cadastroAlunoSO.getValue();

            if (nome == null ||
                cpf == null ||
                genero == null ||
                numeroCelular == null ||
                escolaridade == null ||
                turma == null) {
                exibirAlerta("Cadastro impedido", "Por favor, preencha os campos corretamente.");
                return;
            }

            if (dataNascimento.isAfter(LocalDate.now())) {
                exibirAlerta("Data inválida", "A data de nascimento não pode ser futura.");
                return;
            }

            if (membroEquipe.matricularAluno(nome, cpf, genero, dataNascimento, numeroCelular, escolaridade, obsSaude, temInternet, temComputador, temSmartphone, sistemaOperacional, turma)) {
                LimparCamposAluno();
                exibirAlertaCadastroConcluido();
                carregarPessoas();
            } else {
                exibirAlerta("Cadastro impedido", "Verifique a idade do aluno.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro inesperado", "Ocorreu um erro ao tentar cadastrar o aluno. Por favor, tente novamente.");
        }
    }




    @FXML
    public void clicarBtnCadastrarEquipeFinal(ActionEvent event) {
        System.out.println("Botão CADASTRAR EQUIPE clicado.");

        try {
            String nome = InputUtils.validarNome(cadastroEquipeNome.getText());
            String cpf = InputUtils.validarCPF(cadastroEquipeCPF.getText());
            String numeroCelular = InputUtils.validarTelefone(cadastroEquipeTelefone.getText());
            Genero genero = (Genero) cadastroEquipeGenero.getValue();
            Cargo cargo = (Cargo) cadastroEquipeCargo.getValue();
            String matricula = cadastroEquipeMatricula.getText();
            String cursoUFRN = cadastroEquipeCursoUFRN.getText();
            String email = InputUtils.validarEmail(cadastroEquipeEmail.getText());

            if (nome == null ||
                    cpf == null ||
                    numeroCelular == null ||
                    genero == null ||
                    cargo == null ||
                    matricula.isBlank() ||
                    cursoUFRN.isBlank() ||
                    email == null) {
                exibirAlerta("Cadastro impedido", "Por favor, preencha os campos corretamente.");
                return;
            }

            // Realizar o cadastro do membro da equipe
            if (membroEquipe.cadastrarMembroEquipe(nome, cpf, genero, numeroCelular, matricula, cursoUFRN, email, cargo)) {
                LimparCamposEquipe();
                exibirAlertaCadastroConcluido();
                carregarPessoas();
            } else {
                exibirAlerta("Cadastro impedido", "Não foi possível cadastrar o membro da equipe. Verifique os dados e tente novamente.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro inesperado", "Ocorreu um erro ao tentar cadastrar o membro da equipe. Por favor, tente novamente.");
        }
    }


    @FXML
    public void clicarBtnCadastrarTurmaFinal(ActionEvent event) {
        System.out.println("Botão CADASTRAR TURMA clicado.");
        String nome = cadastroTurmaNome.getText();
        Horario horario = (Horario) cadastroTurmaHorario.getValue();
        Curso curso = (Curso) cadastroTurmaCurso.getValue();
        Integer vagas = Integer.parseInt(cadastroTurmaVagas.getText());
        LocalDate dataInicio = cadastroTurmaDataInicio.getValue();
        LocalDate dataTermino = cadastroTurmaDataTermino.getValue();


        membroEquipe.cadastrarTurma(nome, curso, horario, vagas, dataInicio, dataTermino);
        LimparCamposTurma();
        exibirAlertaCadastroConcluido();
        carregarTurmas();
        cadastroAlunoTurmaDisponiveis.setItems(FXCollections.observableArrayList(turmas));
    }
    @FXML
    public void LimparCamposTurma() {
        cadastroTurmaNome.clear();
        cadastroTurmaHorario.setValue(null);
        cadastroTurmaCurso.setValue(null);
        cadastroTurmaVagas.clear();
        cadastroTurmaDataInicio.setValue(null);
        cadastroTurmaDataTermino.setValue(null);
    }

    @FXML
    public void LimparCamposEquipe() {
        cadastroEquipeNome.clear();
        cadastroEquipeCPF.clear();
        cadastroEquipeTelefone.clear();
        cadastroEquipeCargo.setValue(null);
        cadastroEquipeGenero.setValue(null);
        cadastroEquipeMatricula.clear();
        cadastroEquipeCursoUFRN.clear();
        cadastroEquipeEmail.clear();
    }

    @FXML
    public void LimparCamposAluno() {
        cadastroAlunoNome.clear();
        cadastroAlunoDataNascimento.setValue(null);
        cadastroAlunoCPF.clear();
        cadastroAlunoGenero.setValue(null);
        cadastroAlunoTelefone.clear();
        cadastroAlunoEscolaridade.setValue(null);
        cadastroAlunoTurmaDisponiveis.setValue(null);
        cadastroAlunoObsSaude.clear();
        checkAlunoInternet.setSelected(false);
        checkAlunoComputador.setSelected(false);
        checkAlunoSmartphone.setSelected(false);
        cadastroAlunoSO.setValue(null);

    }

    @FXML
    void clicarBtnPerfil(ActionEvent event) {
        tabPane.getSelectionModel().select(perfilTab);
        System.out.println("Botão PERFIL clicado.");
    }

    @FXML
    void clicarBtnTurmas(ActionEvent event) {
        tabPane.getSelectionModel().select(turmasTab);
        System.out.println("Botão TURMAS clicado.");
    }

    @FXML
    void clicarBtnCadastrarAluno(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarAlunoTab);
        System.out.println("Botão CADASTRAR ALUNO clicado.");
    }

    @FXML
    void clicarBtnCadastrarEquipe(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarEquipeTab);
        System.out.println("Botão CADASTRAR EQUIPE clicado.");
    }

    @FXML
    void clicarBtnPessoas(ActionEvent event) {
        tabPane.getSelectionModel().select(pessoasTab);
        System.out.println("Botão PESSOAS clicado.");
    }

    @FXML
    void clicarBtnTabCadastrarTurma(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarTurmaTab);
        System.out.println("Botão CADASTRAR TURMA TAB clicado.");
    }

    @FXML
    private void carregarTurmas() {
        VBoxListaDeTurmas.getChildren().clear();
        try {
            // Adicionar cada tarefa ao VBox
            for (Turma turma : turmas) {
                String nome = turma.getNome();
                boolean concluida = turma.getConcluido();
                Horario horario = turma.getHorario();

                // Adiciona a tarefa ao VBox
                adicionarBlocoTurma(turma);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar turmas " + e.getMessage());
        }
    }

    @FXML
    void adicionarBlocoTurma(Turma turma) {
        // Criação do HBox
        HBox blocoTurma = new HBox();
        blocoTurma.setPrefSize(637, 129); // Dimensão fixa
        blocoTurma.setStyle("-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");

        // Criação do Pane dentro do HBox para layout manual
        Pane paneTurma = new Pane();
        paneTurma.setPrefSize(630, 130); // Mesma altura que o HBox

        // Criação das Labels
        Label labelNome = new Label("NOME: " + turma.getNome());
        labelNome.setLayoutX(14);
        labelNome.setLayoutY(14);
        labelNome.setPrefSize(500, 30);
        labelNome.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Label horarioLabel = new Label("HORARIO: " + turma.getHorario());
        horarioLabel.setLayoutX(14);
        horarioLabel.setLayoutY(44);
        horarioLabel.setPrefSize(500, 30);
        horarioLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        // Criação do Button chamada
        Button btn_chamada = new Button("Chamada");
        btn_chamada.setLayoutX(250);
        btn_chamada.setLayoutY(59);
        btn_chamada.setPrefSize(100, 30);
        btn_chamada.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

        btn_chamada.setOnAction(event -> {
            abrirTelaChamada(turma);
        });

        // Criação do Button para ver detalhes
        Button btn_verTurma = new Button("Ver");
        btn_verTurma.setLayoutX(370);
        btn_verTurma.setLayoutY(10);
        btn_verTurma.setPrefSize(100, 30);
        btn_verTurma.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

        // Ação do botão de ver
        btn_verTurma.setOnAction(event -> {
            abrirTelaVerTurma(turma);
        });

        // Criação do Button para excluir
        Button btn_apagar = new Button("Apagar");
        btn_apagar.setLayoutX(370);
        btn_apagar.setLayoutY(59);
        btn_apagar.setPrefSize(100, 30);
        btn_apagar.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

        // Ação do botão de excluir
        btn_apagar.setOnAction(event -> {
            if (exibirAlertaConfirmarApagar(turma.getNome())){
                // Remover do VBox
                VBoxListaDeTurmas.getChildren().remove(blocoTurma);
                turmas.remove(turma);
            }
        });

        if(!turma.getConcluido()){
            // Criação do Button concluir
            Button btn_concluir = new Button("Concluir");
            btn_concluir.setLayoutX(250);
            btn_concluir.setLayoutY(10);
            btn_concluir.setPrefSize(100, 30);
            btn_concluir.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

            paneTurma.getChildren().addAll(labelNome, horarioLabel,btn_apagar,btn_verTurma,btn_chamada,btn_concluir);

            btn_concluir.setOnAction(event -> {
                if(exibirAlertaConfirmarConcluir(turma.getNome())){
                    membroEquipe.concluirTurma(turma);
                    paneTurma.getChildren().remove(btn_concluir);
                }
            });

        }else{
            paneTurma.getChildren().addAll(labelNome, horarioLabel,btn_apagar,btn_verTurma,btn_chamada);
        }

        // Adicionar o Pane ao HBox
        blocoTurma.getChildren().add(paneTurma);

        // Adicionar o HBox ao VBox existente (activitiesVBox)
        VBoxListaDeTurmas.getChildren().add(blocoTurma);
    }

    @FXML
    private void carregarPessoas() {
        VBoxListaDePessoas.getChildren().clear();
        try {
            // Adicionar cada pss ao VBox
            for (Pessoa pessoa : pessoas) {
                String nome = pessoa.getNome();

                // Adiciona a tarefa ao VBox
                adicionarBlocoPessoa(pessoa);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar pessoas " + e.getMessage());
        }
    }

    @FXML
    void adicionarBlocoPessoa(Pessoa pessoa) {
        // Criação do HBox
        HBox blocoPessoa = new HBox();
        blocoPessoa.setPrefSize(637, 129); // Dimensão fixa
        blocoPessoa.setStyle("-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");

        // Criação do Pane dentro do HBox para layout manual
        Pane panePessoa = new Pane();
        panePessoa.setPrefSize(630, 130); // Mesma altura que o HBox

        // Criação das Labels
        Label labelNome = new Label("NOME: " + pessoa.getNome());
        labelNome.setLayoutX(14);
        labelNome.setLayoutY(14);
        labelNome.setPrefSize(500, 30);
        labelNome.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        // Determinação do tipo da pessoa
        String tipoPessoa = (pessoa instanceof Aluno) ? "Aluno" : "Membro da Equipe";

        // Label para o tipo da pessoa
        Label labelTipo = new Label(tipoPessoa);
        labelTipo.setLayoutX(14);
        labelTipo.setLayoutY(50); // Ajuste para posicionar abaixo do nome
        labelTipo.setPrefSize(500, 30);
        labelTipo.setStyle("-fx-text-fill: white; -fx-font-size: 14;");


        // Criação do Button para ver detalhes
        Button btn_verPessoa = new Button("Ver");
        btn_verPessoa.setLayoutX(370);
        btn_verPessoa.setLayoutY(10);
        btn_verPessoa.setPrefSize(100, 30);
        btn_verPessoa.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

        // Criação do Button para excluir atividade
        Button btn_apagar = new Button("Apagar");
        btn_apagar.setLayoutX(370);
        btn_apagar.setLayoutY(59);
        btn_apagar.setPrefSize(100, 30);
        btn_apagar.setStyle("-fx-text-fill: black; -fx-font-size: 14;");

        // Ação do botão de verPessoa
        btn_verPessoa.setOnAction(event -> {
            abrirTelaVerPessoa(pessoa);
        });

        // Ação do botão de excluir
        btn_apagar.setOnAction(event -> {
            if (exibirAlertaConfirmarApagar(pessoa.getNome())){
                // Remover do VBox
                VBoxListaDePessoas.getChildren().remove(blocoPessoa);
                pessoas.remove(pessoa);
                membroEquipe.removerPessoaDasTurmas(pessoa);
            }
        });
        // Adicionar todos os componentes ao Pane
        panePessoa.getChildren().addAll(labelNome,btn_apagar,btn_verPessoa, labelTipo);

        // Adicionar o Pane ao HBox
        blocoPessoa.getChildren().add(panePessoa);

        // Adicionar o HBox ao VBox existente (activitiesVBox)
        VBoxListaDePessoas.getChildren().add(blocoPessoa);
    }

    private void exibirAlertaCadastroConcluido() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Cadastro Concluido");
        alerta.setContentText("Parabens! Cadastro Concluido com sucesso!");
        alerta.showAndWait();
    }

    private boolean exibirAlertaConfirmarApagar(String objeto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(objeto + " será apagada(o)");
        alerta.setHeaderText(null);
        alerta.setContentText(objeto + " será apagada(o). Deseja continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private boolean exibirAlertaConfirmarConcluir(String objeto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(objeto + " será concluido(a)");
        alerta.setHeaderText(null);
        alerta.setContentText(objeto + " será concluido(a). Deseja continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void abrirTelaVerPessoa(Pessoa pessoa) {
        if (pessoa instanceof Aluno) {
            try {
                FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerAluno.fxml"));
                Parent root = loader.load();

                // Obtém o controlador
                VerAlunoController controller = loader.getController();

                Aluno aluno = (Aluno) pessoa;
                controller.setAluno(aluno);

                // Configura a janela
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Aluno");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar VerAluno.fxml: " + e.getMessage());
            }
        } else if (pessoa instanceof MembroEquipe) {
            try {
                FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerMembro.fxml"));
                Parent root = loader.load();

                VerMembroController controller = loader.getController();

                MembroEquipe membro = (MembroEquipe) pessoa;
                controller.setMembroEquipe(membro);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Membro da Equipe");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar VerAluno.fxml: " + e.getMessage());
            }

        }
    }

    private void abrirTelaVerTurma(Turma turma) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerTurma.fxml"));
            Parent root = loader.load();

            VerTurmaController controller = loader.getController();

            controller.setTurma(turma);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Turma");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar VerTurma.fxml: " + e.getMessage());
        }

    }

    private void abrirTelaChamada(Turma turma) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/Chamada.fxml"));
            Parent root = loader.load();

            ChamadaController controller = loader.getController();

            controller.setTurma(turma);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Chamada");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar Chamada.fxml: " + e.getMessage());
        }

    }

}
