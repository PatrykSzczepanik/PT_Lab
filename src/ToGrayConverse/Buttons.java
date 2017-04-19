package ToGrayConverse;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * Created by Szczepan on 19.03.2017.
 */
public class Buttons extends Parent {
    private Controller window;
    private Button selectFilesButton;
    private Button selectDirButton;
    private Button converseButton;
    private Button exitButton;

    public Buttons(Controller controller) {
        this.window = controller;
        HBox buttonsGrid = new HBox(10);

        this.selectFilesButton = new Button("Wybierz pliki...");
        selectFilesButton.setPrefSize(187, 40);

        this.selectDirButton = new Button("Wybierz folder...");
        selectDirButton.setPrefSize(188, 40);

        this.converseButton = new Button("Konwertuj");
        converseButton.setPrefSize(187, 40);

        this.exitButton = new Button("Wyjście");
        exitButton.setPrefSize(188, 40);

        selectFilesButtonActions();
        selectDirButtonActions();
        converseButtonActions();
        exitButtonActions();

        buttonsGrid.getChildren().addAll(selectFilesButton, selectDirButton, converseButton, exitButton);
        getChildren().addAll(buttonsGrid);
    }

    private void selectFilesButtonActions(){
        selectFilesButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz pliki do konwersji");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JPG images", "*.jpg"));
            List<File> files = fileChooser.showOpenMultipleDialog(this.window.getMainStage());
            if (files != null){
                this.window.setPictrues(files);
                this.window.initializeList();
            }
        });
    }

    private void selectDirButtonActions(){
        selectDirButton.setOnMouseClicked(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Wybierz folder docelowy");
            File dirPath = directoryChooser.showDialog(this.window.getMainStage());
            if (dirPath != null){
                this.window.setDirPath(dirPath);
            }
        });
    }

    private void converseButtonActions(){
        converseButton.setOnMouseClicked(event -> this.window.converse());
    }

    private void exitButtonActions(){
        exitButton.setOnMouseClicked(event -> this.window.getMainStage().close());
    }
}
