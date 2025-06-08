package Entities;

public class TesteEntity {
    private String cpfPaciente;
    private String dataTeste;
    private String resultado;

    public TesteEntity() {
    }

    public TesteEntity(String cpfPaciente, String dataTeste, String resultado) {
        this.cpfPaciente = cpfPaciente;
        this.dataTeste = dataTeste;
        this.resultado = resultado;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getDataTeste() {
        return dataTeste;
    }

    public void setDataTeste(String dataTeste) {
        this.dataTeste = dataTeste;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
