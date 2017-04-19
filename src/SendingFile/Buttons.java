package SendingFile;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Szczepan on 18.03.2017.
 */
public class Buttons extends Parent {

    public Controller clientWindow;

    public Buttons(Controller window){
        this.clientWindow = window;
        HBox grid = new HBox(10);

        Button browse = new Button("Przeglądaj...");
        browse.setPrefSize(253, 40);

        Button send = new Button("Wyślij");
        send.setPrefSize(253, 40);
        FileChooser fileChooser = new FileChooser();

        Button exit = new Button("Wyjście");
        exit.setPrefSize(254, 40);

        browse.setOnMouseClicked(event -> {
            fileChooser.setTitle("Wybierz pliki do wysłania");
            File tempFile = fileChooser.showOpenDialog(this.clientWindow.getMainStage());
            if (tempFile != null){
                window.setFile(tempFile);
            }
            this.clientWindow.refreshWindow();
        });

        send.setOnMouseClicked(event -> {
            if (this.clientWindow.getFile() != null){
                this.clientWindow.sendButtonAction();
            } else {
                System.out.print("No files");
            }
        });

        exit.setOnMouseClicked(event -> this.clientWindow.getMainStage().close());

        grid.getChildren().addAll(browse, send, exit);
        getChildren().addAll(grid);
    }
}
