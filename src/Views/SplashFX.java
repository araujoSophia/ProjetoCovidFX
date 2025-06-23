package Views;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashFX {

    public void showSplash(Runnable onComplete) {
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: white; -fx-padding: 40px;");
        root.setPrefSize(300, 200);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Label label = new Label("Covid-25 Monitoramento");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        ProgressIndicator progress = new ProgressIndicator();

        root.getChildren().addAll(label, progress);
        Scene scene = new Scene(root);
        splashStage.setScene(scene);
        splashStage.centerOnScreen();
        splashStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}
            Platform.runLater(() -> {
                splashStage.close();
                onComplete.run();
            });
        }).start();
    }
}
