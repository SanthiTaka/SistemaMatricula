
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String codigo;
    private String nome;
    private boolean obrigatoria; // true = obrigatória, false = optativa
    private Professor professor;
    private List<Matricula> matriculas;
    private final int maxAlunos = 60;
    private final int minAlunos = 3;
    private boolean ativa = true; // por padrão

    public Disciplina(String codigo, String nome, boolean obrigatoria, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.obrigatoria = obrigatoria;
        this.professor = professor;
        this.matriculas = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public boolean isObrigatoria() { return obrigatoria; }
    public Professor getProfessor() { return professor; }
    public List<Matricula> getMatriculas() { return matriculas; }
    public boolean isAtiva() { return ativa; }

    public boolean possuiVaga() {
        long ativas = matriculas.stream().filter(m -> m.getStatus() == MatriculaStatus.ATIVA).count();
        return ativas < maxAlunos;
    }

    public void adicionarMatricula(Matricula m) {
        if (!matriculas.contains(m)) {
            matriculas.add(m);
        }
    }

    public void removerMatricula(Matricula m) {
        matriculas.remove(m);
    }

    /**
     * Verifica ativação ao final do período: se tem >= minAlunos mantém ativa,
     * caso contrário marca como inativa (cancelada).
     */
    public void verificarAtivacaoAoFecharPeriodo() {
        long ativas = matriculas.stream().filter(m -> m.getStatus() == MatriculaStatus.ATIVA).count();
        if (ativas < minAlunos) {
            this.ativa = false;
        } else {
            this.ativa = true;
        }
    }

    @Override
    public String toString() {
        return "Disciplina{" + "codigo='" + codigo + '\'' + ", nome='" + nome + '\'' + ", obrigatoria=" + obrigatoria + '}';
    }
}
