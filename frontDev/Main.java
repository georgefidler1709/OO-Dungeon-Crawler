package frontDev;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setHeight(600);
        primaryStage.setWidth(850);
        Screen screen = new Screen(primaryStage, "Map Creation", "frontDev/entitySelect.fxml");
        EntitySelectController controller = new EntitySelectController(primaryStage, null);
        screen.start(controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
