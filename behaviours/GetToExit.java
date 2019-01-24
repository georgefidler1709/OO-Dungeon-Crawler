package behaviours;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import characters.Direction;
import environment.Exit;
import environment.Ground;
import environment.Map;
import environment.Tile;
import gameLogic.LogicCondition;

//Simple behaviour to head straight for the exit.
//Used by the Innocent.
public class GetToExit implements MoveStrategy {

	@Override
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map) {
		return findExit(startPos, playerPos, map);
	}
	
	//Scan the map in a floorfill to find the closest exit to the start position.
	public Tile findExit(Tile startPos, Tile playerPos, Map map) {
		Queue<Tile> searchQ = new LinkedBlockingQueue<>();
		HashMap<Tile, Boolean> visited = new HashMap<>();
		searchQ.add(startPos);
		while(!searchQ.isEmpty()) {
			Tile currTile = searchQ.remove();
			visited.put(currTile, true);
			for (Direction dir : Direction.values()) {
				if(dir == Direction.NONE) continue;
				Tile testTile = map.getNextTileInDir(currTile, dir);
				if(testTile == null || visited.containsKey(testTile)) continue; 
				if(testTile.hasExit()) return testTile;
				if(!isImpassable(testTile)) searchQ.add(testTile);
			}
		}
		return playerPos;
	}
	
	//Entities using this movestrategy are not allowed to enter the same square as the player as this would kill them.
	//Users of this strategy are meant to be innocent bystanders.
	@Override
	public boolean isImpassable(Tile s) {
		return !(s.stepOnable() && ! s.hasPlayer());
	}

}