package frontDev;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import characters.Direction;
import environment.Ground;
import environment.Tile;
import java.util.concurrent.TimeUnit;

import gameEngine.GameEngine;
import gameEngine.Input;
import gameEngine.InputType;
import gameEngine.Status;
import gameLogic.LogicCondition;
import items.inventory.Inventory;
import items.inventory.InventorySlot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PlayerController {
	
	private final int NO_SET_ITEM = -1;
	
	private Stage currStage;
	private GameEngine gameEngine;
	private Status currStatus;
	private int itemToUse;
	private ArrayList<ToggleButton> toggleList;
	private ArrayList<Label> itemQtyLabelList;
	private ArrayList<ImageView> imageFiles;
	
	@FXML
	private GridPane mapGrid;
	
	@FXML
	private Pane origin;
	
	@FXML
	private Button upBtn;
	
	@FXML
	private Button downBtn;
	
	@FXML
	private Button leftBtn;
	
	@FXML
	private Button rightBtn;

	@FXML
	private Button backBtn;
	
	@FXML
	private TextFlow winConditions;
	
	@FXML
	private GridPane inventory;
	
	@FXML
	private ToggleButton toggleBtn1;
	
	@FXML
	private ToggleButton toggleBtn2;
	
	@FXML
	private ToggleButton toggleBtn3;
	
	@FXML
	private ToggleButton toggleBtn4;
	
	@FXML
	private ToggleButton toggleBtn5;
	
	@FXML
	private ToggleButton toggleBtn6;
	
	@FXML
	private ToggleButton toggleBtn7;
	
	@FXML
    private ToggleGroup toggleGroup;
	
	@FXML
	private Label itemQty1;
	
	@FXML
	private Label itemQty2;
	
	@FXML
	private Label itemQty3;
	
	@FXML
	private Label itemQty4;
	
	@FXML
	private Label itemQty5;
	
	@FXML
	private Label itemQty6;
	
	@FXML
	private Label itemQty7;
	
	@FXML
	private Label gameMessage;
	
	
	/**
	 * @param Stage, GameEngine
	 * A constructor for the PlayerController
	 */
    public PlayerController(Stage s, GameEngine gameEngine) {
        this.currStage = s;
        this.gameEngine = gameEngine;
        this.currStatus = Status.CONTINUE;
        this.itemToUse = NO_SET_ITEM;
        this.toggleList = new ArrayList<>();
        this.itemQtyLabelList = new ArrayList<Label>();
        this.imageFiles = new ArrayList<>();
    }

   
    /**
	 * Sets up the PlayerController
	 */
	@FXML
    public void initialize() throws FileNotFoundException {
		
		gameMessage.setText(" ");
    	setToggleList();
    	setItemQtyLabelList();
    	
    	gridSetup();
    	displayScreen(); 
    	//setToggleArray();

    }
	
	/**
	 * Calls the methods necessary to display the screen 
	 */
	private void displayScreen() throws FileNotFoundException {
    	displayMap();
    	displayWinConditions();
    	displayInventory();
	}

	/**
	 * Displays the inventory to the player
	 */
	private void displayInventory() {
	
		Iterator<InventorySlot> inventoryIterator = gameEngine.getInventoryIterator();
		
		
		int i = 0;
		while(inventoryIterator.hasNext()) {
			InventorySlot itemSlot = inventoryIterator.next();
			
			if(itemSlot.getNum() > 0) {
				ToggleButton toggle = toggleList.get(i);
				Label label = itemQtyLabelList.get(i);
				
				toggle.setGraphic(new ImageView(itemSlot.getItemImage()));
				label.setText(Integer.toString(itemSlot.getNum()));
				i++;	
			}
		}
		while(i < toggleList.size()) {
			ToggleButton toggle = toggleList.get(i);
			Label label = itemQtyLabelList.get(i);
			
			toggle.setGraphic(null);
			label.setText("");
			i++;
		}
	}

	/**
	 * Displays the win conditions
	 */
	private void displayWinConditions() {
    	
    	/*
    	 * Improvements to this display include
    	 * 	- Updating the completed conditions via a check box or colour change
    	 * 	- Better size and font of the text
    	 */
    	
		String display = "Win Conditions"; 
		for(LogicCondition wc : gameEngine.getLogicConditions()) { //fix train wreck
			display += "\n - " + wc;
			if(wc.checkLogicSatisfied(gameEngine.getMap())) {
				display += " (OBJECTIVE COMPLETED)";
			}
		}
		Text text = new Text(display);
		winConditions.getChildren().clear();
		winConditions.getChildren().add(text);
	}


	/**
	 * Sets up the grid defaults
	 */
	private void gridSetup() {
    	mapGrid.setHgap(0);
    	mapGrid.setVgap(0);
    }
    
	/**
	 * Displays the map to the player
	 */
    private void displayMap() throws FileNotFoundException {
    
    	//Add structures
    	Iterator<Tile> mapIterator = gameEngine.getMapIterator(); 
    	clearImageFiles();
    	while(mapIterator.hasNext()) {
    		Tile tile = mapIterator.next();
    		displayTile(tile);
    	}
    	
    }

    /**
	 * Clears open image files to reduce space
	 */
	private void clearImageFiles() {
		while(imageFiles.size() > 0) {
			imageFiles.get(0).setImage(null);
			imageFiles.remove(0);
		}
	}

	/**
	 * @param tile
	 * displays a tile object
	 */
	private void displayTile(Tile tile) throws FileNotFoundException {
		
		ImageView iv = new ImageView(new Ground().getImage());
		mapGrid.add(iv, tile.getX(), tile.getY());
		imageFiles.add(iv);
		
    	if(tile.getStructure() != null) {
    		iv = new ImageView(tile.getStructure().getImage());
    		mapGrid.add(iv, tile.getX(), tile.getY());
    		imageFiles.add(iv);
    	}
    	if(tile.hasPlayer()) {
    		iv = new ImageView(tile.getPlayer().getImage());
    		mapGrid.add(iv, tile.getX(), tile.getY());
    		imageFiles.add(iv);
    		mapGrid.add(new ImageView(tile.getPlayer().getImage()), tile.getX(), tile.getY());
    	}
    	if(tile.hasEnemy()) {
    		iv = new ImageView(tile.getEnemy().getImage());
    		mapGrid.add(iv, tile.getX(), tile.getY());
    		imageFiles.add(iv);
    	}
    	if(tile.getItem() != null) {
    		iv = new ImageView(tile.getItem().getImage());
    		mapGrid.add(iv, tile.getX(), tile.getY());
    		imageFiles.add(iv);
    	}
    	
    	if(tile.getMovable() != null) {
    		iv = new ImageView(tile.getMovable().getImage());
    		mapGrid.add(iv, tile.getX(), tile.getY());
    		imageFiles.add(iv);
    	}
    }
    
	
	/**
	 * Handles the up button being pressed
	 */
    @FXML
    public void handleUpBtn() throws FileNotFoundException {
    	Input input;
    	if(itemUseRequested()) {
    		System.out.println("Using item: " + itemToUse + " to the UP");
    		input = new Input(InputType.USE, Direction.UP, getItemToUse());
    	} else {
	    	input = new Input(InputType.MOVE, Direction.UP);
    	}
    	Status status = gameEngine.tick(input);
    	displayScreen();
    	setStatus(status);
    	runStatusHandle();
    }
    
    
    /**
	 * Handles the down button being pressed
	 */
    @FXML
    public void handleDownBtn() throws FileNotFoundException {
    	Input input;
    	if(itemUseRequested()) {
    		System.out.println("Using item: " + itemToUse + " to the DOWN");
    		input = new Input(InputType.USE, Direction.DOWN, getItemToUse());
    	} else {
	    	input = new Input(InputType.MOVE, Direction.DOWN);
    	}
    	Status status = gameEngine.tick(input);
    	displayScreen();
    	setStatus(status);
    	runStatusHandle();
    }
    
    /**
	 * Handles the left button being pressed
	 */
    @FXML
    public void handleLeftBtn() throws FileNotFoundException {
    	Input input;
    	if(itemUseRequested()) {
    		System.out.println("Using item: " + itemToUse + " to the LEFT");
    		input = new Input(InputType.USE, Direction.LEFT, getItemToUse());
    	} else {
	    	input = new Input(InputType.MOVE, Direction.LEFT);
    	}
    	Status status = gameEngine.tick(input);
    	displayScreen();
    	setStatus(status);
    	runStatusHandle();
    }
    
    /**
	 * Handles the right button being pressed
	 */
    @FXML
    public void handleRightBtn() throws FileNotFoundException {
    	Input input;
    	if(itemUseRequested()) {
    		System.out.println("Using item: " + itemToUse + " to the RIGHT");
    		input = new Input(InputType.USE, Direction.RIGHT, getItemToUse());		
    	} else {
	    	input = new Input(InputType.MOVE, Direction.RIGHT);
    	}
    	Status status = gameEngine.tick(input);
    	displayScreen();
    	setStatus(status);
    	runStatusHandle();
    }
    
    /**
	 * Handles the inventory toggle being selected 
	 */
    @FXML
    public void toggleOn() {
    	System.out.print("Toggle selected - ");
    	resetItemToUse();
    	int index = toggleIndex(toggleGroup.getSelectedToggle());
    	System.out.println(" Selected item at index: " + index);
    	setItemToUse(index);
    }
    
    /**
	 * Handles the back button being pressed
	 */
    @FXML
    public void handleBackBtn() {
    	Screen screen = new Screen(currStage, "Map Creation", "frontDev/entitySelect.fxml");
        EntitySelectController controller = new EntitySelectController(currStage, gameEngine);
        screen.start(controller);
    }
    
    /**
     * @param Toggle
	 * sets the toggle index
	 */
    private int toggleIndex(Toggle toggle) {
    	if (toggleList.contains(toggle)) {
    		return toggleList.indexOf(toggle);
    	} else {
    		return NO_SET_ITEM;
    	}
    }
    
    /**
	 * Handles a status change from the gameEngine
	 */
    private void runStatusHandle() throws FileNotFoundException {
		// chose what should be displayed to player depending on current status
    	

    	
    	if(getStatus() == Status.CONTINUE) {
    		return;
    	}
    	if(getStatus() == Status.WIN) {
    		gameMessage.setText("WIN");
    		System.out.println("WIN");
    		
    	} else if(getStatus() == Status.LOSE) {
    		gameMessage.setText("LOSE");
    		System.out.println("LOSE");
    	}


	}

    /**
     * @param Status
	 * Sets the current status
	 */
	private void setStatus(Status status) {
    	this.currStatus = status;
    }
	
	/**
     * @return Status
	 * Returns the current status
	 */
	private Status getStatus() {
    	return this.currStatus;
    }
  
	/**
     * @param int
	 * Sets the item to use
	 */
    private void setItemToUse(int itemIndex) {
    	if(itemIndex == NO_SET_ITEM) { 
    		return;
    	}
    	Inventory inv = gameEngine.getInventory();
    	if(itemIndex < inv.size() && inv.usableItem(itemIndex)) {
    		this.itemToUse = itemIndex;
    		deselectToggle();
    	}
    }

    /**
     * @return int
	 * Returns the item to use
	 */
	private int getItemToUse() {
		int itemIndex = this.itemToUse;
		resetItemToUse();
		deselectToggle();
    	return itemIndex;
    }
    
	/**
     * Deselects the toggle
	 */
    private void deselectToggle() {
    	try {
    		toggleGroup.getSelectedToggle().setSelected(false);
    	} catch (NullPointerException e) {
    		
    	}
	}

    /**
     * Resets the item use variable
	 */
	private void resetItemToUse() {
		this.itemToUse = NO_SET_ITEM;
    }
    
	/**
     * @param boolean
     * Returns if item use is set
	 */
    private boolean itemUseRequested() {
    	return (this.itemToUse != NO_SET_ITEM);
    }
    
    /**
     * Sets up the toggle list for inventory
	 */
    private void setToggleList() {
		toggleList.add(toggleBtn1);
		toggleList.add(toggleBtn2);
		toggleList.add(toggleBtn3);
		toggleList.add(toggleBtn4);
		toggleList.add(toggleBtn5);
		toggleList.add(toggleBtn6);
		toggleList.add(toggleBtn7);
	}
    
    /**
     * Sets up the label list for inventory
	 */
    private void setItemQtyLabelList() {
    	itemQtyLabelList.add(itemQty1);
    	itemQtyLabelList.add(itemQty2);
    	itemQtyLabelList.add(itemQty3);
    	itemQtyLabelList.add(itemQty4);
    	itemQtyLabelList.add(itemQty5);
    	itemQtyLabelList.add(itemQty6);
    	itemQtyLabelList.add(itemQty7);
	}
}





