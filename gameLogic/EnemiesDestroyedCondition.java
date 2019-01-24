package gameLogic;

import java.util.Iterator;
import environment.Map;
import environment.Tile;
import items.weapons.Weapon;


public class EnemiesDestroyedCondition implements LogicCondition {

	
	/**
	 * A constructor for the win condition object
	 */
	public EnemiesDestroyedCondition() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param map
	 * @return boolean
	 * Determines from the map if the game logic is satisfied
	 */
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.allEnemiesDestoyed();
	}

	/**
	 * @return string
	 * Returns the win condition string
	 */
	@Override
	public String toString() {
		return "Destroy all enemies";
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
		return "'Destory all Enemies' win condition cannot be played while there is an exit on the map.";
	}
	
	public boolean isTarget(Tile t) {
		return (t.getItem() instanceof Weapon);
	};
	
}
