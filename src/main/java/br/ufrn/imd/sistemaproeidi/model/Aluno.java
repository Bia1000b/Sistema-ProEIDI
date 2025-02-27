package br.ufrn.imd.sistemaproeidi.model;
import br.ufrn.imd.sistemaproeidi.model.enums.*;

import java.time.LocalDate;
import java.util.Vector;

public class Aluno extends Pessoa {
    private LocalDate dataNascimento;
    private Escolaridade escolaridade;
    private String obsSaude;
    private boolean temInternet;
    private boolean temComputador;
    private boolean temSmartphone;
    private SistemaOperacional sistemaOperacional;
    private Curso cursoAtual;
    private String codigoTurma;
    private Vector<Curso> CursosFeitos;


    public Aluno(String nome, String CPF, Genero genero, String numeroCelular, LocalDate dataNascimento, Escolaridade escolaridade, String obsSaude, boolean temInternet, boolean temComputador, boolean temSmartphone, SistemaOperacional sistemaOperacional) {
        super(nome, CPF, genero, numeroCelular);
        this.dataNascimento = dataNascimento;
        this.escolaridade = escolaridade;
        this.obsSaude = obsSaude;
        this.temInternet = temInternet;
        this.temComputador = temComputador;
        this.temSmartphone = temSmartphone;
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Vector<Curso> getCursosFeitos() {
        if (CursosFeitos == null) {
            CursosFeitos = new Vector<>();
        }
        return CursosFeitos;
    }

    public void setCursosFeitos(Vector<Curso> cursosFeitos) {
        CursosFeitos = cursosFeitos;
    }

    public Curso getCursoAtual() {
        return cursoAtual;
    }

    public void setCursoAtual(Curso cursoAtual) {
        this.cursoAtual = cursoAtual;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getObsSaude() {
        return obsSaude;
    }

    public void setObsSaude(String obsSaude) {
        this.obsSaude = obsSaude;
    }

    public boolean isTemInternet() {
        return temInternet;
    }

    public void setTemInternet(boolean temInternet) {
        this.temInternet = temInternet;
    }

    public boolean isTemComputador() {
        return temComputador;
    }

    public void setTemComputador(boolean temComputador) {
        this.temComputador = temComputador;
    }

    public boolean isTemSmartphone() {
        return temSmartphone;
    }

    public void setTemSmartphone(boolean temSmartphone) {
        this.temSmartphone = temSmartphone;
    }

    public SistemaOperacional getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

}
