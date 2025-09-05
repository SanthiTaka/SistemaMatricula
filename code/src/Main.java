import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Secretaria secretaria = new Secretaria("Secretaria Central", "sec", "123");
    private static PeriodoMatricula periodo;
    private static SistemaDeCobranca cobranca = new SistemaDeCobranca();

    public static void main(String[] args) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Abrir período de matrícula");
            System.out.println("2. Fechar período de matrícula");
            System.out.println("3. Cadastrar curso");
            System.out.println("4. Cadastrar professor");
            System.out.println("5. Cadastrar aluno");
            System.out.println("6. Cadastrar disciplina");
            System.out.println("7. Listar alunos");
            System.out.println("8. Listar professores");
            System.out.println("9. Listar disciplinas");
            System.out.println("10. Matricular aluno em disciplina");
            System.out.println("11. Cancelar matrícula");
            System.out.println("12. Gerar currículo de curso");
            System.out.println("13. Listar disciplinas ativas de um aluno");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());
            try {
                switch (opcao) {
                    case 1 -> abrirPeriodo();
                    case 2 -> fecharPeriodo();
                    case 3 -> cadastrarCurso();
                    case 4 -> cadastrarProfessor();
                    case 5 -> cadastrarAluno();
                    case 6 -> cadastrarDisciplina();
                    case 7 -> listarAlunos();
                    case 8 -> listarProfessores();
                    case 9 -> listarDisciplinas();
                    case 10 -> matricularAluno();
                    case 11 -> cancelarMatricula();
                    case 12 -> gerarCurriculo();
                    case 13 -> listarDisciplinasAluno();
                    case 0 -> executando = false;
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private static void abrirPeriodo() {
        System.out.println("Defina a duração do período em minutos: ");
        int minutos = Integer.parseInt(scanner.nextLine());
        periodo = new PeriodoMatricula(LocalDateTime.now(), LocalDateTime.now().plusMinutes(minutos));
        periodo.abrirPeriodo();
        System.out.println("✅ Período de matrícula aberto!");
    }

    private static void fecharPeriodo() {
        if (periodo != null) {
            periodo.fecharPeriodo();
            // verificar ativação das disciplinas
            secretaria.getDisciplinas().forEach(Disciplina::verificarAtivacaoAoFecharPeriodo);
            System.out.println("✅ Período de matrícula fechado.");
        } else {
            System.out.println("Nenhum período aberto.");
        }
    }

    private static void cadastrarCurso() {
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();
        System.out.print("Número de créditos: ");
        int creditos = Integer.parseInt(scanner.nextLine());
        Curso c = secretaria.criarCurso(nome, creditos);
        System.out.println("✅ Curso cadastrado: " + c);
    }

    private static void cadastrarProfessor() {
        System.out.print("ID do professor: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        Professor p = secretaria.cadastrarProfessor(id, nome, login, senha);
        System.out.println("✅ Professor cadastrado: " + p);
    }

    private static void cadastrarAluno() {
        System.out.print("ID do aluno: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        Aluno a = secretaria.cadastrarAluno(id, nome, login, senha);
        System.out.println("✅ Aluno cadastrado: " + a);
    }

    private static void cadastrarDisciplina() {
        if (secretaria.getProfessores().isEmpty()) {
            System.out.println("⚠ Cadastre primeiro um professor!");
            return;
        }
        if (secretaria.getDisciplinas().isEmpty() && secretaria.getProfessores().isEmpty()) {
            System.out.println("⚠ Cadastre primeiro professor e curso!");
            return;
        }
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome da disciplina: ");
        String nome = scanner.nextLine();
        System.out.print("Obrigatória? (s/n): ");
        boolean obrigatoria = scanner.nextLine().equalsIgnoreCase("s");

        System.out.println("Escolha professor: ");
        List<Professor> profs = secretaria.getProfessores();
        for (int i = 0; i < profs.size(); i++) {
            System.out.println(i + " - " + profs.get(i));
        }
        int idxProf = Integer.parseInt(scanner.nextLine());

        System.out.println("Escolha curso: ");
        List<Curso> cursos = secretaria.getCursos();
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println(i + " - " + cursos.get(i));
        }
        int idxCurso = Integer.parseInt(scanner.nextLine());

        Disciplina d = secretaria.cadastrarDisciplina(codigo, nome, obrigatoria, profs.get(idxProf), cursos.get(idxCurso));
        System.out.println("✅ Disciplina cadastrada: " + d);
    }

    private static void listarAlunos() {
        secretaria.getAlunos().forEach(System.out::println);
    }

    private static void listarProfessores() {
        secretaria.getProfessores().forEach(System.out::println);
    }

    private static void listarDisciplinas() {
        secretaria.getDisciplinas().forEach(System.out::println);
    }

    private static void matricularAluno() {
        if (periodo == null || !periodo.isAberto()) {
            System.out.println("⚠ Abra o período de matrícula primeiro!");
            return;
        }
        if (secretaria.getAlunos().isEmpty() || secretaria.getDisciplinas().isEmpty()) {
            System.out.println("⚠ Cadastre aluno e disciplina primeiro!");
            return;
        }
        System.out.println("Escolha aluno: ");
        List<Aluno> alunos = secretaria.getAlunos();
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i));
        }
        int idxAluno = Integer.parseInt(scanner.nextLine());

        System.out.println("Escolha disciplina: ");
        List<Disciplina> disciplinas = secretaria.getDisciplinas();
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.println(i + " - " + disciplinas.get(i));
        }
        int idxDisc = Integer.parseInt(scanner.nextLine());

        Aluno aluno = alunos.get(idxAluno);
        Disciplina disc = disciplinas.get(idxDisc);

        Matricula m = aluno.matricularDisciplina(disc, periodo, cobranca);
        System.out.println("✅ Matrícula criada: " + m);
    }

    private static void cancelarMatricula() {
        if (periodo == null || !periodo.isAberto()) {
            System.out.println("⚠ Período precisa estar aberto para cancelamento!");
            return;
        }
        System.out.println("Escolha aluno: ");
        List<Aluno> alunos = secretaria.getAlunos();
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i));
        }
        int idxAluno = Integer.parseInt(scanner.nextLine());
        Aluno aluno = alunos.get(idxAluno);

        List<Matricula> matriculas = aluno.getMatriculas();
        if (matriculas.isEmpty()) {
            System.out.println("⚠ Nenhuma matrícula encontrada.");
            return;
        }

        System.out.println("Escolha matrícula para cancelar: ");
        for (int i = 0; i < matriculas.size(); i++) {
            System.out.println(i + " - " + matriculas.get(i));
        }
        int idxMat = Integer.parseInt(scanner.nextLine());
        aluno.cancelarMatricula(matriculas.get(idxMat), periodo);
        System.out.println("✅ Matrícula cancelada.");
    }

    private static void gerarCurriculo() {
        System.out.println("Escolha curso: ");
        List<Curso> cursos = secretaria.getCursos();
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println(i + " - " + cursos.get(i));
        }
        int idxCurso = Integer.parseInt(scanner.nextLine());
        secretaria.gerarCurriculoDoSemestre(cursos.get(idxCurso));
    }

    private static void listarDisciplinasAluno() {
        System.out.println("Escolha aluno: ");
        List<Aluno> alunos = secretaria.getAlunos();
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i));
        }
        int idxAluno = Integer.parseInt(scanner.nextLine());
        Aluno aluno = alunos.get(idxAluno);
        aluno.listarDisciplinasAtivas().forEach(System.out::println);
    }
}
