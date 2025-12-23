package br.edu.infnet.kauaapi.model;

public abstract class Pessoa {

    private int id;
    private String nome;
    protected String email;
    String telefone;

    public Pessoa() {
    }

    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Pessoa(int id, String nome, String email, String telefone) {
        this(id, nome);
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public abstract void exibirInformacoes();

    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("%s[%d] %s - Email: %s - Telefone: %s",
                getTipo(), id, nome,
                email != null ? email : "N/A",
                telefone != null ? telefone : "N/A");
    }
}
