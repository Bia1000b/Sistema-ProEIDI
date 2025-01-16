package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.Gerenciador;
import br.ufrn.imd.sistemaproeidi.model.MembroEquipe;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import br.ufrn.imd.sistemaproeidi.model.enums.Curso;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;

public class VerMembroController {
    @FXML private MembroEquipe membroEquipe;

    @FXML private ListView<String> listViewFaltas;
    @FXML private Label cursoUFRN, email, faltas, matricula, nomeUsuario, numeroDeTelefone, CPF, Genero;

    public void initialize() {
        System.out.println("Tela Ver Membro carregada!");
    }

    public void setMembroEquipe(MembroEquipe membroEquipe) {
        this.membroEquipe = membroEquipe;
        carregarDadosMembro();
        carregarFaltas();
    }

    private void carregarDadosMembro() {
        if (membroEquipe != null) {
            nomeUsuario.setText(membroEquipe.getNome());
            CPF.setText(membroEquipe.getCPF());
            Genero.setText(membroEquipe.getGenero().toString());
            cursoUFRN.setText(membroEquipe.getCursoUFRN().toString());
            email.setText(membroEquipe.getEmail());
            faltas.setText(Integer.toString(membroEquipe.getFaltas().size()));
            matricula.setText(membroEquipe.getMatricula());
            numeroDeTelefone.setText(membroEquipe.getNumeroCelular());

            System.out.println("Membro carregado: " + membroEquipe.getNome());
        } else {
            System.out.println("Nenhum membro foi carregado.");
        }
    }

    private void carregarFaltas(){
        if (membroEquipe.getFaltas() != null) {
            ObservableList<String> faltas = FXCollections.observableArrayList();

            for (LocalDate falta : membroEquipe.getFaltas()) {
                faltas.add(InputUtils.formatLocalDate(falta));
            }

            listViewFaltas.setItems(faltas);
        }
    }
}
