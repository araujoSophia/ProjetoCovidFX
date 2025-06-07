package Entities;

public class JogoEntity {
    private String apelidoTimeCasa;
    private String apelidoTimeVisitante;
    private int golsTimeCasa;
    private int golsTimeVisitante;

    public JogoEntity() {}

    public JogoEntity(String apelidoTimeCasa, String apelidoTimeVisitante, int golsTimeCasa, int golsTimeVisitante) {
        this.apelidoTimeCasa = apelidoTimeCasa;
        this.apelidoTimeVisitante = apelidoTimeVisitante;
        this.golsTimeCasa = golsTimeCasa;
        this.golsTimeVisitante = golsTimeVisitante;
    }

    public String getApelidoTimeCasa() {
        return apelidoTimeCasa;
    }

    public void setApelidoTimeCasa(String apelidoTimeCasa) {
        this.apelidoTimeCasa = apelidoTimeCasa;
    }

    public String getApelidoTimeVisitante() {
        return apelidoTimeVisitante;
    }

    public void setApelidoTimeVisitante(String apelidoTimeVisitante) {
        this.apelidoTimeVisitante = apelidoTimeVisitante;
    }

    public int getGolsTimeCasa() {
        return golsTimeCasa;
    }

    public void setGolsTimeCasa(int golsTimeCasa) {
        this.golsTimeCasa = golsTimeCasa;
    }

    public int getGolsTimeVisitante() {
        return golsTimeVisitante;
    }

    public void setGolsTimeVisitante(int golsTimeVisitante) {
        this.golsTimeVisitante = golsTimeVisitante;
    }
}
