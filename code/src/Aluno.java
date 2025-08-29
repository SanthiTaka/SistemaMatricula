package edu.univ.matriculas.dominio;

import java.util.*;

public class Aluno extends Usuario {
    private final String matricula;
    private Curso curso;
    private final Set<Disciplina> obrigatorias = new HashSet<>();
    private final Set<Disciplina> optativas = new HashSet<>();

    public Aluno(long id, String nome, String login, String senha, String matricula, Curso curso) {
        super(id, nome, login, hashSimples(senha));
        this.matricula = Objects.requireNonNull(matricula);
        this.curso = Objects.requireNonNull(curso);
    }

    public String getMatricula() { return matricula; }
    public Curso getCurso() { return curso; }
    public Set<Disciplina> getObrigatorias() { return Collections.unmodifiableSet(obrigatorias); }
    public Set<Disciplina> getOptativas() { return Collections.unmodifiableSet(optativas); }

    void adicionaInterno(Disciplina d) {
        if (d.getTipo() == TipoDisciplina.OBRIGATORIA) obrigatorias.add(d);
        else optativas.add(d);
    }
    void removeInterno(Disciplina d) {
        obrigatorias.remove(d);
        optativas.remove(d);
    }

    public int totalMatriculas() { return obrigatorias.size() + optativas.size(); }
}
