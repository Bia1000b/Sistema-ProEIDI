package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.Gerenciador;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import br.ufrn.imd.sistemaproeidi.model.enums.Curso;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;

public class VerAlunoController {
    @FXML private Aluno aluno;
    private Turma turmaAluno;

    @FXML
    private Label CPF, Computador, Smartphone, DataDeNascimento, CursoAtual, Escolaridade, Faltas, Genero, Internet, NumeroDeTelefone, ObsSaude, SO, TurmaAtual, nomeUsuario, totalFaltas;

    @FXML
    private ListView<String> ListViewCursos, listViewFaltas;

    public void initialize() {
        System.out.println("Tela Ver carregada!");
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        aluno.detalharAluno(); //OPCIONAL
        carregarDadosAluno();
        carregarCursosFeitos();
        carregarFaltas();
    }

    public void setTurmaAluno(){
        this.turmaAluno = Gerenciador.buscarTurma(this.aluno.getCodigoTurma());
    }

    private void carregarDadosAluno() {
        if (aluno != null) {
            setTurmaAluno();
            nomeUsuario.setText(aluno.getNome());
            CPF.setText(aluno.getCPF());
            Genero.setText(aluno.getGenero().toString());
            NumeroDeTelefone.setText(aluno.getNumeroCelular());
            DataDeNascimento.setText(aluno.getDataNascimento().toString());
            Escolaridade.setText(aluno.getEscolaridade().toString());
            ObsSaude.setText(aluno.getObsSaude());
            SO.setText(aluno.getSistemaOperacional().toString());
            TurmaAtual.setText(turmaAluno.getNome());
            CursoAtual.setText(InputUtils.formatEnum(aluno.getCursoAtual().toString()));
            Faltas.setText(String.valueOf(aluno.getFaltas().size()));

            Internet.setText(aluno.isTemInternet() ? "Sim" : "Não");
            Computador.setText(aluno.isTemComputador() ? "Sim" : "Não");
            Smartphone.setText(aluno.isTemSmartphone() ? "Sim" : "Não");

            totalFaltas.setText(Integer.toString(aluno.getFaltas().size()));

            System.out.println("Dados do aluno carregados: " + aluno.getNome());
        } else {
            System.out.println("Nenhum aluno foi carregado.");
        }
    }


    private void carregarCursosFeitos(){
        if (aluno.getCursosFeitos() != null) {
            ObservableList<String> cursos = FXCollections.observableArrayList();

            for (Curso curso : aluno.getCursosFeitos()) {
                cursos.add(InputUtils.formatEnum(curso.toString()));
            }

            ListViewCursos.setItems(cursos);
        }
    }

    private void carregarFaltas(){
        if (aluno.getFaltas() != null) {
            ObservableList<String> faltas = FXCollections.observableArrayList();

            for (LocalDate falta : aluno.getFaltas()) {
                faltas.add(InputUtils.formatLocalDate(falta));
            }

            listViewFaltas.setItems(faltas);
        }
    }
}
