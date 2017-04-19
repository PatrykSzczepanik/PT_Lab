package ToGrayConverse;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.io.File;

/**
 * Created by Szczepan on 19.03.2017.
 */
public class ImageProcessingJob {
    private File file;
    private SimpleStringProperty status;
    private SimpleDoubleProperty progress;
    static private int nFiles = 0;

    public ImageProcessingJob(File file) {
        nFiles++;
        this.file = file;
        this.status = new SimpleStringProperty("Oczekuje");
        this.progress = new SimpleDoubleProperty(0);
    }

    public File getFile() {
        return file;
    }

    public ObservableValue<String> getStatus() {
        return status;
    }

    public DoubleProperty getProgress() {
        return progress;
    }

    public int getNFiles(){
        return nFiles;
    }

    public void setProgress(double progress){
        this.progress.set(progress);
    }

    public void setStatus(String newStatus){
        this.status.set(newStatus);
    }
}
