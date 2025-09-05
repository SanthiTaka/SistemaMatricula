
import br.universidade.model.Aluno;
import br.universidade.model.Matricula;

public class SistemaDeCobranca {
    /**
     * Notifica o sistema de cobrança sobre a matrícula do aluno.
     * Aqui é uma simulação (imprime no console). Em produção faria uma chamada HTTP, fila, etc.
     */
    public void notificarAluno(Aluno aluno, Matricula matricula) {
        System.out.println("Notificando sistema de cobrança: aluno=" + aluno.getIdAluno()
                + ", disciplina=" + matricula.getDisciplina().getCodigo()
                + ", data=" + matricula.getData());
    }
}
