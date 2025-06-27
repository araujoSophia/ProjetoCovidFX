package Views;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import Repositories.ObitoRepository;
import Repositories.PacienteRepository;
import Repositories.TesteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FrmExportacaoController {

    @FXML private Label lblStatus;
    private final Stage stage = new Stage();

    private final PacienteRepository pacienteRepo = new PacienteRepository();
    private final TesteRepository testeRepo = new TesteRepository();
    private final ObitoRepository obitoRepo = new ObitoRepository();

    public void exportarCSV() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Salvar CSV");
        chooser.setInitialFileName("estatisticas.csv");
        File file = chooser.showSaveDialog(stage);

        if (file == null) return;

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            int totalPacientes = pacienteRepo.contar();
            int testados = testeRepo.contar();
            int infectados = testeRepo.contarPositivos();
            int obitos = obitoRepo.contar();

            out.println("Tipo,Quantidade");
            out.printf("Total de Pacientes,%d%n", totalPacientes);
            out.printf("Pacientes Testados,%d%n", testados);
            out.printf("Pacientes Infectados,%d%n", infectados);
            out.printf("Óbitos,%d%n", obitos);

            out.println();
            out.println("Mês/Ano,Casos Positivos");

            Map<String, Integer> porMes = testeRepo.contarPositivosPorMes();
            for (Map.Entry<String, Integer> entry : porMes.entrySet()) {
                out.printf("%s,%d%n", entry.getKey(), entry.getValue());
            }

            lblStatus.setText("✅ CSV exportado com sucesso.");
        } catch (Exception e) {
            lblStatus.setText("❌ Falha ao exportar CSV.");
            e.printStackTrace();
        }
    }
}
