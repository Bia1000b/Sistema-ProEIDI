package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.MembroEquipe;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Vector;

public class PrincipalEquipeController {

    @FXML private Tab perfilTab, turmasTab, buscarTab,cadastrarAlunoTab,cadastrarEquipeTab,chamadaTab;
    @FXML private TabPane tabPane;
    @FXML private Button btn_perfil, btn_perfil1, btn_perfil11, btn_perfil111, btn_perfil1111, btn_perfil11111;
    @FXML private Button btn_turma, btn_turma1, btn_turma11, btn_turma111, btn_turma1111, btn_turma11111;
    @FXML private Button btn_cadastrarAluno, btn_cadastrarAluno1, btn_cadastrarAluno11, btn_cadastrarAluno111, btn_cadastrarAluno1111, btn_cadastrarAluno11111;
    @FXML private Button btn_cadastrarEquipe, btn_cadastrarEquipe1, btn_cadastrarEquipe11, btn_cadastrarEquipe111, btn_cadastrarEquipe1111, btn_cadastrarEquipe11111;
    @FXML private Button btn_chamada, btn_chamada1, btn_chamada11, btn_chamada111, btn_chamada1111, btn_chamada11111;
    @FXML private Button btn_buscar, btn_buscar1, btn_buscar11, btn_buscar111, btn_buscar1111, btn_buscar11111;
    @FXML private TextField cadastroAlunoCPF, cadastroAlunoNome, cadastroAlunoObsSaude, cadastroAlunoTelefone, cadastroEquipeCPF, cadastroEquipeCursoUFRN, cadastroEquipeEmail, cadastroEquipeMatricula, cadastroEquipeNome, cadastroEquipeTelefone;
    @FXML private DatePicker cadastroAlunoDataNascimento;
    @FXML private ChoiceBox<?> cadastroAlunoEscolaridade, cadastroAlunoGenero, cadastroAlunoSO, cadastroAlunoTurmaDisponiveis, cadastroEquipeCaargo;
    @FXML private CheckBox checkAlunoComputador, checkAlunoInternet, checkAlunoSmartphone;
    @FXML private Label nomeUsuario, nomeUsuario1, cursoUFRN, cursoUFRN1, email, email1, faltas, faltas1, matricula, matricula1, matricula11, numeroDeTelefone, numeroDeTelefone1;
    @FXML private Pane inner_pane2, inner_pane21, inner_pane211, inner_pane2111, inner_pane21111, inner_pane211111, pane_111112, pane_1111121, pane_11111211, pane_111112111, pane_1111121111, pane_11111211111, pane_111112111111;
    @FXML private HBox root2, root21, root211, root2111, root21111, root211111;

    private MembroEquipe membroEquipe;
    private Vector<Turma> turmas;

    private Stage principalSceneEquipe;

    public void setPrincipalSceneEquipe(Stage stage) {
        this.principalSceneEquipe = stage;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Equipe carregada!");
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
            nomeUsuario1.setText(membroEquipe.getNome()); //REVER
            cursoUFRN.setText(membroEquipe.getCursoUFRN());
            cursoUFRN1.setText(membroEquipe.getCursoUFRN());
            email.setText(membroEquipe.getEmail());
            email1.setText(membroEquipe.getEmail());
            faltas.setText(Integer.toString(membroEquipe.getFaltas().size()));
            faltas1.setText(Integer.toString(membroEquipe.getFaltas().size()));
            matricula.setText(membroEquipe.getMatricula());
            matricula1.setText(membroEquipe.getMatricula());
            numeroDeTelefone.setText(membroEquipe.getNumeroCelular());
            numeroDeTelefone1.setText(membroEquipe.getNumeroCelular());
        }
    }



    @FXML
    void clicarBtnCadastrar(ActionEvent event) {
        tabPane.getSelectionModel().select(perfilTab);
        System.out.println("Botão PERFIL clicado.");
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
