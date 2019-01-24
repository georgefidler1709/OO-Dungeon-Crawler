package frontDev;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import characters.*;
import environment.Boulder;
import environment.Door;
import environment.Exit;
import environment.FloorSwitch;
import environment.Ground;
import environment.Map;
import environment.Movable;
import environment.NonTile;
import environment.Pit;
import environment.Structure;
import environment.Tile;
import environment.Wall;

import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import gameEngine.GameEngine;
import gameLogic.BoulderSwitchesCondition;
import gameLogic.EnemiesDestroyedCondition;
import gameLogic.ExitCondition;
import gameLogic.GameLogic;
import gameLogic.LogicCondition;
import gameLogic.SaveInnocentCondition;
import gameLogic.TreasureCollectedCondition;
import items.*;
import items.objective.SkeletonKey;
import items.objective.Key;
import items.objective.Treasure;
import items.potions.Potion;
import items.potions.hover.Hover;
import items.potions.invincibility.Invincibility;
import items.weapons.Arrow;
import items.weapons.Sword;
import items.weapons.bomb.Bomb;

public class EntitySelectController extends Controller {
 
	//all the toggles used for selecting entity to add to map
    @FXML
    private ToggleButton togglerEnemy0;
    @FXML
    private ToggleButton togglerEnemy1;
    @FXML
    private ToggleButton togglerEnemy2;
    @FXML
    private ToggleButton togglerEnemy3;
    @FXML
    private ToggleButton togglerEnemy4;
    @FXML
    private ToggleButton togglerEnemy5;
    @FXML
    private ToggleButton togglerEnemy6;
    @FXML
    private ToggleButton togglerEnemy7;
    @FXML
    private ToggleButton togglerItem0;
    @FXML
    private ToggleButton togglerItem1;
    @FXML
    private ToggleButton togglerItem2;
    @FXML
    private ToggleButton togglerItem3;
    @FXML
    private ToggleButton togglerItem4;
    @FXML
    private ToggleButton togglerItem5;
    @FXML
    private ToggleButton togglerItem6;
    @FXML
    private ToggleButton togglerItem7;
    @FXML
    private ToggleButton togglerStructure0;
    @FXML
   	private ToggleButton togglerStructure1;
    @FXML
    private ToggleButton togglerStructure2;
    @FXML
    private ToggleButton togglerStructure3;
    @FXML
    private ToggleButton togglerStructure4;
    @FXML
    private ToggleButton togglerStructure5;
    @FXML
    private ToggleButton togglerStructure6;
    @FXML
    private ToggleButton togglerStructure7;
    @FXML
    private ToggleButton togglerMisc0;
    @FXML
    private ToggleButton togglerMisc1;
    @FXML
    private ToggleButton togglerMisc2;
    @FXML
    private ToggleButton togglerMisc3;
    @FXML
    private ToggleButton togglerMisc4;
    @FXML
    private ToggleButton togglerMisc5;
    @FXML
    private ToggleButton togglerMisc6;
    @FXML
    private ToggleButton togglerMisc7;
    
    @FXML
    private ToggleGroup entityList;
    
    @FXML
    private GridPane mapGrid;
    
    //tabs for each entity type (and miscellaneous for extras that do not have enough classes to merit their own tab)
    @FXML
    private TabPane entitySelect;
    @FXML 
    private Tab enemiesTab;
    @FXML 
    private Tab itemsTab;
    @FXML 
    private Tab structuresTab;   
    @FXML 
    private Tab miscTab;
    
    //drop down to select win condition
    @FXML 
    private MenuButton winConsDisplay;
    
    //display for error messages to the creator
    @FXML
    private TextArea errorsDisplay;
    
    @FXML
    private Button playButton;

    private ArrayList<ToggleButton> enemyToggles;
    private ArrayList<ToggleButton> itemToggles;
    private ArrayList<ToggleButton> structureToggles;
    private ArrayList<ToggleButton> miscToggles;
    
    private ArrayList<Enemy> enemyList;
    private ArrayList<Item> itemList;
    private ArrayList<Structure> structureList;
    private ArrayList<Movable> movableList;
    private Player player;
    
    private ArrayList<LogicCondition>winConsList;
    
    private Stage currStage;
   
    GameEngine gameEngine;
    
    public EntitySelectController(Stage s, GameEngine g) {
    	super(s);
    	this.currStage = s;
    	gameEngine = g;
    	enemyToggles = new ArrayList<ToggleButton>(8);
    	itemToggles = new ArrayList<ToggleButton>(8);
    	structureToggles = new ArrayList<ToggleButton>(8);
    	miscToggles = new ArrayList<ToggleButton>(8);
    	enemyList = new ArrayList<Enemy>(8);
    	itemList = new ArrayList<Item>(8);
    	structureList = new ArrayList<Structure>(8);
    	movableList = new ArrayList<Movable>(7);
    	winConsList = new ArrayList<LogicCondition>();
    }

    @FXML
    public void initialize() {
    	
    	//initialise all the things and then display the result
    	initialiseWinConsList();
    	if(gameEngine == null) 
    		gameEngine = new GameEngine(defaultMap(), defaultLogic());
    	initialiseEnemyList();
    	initialiseEnemyToggles();
    	initialiseItemList();
    	initialiseItemToggles();
    	initialiseStructureList();
    	initialiseStructureToggles();
    	initialisePlayer();
    	initialiseMovableList();
    	initialiseMiscToggles();
    	initialiseWinConsDisplay();
	    paneMap();
		displayMap();

	    
    }
    
    
    //Function called when a tile on the map display is left-clicked
    public void tileClicked(Tile tile) {
    	
    	//if the tile contains an entity, clear that entity
    	if(!tile.isEmpty()) {
    		tile.clear();
    		displayTile(tile);
    		return;
    	}
    	
    	//otherwise determine which tab is currently selected
    	int togNum;
    	Tab currTab = entitySelect.getSelectionModel().getSelectedItem();
    	ToggleButton currSelected = (ToggleButton)entityList.getSelectedToggle();
 	    if(currTab == enemiesTab) {
 	    	togNum = findSelectedToggle(currSelected, enemyToggles);
 	    	//if there is no enemy selected in the tab, clear the cell i.e. reshape the map
 	    	if(togNum == -1) {
 	    		clearCell(tile);
	    		return;
	    	}
 	    	//if there is an enemy selected, create a new object of that class and add it to the map.
 	    	Enemy selectedEnemy = enemyList.get(togNum);
 	    	selectedEnemy = selectedEnemy.createNew();
 	    	selectedEnemy.setPosition(tile);
 	    	selectedEnemy.addToMap(this.gameEngine.getMap(), tile);
 	    	//if the enemy added to the map needs to follow the gameEngine (e.g. strategist, that needs to know the win conditions)
 	    	if(selectedEnemy instanceof TeamPlayer && 
 	    		gameEngine.getLogic().requestedTeamMate((TeamPlayer)selectedEnemy)) {
 	    		gameEngine.getLogic().addToTeam((TeamPlayer)selectedEnemy);
 	    	}
 	    	displayTile(tile);  	
 	    }
 	    else if(currTab == itemsTab) {
	    	togNum = findSelectedToggle(currSelected, itemToggles);
	    	//if there is no item selected in the tab, clear the cell i.e. reshape the map
	    	if(togNum == -1) {
 	    		clearCell(tile);
	    		return;
	    	}
	    	
	    	//if there is an item selected, create a new object of that class and add it to the map.
	    	Item selectedItem = itemList.get(togNum);
	    	selectedItem = selectedItem.createNew();
	    	selectedItem.addToMap(this.gameEngine.getMap(), tile);
	    	displayTile(tile); 	
	    }
 	   else if(currTab == structuresTab) {
 		   //if there is no structure selected in the tab, clear the cell i.e. reshape the map
	    	togNum = findSelectedToggle(currSelected, structureToggles);
	    	if(togNum == -1) {
 	    		clearCell(tile);
	    		return;
	    	}
	    	
	    	//if there is an structure selected, create a new object of that class and add it to the map.
	    	Structure selectedStruct = structureList.get(togNum);
	    	selectedStruct = selectedStruct.createNew();
	    	selectedStruct.addToMap(this.gameEngine.getMap(), tile);
	    	displayTile(tile);   	
	    }
 	  else if(currTab == miscTab) {
 		//if there is no entity selected in the tab, clear the cell i.e. reshape the map
	    	togNum = findSelectedToggle(currSelected, miscToggles);
	    	if(togNum == -1) {
 	    		clearCell(tile);
	    		return;
	    	}
	    	
	    	//if there is an entity selected, create a new object of that class and add it to the map.
	    	else if(togNum == 0) {
		    	player.addToMap(this.gameEngine.getMap(), tile);
	    	} 
	    	else {
	    		Movable selectedMovable = movableList.get(togNum - 1);
	    		selectedMovable = selectedMovable.createNew();
		    	selectedMovable.addToMap(this.gameEngine.getMap(), tile);
	    	}
	    	displayTile(tile);  	
	    }
    	
    }
    
    //clears a cell by filling it with a transparent, non-interactive structure
    public void clearCell(Tile tile) {
    	tile.clear();
		tile.setStructure(new NonTile());
		displayTile(tile);
    }
    
    private TeamPlayer toPair = null;
    
    //handles pairing of entities which have a dynamic relationship determined by the creator via the observer pattern
    //e.g. hounds and hunters or doors and keys.
    //This is handled by right clicking the entities on the map you wish to pair.
    public void pairEntity(Tile tile) {
    
    	if(! tile.isEmpty()) {
    		//If no observer (teamPlayer) has been selected yet. Attempt to select one in the selected tile.
    		if(toPair == null) {
    			toPair = tile.getTeamPlayer();
	    		if(toPair != null)errorsDisplay.setText("Selected for pairing. Please select pair");
	    		else errorsDisplay.setText("entity cannot pair");
	    		
    		} else {
    			// If there is a observer ready, try to find an appropriate observed (TeamLeader) in the selected tile.
    			TeamLeader pairLeader = tile.getTeamLeader();
    			if(pairLeader != null && pairLeader.requestedTeamMate(toPair)) {
    					pairLeader.addToTeam(toPair);
    					pairLeader.notifyTeamMates();
    					toPair = null;
    					errorsDisplay.setText("entities paired");
    			}
    			//on fail cases, display appropriate error messages to the creator.
    			else {
    				errorsDisplay.setText("Could not pair the selected");
    				toPair = null;
    			}
    		}
    	}
    	else {
    		errorsDisplay.setText("Cannot pair empty tile");
    		toPair = null;
    	}
    }
    
    //Put a pane in each grid cell, each linked to their corresponding map tile.
    private void paneMap() {
	    Iterator<Tile> mapIterator = gameEngine.getMapIterator();
		while(mapIterator.hasNext()) {
			Tile tile = mapIterator.next();
			Pane p = new Pane();
			p.setOnMouseClicked(e -> {
				if(e.getButton() == MouseButton.SECONDARY) {
					pairEntity(tile);
					return;
				}
				else tileClicked(tile);
	        });
			mapGrid.add(p, tile.getX(), tile.getY());
		}
    }
    
    //Set up a default map on boot of creation mode
    private Map defaultMap() {
    	Map defaultMap = new Map(10, 10);
    	int i, j;
    	
    	for(i = 0, j = 0; i<10; i++) {
    		Tile tile = defaultMap.getTile(i, j);
    		tile.setStructure(new Wall());
    	}
    	for(i = 0, j = 9; i<10; i++) {
    		Tile tile = defaultMap.getTile(i, j);
    		tile.setStructure(new Wall());
    	}
    	for(i = 0, j = 0; j<10; j++) {
    		Tile tile = defaultMap.getTile(i, j);
    		tile.setStructure(new Wall());
    	}
    	for(i = 9, j = 0; j<10; j++) {
    		Tile tile = defaultMap.getTile(i, j);
    		tile.setStructure(new Wall());
    	}
    	
    	for(i = 1; i < 9; i++) {
    		for(j = 1; j < 9; j++) {
    			Tile tile = defaultMap.getTile(i, j);
    			tile.setStructure(new Ground());
    		}
    	}
    	
    	Tile playerTile = defaultMap.getTile(1, 1);
    	playerTile.setPlayer(new Player(playerTile));
    	Tile tile = defaultMap.getTile(8, 8);
    	tile.setStructure(new Exit());
    	
		return defaultMap;
    }
    
    //iterate through every tile on the map and display it.
    private void displayMap() {
        
    	//Add structures
    	Iterator<Tile> mapIterator = gameEngine.getMapIterator(); 
    	while(mapIterator.hasNext()) {
    		Tile tile = mapIterator.next();
    		displayTile(tile);
    	}
    }
    
    //Display the image for every entity in the tile.
    private void displayTile(Tile tile) {
    	if(tile.getStructure() != null) {
    		ImageView iv = new ImageView(tile.getStructure().getImage());
    		iv.setMouseTransparent(true);
    		mapGrid.add(iv, tile.getX(), tile.getY());
    	}
    	if(tile.hasPlayer()) {
    		ImageView iv = new ImageView(tile.getPlayer().getImage());
    		iv.setMouseTransparent(true);
    		mapGrid.add(iv, tile.getX(), tile.getY());
    	}
    	if(tile.hasEnemy()) {
    		ImageView iv = new ImageView(tile.getEnemy().getImage());
    		iv.setMouseTransparent(true);
        	mapGrid.add(iv, tile.getX(), tile.getY());
    	}
    	if(tile.getItem() != null) {
    		ImageView iv = new ImageView(tile.getItem().getImage());
    		iv.setMouseTransparent(true);
    		mapGrid.add(iv, tile.getX(), tile.getY());
    	}
    	if(tile.getMovable() != null) {
    		ImageView iv = new ImageView(tile.getMovable().getImage());
    		iv.setMouseTransparent(true);
    		mapGrid.add(iv, tile.getX(), tile.getY());
    	}
    	
    }

    //Default win condition is whatever is in the first position in the winConsList 
    private GameLogic defaultLogic() {
    	GameLogic gm = new GameLogic();
    	gm.setCondition(winConsList.get(0));
    	return gm;    
    }
    
    //Each list is initialised with one of each class within that superclass. These are used to display
    //the possible entities for the creator to add to the map on the sidebar.
    private void initialiseEnemyList() {
    	enemyList.add(new Hunter(new Tile(0,0)));
    	enemyList.add(new Hound(new Tile(0,0)));
    	enemyList.add(new Coward(new Tile(0,0)));
    	enemyList.add(new Strategist(new Tile(0,0)));
    	enemyList.add(new Innocent(new Tile(0,0)));
    }
    
    private void initialiseItemList() {
    	itemList.add(new Key());
    	itemList.add(new Treasure());
    	itemList.add(new Potion(new Hover()));
    	itemList.add(new Potion(new Invincibility()));
    	itemList.add(new Arrow());
    	itemList.add(new Bomb());
    	itemList.add(new Sword());
	itemList.add(new SkeletonKey());
    } 
    
    private void initialiseStructureList() {
    	structureList.add(new Exit());
    	structureList.add(new Wall());
    	structureList.add(new Pit());
    	structureList.add(new FloorSwitch());
    	structureList.add(new Door(null));	
    } 
    
    private void initialiseWinConsList() {
    	winConsList.add(new ExitCondition());
    	winConsList.add(new EnemiesDestroyedCondition());
    	winConsList.add(new TreasureCollectedCondition());
    	winConsList.add(new BoulderSwitchesCondition());
    	winConsList.add(new SaveInnocentCondition());
    	
    }
    
    private void initialiseMovableList() {
    	movableList.add(new Boulder());
    }
    
    private void initialisePlayer() {
    	player = new Player(null);
    }
    
    //Handling the drop down display in which the creator can selected the win conditions for the game.
    private void initialiseWinConsDisplay() {
    	//Initialise the dropdown menu, tick all wincons that are already selected.
    	for(LogicCondition lc : winConsList) {
    		CheckMenuItem option = new CheckMenuItem(lc.toString());
    		if(gameEngine.getLogic().hasCondition(lc)) {
    			option.setSelected(true);
    		}
    		
    		//When a win condition is selected, add it to the map and tick it on the dropdown menu.
    		option.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if(option.selectedProperty().getValue()) {
						option.setSelected(true);
						
						//The default wincon, in this case the Exit win condition, is special in that it cannot be played in tandem with other win conditions.
						LogicCondition defaultWinCon = winConsList.get(0);
						if(! lc.equals(defaultWinCon)) {
							gameEngine.getLogic().removeCondition(defaultWinCon);
							CheckMenuItem defaultWinConItem = (CheckMenuItem) winConsDisplay.getItems().get(0);
							defaultWinConItem.setSelected(false);
						}
						else {
							gameEngine.setLogic(defaultLogic());
							for(int i = 1; i < winConsDisplay.getItems().size(); i++) {
								CheckMenuItem currWinConItem = (CheckMenuItem) winConsDisplay.getItems().get(i);
								currWinConItem.setSelected(false);
							}
						}
						gameEngine.getLogic().addCondition(lc);
					}
					//If the win condition is already selected, remove it.
					else {
						option.setSelected(false);
						gameEngine.getLogic().removeCondition(lc);
					}
					
				}
    		});
    		winConsDisplay.getItems().add(option);
    	}	
    }
    
    
    //Entity toggle buttons are added to the appropriate list and then paired with the corresponding entity in the corresponding entity list
    //e.g. togglerEnemy0 will display the image for the Enemy in enemyList[0].
    private void initialiseEnemyToggles() {
    	enemyToggles.add(togglerEnemy0);
    	enemyToggles.add(togglerEnemy1);
    	enemyToggles.add(togglerEnemy2);
    	enemyToggles.add(togglerEnemy3);
    	enemyToggles.add(togglerEnemy4);
    	enemyToggles.add(togglerEnemy5);
    	enemyToggles.add(togglerEnemy6);
    	enemyToggles.add(togglerEnemy7);
    	
    	for(int i = 0; i < enemyList.size(); i++) {
    		enemyToggles.get(i).setGraphic(new ImageView(enemyList.get(i).getImage()));

    	}
    }
    
    private void initialiseItemToggles() {
    	itemToggles.add(togglerItem0);
    	itemToggles.add(togglerItem1);
    	itemToggles.add(togglerItem2);
    	itemToggles.add(togglerItem3);
    	itemToggles.add(togglerItem4);
    	itemToggles.add(togglerItem5);
    	itemToggles.add(togglerItem6);
    	itemToggles.add(togglerItem7);
    	
    	for(int i = 0; i < itemList.size(); i++) {
    		itemToggles.get(i).setGraphic(new ImageView(itemList.get(i).getImage()));

    	}
    }
    
    private void initialiseStructureToggles() {
    	structureToggles.add(togglerStructure0);
    	structureToggles.add(togglerStructure1);
    	structureToggles.add(togglerStructure2);
    	structureToggles.add(togglerStructure3);
    	structureToggles.add(togglerStructure4);
    	structureToggles.add(togglerStructure5);
    	structureToggles.add(togglerStructure6);
    	structureToggles.add(togglerStructure7);
    	
    	for(int i = 0; i < structureList.size(); i++) {
    		structureToggles.get(i).setGraphic(new ImageView(structureList.get(i).getImage()));
    	}
    }
   
    private void initialiseMiscToggles() {
    	miscToggles.add(togglerMisc0);
    	miscToggles.add(togglerMisc1);
    	miscToggles.add(togglerMisc2);
    	miscToggles.add(togglerMisc3);
    	miscToggles.add(togglerMisc4);
    	miscToggles.add(togglerMisc5);
    	miscToggles.add(togglerMisc6);
    	miscToggles.add(togglerMisc7);
    	
    	miscToggles.get(0).setGraphic(new ImageView(player.getImage()));

    	for(int i = 0; i < movableList.size(); i++) {
    		miscToggles.get(i + 1).setGraphic(new ImageView(movableList.get(i).getImage()));
    	}
    	
    }
    
    //Search through a toggle list to find the index of the currently selected toggleButton
    private int findSelectedToggle(ToggleButton target, ArrayList<ToggleButton> list) {
    	for(int i = 0; i < list.size(); i++) {
    		if(list.get(i) == target) return i;
    	}
    	return -1;
    }
    
    //Start the game
    @FXML
    private void playButtonClicked() {
    	//Check with the gameEngine that the game is valid, if not do not start the game
    	//And inform the creator what their error is.
    	String mapErrors = gameEngine.validGame();
    	if(! mapErrors.equals("")) {
			errorsDisplay.setText(mapErrors);
			return;
		}
    	//notify the followers of the gameEngine that the game is about to start.
    	gameEngine.getLogic().notifyTeamMates();
    	//start the game.
    	PlayerScreen ps = new PlayerScreen(currStage, gameEngine);
    	ps.start();
    }

}
