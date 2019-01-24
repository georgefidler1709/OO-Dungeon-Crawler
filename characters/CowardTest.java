package characters;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import characters.Direction;
import environment.Map;
import environment.Tile;
import environment.Wall;

class CowardTest {
	
	@Test
	void fakeAttackTest() {
		Map map = new Map(10, 10);
		
		//Test 1. If the coward is far away from the player, move towards the player
		Coward c = new Coward(map.getTile(0, 2));
		Player p = new Player(map.getTile(4, 2));
		map.getTile(4, 0).setPlayer(p);
		
		System.out.println("[" + Integer.toString(c.getY()) + "]" + "[" + Integer.toString(c.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = c.move(map);
		c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
		System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
		assertTrue(c.getPosition() == map.getTile(1, 2));
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void basicRetreatTest() {
		Map map = new Map(10, 10);
		//Test 2.1. Once within a radius of 3 moves from the player, run away.
		Coward c = new Coward(map.getTile(6, 2));
		Player p = new Player(map.getTile(9, 2));
		map.getTile(9, 2).setPlayer(p);
		
		System.out.println("[" + Integer.toString(c.getY()) + "]" + "[" + Integer.toString(c.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = c.move(map);
		c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
		System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
		assertTrue(c.getPosition() == map.getTile(5, 2));
		
		//Test 2.2. Coward should continue retreating until it hits the edge of the map.
		System.out.println("Coward should continue retreating until it hits the edge of the map.");
		while(true) {
			moveDir = c.move(map);
			c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
			System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
			if(c.getPosition() == map.getTile(0, 2)) break;
		}
		assertTrue(c.getPosition() == map.getTile(0, 2));
		
		//Test 2.3. Once the coward can no longer retreat in a straight line. It should return to moving towards the player (until it is within the radius again).
		System.out.println("Once the coward can no longer retreat in a straight line. It should return to moving towards the player (until it is within the radius again).");
		while(true) {
			moveDir = c.move(map);
			c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
			System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
			if(c.getPosition() == map.getTile(6, 2)) break;
		}
		assertTrue(c.getPosition() == map.getTile(6, 2));
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void retreatDirectionTest() {
		//Test 3. When the coward is within 3 tiles of the player, pick to retreat in the direction which will get farthest from the player the fastest.
		Map map = new Map(10, 10);
		Coward c = new Coward(map.getTile(7, 1));
		Player p = new Player(map.getTile(9, 2));
		map.getTile(9, 2).setPlayer(p);
		
		System.out.println("When the coward is within 3 tiles of the player, pick to retreat in the direction which will get farthest from the player the fastest.");
		System.out.println("[" + Integer.toString(c.getY()) + "]" + "[" + Integer.toString(c.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = c.move(map);
		c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
		System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
		assertTrue(c.getPosition() == map.getTile(6, 1));
	}
	
	@Test
	void retreatImpassableTest() {
		//test 4. Coward's retreat will also stop if a impassable tile is reached on the retreat path.
		Map map = new Map(10, 10);
		Coward c = new Coward(map.getTile(7, 1));
		Player p = new Player(map.getTile(9, 2));
		map.getTile(9, 2).setPlayer(p);
		
		System.out.println("Begin Retreat");
		System.out.println("[" + Integer.toString(c.getY()) + "]" + "[" + Integer.toString(c.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = c.move(map);
		c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
		System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
		
		System.out.println("Wall at [4][1]: Coward's retreat will also stop if a impassable tile is reached on the retreat path.");
		map.getTile(4, 1).setStructure(new Wall());
		while(true) {
			moveDir = c.move(map);
			c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
			System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
			if(c.getPosition() == map.getTile(6, 2)) break;
		}
		assertTrue(c.getPosition() == map.getTile(6, 2));
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void cowerTest() {
		//Test 5. If the coward cannot retreat any further but is still within radius of the player, the coward should cower against the boundary (not move).
		Map map = new Map(10, 10);
		Coward c = new Coward(map.getTile(3, 3));
		Tile initialPosition = c.getPosition();
		Player p = new Player(map.getTile(3, 4));
		map.getTile(3, 4).setPlayer(p);
		
		map.getTile(3, 2).setStructure(new Wall());
		System.out.println("Wall at [3][2]: If the coward cannot retreat any further but is still within radius of the player, the coward should cower against the boundary (not move).");
		System.out.println("[" + Integer.toString(c.getY()) + "]" + "[" + Integer.toString(c.getX()) + "]" + " to " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = c.move(map);
		c.setPosition(map.getNextTileInDir(c.getPosition(), moveDir));
		System.out.println(Integer.toString(c.getY()) + "," + Integer.toString(c.getX()) + "\n");
		assertTrue(c.getPosition() == initialPosition);
		System.out.println("===================");	
	}

}
