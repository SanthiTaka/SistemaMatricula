
import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario {

    private String nome;
    private List<Curso> cursos;
    private List<Disciplina> disciplinas;
    private List<Professor> professores;
    private List<Aluno> alunos;

    public Secretaria(String nome, String login, String senha) {
        super(login, senha);
        this.nome = nome;
        this.cursos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.alunos = new ArrayList<>();
    }

    public Curso criarCurso(String nomeCurso, int numeroCreditos) {
        Curso c = new Curso(nomeCurso, numeroCreditos);
        cursos.add(c);
        return c;
    }

    public Disciplina cadastrarDisciplina(String codigo, String nome, boolean obrigatoria, Professor professor, Curso curso) {
        Disciplina d = new Disciplina(codigo, nome, obrigatoria, professor);
        disciplinas.add(d);
        if (curso != null) {
            curso.adicionarDisciplina(d);
        }
        return d;
    }

    public Professor cadastrarProfessor(String idProfessor, String nome, String login, String senha) {
        Professor p = new Professor(idProfessor, nome, login, senha);
        professores.add(p);
        return p;
    }

    public Aluno cadastrarAluno(String idAluno, String nome, String login, String senha) {
        Aluno a = new Aluno(idAluno, nome, login, senha);
        alunos.add(a);
        return a;
    }

    public void gerarCurriculoDoSemestre(Curso curso) {
        System.out.println("Currículo do curso " + curso.getNome());
        curso.getDisciplinas().forEach(d -> System.out.println(d));
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

}
