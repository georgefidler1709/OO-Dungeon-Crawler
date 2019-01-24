package gameLogic;

import environment.Map;
import environment.Tile;

public class DeathCondition implements LogicCondition {

	
	/**
	 * A constructor for the DeathCondition object
	 */
	public DeathCondition() {

	}
	
	/**
	 * @param map
	 * @return boolean
	 * Determines from the map if the game logic is satisfied
	 */
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.playerDead();
	}
	
	public boolean isTarget(Tile t) {
		return false;
	};
	

}
