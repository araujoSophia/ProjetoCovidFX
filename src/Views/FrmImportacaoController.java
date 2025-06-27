package Views;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.Scanner;

import Entities.ObitoEntity;
import Entities.PacienteEntity;
import Entities.TesteEntity;
import Services.CovidService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FrmImportacaoController {

    @FXML private Label lblMensagem;
    private final Stage stage = new Stage();
    private final CovidService covidService = new CovidService();

  public void importarPacientes() {
    File file = selecionarArquivo();
    if (file == null) return;

    try {
        if (file.getName().endsWith(".bin")) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    PacienteEntity paciente = (PacienteEntity) ois.readObject();
                    covidService.inserirPaciente(paciente);
                }
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] campos = linha.split(";");
                    if (campos.length >= 7) {
                        PacienteEntity paciente = new PacienteEntity();
                        paciente.setCpf(campos[0].trim());
                        paciente.setNome(campos[1].trim());
                        paciente.setDataNasc(campos[2].trim());
                        paciente.setIdade(Integer.parseInt(campos[3].trim()));
                        paciente.setEstado(campos[4].trim());
                        paciente.setCidade(campos[5].trim());
                        paciente.setFoto(campos[6].trim());

                        covidService.inserirPaciente(paciente);
                    }
                }
            }
        }

        lblMensagem.setText("✅ Pacientes importados com sucesso!");
    } catch (EOFException e) {
        lblMensagem.setText("✅ Fim do arquivo binário.");
    } catch (Exception e) {
        lblMensagem.setText("❌ Erro ao importar pacientes.");
        e.printStackTrace();
    }
}


   public void importarTestes() {
    File file = selecionarArquivo();
    if (file == null) return;

    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] campos = linha.split(";");

            if (campos.length >= 3) {
                TesteEntity teste = new TesteEntity();
                teste.setCpfPaciente(campos[0].trim());
                teste.setDataTeste(campos[1].trim());
                teste.setResultado(campos[2].trim());

                covidService.inserirTeste(teste);
            }
        }
        lblMensagem.setText("✅ Testes importados com sucesso!");
    } catch (Exception e) {
        lblMensagem.setText("❌ Erro ao importar testes.");
        e.printStackTrace();
        }
    }
    public void importarObitos() {
    File file = selecionarArquivo();
    if (file == null) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] campos = linha.split(";");

            if (campos.length >= 2) {
                ObitoEntity obito = new ObitoEntity();
                obito.setCpfPaciente(campos[0].trim());
                obito.setDataObito(campos[1].trim());

                covidService.inserirObito(obito);
            }
        }
        lblMensagem.setText("✅ Óbitos importados com sucesso!");
    } catch (Exception e) {
        lblMensagem.setText("❌ Erro ao importar óbitos.");
        e.printStackTrace();
    }
}

    private File selecionarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo");
        return fileChooser.showOpenDialog(stage);
    }
}
