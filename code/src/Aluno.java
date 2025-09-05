
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Aluno extends Usuario {

    private String idAluno;
    private String nome;
    private List<Matricula> matriculas;

    public Aluno(String idAluno, String nome, String login, String senha) {
        super(login, senha);
        this.idAluno = idAluno;
        this.nome = nome;
        this.matriculas = new ArrayList<>();
    }

    public String getIdAluno() {
        return idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public Matricula matricularDisciplina(Disciplina disciplina, PeriodoMatricula periodo, SistemaDeCobranca cobranca) {
        if (!periodo.isAberto()) {
            throw new IllegalStateException("Período de matrícula fechado.");
        }

        boolean jaMatriculado = matriculas.stream()
                .filter(m -> m.getDisciplina().equals(disciplina))
                .anyMatch(m -> m.getStatus() == MatriculaStatus.ATIVA);
        if (jaMatriculado) {
            throw new IllegalStateException("Aluno já matriculado nesta disciplina.");
        }

        long obrigatoriasAtivas = matriculas.stream()
                .filter(m -> m.getStatus() == MatriculaStatus.ATIVA)
                .filter(m -> m.getDisciplina().isObrigatoria())
                .count();
        long optativasAtivas = matriculas.stream()
                .filter(m -> m.getStatus() == MatriculaStatus.ATIVA)
                .filter(m -> !m.getDisciplina().isObrigatoria())
                .count();

        if (disciplina.isObrigatoria() && obrigatoriasAtivas >= 4) {
            throw new IllegalStateException("Limite de 4 disciplinas obrigatórias atingido.");
        }
        if (!disciplina.isObrigatoria() && optativasAtivas >= 2) {
            throw new IllegalStateException("Limite de 2 disciplinas optativas atingido.");
        }

        synchronized (disciplina) {
            if (!disciplina.possuiVaga()) {
                throw new IllegalStateException("Disciplina sem vagas.");
            }
            ''
            Matricula matricula = new Matricula(this, disciplina, periodo);
            matriculas.add(matricula);
            disciplina.adicionarMatricula(matricula);
            // Notificar cobrança
            if (cobranca != null) {
                cobranca.notificarAluno(this, matricula);
            }
            return matricula;
        }
    }

    public void cancelarMatricula(Matricula matricula, PeriodoMatricula periodo) {
        if (!periodo.isAberto()) {
            throw new IllegalStateException("Só é possível cancelar durante o período de matrícula.");
        }
        if (!matriculas.contains(matricula)) {
            throw new IllegalArgumentException("Matrícula não encontrada para este aluno.");
        }
        matricula.setStatus(MatriculaStatus.CANCELADA);
        matricula.getDisciplina().removerMatricula(matricula);
    }

    public List<Disciplina> listarDisciplinasAtivas() {
        return matriculas.stream()
                .filter(m -> m.getStatus() == MatriculaStatus.ATIVA)
                .map(Matricula::getDisciplina)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Aluno{" + "idAluno='" + idAluno + '\'' + ", nome='" + nome + '\'' + '}';
    }
}
