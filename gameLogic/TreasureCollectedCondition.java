package gameLogic;

import java.util.Iterator;
import environment.Map;
import environment.Tile;
import items.objective.Treasure;

public class TreasureCollectedCondition implements LogicCondition {

	/**
	 * A constructor for the win condition object
	 */
	public TreasureCollectedCondition() {
		
	}

	/**
	 * @param map
	 * @return boolean win
	 * Determines from the map if the game logic is satisfied
	 */
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.allTreasureCollected();
	}
	
	/**
	 * @return string
	 * Returns the win condition string
	 */
	@Override
	public String toString() {
		return "Collect all treasure";
	}
	
	@Override
	public boolean isValidMap(Map map) {
		Iterator<Tile> mapIterator = map.iterator();
		while(mapIterator.hasNext()) {
			Tile t = mapIterator.next();
			if(t.hasExit()) return false;
		}
		return true;
	}

	@Override
	public String mapRequirements() {
		return "'Collect all treasure' win condition cannot be played while there is an exit on the map.";
	}
	
	public boolean isTarget(Tile t) {
		return (t.getItem() instanceof Treasure);
	};

}
