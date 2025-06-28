package Views;

import Models.TesteModel;
import Services.CovidService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FrmTestesController {

    @FXML
    private TableView<TesteModel> tabelaTestes;

    @FXML
    private TableColumn<TesteModel, String> colData;

    @FXML
    private TableColumn<TesteModel, String> colCpfPaciente;

    @FXML
    private TableColumn<TesteModel, String> colResultado;

    private CovidService covidService = new CovidService();

    @FXML
    private void initialize() {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCpfPaciente.setCellValueFactory(new PropertyValueFactory<>("cpfPaciente"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        ObservableList<TesteModel> dados = FXCollections.observableArrayList(covidService.obterTestesParaTabela());
        tabelaTestes.setItems(dados);
    }

    
}
