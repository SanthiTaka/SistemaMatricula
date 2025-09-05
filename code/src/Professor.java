
import java.util.List;
import java.util.stream.Collectors;

public class Professor extends Usuario {
    private String idProfessor;
    private String nome;

    public Professor(String idProfessor, String nome, String login, String senha) {
        super(login, senha);
        this.idProfessor = idProfessor;
        this.nome = nome;
    }

    public String getIdProfessor() { 
        return idProfessor; 
    }

    public String getNome() { 
        return nome; 
    }

    public void setNome(String nome) { 
        this.nome = nome; 
    }

    /**
     * Retorna os alunos matriculados em uma disciplina (somente alunos com matrícula ATIVA).
     */
    public List<Aluno> consultarAlunosMatriculados(Disciplina disciplina) {
        return disciplina.getMatriculas().stream()
                .filter(m -> m.getStatus() == MatriculaStatus.ATIVA)
                .map(Matricula::getAluno)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Professor{" + "idProfessor='" + idProfessor + '\'' + ", nome='" + nome + '\'' + '}';
    }
}
