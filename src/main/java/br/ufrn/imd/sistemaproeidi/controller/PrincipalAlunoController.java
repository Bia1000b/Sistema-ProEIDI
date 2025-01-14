package br.ufrn.imd.sistemaproeidi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import br.ufrn.imd.sistemaproeidi.model.*;
import javafx.stage.Stage;

public class PrincipalAlunoController {
    @FXML private TabPane tabPane;
    @FXML private Label nomeUsuario;
    @FXML private Tab perfilTab, turmaTab;
    @FXML private Button btn_perfil, btn_turma,btn_perfil1, btn_turma1;
    private Aluno aluno;

    private Stage principalSceneAluno;

    public void setPrincipalSceneAluno(Stage stage) {
        this.principalSceneAluno = stage;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        carregarDadosAluno();
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Aluno carregada!");
        //nome.setText();
    }

    private void carregarDadosAluno() {
        if (aluno != null) {
            nomeUsuario.setText(aluno.getNome()); // Atualize o nome no Label
            System.out.println("Dados do aluno carregados: " + aluno.getNome());
        }
    }

    @FXML
    void clicarBtnPerfil(ActionEvent event) {
        tabPane.getSelectionModel().select(perfilTab);
        System.out.println("Botão PERFIL clicado.");
    }

    @FXML
    void clicarBtnTurma(ActionEvent event) {
        tabPane.getSelectionModel().select(turmaTab);
        System.out.println("Botão TURMA clicado.");
    }
}
