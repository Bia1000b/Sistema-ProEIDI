package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.SistemaApplication;
import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.model.enums.*;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

public class PrincipalEquipeController {

    @FXML private Tab perfilTab, turmasTab, buscarTab,cadastrarAlunoTab,cadastrarEquipeTab,cadastrarTurmaTab, pessoasTab;
    @FXML private TabPane tabPane;
    @FXML private VBox VBoxListaDeTurmas, VBoxListaDePessoas;
    @FXML private Button btn_menu_perfil, btn_menu_perfil1, btn_menu_perfil11, btn_menu_perfil111, btn_menu_perfil1111, btn_menu_perfil11111;
    @FXML private Button btn_menu_turma, btn_menu_turma1, btn_menu_turma11, btn_menu_turma111, btn_menu_turma1111, btn_menu_turma11111;
    @FXML private Button btn_Tab_cadastrarAluno, btn_Tab_cadastrarEquipe;
    @FXML private Button btn_menu_pessoas, btn_menu_pessoas1, btn_menu_pessoas11, btn_menu_pessoas111, btn_menu_pessoas1111, btn_menu_pessoas11111;
    @FXML private Button btn_menu_chamada, btn_menu_chamada1, btn_menu_chamada11, btn_menu_chamada111, btn_menu_chamada1111, btn_menu_chamada11111;
    @FXML private Button btn_menu_buscar, btn_menu_buscar1, btn_menu_buscar11, btn_menu_buscar111, btn_menu_buscar1111, btn_menu_buscar11111;
    @FXML private TextField cadastroAlunoCPF, cadastroAlunoNome, cadastroAlunoObsSaude, cadastroAlunoTelefone, cadastroEquipeCPF, cadastroEquipeCursoUFRN, cadastroEquipeEmail, cadastroEquipeMatricula, cadastroEquipeNome, cadastroEquipeTelefone;
    @FXML private TextField cadastroTurmaNome, cadastroTurmaVagas;
    @FXML private DatePicker cadastroAlunoDataNascimento, cadastroTurmaDataInicio, cadastroTurmaDataTermino;
    @FXML private ChoiceBox<Curso> cadastroTurmaCurso;
    @FXML private ChoiceBox<Genero> cadastroAlunoGenero;
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
        cadastroAlunoSO.setItems(FXCollections.observableArrayList(SistemaOperacional.values()));
        cadastroAlunoEscolaridade.setItems(FXCollections.observableArrayList(Escolaridade.values()));
        cadastroAlunoTurmaDisponiveis.setItems(FXCollections.observableArrayList(turmas));
        cadastroEquipeCargo.setItems(FXCollections.observableArrayList(Cargo.values()));
        cadastroTurmaCurso.setItems(FXCollections.observableArrayList(Curso.values()));
        cadastroTurmaHorario.setItems(FXCollections.observableArrayList(Horario.values()));
        carregarTurmas();
        carregarPessoas();

    }

    public void setMembroEquipe(MembroEquipe membroEquipe) {
        this.membroEquipe = membroEquipe;
        membroEquipe.detalharMembroEquipe(); //OPCIONAL
        carregarDadosMembroEquipe();
//        carregarCursosFeitos();
//        carregarFaltas();
//        carregarAlunosTurma();
    }

    //muitos dados repetidos, rever isso
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
        String nome = cadastroAlunoNome.getText();
        LocalDate dataNascimento = cadastroAlunoDataNascimento.getValue();
        String cpf = cadastroAlunoCPF.getText();
        Genero genero = (Genero) cadastroAlunoGenero.getValue();
        String numeroCelular = cadastroAlunoTelefone.getText();
        Escolaridade escolaridade = (Escolaridade) cadastroAlunoEscolaridade.getValue();
        Turma turma = (Turma) cadastroAlunoTurmaDisponiveis.getValue();
        String obsSaude = cadastroAlunoObsSaude.getText();
        boolean temInternet = checkAlunoInternet.isSelected();
        boolean temComputador = checkAlunoComputador.isSelected();
        boolean temSmartphone = checkAlunoSmartphone.isSelected();
        SistemaOperacional sistemaOperacional = (SistemaOperacional) cadastroAlunoSO.getValue();

        membroEquipe.matricularAluno(nome, cpf, genero, dataNascimento, numeroCelular, escolaridade, obsSaude, temInternet, temComputador, temSmartphone, sistemaOperacional, turma);
        LimparCamposAluno();
        exibirAlertaCadastroConcluido();
        carregarPessoas();
    }

    @FXML
    public void clicarBtnCadastrarEquipeFinal(ActionEvent event) {
        System.out.println("Botão CADASTRAR EQUIPE clicado.");
        String nome = cadastroEquipeNome.getText();
        String cpf = cadastroEquipeCPF.getText();
        String numeroCelular = cadastroEquipeTelefone.getText();
        Cargo cargo = (Cargo) cadastroEquipeCargo.getValue();
        String matricula = cadastroEquipeMatricula.getText();
        String cursoUFRN = cadastroEquipeCursoUFRN.getText();
        String email = cadastroEquipeEmail.getText();

        membroEquipe.cadastrarMembroEquipe(nome, cpf, null, numeroCelular, matricula, cursoUFRN, email, cargo);
        LimparCamposEquipe();
        exibirAlertaCadastroConcluido();
        carregarPessoas();
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
    }
    @FXML
    public void LimparCamposTurma() {
        // Limpa os campos da interface
        cadastroTurmaNome.clear();
        cadastroTurmaHorario.setValue(null);
        cadastroTurmaCurso.setValue(null);
        cadastroTurmaVagas.clear();
        cadastroTurmaDataInicio.setValue(null);
        cadastroTurmaDataTermino.setValue(null);
    }

    @FXML
    public void LimparCamposEquipe() {
        // Limpa os campos da interface
        cadastroEquipeNome.clear();
        cadastroEquipeCPF.clear();
        cadastroEquipeTelefone.clear();
        cadastroEquipeCargo.setValue(null);
        cadastroEquipeMatricula.clear();
        cadastroEquipeCursoUFRN.clear();
        cadastroEquipeEmail.clear();
    }

    @FXML
    public void LimparCamposAluno() {
        // Limpa os campos da interface
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
    void clicarBtnChamada(ActionEvent event) {
        tabPane.getSelectionModel().select(chamadaTab);
        System.out.println("Botão CHAMADA clicado.");
    }

    @FXML
    void clicarBtnBuscar(ActionEvent event) {
        tabPane.getSelectionModel().select(buscarTab);
        System.out.println("Botão BUSCAR clicado.");
    }
}
