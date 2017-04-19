package SendingFile;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Szczepan on 18.03.2017.
 */
public class Controller extends Application {
    private Stage mainStage;
    private Scene mainScene;
    private Pane mainPane;
    private Pane list;
    private File file;
    private int port;
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage primaryStage) throws Exception {
        statusLabel = new Label("Waiting");
        progressBar = new ProgressBar();
        this.port = 5000;
        this.mainStage = primaryStage;
        this.mainStage.setTitle("Wysyłanie pliku");

        this.mainPane = new Pane();
        this.mainPane.setPrefSize(800, 600);

        VBox grids = new VBox(10);
        grids.setTranslateX(10);
        grids.setTranslateY(10);

        list = new Pane();
        list.setPrefSize(780, 530);
        list.setStyle("-fx-background-color: #EEEEEE;");

        Buttons buttons = new Buttons(this);

        grids.getChildren().addAll(list, buttons);
        mainPane.getChildren().addAll(grids);

        this.mainScene = new Scene(mainPane);
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public void sendButtonAction(){
        Task<File> sendFileTask = new SendFileTask(this.port, this.file);
        this.statusLabel.textProperty().bind(sendFileTask.messageProperty());
        this.progressBar.progressProperty().bind(sendFileTask.progressProperty());
        executor.submit(sendFileTask);
    }

    public void refreshWindow(){
        list.getChildren().clear();
        int i = 1;
        if (this.file != null) {
            Text text = new Text(i + ".\t" + file.getName());
            text.setX(5);
            text.setY(22 * i);

            Label labelText = statusLabel;
            labelText.setTranslateX(500);
            labelText.setTranslateY(6 * i);

            ProgressBar progress = progressBar;
            progress.setTranslateX(610.0);
            progress.setTranslateY(6 * i);
            list.getChildren().addAll(text, labelText, progress);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static void main(String[] args){
        launch(args);
    }
}
