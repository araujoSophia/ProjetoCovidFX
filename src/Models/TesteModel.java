package Models;

public class TesteModel {
    private String data;
    private String cpfPaciente;
    private String resultado;

    public TesteModel(String data, String cpfPaciente, String resultado) {
        this.data = data;
        this.cpfPaciente = cpfPaciente;
        this.resultado = resultado;
    }

    public String getData() {
        return data;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public String getResultado() {
        return resultado;
    }
}
