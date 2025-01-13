package br.ufrn.imd.sistemaproeidi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class PrincipalEquipeController {
    @FXML
    private TabPane tabPane;

    private Stage principalSceneEquipe;

    public void setPrincipalSceneEquipe(Stage stage) {
        this.principalSceneEquipe = stage;
    }

}
