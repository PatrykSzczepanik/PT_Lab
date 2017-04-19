package ToGrayConverse;

import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * Created by Szczepan on 19.03.2017.
 */
public class RadioButtons extends Parent{
    private Controller window;

    public RadioButtons(Controller window) {
        this.window = window;
        HBox radioButtonsGrid = new HBox(10);
        ToggleGroup groupOfButtons = new ToggleGroup();

        RadioButton sequential = new RadioButton("sekwencyjne");
        sequential.setToggleGroup(groupOfButtons);
        sequential.setPrefSize(150, 40);

        RadioButton parallelCommonThreads = new RadioButton("współbieżne, domyślna pula wątków");
        parallelCommonThreads.setToggleGroup(groupOfButtons);
        parallelCommonThreads.setPrefSize(300, 40);

        RadioButton parallelCustomThreads = new RadioButton("współbieżne, ustalona pula wątków");
        parallelCustomThreads.setToggleGroup(groupOfButtons);
        parallelCustomThreads.setPrefSize(300, 40);

        groupOfButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == sequential){
                window.setTypeOfConverse("sequential");
            } else if (newValue == parallelCommonThreads){
                window.setTypeOfConverse("parallelCommonThreads");
            } else if (newValue == parallelCustomThreads){
                window.setTypeOfConverse("parallelCustomThreads");
            }
        });

        radioButtonsGrid.getChildren().addAll(sequential, parallelCommonThreads, parallelCustomThreads);
        getChildren().addAll(radioButtonsGrid);
    }
}
