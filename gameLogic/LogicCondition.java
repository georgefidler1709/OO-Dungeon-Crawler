package gameLogic;

import environment.Map;
import environment.Tile;

public interface LogicCondition {

	/**
	 * @param map
	 * @return boolean win
	 * Determines from the map if the game logic is satisfied
	 */
	public boolean checkLogicSatisfied(Map map);
	
	public default boolean isValidMap(Map map) {
		return true;
	}
	public default String mapRequirements() {
		return "";
	}
	
	public default boolean equals(LogicCondition lc) {
		return this.getClass().equals(lc.getClass());
	}
	
	public abstract boolean isTarget(Tile t);
	
}
