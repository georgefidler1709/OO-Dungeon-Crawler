package behaviours;

import characters.Direction;
import environment.Map;
import environment.Tile;

//Strategy used when the coward gets too close to the player.
public class Retreat implements MoveStrategy {

	//Stores the retreat direction so that it can continue to run in a straight line in future turns.
	private Direction retreatDir;
	
	public Retreat() {
		super();
		this.retreatDir = Direction.NONE;
	}
	
	//Retreat's in the direction opposite to the player in a straight line as long as it is able.
	public Direction chooseMove(Tile startPos, Tile playerPos, Map map) {
		if(retreatDir == Direction.NONE) setretreatDir(startPos, playerPos, map);

		Tile nextMove = map.getNextTileInDir(startPos, retreatDir);
	//If cannot retreat any further, request to switch back to CowardlyAttack
		if(nextMove == null || isImpassable(nextMove)) {
			retreatDir = Direction.NONE;
			return Direction.NONE;
		} else return getRelativeDir(startPos, nextMove);
	}

	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		return null;
	}
	
	//Pick's retreat direction opposite to the player.
	public void setretreatDir(Tile startPos, Tile playerPos, Map map) {
		retreatDir = getRelativeDir(startPos, playerPos).reverseDirection();
	}

}
