package br.ufrn.imd.sistemaproeidi.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import br.ufrn.imd.sistemaproeidi.model.*;
import javafx.stage.Stage;

public class PrincipalAlunoController {
    @FXML
    private TabPane tabPane;

    private Stage principalSceneAluno;

    public void setPrincipalSceneAluno(Stage stage) {
        this.principalSceneAluno = stage;
    }

}
