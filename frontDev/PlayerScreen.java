package frontDev;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import characters.Direction;
import environment.Map;
import gameEngine.GameEngine;
import gameEngine.Input;
import gameEngine.InputType;
import gameEngine.Status;

public class PlayerScreen {

    private Stage s;
    private String title;
    private FXMLLoader fxmlLoader;
    private GameEngine gameEngine;

    /**
     * @param Stage, GameEngine
	 * A constructor for the PlayerScreen
	 */
    public PlayerScreen(Stage s, GameEngine gameEngine) {
        this.s = s;
        this.title = "Player Mode";
        this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("frontDev/playerMode.fxml"));
        this.gameEngine = gameEngine;
    }

    
    /**
	 * Starts the PlayerScreen process
	 * 
	 * Also handles Arrow Keys 
	 */
    public void start()  {
        s.setTitle(title);
        // set controller for start.fxml
        PlayerController pc = new PlayerController(s, gameEngine);
        fxmlLoader.setController(pc);
        try {
            // load into a Parent node called root
            Parent root = fxmlLoader.load();
            Scene sc = new Scene(root, 600, 600);  
            
        	sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	
    			@Override
    			public void handle(KeyEvent event) {
    				// TODO Auto-generated method stub
    				System.out.println(event.getCode());
    				try {
    	                switch (event.getCode()) {
    	                    case UP:    pc.handleUpBtn(); break;
    	                    case DOWN:  pc.handleDownBtn(); break;
    	                    case LEFT:  pc.handleLeftBtn(); break;
    	                    case RIGHT: pc.handleRightBtn(); break;
    					default:
    						break;
    	                }
    				}
    				catch (FileNotFoundException e) {
    					
    				}
                  
    			}
            });
            
            
            s.setScene(sc);
            System.out.println(sc);
            s.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}



