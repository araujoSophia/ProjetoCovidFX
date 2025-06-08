package Views;

import Entities.PacienteEntity;
import Services.CovidService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DlgPacienteController {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private DatePicker txtDataNasc;
    @FXML
    private TextField txtIdade;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtCidade;
    @FXML
    private ImageView imgFoto;
    @FXML
    private String caminhoFotoSelecionada;
    @FXML
    private Button btnSelecionarFoto;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    private CovidService covidService = new CovidService();
    private PacienteEntity pacienteExistente;
    private boolean resposta = false;

    private Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setPacienteExistente(String cpf) throws Exception {
        this.pacienteExistente = cpf == null ? null : covidService.buscarPacientePorCpf(cpf);
        if (pacienteExistente != null) {
            txtCpf.setText(pacienteExistente.getCpf());
            // txtCpf.setDisable(true); // Não permitir editar cpf em edição
            txtCpf.setText(pacienteExistente.getCpf());
            dialogStage.setTitle("Editar cpf");
        } else {
            dialogStage.setTitle("Inserir Paciente");
        }
    }

    public boolean getResposta() {
        return resposta;
    }

    @FXML
    private void onSelecionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Foto");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        File arquivo = fileChooser.showOpenDialog(null);

        if (arquivo != null) {
            caminhoFotoSelecionada = arquivo.getAbsolutePath();
            Image imagem = new Image(arquivo.toURI().toString());
            imgFoto.setImage(imagem);
        }
    }

    @FXML
    private void handleSalvar() {
        try {
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            String estado = txtEstado.getText().trim();
            String cidade = txtCidade.getText().trim();

            String dataNasc = "";
            if (txtDataNasc.getValue() != null) {
                dataNasc = txtDataNasc.getValue().toString(); // Isso vem no formato "yyyy-MM-dd"
            }

            String idadeStr = txtIdade.getText().trim();
            int idade;
            String foto = caminhoFotoSelecionada;

            if (cpf.isEmpty() || nome.isEmpty() || dataNasc.isEmpty() || idadeStr.isEmpty() || estado.isEmpty()
                    || cidade.isEmpty() || foto == null) {
                mostrarErro("Todos os campos devem ser preenchidos, incluindo a foto.");
                return;
            }

            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 0 || idade > 130) {
                    mostrarErro("Idade inválida! Deve ser um número entre 0 e 130.");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarErro("Idade inválida! Deve ser um número inteiro.");
                return;
            }

            if (pacienteExistente == null) {
                PacienteEntity novoPaciente = new PacienteEntity();
                novoPaciente.setCpf(cpf);
                novoPaciente.setNome(nome);
                novoPaciente.setDataNasc(dataNasc);
                novoPaciente.setIdade(idade);
                novoPaciente.setEstado(estado);
                novoPaciente.setCidade(cidade);
                novoPaciente.setFoto(foto);
                covidService.inserirPaciente(novoPaciente);

            } else {
                pacienteExistente.setNome(nome);
                covidService.editarPaciente(pacienteExistente);
            }
            resposta = true;
            dialogStage.close();
        } catch (Exception ex) {
            // Aqui você pode exibir diálogo de erro
            mostrarErro("Erro ao criar paciente! Tente Novamente!");
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        resposta = false;
        dialogStage.close();
    }

    // Método para abrir a janela (modal), como o showDialog do Swing
    public static boolean showDialog(Stage owner, String cpf) {
        try {
            FXMLLoader loader = new FXMLLoader(DlgPacienteController.class.getResource("DlgPaciente.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(owner);
            stage.setScene(scene);
            stage.setResizable(false);

            DlgPacienteController controller = loader.getController();
            controller.setDialogStage(stage);

            try {
                controller.setPacienteExistente(cpf);
            } catch (Exception e) {
                e.printStackTrace();
            }

            stage.showAndWait();
            return controller.getResposta();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sobrecarga para inserir novo paciente
    public static boolean showDialog(Stage owner) {
        return showDialog(owner, null);
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro no Cadastro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
