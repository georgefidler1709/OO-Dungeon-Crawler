package behaviours;

import characters.Direction;
import environment.Map;
import environment.Tile;

//Strategy used by the Hound when its hunter is still alive.
public class TeamAmbush implements MoveStrategy {
	
	Tile leaderPos;
	
	public TeamAmbush(Tile leaderPos) {
		super();
		this.leaderPos = leaderPos;
	}
	
	public TeamAmbush() {
		super();
		this.leaderPos = null;
	}
	
	public void setLeaderPos(Tile leaderPos) {
		this.leaderPos = leaderPos;
	}

	//Determines which direction the hunter will attack the player from 
	//And paths to the other side of the player to make a pincer attack.
	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		if(leaderPos == null) return null;
		Direction hunterAttackDir = getRelativeDir(playerPos, leaderPos);
		Direction ambushDir = hunterAttackDir.reverseDirection();
		Tile testTile = map.getNextTileInDir(playerPos, ambushDir);

		if(testTile != null && !isImpassable(testTile)) return testTile;
		else return playerPos;
	}
	
	public Direction chooseMove(Tile startPos, Tile playerPos, Map map) {
		Tile targetTile = chooseTargetTile(startPos, playerPos, map);
		Tile nextMove = AStarSearch(startPos, targetTile, map);
		return getRelativeDir(startPos, nextMove);
	}

}
