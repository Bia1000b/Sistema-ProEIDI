package br.ufrn.imd.sistemaproeidi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class PrincipalEquipeController {

    @FXML private TabPane tabPane;
    @FXML private Label nomeUsuario;
    @FXML private Tab perfilTab, turmasTab, buscarTab,cadastrarAlunoTab,cadastrarEquipeTab,chamadaTab;
    @FXML private Button btn_perfil, btn_turmas, btn_cadastrarAluno,btn_cadastrarEquipe, btn_chamada, btn_buscar;

    private Stage principalSceneEquipe;

    public void setPrincipalSceneEquipe(Stage stage) {
        this.principalSceneEquipe = stage;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Equipe carregada!");
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
