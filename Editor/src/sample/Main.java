package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.windows.MainWindow;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainWindow mainWindow = new MainWindow();
        VBox root = mainWindow.getRoot();
        primaryStage.setTitle("PoolBets Database Editor");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            if (mainWindow.getClient() != null)
                mainWindow.getClient().disconnect();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
