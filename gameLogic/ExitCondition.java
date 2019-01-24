package gameLogic;

import java.util.Iterator;

import environment.Exit;
import environment.Map;
import environment.Tile;

public class ExitCondition implements LogicCondition {

	/**
	 * A constructor for the ExitCondition object
	 */
	public ExitCondition() {
	
	}

	
	/**
	 * @param map
	 * @return boolean win
	 * Determines from the map if the game logic is satisfied
	 */
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.playerOnExit();
	}

	
	/**
	 * @return string
	 * Returns the win condition string
	 */
	@Override
	public String toString() {
		return "Make it to the exit";
	}

	@Override
	public boolean isValidMap(Map map) {
		Iterator<Tile> mapIterator = map.iterator();
		while(mapIterator.hasNext()) {
			Tile t = mapIterator.next();
			if(t.hasExit()) return true;
		}
		return false;
	}

	@Override
	public String mapRequirements() {
		return "Exit required for exit condition";
	}
	
	public boolean isTarget(Tile t) {
		return (t.getStructure() instanceof Exit);
	};
		
}
