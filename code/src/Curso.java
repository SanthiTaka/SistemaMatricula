
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int numeroCreditos;
    private List<Disciplina> disciplinas;

    public Curso(String nome, int numeroCreditos) {
        this.nome = nome;
        this.numeroCreditos = numeroCreditos;
        this.disciplinas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public int getNumeroCreditos() { return numeroCreditos; }
    public List<Disciplina> getDisciplinas() { return disciplinas; }

    public void adicionarDisciplina(Disciplina d) {
        if (!disciplinas.contains(d)) {
            disciplinas.add(d);
        }
    }

    @Override
    public String toString() {
        return "Curso{" + "nome='" + nome + '\'' + ", numeroCreditos=" + numeroCreditos + '}';
    }
}
