package behaviours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import characters.Direction;
import environment.Ground;
import environment.Map;
import environment.Structure;
import environment.Tile;
import gameLogic.LogicCondition;

//Strategy used by the Strategist
public class StrategicAttack implements MoveStrategy {
	
	//This will need to change to be a more general 'winconditions' including items and structures.
	//I would also like to initiate a relationship between strategists and gameLogic like pub-sub or observer pattern to provide this.
	ArrayList<LogicCondition> expectedTargets;
	
	public StrategicAttack() {
		super();
		this.expectedTargets = null;
	}
	
	public StrategicAttack(ArrayList<LogicCondition> expectedTargets) {
		super();
		this.expectedTargets = expectedTargets;
	}
	
	public void setExpectedTargets(ArrayList<LogicCondition> expectedTargets) {
		this.expectedTargets = expectedTargets;
	}
	
	//Instead of mapping to the player, Find an 'expected target' closest to the player
	//And assume they will try to move there. So map to the square you expect them
	//to move to next turn instead.
	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		if(expectedTargets == null) return null;
		Tile expectedTargetTile = getNearestTarget(startPos, playerPos, map);
		if(expectedTargetTile == playerPos) return playerPos;
		else return AStarSearch(playerPos, expectedTargetTile, map);	
	}
	
	//Finds the 'expected target' nearest to the player
	public Tile getNearestTarget(Tile startPos, Tile playerPos, Map map) {
		Queue<Tile> searchQ = new LinkedBlockingQueue<>();
		HashMap<Tile, Boolean> visited = new HashMap<>();
		searchQ.add(playerPos);
		while(!searchQ.isEmpty()) {
			Tile currTile = searchQ.remove();
			visited.put(currTile, true);
			for (Direction dir : Direction.values()) {
				if(dir == Direction.NONE) continue;
				Tile testTile = map.getNextTileInDir(currTile, dir);
				if(testTile == null || visited.containsKey(testTile)) continue; 
				for(LogicCondition s: expectedTargets) {
					if(s.isTarget(testTile)) return testTile;
				}
				if(!isImpassable(testTile)) searchQ.add(testTile);
			}
		}
		return playerPos;
	}

}
