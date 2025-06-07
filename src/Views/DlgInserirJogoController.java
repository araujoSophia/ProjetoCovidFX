package Views;

import Entities.JogoEntity;
import Services.CampeonatoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class DlgInserirJogoController {

    @FXML private ComboBox<String> cmbTimeCasa;
    @FXML private ComboBox<String> cmbTimeVisitante;
    @FXML private Spinner<Integer> spnGolsCasa;
    @FXML private Spinner<Integer> spnGolsVisitante;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    private CampeonatoService campeonatoService = new CampeonatoService();
    private boolean resposta = false;
    private Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public boolean isResposta() {
        return resposta;
    }

    @FXML
    public void initialize() {
        List<String> apelidos = campeonatoService.listarApelidos();
        cmbTimeCasa.getItems().addAll(apelidos);
        cmbTimeVisitante.getItems().addAll(apelidos);

        spnGolsCasa.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        spnGolsVisitante.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
    }

    @FXML
    private void salvar() {
        try {
            String apelidoCasa = cmbTimeCasa.getValue();
            String apelidoVisitante = cmbTimeVisitante.getValue();

            if (apelidoCasa == null || apelidoVisitante == null) {
                throw new Exception("Selecione ambos os times.");
            }

            if (apelidoCasa.equals(apelidoVisitante)) {
                throw new Exception("Os times não podem ser iguais.");
            }

            int golsCasa = spnGolsCasa.getValue();
            int golsVisitante = spnGolsVisitante.getValue();

            JogoEntity jogo = new JogoEntity(apelidoCasa, apelidoVisitante, golsCasa, golsVisitante);
            campeonatoService.inserirJogo(jogo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Jogo registrado com sucesso.", ButtonType.OK);
            alert.initOwner(dialogStage);
            alert.showAndWait();

            resposta = true;
            dialogStage.close();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK);
            alert.initOwner(dialogStage);
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelar() {
        resposta = false;
        dialogStage.close();
    }

    public boolean executar(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DlgInserirJogo.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            dialogStage = new Stage();
            dialogStage.setTitle("Inserir Jogo");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(owner);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Injeta o próprio stage
            setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return resposta;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método estático para facilitar a chamada sem precisar instanciar o controller manualmente
    public static boolean showDialog(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(DlgInserirJogoController.class.getResource("DlgInserirJogo.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Inserir Jogo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(owner);
            stage.setScene(scene);
            stage.setResizable(false);

            DlgInserirJogoController controller = loader.getController();
            controller.setDialogStage(stage);

            stage.showAndWait();

            return controller.isResposta();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
