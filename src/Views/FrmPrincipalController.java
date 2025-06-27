package Views;

import java.io.IOException;

import Models.PacienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FrmPrincipalController {

    @FXML
    private Button btnPacientes;
    @FXML
    private Button btnTestes;
    @FXML
    private Button btnListarTestes;
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<PacienteModel> tabelaPacientes;

    @FXML
    private void abrirTelaSobre() {
        Views.TelaSobre tela = new Views.TelaSobre();
        tela.setVisible(true);
    }

    @FXML
    private void abrirPacientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmPacientes.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Pacientes");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirCadastroTestes() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Views.DlgTesteController.showDialog(stage);
    }

    public void abrirListagemTestes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmTestes.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initOwner(rootPane.getScene().getWindow());
            stage.setTitle("Listagem de Testes");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirCadastroObitos() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Views.DlgObitoController.showDialog(stage);
    }

    @FXML
    private void abrirListagemObitos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrmObitos.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initOwner(rootPane.getScene().getWindow());
            stage.setTitle("Listagem de Óbitos");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
private void abrirGrafico(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FrmGrafico.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Gráfico COVID");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
@FXML
private void abrirDashboard(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FrmDashboard.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Dashboard COVID");
        stage.setScene(new Scene(root));
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            }
    }
    @FXML
private void abrirImportacao(ActionEvent event) {
    abrirTela("/views/FrmImportacao.fxml", "Importar Dados");
}

@FXML
private void abrirExportacao(ActionEvent event) {
    abrirTela("/views/FrmExportacao.fxml", "Exportar Estatísticas");
}

private void abrirTela(String fxml, String titulo) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}