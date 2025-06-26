package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Repositories.ObitoRepository;
import Repositories.PacienteRepository;
import Repositories.TesteRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class FrmGraficoController implements Initializable {

    @FXML
    private PieChart pieChart;

    private final PacienteRepository PacienteRepository = new PacienteRepository();
    private final TesteRepository TesteRepository = new TesteRepository();
    private final ObitoRepository ObitoRepository = new ObitoRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int totalPacientes = PacienteRepository.contar();
        int totalTestados = TesteRepository.contar();
        int totalInfectados = TesteRepository.contarPositivos();
        int totalObitos = ObitoRepository.contar();

        pieChart.getData().addAll(
        new PieChart.Data("Pacientes: " + totalPacientes, totalPacientes),
        new PieChart.Data("Testados: " + totalTestados, totalTestados),
        new PieChart.Data("Infectados: " + totalInfectados, totalInfectados),
        new PieChart.Data("Óbitos: " + totalObitos, totalObitos)
    );
    }
}
