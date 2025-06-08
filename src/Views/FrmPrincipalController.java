package Views;

import Models.PacienteModel;
import Models.TimeModel;
import Services.CampeonatoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FrmPrincipalController {

    @FXML
    private Button btnZerarCampeonato;
    @FXML
    private Button btnInserirTime;
    @FXML
    private Button btnInserirJogo;
    @FXML
    private Button btnPacientes;
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<TimeModel> tabelaTimes;
    @FXML
    private TableView<PacienteModel> tabelaPacientes;
    @FXML
    private TableColumn<TimeModel, Integer> colPosicao;
    @FXML
    private TableColumn<TimeModel, String> colApelido;
    @FXML
    private TableColumn<TimeModel, String> colNome;
    @FXML
    private TableColumn<TimeModel, Integer> colPontos;
    @FXML
    private TableColumn<TimeModel, Integer> colGP;
    @FXML
    private TableColumn<TimeModel, Integer> colGC;
    @FXML
    private TableColumn<TimeModel, Integer> colSG;
    @FXML
    private TableColumn<TimeModel, Void> colEditar;
    @FXML
    private TableColumn<TimeModel, Void> colRemover;

    private CampeonatoService campeonatoService = new CampeonatoService();

    @FXML
    private void initialize() {
        // Ligando colunas às propriedades do TimeModel
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colApelido.setCellValueFactory(new PropertyValueFactory<>("apelido"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));
        colGP.setCellValueFactory(new PropertyValueFactory<>("GolsMarcados"));
        colGC.setCellValueFactory(new PropertyValueFactory<>("GolsSofridos"));
        colSG.setCellValueFactory(new PropertyValueFactory<>("SaldoGols"));

        // Colunas de botão: "Editar" e "Remover"
        adicionarColunaBotao(colEditar, "Editar", this::editarTime);
        adicionarColunaBotao(colRemover, "Remover", this::removerTime);

        atualizarTabela();
    }

    private void atualizarTabela() {
        ObservableList<TimeModel> dados = FXCollections.observableArrayList(campeonatoService.obterClassificacao());
        tabelaTimes.setItems(dados);
    }

    private void adicionarColunaBotao(TableColumn<TimeModel, Void> coluna, String texto,
            java.util.function.Consumer<TimeModel> acao) {
        coluna.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button(texto);

            {
                btn.setOnAction(e -> {
                    TimeModel time = getTableView().getItems().get(getIndex());
                    acao.accept(time);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void editarTime(TimeModel time) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        DlgTimeController.showDialog(stage, time.getApelido());
        atualizarTabela();
    }

    private void removerTime(TimeModel time) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Remoção");
        confirm.setHeaderText("Remover time: " + time.getNome());
        confirm.setContentText("Tem certeza que deseja remover este time?");
        confirm.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                campeonatoService.removerTime(time.getApelido());
                atualizarTabela();
                mostrarInfo("Time removido com sucesso.");
            }
        });
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void acionou(javafx.event.ActionEvent event) {
        Object origem = event.getSource();
        try {
            if (origem == btnZerarCampeonato) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deseja reiniciar o campeonato?",
                        ButtonType.YES, ButtonType.NO);
                confirm.setTitle("Confirmar");
                confirm.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.YES) {
                        campeonatoService.reiniciarCampeonato();
                        atualizarTabela();
                        mostrarInfo("Campeonato zerado com sucesso.");
                    }
                });

            } else if (origem == btnInserirTime) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                if (DlgTimeController.showDialog(stage)) {
                    mostrarInfo("Time adicionado com sucesso!");
                    atualizarTabela();
                }

            } else if (origem == btnInserirJogo) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                if (DlgInserirJogoController.showDialog(stage)) {
                    atualizarTabela();
                    mostrarInfo("Jogo adicionado com sucesso.");
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage());
            alert.setTitle("Erro");
            alert.showAndWait();
        }
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
    private void abrirTestes() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Views.DlgTesteController.showDialog(stage);
    }

}
