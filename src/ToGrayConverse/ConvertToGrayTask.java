package ToGrayConverse;

import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Szczepan on 20.03.2017.
 */
public class ConvertToGrayTask extends Task {
    private Controller controller;
    private ImageProcessingJob fileToConvert;
    private File originalFile;    //oryginalny plik graficzny
    private File outputDir;    //katalog docelowy
    static public int nDones = 0;

    public ConvertToGrayTask(Controller controller, ImageProcessingJob file, File dirPath){
        this.controller = controller;
        this.fileToConvert = file;
        this.originalFile = file.getFile();
        this.outputDir = dirPath;
    }

    @Override
    protected Object call() throws Exception {
        try {
            this.fileToConvert.setStatus("Przetwarzanie...");
            //wczytanie oryginalnego pliku do pamięci
            BufferedImage original = ImageIO.read(originalFile);

            //przygotowanie bufora na grafikę w skali szarości
            BufferedImage grayscale = new BufferedImage(
                    original.getWidth(), original.getHeight(), original.getType());
            //przetwarzanie piksel po pikselu
            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getHeight(); j++) {
                    //pobranie składowych RGB
                    int red = new Color(original.getRGB(i, j)).getRed();
                    int green = new Color(original.getRGB(i, j)).getGreen();
                    int blue = new Color(original.getRGB(i, j)).getBlue();
                    //obliczenie jasności piksela dla obrazu w skali szarości
                    int luminosity = (int) (0.30*red + 0.50*green + 0.20*blue);
                    //przygotowanie wartości koloru w oparciu o obliczoną jaskość
                    int newPixel =
                            new Color(luminosity, luminosity, luminosity).getRGB();
                    //zapisanie nowego piksela w buforze
                    grayscale.setRGB(i, j, newPixel);
                }
                //obliczenie postępu przetwarzania jako liczby z przedziału [0, 1]
                double progress = (1.0 + i) / original.getWidth();
                //aktualizacja własności zbindowanej z paskiem postępu w tabeli
                Platform.runLater(() ->  this.fileToConvert.setProgress(progress));

            }
            //przygotowanie ścieżki wskazującej na plik wynikowy
            Path outputPath =
                    Paths.get(outputDir.getAbsolutePath(), originalFile.getName());

            //zapisanie zawartości bufora do pliku na dysku
            ImageIO.write(grayscale, "jpg", outputPath.toFile());
            this.fileToConvert.setStatus("Zakończone");
            nDones++;
            if (nDones == fileToConvert.getNFiles()){
                Platform.runLater(() -> this.controller.stopTimer());
            }
        } catch (IOException ex) {
            //translacja wyjątku
            throw new RuntimeException(ex);
        }
        return null;
    }
}
