package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.SistemaApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import br.ufrn.imd.sistemaproeidi.model.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    private TextField CPFusuario;
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela inicial carregada!");
        CPFusuario.setText("");
    }

    public void processarCPF(){
        Turma turmaInicial = Gerenciador.criarTurmaPadrao();
        Gerenciador.criarAlunoPadrao(turmaInicial);
        String cpf = CPFusuario.getText();

        if (cpf.isEmpty()) {
            System.out.println("O campo CPF está vazio!");
        } else {
            System.out.println("CPF digitado: " + cpf);
            if(Gerenciador.buscarPessoa(cpf) == null){
                exibirAlerta("Login Inválido", "CPF incorreto ou não cadastrado!.");
            }else if ((Gerenciador.buscarPessoa(cpf)) instanceof Aluno){
                Aluno aluno = (Aluno) Gerenciador.buscarPessoa(cpf);
                abrirTelaPrincipalAluno(aluno);
            }else{
                abrirTelaPrincipalEquipe();
            }
        }
    }

    private void abrirTelaPrincipalAluno(Aluno aluno) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/PrincipalAluno.fxml"));

            Scene scene = new Scene(loader.load());

            // Obtendo o controlador da tela principal do aluno
            PrincipalAlunoController controller = loader.getController();
            controller.setAluno(aluno); // Passando o objeto Aluno para o controlador

            Stage stage = new Stage();
            stage.setTitle("Aluno");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar PrincipalAluno.fxml: " + e.getMessage());
        }
    }

    private void abrirTelaPrincipalEquipe() {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/PrincipalEquipe.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Equipe");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar PrincipalAluno.fxml: " + e.getMessage());
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
