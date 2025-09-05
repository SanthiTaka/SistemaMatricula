
import java.time.LocalDateTime;

public class Matricula {
    private Aluno aluno;
    private Disciplina disciplina;
    private LocalDateTime data;
    private MatriculaStatus status;
    private PeriodoMatricula periodo;

    public Matricula(Aluno aluno, Disciplina disciplina, PeriodoMatricula periodo) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.periodo = periodo;
        this.data = LocalDateTime.now();
        this.status = MatriculaStatus.ATIVA;
    }

    public Aluno getAluno() { return aluno; }
    public Disciplina getDisciplina() { return disciplina; }
    public LocalDateTime getData() { return data; }
    public MatriculaStatus getStatus() { return status; }
    public void setStatus(MatriculaStatus status) { this.status = status; }
    public PeriodoMatricula getPeriodo() { return periodo; }

    @Override
    public String toString() {
        return "Matricula{" + "aluno=" + aluno + ", disciplina=" + disciplina + ", status=" + status + '}';
    }
}
