package edu.univ.matriculas.dominio;

import java.util.Objects;

public abstract class Usuario {
    private final long id;
    private String nome;
    private String login;
    private String senhaHash;

    protected Usuario(long id, String nome, String login, String senhaHash) {
        this.id = id;
        this.nome = Objects.requireNonNull(nome);
        this.login = Objects.requireNonNull(login);
        this.senhaHash = Objects.requireNonNull(senhaHash);
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }

    public boolean autenticar(String senhaPura) {
        return senhaHash.equals(hashSimples(senhaPura));
    }

    public void trocarSenha(String senhaNova) {
        this.senhaHash = hashSimples(Objects.requireNonNull(senhaNova));
    }

    protected static String hashSimples(String s) {
        // hashing didática (NÃO use em produção)
        return Integer.toHexString(s.hashCode());
    }

    @Override public String toString() { return "%s(%s)".formatted(getClass().getSimpleName(), nome); }
}
