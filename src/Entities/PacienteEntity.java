package Entities;

public class PacienteEntity {
    private String cpf;
    private String nome;
    private String dataNasc;
    private int idade;
    private String estado;
    private String cidade;
    private String foto; // Caminho da foto ou blob no banco, depois decidimos.

    public PacienteEntity() {
    }

    public PacienteEntity(String cpf, String nome, String dataNasc, int idade, String estado, String cidade,
            String foto) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.idade = idade;
        this.estado = estado;
        this.cidade = cidade;
        this.foto = foto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
