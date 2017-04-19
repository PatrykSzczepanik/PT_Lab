package ToGrayConverse;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * Created by Szczepan on 19.03.2017.
 */
public class PictruesTable extends Parent {
    private Controller window;
    private TableView table;
    @FXML TableColumn<ImageProcessingJob, String> imageNameColumn;
    @FXML TableColumn<ImageProcessingJob, Double> progressColumn;
    @FXML TableColumn<ImageProcessingJob, String> statusColumn;

    public PictruesTable(Controller controller) {
        this.window = controller;
        VBox tables = new VBox(0);

        this.table = new TableView();
        this.table.setPrefSize(780, 385);
        this.table.setStyle("-fx-background-color: #EEEEEE");

        this.imageNameColumn = new TableColumn("Nazwa pliku");
        this.imageNameColumn.setPrefWidth(458);

        this.statusColumn = new TableColumn("Status");
        this.statusColumn.setPrefWidth(170);

        this.progressColumn = new TableColumn("Postęp");
        this.progressColumn.setPrefWidth(150);

        table.getColumns().addAll(imageNameColumn, statusColumn, progressColumn);

        tables.getChildren().addAll(table);
        getChildren().addAll(tables);
    }

    public void initializeListInTable(ObservableList<ImageProcessingJob> listToTable){
        listToTable.clear();
        for (File file: this.window.getPictrues()){
            ImageProcessingJob tempImage = new ImageProcessingJob(file);
            listToTable.add(tempImage);
        }
        imageNameColumn.setCellValueFactory(                //nazwa pliku
                p -> new SimpleStringProperty(p.getValue().getFile().getName()));
        statusColumn.setCellValueFactory(                   //status przetwarzania
                p -> p.getValue().getStatus());
        progressColumn.setCellFactory(                      //wykorzystanie paska postępu
                ProgressBarTableCell.<ImageProcessingJob>forTableColumn());
        progressColumn.setCellValueFactory(                 //postęp przetwarzania
                p -> p.getValue().getProgress().asObject());

        table.setItems(listToTable);
    }

}
