package Views;

import Models.PacienteModel;
import Services.CovidService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FrmPacientesController {

    @FXML
    private Button btnInserirPaciente;
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<PacienteModel> tabelaPacientes;
    @FXML
    private TableColumn<PacienteModel, Integer> colPosicao;
    @FXML
    private TableColumn<PacienteModel, String> colCpf;
    @FXML
    private TableColumn<PacienteModel, String> colNome;
    @FXML
    private TableColumn<PacienteModel, String> colIdade;
    @FXML
    private TableColumn<PacienteModel, String> colCidade;
    @FXML
    private TableColumn<PacienteModel, String> colEstado;
    @FXML
    private TableColumn<PacienteModel, String> colDataNasc;
    @FXML
    private TableColumn<PacienteModel, Void> colEditar;
    @FXML
    private TableColumn<PacienteModel, Void> colRemover;
    @FXML
    private TextField txtFiltro;

    private CovidService covidService = new CovidService();

    @FXML
    private void initialize() {
        // Ligando colunas às propriedades do PacienteModel
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));

        // Colunas de botão: "Editar" e "Remover"
        adicionarColunaBotao(colEditar, "Editar", this::editarPaciente);
        adicionarColunaBotao(colRemover, "Remover", this::removerPaciente);

        atualizarTabela();
    }

    private void atualizarTabela() {
        ObservableList<PacienteModel> dados = FXCollections
                .observableArrayList(covidService.obterPacientesParaTabela());
        tabelaPacientes.setItems(dados);
    }

    @FXML
    private void filtrarPacientes() {
        String filtro = txtFiltro.getText().trim().toLowerCase();

        if (filtro.isEmpty()) {
            mostrarAlerta("Por favor, digite algo para filtrar.");
            return;
        }

        ObservableList<PacienteModel> listaFiltrada = FXCollections.observableArrayList();

        for (PacienteModel paciente : FXCollections.observableArrayList(covidService.obterPacientesParaTabela())) {
            if (paciente.getNome().toLowerCase().contains(filtro) ||
                    paciente.getCpf().toLowerCase().contains(filtro) ||
                    paciente.getCidade().toLowerCase().contains(filtro) ||
                    paciente.getEstado().toLowerCase().contains(filtro) ||
                    paciente.getDataNasc().contains(filtro)) {
                listaFiltrada.add(paciente);
            }
        }

        tabelaPacientes.setItems(listaFiltrada);
    }

    @FXML
    private void mostrarTodosPacientes() {
        atualizarTabela();
    }

    private void adicionarColunaBotao(TableColumn<PacienteModel, Void> coluna, String texto,
            java.util.function.Consumer<PacienteModel> acao) {
        coluna.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button(texto);

            {
                btn.setOnAction(e -> {
                    PacienteModel paciente = getTableView().getItems().get(getIndex());
                    acao.accept(paciente);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void editarPaciente(PacienteModel paciente) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        try {
            boolean resultado = DlgPacienteController.showDialog(stage, paciente.getCpf());
            if (resultado) {
                mostrarInfo("Paciente editado com sucesso!");
                atualizarTabela();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro: " + e.getMessage());
        }
    }

    private void removerPaciente(PacienteModel paciente) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Remoção");
        confirm.setHeaderText("Remover paciente: " + paciente.getNome());
        confirm.setContentText("Tem certeza que deseja remover este paciente?");
        confirm.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                try {
                    covidService.removerPaciente(paciente.getCpf());
                    atualizarTabela();
                    mostrarInfo("Paciente removido com sucesso.");
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarErro("Erro ao remover paciente: " + e.getMessage());
                }
            }
        });
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.NONE, mensagem, ButtonType.OK);
        alert.setTitle("Sucesso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem, ButtonType.OK);
        alert.setTitle("Atenção");
        alert.showAndWait();
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle("Erro");
        alert.showAndWait();
    }

    @FXML
    private void acionou(javafx.event.ActionEvent event) {
        Object origem = event.getSource();
        try {
            if (origem == btnInserirPaciente) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                if (DlgPacienteController.showDialog(stage)) {
                    mostrarInfo("Paciente adicionado com sucesso!");
                    atualizarTabela();
                }

            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage());
            alert.setTitle("Erro");
            alert.showAndWait();
        }
    }
}
