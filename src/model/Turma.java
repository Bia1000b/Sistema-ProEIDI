package src.model;

import src.model.enums.Curso;
import src.model.enums.Horario;

import java.time.LocalDate;
import java.util.Vector;

public class Turma {
    private Vector<Aluno> alunos = new Vector<Aluno>();
    private Vector<MembroEquipe> equipe = new Vector<MembroEquipe>();

    private String nome;
    private Curso curso;
    private Horario horario;
    private Integer numeroVagas;
    private Boolean concluido;
    private LocalDate DataInicio;
    private LocalDate DataTermino;

    public Turma(Curso curso, Horario horario, Integer numeroVagas, LocalDate dataInicio, LocalDate dataTermino) {
        this.curso = curso;
        this.horario = horario;
        this.numeroVagas = numeroVagas;
        this.concluido = false;
        DataInicio = dataInicio;
        DataTermino = dataTermino;
    }

    public String getNome() {
        return nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public Vector<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Vector<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Vector<MembroEquipe> getEquipe() {
        return equipe;
    }

    public void setEquipe(Vector<MembroEquipe> equipe) {
        this.equipe = equipe;
    }

    public Integer getNumeroVagas() {
        return numeroVagas;
    }

    public void setNumeroVagas(Integer numeroVagas) {
        this.numeroVagas = numeroVagas;
    }

    private void concluir(){
        //se hoje == dataTermino
        // this.concluido = true;
    }
}
