package Views;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import Repositories.ObitoRepository;
import Repositories.TesteRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class FrmDashboardController implements Initializable {

    @FXML private BarChart<String, Number> graficoEvolucao;
    @FXML private Label lblMortalidade;
    @FXML private Label lblInfectadosPorcentagem;

    private final TesteRepository testeRepository = new TesteRepository();
    private final ObitoRepository obitoRepository = new ObitoRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarGraficoEvolucao();
        carregarTaxas();
    }

    private void carregarGraficoEvolucao() {
        Map<String, Integer> casosPorMes = testeRepository.contarPositivosPorMes();

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Infectados");

        casosPorMes.forEach((mes, total) -> {
            serie.getData().add(new XYChart.Data<>(mes, total));
        });

        graficoEvolucao.getData().add(serie);
    }

    private void carregarTaxas() {
        int infectados = testeRepository.contarPositivos();
        int obitos = obitoRepository.contar();
        int testados = testeRepository.contar();

        double taxaMortalidade = infectados > 0 ? ((double) obitos / infectados) * 100 : 0;
        double taxaInfectados = testados > 0 ? ((double) infectados / testados) * 100 : 0;

        lblMortalidade.setText(String.format("Taxa de Mortalidade: %.2f%%", taxaMortalidade));
        lblInfectadosPorcentagem.setText(String.format("Percentual de Infectados: %.2f%%", taxaInfectados));
    }
}
