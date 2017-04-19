package ToGrayConverse;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Szczepan on 19.03.2017.
 */
public class Controller extends Application {
    private Stage mainStage;
    private Scene mainScene;
    private Pane mainPane;

    private PictruesTable table;
    private Label timeLabel;
    private Label dirLabel;
    private RadioButtons radioButtons;
    private Buttons buttons;

    private ArrayList<File> pictrues;
    private File dirPath;
    private String typeOfConverse;

    private ForkJoinPool customPool = new ForkJoinPool(4);
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ObservableList<ImageProcessingJob> listToTable;

    private long start;
    private long stop;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.pictrues = new ArrayList<>();
        this.typeOfConverse = new String();
        this.listToTable = FXCollections.observableArrayList();

        start = 0;
        stop = 0;

        this.mainStage = primaryStage;
        this.mainStage.setTitle("Konwersja plików graficznych");

        this.mainPane = new Pane();
        this.mainPane.setPrefSize(800, 600);

        this.dirLabel = new Label("Lokalizacja docelowa:\t\t");
        this.dirLabel.setPrefSize(780, 40);
        this.dirLabel.setStyle("-fx-background-color: #EEEEEE");

        this.timeLabel = new Label("Czas konwersji:\t\t\t0 s");
        this.timeLabel.setPrefSize(780, 40);
        this.timeLabel.setStyle("-fx-background-color: #EEEEEE");

        VBox listButtonsGrid = new VBox(10);
        listButtonsGrid.setPrefWidth(780);
        listButtonsGrid.setTranslateX(10);
        listButtonsGrid.setTranslateY(10);

        this.table = new PictruesTable(this);
        this.radioButtons = new RadioButtons(this);
        this.buttons = new Buttons(this);

        listButtonsGrid.getChildren().addAll(table, timeLabel, dirLabel, radioButtons, buttons);
        mainPane.getChildren().addAll(listButtonsGrid);

        this.mainScene = new Scene(this.mainPane);

        this.mainStage.setScene(this.mainScene);
        this.mainStage.show();
    }

    public File getDirPath() {
        return dirPath;
    }

    public void setDirPath(File dirPath) {
        this.dirPath = dirPath;
        this.dirLabel.setText("Lokalizacja docelowa:\t\t" + dirPath.getAbsolutePath());
    }

    public ArrayList<File> getPictrues(){
        return pictrues;

    }

    public void setPictrues(List<File> files){
        pictrues.clear();
        pictrues.addAll(files);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setTimeLabel(long newTime){
        Double tempTime = new Long(newTime).doubleValue()/1000;
        timeLabel.setText("Czas konwersji:\t\t\t" + Double.toString(tempTime) + " s");
    }

    public void setTypeOfConverse(String newStatus){
        this.typeOfConverse = newStatus;
    }

    public void initializeList() {
        table.initializeListInTable(listToTable);
    }

    public void converse() {
        if (dirPath == null){
            return;
        }
        if (this.typeOfConverse != null){
            start = System.currentTimeMillis();
            ConvertToGrayTask.nDones = 0;
            if (this.typeOfConverse.equals("sequential")){
                converseSequential();
            } else if (this.typeOfConverse.equals("parallelCommonThreads")){
                converseParallelCommon();
            } else if (this.typeOfConverse.equals("parallelCustomThreads")){
                converseParallelCustom();
            }
        }
    }

    public void converseSequential() {
        listToTable.stream().forEach(el -> {
            try {
                Task task = new ConvertToGrayTask(this, el, getDirPath());
                executor.submit(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void converseParallelCustom() {
        listToTable.parallelStream().forEach(el -> {
            try {
                Task task = new ConvertToGrayTask(this, el, getDirPath());
                customPool.submit(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void converseParallelCommon() {
        listToTable.parallelStream().forEach(el -> {
            try {
                Task task = new ConvertToGrayTask(this, el, getDirPath());
                ForkJoinPool.commonPool().submit(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void stopTimer() {
        stop = System.currentTimeMillis();
        setTimeLabel(stop-start);
    }

    public static void main(String[] args){
        launch(args);
    }
}
