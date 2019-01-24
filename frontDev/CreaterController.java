package frontDev;


import characters.Player;
import environment.Exit;
import environment.Ground;
import environment.Map;
import environment.Tile;
import environment.Wall;
import gameEngine.GameEngine;
import gameLogic.ExitCondition;
import gameLogic.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreaterController {
	
	@FXML
    private Button defaultMapBtn;
	
	private Stage currStage;

    public CreaterController(Stage s) {
        this.currStage = s;

    }

    @FXML
    public void initialize() {
 
    }
    
    @FXML
    public void handleDefaultMapBtn() {
    	PlayerScreen ps = new PlayerScreen(currStage, defaultGameEngine());
        ps.start();
    
    }
    
    private GameEngine defaultGameEngine() {
    	return new GameEngine(defaultMap(), defaultLogic());
    }
    
    private GameLogic defaultLogic() {
    	GameLogic gm = new GameLogic();
    	gm.setCondition(new ExitCondition());
    	return gm;
    }
    
    private Map defaultMap() {
    	Map defaultMap = new Map(10, 10);
    	int i, j;
    	
    	for(i = 0, j = 0; i<10; i++) {
    		defaultMap.getTile(i, j).setStructure(new Wall());
    	}
    	for(i = 0, j = 9; i<10; i++) {
    		defaultMap.getTile(i, j).setStructure(new Wall());
    	}
    	for(i = 0, j = 0; j<10; j++) {
    		defaultMap.getTile(i, j).setStructure(new Wall());
    	}
    	for(i = 9, j = 0; j<10; j++) {
    		defaultMap.getTile(i, j).setStructure(new Wall());
    	}
    	
    	for(i = 1; i < 9; i++) {
    		for(j = 1; j < 9; j++) {
    			defaultMap.getTile(i, j).setStructure(new Ground());
    		}
    	}
    	
    	Tile playerTile = defaultMap.getTile(1, 1);
    	playerTile.setPlayer(new Player(playerTile));
    	defaultMap.getTile(8, 8).setStructure(new Exit());
    	
		return defaultMap;
    }
    
}





