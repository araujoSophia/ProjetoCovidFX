import Views.SplashFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SplashFX splash = new SplashFX();
        splash.showSplash(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Views/FrmPrincipal.fxml"));
                primaryStage.setTitle("Sistema de Cadastro de Ocorrência de Covid");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

