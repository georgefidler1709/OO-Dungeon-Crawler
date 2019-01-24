package gameLogic;

import java.util.Iterator;
import environment.Map;
import environment.Tile;


public class BoulderSwitchesCondition implements LogicCondition {

	
	/**
	 * A constructor for the win condition object
	 */
	public BoulderSwitchesCondition() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param map
	 * @return boolean win
	 * Determines from the map if the game logic is satisfied
	 */
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.allSwitchesCovered();
	}

	/**
	 * @return string
	 * Returns the win condition string
	 */
	@Override
	public String toString() {
		return "Place all boulders on switches";
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
		return "'Place all boulders on switches' win condition cannot be played while there is an exit on the map.";
	}
	
	public boolean isTarget(Tile t) {
		return t.getMovable() != null;
	};

}
