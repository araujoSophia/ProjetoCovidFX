package Views;

import Entities.TesteEntity;
import Services.CovidService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;

public class DlgTesteController {

    @FXML
    private DatePicker dateDataTeste;
    @FXML
    private TextField txtCpf;
    @FXML
    private ComboBox<String> cmbResultado;

    private Stage dialogStage;
    private boolean resposta = false;

    private CovidService covidService = new CovidService();

    @FXML
    private void initialize() {
        cmbResultado.getItems().addAll("Positivo", "Negativo");
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void salvar() {
        try {
            String cpf = txtCpf.getText().trim();
            String resultado = cmbResultado.getValue();
            String data = (dateDataTeste.getValue() != null) ? dateDataTeste.getValue().toString() : null;

            if (cpf.isEmpty() || resultado == null || data == null) {
                mostrarAlerta("Preencha todos os campos!");
                return;
            }

            if (!covidService.pacienteExiste(cpf)) {
                mostrarAlerta("Paciente com esse CPF não encontrado!");
                return;
            }

            TesteEntity teste = new TesteEntity(cpf, data, resultado);
            covidService.inserirTeste(teste);

            mostrarInfo("Teste adicionado com sucesso!");

            resposta = true;
            dialogStage.close();

        } catch (Exception e) {
            mostrarErro("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        dialogStage.close();
    }

    public boolean getResposta() {
        return resposta;
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.NONE, mensagem, ButtonType.OK);
        alert.setTitle("Sucesso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensagem, ButtonType.OK);
        alert.setTitle("Atenção");
        alert.showAndWait();
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle("Erro");
        alert.showAndWait();
    }

    // Método estático para abrir o dialog
    public static boolean showDialog(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(DlgTesteController.class.getResource("DlgTeste.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.setTitle("Cadastro de Testes");

            DlgTesteController controller = loader.getController();
            controller.setDialogStage(stage);

            stage.showAndWait();
            return controller.getResposta();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
