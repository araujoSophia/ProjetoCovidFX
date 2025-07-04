package Views;

import Models.ObitoModel;
import Services.CovidService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FrmObitosController {

    @FXML
    private TableView<ObitoModel> tabelaObitos;
    @FXML
    private TableColumn<ObitoModel, String> colData;
    @FXML
    private TableColumn<ObitoModel, String> colCpf;

    private CovidService covidService = new CovidService();

    @FXML
    private void initialize() {
        colData.setCellValueFactory(new PropertyValueFactory<>("dataObito"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpfPaciente"));

        carregarTabela();
    }

    private void carregarTabela() {
        ObservableList<ObitoModel> dados = FXCollections.observableArrayList(
                covidService.obterObitosParaTabela());
        tabelaObitos.setItems(dados);
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) tabelaObitos.getScene().getWindow();
        stage.close();
    }
}
