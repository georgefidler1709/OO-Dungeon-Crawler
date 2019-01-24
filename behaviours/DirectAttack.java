package behaviours;

import environment.Map;
import environment.Tile;

//Attack strategy used by the hunter. 
public class DirectAttack implements MoveStrategy {

	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		return playerPos;
	}

}
