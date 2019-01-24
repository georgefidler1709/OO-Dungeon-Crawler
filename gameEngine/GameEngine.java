package gameEngine;

import java.util.ArrayList;
import java.util.Iterator;
import characters.*;
import environment.Map;
import environment.Tile;
import gameLogic.GameLogic;
import gameLogic.LogicCondition;
import items.Item;
import items.inventory.Inventory;
import items.inventory.InventorySlot;

public class GameEngine {
	private Map map;
	private GameLogic logic;
	
	/**
	* constructor for GameEngine
	* @param map The initial map for the game
	* @param logic The win and loss conditions for the game.
	*/
	public GameEngine(Map map, GameLogic logic) {
		this.map = map;
		this.logic = logic;
	}
	
	/**
	* tick function
	* Processes the game one step. This function is called for each user input received.
	* It processes the user input and actions any affects it has on the game.
	* @param input An object specifying the specific action of the user
	* @return status An enum defining whether the player has won/loss
	*/
	public Status tick(Input input) {
		System.out.println("Ticking now");
		this.map.tick();
        // Check if the player has won/lost after their move.
		Status returnStatus;
		if(logic.checkWin(this.map)) {
			return Status.WIN;
		} else if(logic.checkLoss(this.map)) {
			return Status.LOSE;
		}
		
		// Action the input of the player.
		map.storePlayerPos();
		if (input.getType() == InputType.MOVE) {
			
			Tile newLocation = this.map.movePlayer(input.getDirection());
		} else if (input.getType() == InputType.USE) {
			
			Player player = this.map.getPlayer();
			player.useItem(input.getItemNumber(), input.getDirection(), this.map, player);
			
		}
		
        // Check if the player has won/lost after their move.
		if(logic.checkWin(this.map)) {
			return Status.WIN;
		} else if(logic.checkLoss(this.map)) {
			return Status.LOSE;
		} else {
            
            // Actions movements of the mobs on the map.
            ArrayList<Enemy> enemies = this.map.getEnemies();
            for(Enemy enemy : enemies) {
                Direction d = enemy.move(this.map);
                this.map.moveEnemy(enemy, d);
            }
            
            this.map.resolveDeaths();
            this.map.movablesUpdate();
            // Check the win / death conditions.
            if(logic.checkWin(this.map)) {
                returnStatus = Status.WIN;
            } else if(logic.checkLoss(this.map)) {
                returnStatus = Status.LOSE;
            } else {
                returnStatus = Status.CONTINUE;
            }
        }
		
		return returnStatus;
		
	}
	
	public Map getMap() {
		return map;
	}
	
	public GameLogic getLogic() {
		return logic;
	}
	
	public void setLogic(GameLogic gl) {
		this.logic = gl;
	}
	
	public Iterator<Tile> getMapIterator() {
		return getMap().iterator();
	}
	
	public Inventory getInventory() {
		return getMap().getInventory();
	}

	public Iterator<InventorySlot> getInventoryIterator() {
		return getInventory().iterator();
	}

	public ArrayList<LogicCondition> getLogicConditions() {
		return logic.getConditions();
	}
	
	public String validGame() {
		StringBuilder mapErrorsBuilder = new StringBuilder("");
		if(! map.hasOnePlayer()) mapErrorsBuilder.append("Please add exactly 1 player character.");
		for(LogicCondition wc : logic.getConditions()) {
			if(! wc.isValidMap(map)) mapErrorsBuilder.append(wc.mapRequirements() + "\n");
		}
		return mapErrorsBuilder.toString();
		
	}
	
	public ArrayList<LogicCondition> getWinCons() {
		return logic.getConditions();
	}
	
}
