package Entities;

public class ObitoEntity {
    private String cpfPaciente;
    private String dataObito;

    public ObitoEntity() {
    }

    public ObitoEntity(String cpfPaciente, String dataObito) {
        this.cpfPaciente = cpfPaciente;
        this.dataObito = dataObito;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getDataObito() {
        return dataObito;
    }

    public void setDataObito(String dataObito) {
        this.dataObito = dataObito;
    }

}
