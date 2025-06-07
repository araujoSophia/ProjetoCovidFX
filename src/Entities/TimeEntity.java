package Entities;

public class TimeEntity {
    private int id;
    private String apelido;
    private String nome;
    private int pontos;
    private int golsMarcados;
    private int golsSofridos;

    public TimeEntity() {}

    public TimeEntity(int id, String apelido, String nome, int pontos, int golsMarcados, int golsSofridos) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.pontos = pontos;
        this.golsMarcados = golsMarcados;
        this.golsSofridos = golsSofridos;
    }
    
    public TimeEntity(String apelido, String nome) {
        this.apelido = apelido;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public void setGolsMarcados(int golsMarcados) {
        this.golsMarcados = golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public void setGolsSofridos(int golsSofridos) {
        this.golsSofridos = golsSofridos;
    }

    public int getSaldoGols() {
        return golsMarcados - golsSofridos;
    }
}
