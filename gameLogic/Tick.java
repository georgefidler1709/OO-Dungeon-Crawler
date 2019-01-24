package gameLogic;

import environment.Map;

public interface Tick {

	/**
	 * @param map
	 * This helps the gameEngine clock by initiating a tick
	 */
	public boolean tick(Map m);
	
}
