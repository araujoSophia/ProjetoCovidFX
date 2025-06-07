package Views;

import Entities.TimeEntity;
import Services.CampeonatoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;



import java.io.IOException;

public class DlgTimeController {

    @FXML private TextField txtApelido;
    @FXML private TextField txtNome;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    private CampeonatoService campeonatoService = new CampeonatoService();
    private TimeEntity timeExistente;
    private boolean resposta = false;

    private Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setTimeExistente(String apelido) {
        this.timeExistente = apelido == null ? null : campeonatoService.buscarTime(apelido);
        if (timeExistente != null) {
            txtApelido.setText(timeExistente.getApelido());
            txtApelido.setDisable(true);  // Não permitir editar apelido em edição
            txtNome.setText(timeExistente.getNome());
            dialogStage.setTitle("Editar Time");
        } else {
            dialogStage.setTitle("Inserir Time");
        }
    }

    public boolean getResposta() {
        return resposta;
    }

    @FXML
    private void handleSalvar() {
        try {
            String apelido = txtApelido.getText().trim();
            String nome = txtNome.getText().trim();

            if (apelido.isEmpty() || nome.isEmpty()) {
                throw new Exception("Preencha todos os campos.");
            }

            if (timeExistente == null) {
                campeonatoService.inserirTime(new TimeEntity(0, apelido, nome, 0, 0, 0));
            } else {
                timeExistente.setNome(nome);
                campeonatoService.editarTime(timeExistente);
            }
            resposta = true;
            dialogStage.close();
        } catch (Exception ex) {
            // Aqui você pode exibir diálogo de erro
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        resposta = false;
        dialogStage.close();
    }

    // Método para abrir a janela (modal), como o showDialog do Swing
    public static boolean showDialog(Stage owner, String apelido) {
        try {
            FXMLLoader loader = new FXMLLoader(DlgTimeController.class.getResource("DlgTime.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(owner);
            stage.setScene(scene);
            stage.setResizable(false);

            DlgTimeController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setTimeExistente(apelido);

            stage.showAndWait();
            return controller.getResposta();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sobrecarga para inserir novo time
    public static boolean showDialog(Stage owner) {
        return showDialog(owner, null);
    }
}
