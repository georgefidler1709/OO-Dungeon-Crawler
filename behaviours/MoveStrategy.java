package behaviours;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import characters.Direction;
import environment.Ground;
import environment.Map;
import environment.Tile;

public interface MoveStrategy {
	
	//Normal A* Search, used by the vast majority of AI move strategies.
	public default Tile AStarSearch(Tile startPos, Tile target, Map map) {
		//already there
		if(startPos == target) return startPos;
		
		Comparator<AStarQueuable> comparator = new AStarComparator();
		PriorityQueue<AStarQueuable> q = new PriorityQueue<>(comparator);
		HashMap<Tile, Integer> pathLength = new HashMap<>();
		HashMap<Tile, Tile> predecessor = new HashMap<>();
		
		//start at search at agent's initial position.
		pathLength.put(startPos, 0);
		q.add(new AStarQueuable(startPos, 0, manhattanDist(startPos, target)));
		Tile curr = null;
		
		while(!q.isEmpty()) {
			curr = q.remove().getTile();			
			if(curr == target) break;
			//for the current tile, check every tile adjacent to it.
			for (Direction dir : Direction.values()) {
				if(dir == Direction.NONE) continue;
				Tile testTile = map.getNextTileInDir(curr, dir);
				//if we haven't found a faster way to that tile before, attempt to add it to the PQueue.
				if(testTile == null) continue;
				if(isShortestPathToNext(curr, testTile, pathLength)) addNeighbour(testTile, curr, target, pathLength, predecessor, q);
			}	
			
		}
		//if we couldn't reach the goal, stay put
		return (curr == target) ? getStartOfPath(curr, startPos, predecessor) : startPos;
	}
	
	//best approximation to the path length with 4-directional movement
	public default int manhattanDist(Tile curr, Tile target) {
		return Math.abs(curr.getX() - target.getX()) + Math.abs(curr.getY() - target.getY());
	}
	
	//Encapsulates the conditions by which a agent will not use a tile in it's path. Could be encapsulated in map but that
	//removes the opportunity for some enemies to avoid different squares to others e.g. let some enemies fall in pits.
	public default boolean isImpassable(Tile s) {
		return !(s.getStructure() instanceof Ground && (!s.hasEnemy() || s.hasInnocent()));
	}
	
	//Checks if this is the shortest path found so far to the 'next' tile in an A* Search.
	public default boolean isShortestPathToNext(Tile curr, Tile next, HashMap<Tile, Integer> pathLength) {
		return(!pathLength.containsKey(next) || pathLength.get(next) <= pathLength.get(curr) + 1);
		
	}	
	
	//If a tile could possibly be on an optimal path, add it to the PQueue.
	public default void addNeighbour(Tile possibleNext, Tile curr, Tile target, HashMap<Tile, Integer> pathLength, HashMap<Tile, Tile> predecessor, PriorityQueue<AStarQueuable> q ) {
		if(!isImpassable(possibleNext) || possibleNext == target) {
			if(!pathLength.containsKey(possibleNext) || pathLength.get(possibleNext) > pathLength.get(curr) + 1) {
				pathLength.put(possibleNext, pathLength.get(curr) + 1);
				predecessor.put(possibleNext, curr);
				q.add(new AStarQueuable(possibleNext, pathLength.get(curr) + 1, manhattanDist(possibleNext, target)));
			}
		}
	}
	
	//Iterative through the predecessors in a successful path to find the first tile in the path.
	public default Tile getStartOfPath(Tile pathEnd, Tile startPos, HashMap<Tile, Tile> predecessor) {
		Tile pred = predecessor.get(pathEnd);
		if(pred == startPos) return pathEnd;
		while(predecessor.get(pred) != startPos) pred = predecessor.get(pred);
		return pred;
	}
	
	//get the relative direction between two points.
	public default Direction getRelativeDir(Tile curr, Tile target) {
		int xDist = curr.getX() - target.getX();
		int yDist = curr.getY() - target.getY();
		if(xDist == 0 && yDist == 0) return Direction.NONE;
		else if (Math.abs(xDist) > Math.abs(yDist)) {
			return (xDist > 0) ? Direction.LEFT  : Direction.RIGHT;
		} 
		else return (yDist > 0) ? Direction.UP : Direction.DOWN;
	}
	
	//Objects used in the PQueue for the A* Search containing all the info needed to adequately prioritise the queue
	class AStarQueuable {
		private Tile tile;
		private int distAlongPath;
		private int distFromTarget;
		
		public AStarQueuable(Tile tile, int distAlongPath, int distFromTarget) {
			this.tile = tile;
			this.distAlongPath = distAlongPath;
			this.distFromTarget = distFromTarget;
		}

		public int getDistAlongPath() {
			return distAlongPath;
		}

		public int getDistFromTarget() {
			return distFromTarget;
		}

		public Tile getTile() {
			return tile;
		}				
	}
	
	//Comparison function used for the PQueue in A* Search
	class AStarComparator implements Comparator<AStarQueuable> {
		@Override
		public int compare(AStarQueuable a, AStarQueuable b) {
			return (a.getDistAlongPath() + a.getDistFromTarget()) - (b.getDistAlongPath() + b.getDistFromTarget());
		}
		
	}
	
	//Choose the target for the agent to search for
	public Tile chooseTargetTile(Tile startPos, Tile playerPos, Map map);
	
	//Choose the direction the agent will request to move in on it's turn
	public default Direction chooseMove(Tile startPos, Tile playerPos, Map map) {
		Tile targetTile = chooseTargetTile(startPos, playerPos, map);
		Tile nextMove = AStarSearch(startPos, targetTile, map);
		return getRelativeDir(startPos, nextMove);
	}
	

}
