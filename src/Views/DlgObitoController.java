package Views;

import Entities.ObitoEntity;
import Services.CampeonatoService;
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

    private CampeonatoService campeonatoService = new CampeonatoService();

    @FXML
    private void salvar() {
        try {
            String cpf = txtCpf.getText().trim();
            String data = (dateDataObito.getValue() != null) ? dateDataObito.getValue().toString() : null;

            if (cpf.isEmpty() || data == null) {
                mostrarAlerta("Preencha todos os campos!");
                return;
            }

            if (!campeonatoService.pacienteExiste(cpf)) {
                mostrarAlerta("Paciente com esse CPF não encontrado!");
                return;
            }

            ObitoEntity obito = new ObitoEntity(cpf, data);
            campeonatoService.inserirObito(obito);

            resposta = true;
            dialogStage.close();

        } catch (Exception e) {
            mostrarAlerta("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        dialogStage.close();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
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
