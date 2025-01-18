package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.Pessoa;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.time.LocalDate;
import java.util.Optional;

public class ChamadaController {

    @FXML private Button btn_confirmarChamada;
    @FXML private TableColumn<Pessoa, String> colunaNome;
    @FXML private TableColumn<Pessoa, Boolean> colunaPresenca;
    @FXML private Label diaDeHoje;
    @FXML private TableView<Pessoa> tabelaChamada;

    private ObservableList<Pessoa> listaPessoasDaTurma;
    private Turma turma;

    @FXML
    public void setTurma(Turma turma){
        this.turma = turma;
        carregarPessoas();
        configurarTabela();
    }

    @FXML
    public void initialize() {
        diaDeHoje.setText("Data: " + InputUtils.formatLocalDate(LocalDate.now()));
    }

    private void configurarTabela() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPresenca.setCellValueFactory(param -> {
            BooleanProperty presente = new SimpleBooleanProperty(true);
            return presente;
        });
        colunaPresenca.setCellFactory(CheckBoxTableCell.forTableColumn(colunaPresenca));
    }

    private void carregarPessoas() {
        listaPessoasDaTurma = FXCollections.observableArrayList();
        listaPessoasDaTurma.addAll(turma.getAlunos());
        listaPessoasDaTurma.addAll(turma.getEquipe());

        if(listaPessoasDaTurma != null){
            tabelaChamada.setItems(listaPessoasDaTurma);
        }
    }

    @FXML
    public void clicarBtnConfirmarChamada(ActionEvent event) {
        if (exibirAlertaConfirmarChamada()) {
            registrarFaltas();
        }
    }

    private boolean exibirAlertaConfirmarChamada() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText(null);
        alerta.setContentText("Chamada será registrada. Confirma?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void registrarFaltas() {
        for (Pessoa pessoa : listaPessoasDaTurma) {
            if (!isPresente(pessoa)) {
                pessoa.getFaltas().add(LocalDate.now());
            }
        }

        for (Pessoa pessoa : listaPessoasDaTurma) {
            System.out.println("Pessoa: " + pessoa.getNome() + " - Faltas: " + pessoa.getFaltas().size());
        }
    }

    private boolean isPresente(Pessoa pessoa) {
        int index = listaPessoasDaTurma.indexOf(pessoa);
        CheckBox checkbox = (CheckBox) tabelaChamada.getColumns().get(1).getCellData(index);
        return checkbox.isSelected();
    }
}
