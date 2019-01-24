package behaviours;

import characters.Direction;
import environment.Map;
import environment.Tile;

//Move Strategy for the coward's state when it is far from the player.
//Pretending to rush the player.
public class CowardlyAttack implements MoveStrategy {
	
	//Distance at which to give up on the attack
	private int distToRetreat;
	
	//Retreat range can either be set initially or later on.
	public CowardlyAttack(int distToRetreat) {
		super();
		this.distToRetreat = distToRetreat;
	}
	
	public CowardlyAttack() {
		super();
		this.distToRetreat = 0;
	}
	
	
	public void setDistToRetreat(int distToRetreat) {
		this.distToRetreat = distToRetreat;
	}

	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		return playerPos;
	}
	
	//Move as normal except return NONE if too close to the player.
	@Override
	public Direction chooseMove(Tile startPos, Tile playerPos, Map map) {
		Tile targetTile = chooseTargetTile(startPos, playerPos, map);
		if(manhattanDist(targetTile, startPos) <= distToRetreat) return Direction.NONE;
		Tile nextMove = AStarSearch(startPos, targetTile, map);
		return getRelativeDir(startPos, nextMove);
	}
	
}
