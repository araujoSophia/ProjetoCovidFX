package Models;

public class ObitoModel {
    private String dataObito;
    private String cpfPaciente;

    public ObitoModel(String dataObito, String cpfPaciente) {
        this.dataObito = dataObito;
        this.cpfPaciente = cpfPaciente;
    }

    public String getDataObito() {
        return dataObito;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }
}
