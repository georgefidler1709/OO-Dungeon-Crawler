package characters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import environment.Map;
import environment.Tile;
import environment.Wall;

class HunterTest {

	@Test
	void normalMoveTest() {
		//Test 1. Hunter will move towards the player
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(0, 0));
		Player p = new Player(map.getTile(1, 0));
		map.getTile(1, 0).setPlayer(p);
		
		System.out.println("[" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = h.move(map);
		h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
		System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");
		assertTrue(h.getPosition() == p.getPosition());
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void reverseMoveTest() {
		//Test 2. Will move in the other direction
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(0, 0));
		Player p = new Player(map.getTile(0, 1));
		map.getTile(0, 1).setPlayer(p);

		System.out.println("[" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = h.move(map);
		h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
		System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");
		assertTrue(h.getPosition() == p.getPosition());
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void MultiMoveTest() {
		//Test 3. Will travel across the map to reach the player in the minimum number of moves.
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(1, 3));
		Player p = new Player(map.getTile(8, 7));
		map.getTile(8, 7).setPlayer(p);

		System.out.println("[" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		int turnCounter = 1;
		int turnNumTarget = Math.abs(h.getY() - p.getY()) + Math.abs(h.getX() - p.getX());
		while(true) {
			Direction moveDir = h.move(map);
			h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
			System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");
			if(h.getPosition() == p.getPosition()) break;
			++turnCounter;	
		}
		assertTrue(turnCounter == turnNumTarget && h.getPosition() == p.getPosition());
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void reverseMultiMoveTest() {
		//Test 4. Will travel across the map to reach the player in the reverse direction.
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(7, 6));
		Player p = new Player(map.getTile(2, 2));
		map.getTile(2, 2).setPlayer(p);
		
		System.out.println("[" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		int turnCounter = 1;
		int turnNumTarget = Math.abs(h.getY() - p.getY()) + Math.abs(h.getX() - p.getX());
		while(true) {
		Direction moveDir = h.move(map);
		h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
			System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");
			if(h.getPosition() == p.getPosition()) break;
			++turnCounter;
			
		}
		assertTrue(turnCounter == turnNumTarget && h.getPosition() == p.getPosition());
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void avoidObstaclesTest() {
		//Test 5. Avoid obstacles to reach the player.
		
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(6, 8));
		Player p = new Player(map.getTile(6, 6));
		map.getTile(6, 6).setPlayer(p);
		
		map.getTile(8, 8).setStructure(new Wall());
		map.getTile(8, 7).setStructure(new Wall());
		map.getTile(7, 8).setStructure(new Wall());
		map.getTile(7, 7).setStructure(new Wall());
		map.getTile(6, 7).setStructure(new Wall());
		map.getTile(5, 7).setStructure(new Wall());
		map.getTile(4, 7).setStructure(new Wall());
		map.getTile(3, 7).setStructure(new Wall());
		map.getTile(2, 7).setStructure(new Wall());
		
		System.out.println("Around obstacles: [" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		int turnCounter = 1;
		int turnNumTarget = Math.abs(h.getY() - p.getY()) + Math.abs(h.getX() - p.getX());
		while(true) {
		Direction moveDir = h.move(map);
		h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
			System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");
			if(h.getPosition() == p.getPosition()) break;
			++turnCounter;
			
		}
		assertTrue(turnCounter > turnNumTarget && h.getPosition() == p.getPosition());
		p.getPosition().removePlayer();
		System.out.println("===================");
	}	
	
	@Test
	void trappedTest() {
		//Test 6. If it is impossible for the hunter to reach the player, do not move.
		Map map = new Map(10, 10);
		Hunter h = new Hunter(map.getTile(6, 8));
		Tile initialState = h.getPosition();
		Player p = new Player(map.getTile(6, 6));
		map.getTile(6, 6).setPlayer(p);
		
		map.getTile(9, 7).setStructure(new Wall());
		map.getTile(8, 7).setStructure(new Wall());
		map.getTile(7, 7).setStructure(new Wall());
		map.getTile(6, 7).setStructure(new Wall());
		map.getTile(5, 7).setStructure(new Wall());
		map.getTile(4, 7).setStructure(new Wall());
		map.getTile(3, 7).setStructure(new Wall());
		map.getTile(2, 7).setStructure(new Wall());
		map.getTile(1, 7).setStructure(new Wall());
		map.getTile(0, 7).setStructure(new Wall());
		
		System.out.println("Unreachable: [" + Integer.toString(h.getY()) + "]" + "[" + Integer.toString(h.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = h.move(map);
		h.setPosition(map.getNextTileInDir(h.getPosition(), moveDir));
			System.out.println(Integer.toString(h.getY()) + "," + Integer.toString(h.getX()) + "\n");

		assertTrue(h.getPosition() == initialState);
		p.getPosition().removePlayer();
		System.out.println("===================");	
	}
}
