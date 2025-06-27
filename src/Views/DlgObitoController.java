package Views;

import Entities.ObitoEntity;
import Services.CovidService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DlgObitoController {

    @FXML
    private TextField txtCpf;
    @FXML
    private DatePicker dateDataObito;

    private Stage dialogStage;
    private boolean resposta = false;

    private CovidService covidService = new CovidService();

    @FXML
    private void salvar() {
        try {
            String cpf = txtCpf.getText().trim();
            String data = (dateDataObito.getValue() != null) ? dateDataObito.getValue().toString() : null;

            if (cpf.isEmpty() || data == null) {
                mostrarAlerta("Preencha todos os campos!");
                return;
            }

            if (!covidService.pacienteExiste(cpf)) {
                mostrarAlerta("Paciente com esse CPF não encontrado!");
                return;
            }

            ObitoEntity obito = new ObitoEntity(cpf, data);
            covidService.inserirObito(obito);

            mostrarInfo("Óbito adicionado com sucesso!");

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

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
        stage.setTitle("Cadastro de Óbitos");
    }

    public boolean getResposta() {
        return resposta;
    }

    public static boolean showDialog(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(DlgObitoController.class.getResource("DlgObito.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);

            DlgObitoController controller = loader.getController();
            controller.setDialogStage(stage);

            stage.showAndWait();
            return controller.getResposta();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
