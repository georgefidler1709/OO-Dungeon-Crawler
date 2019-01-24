package characters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import environment.Map;

class HoundTest {

	@Test
	void ambushTest() {
		//Test 1. Hound will move to the opposite side of the player as the hunter to perform a pincer attack
		Map map = new Map(10, 10);
		Hunter hunter = new Hunter(map.getTile(0, 0));
		Hound hound = new Hound(map.getTile(2, 1), hunter);
		Player player = new Player(map.getTile(1, 0));
		map.getTile(1, 0).setPlayer(player);
		
		System.out.println("Hound: [" + Integer.toString(hound.getY()) + "]" + "[" + Integer.toString(hound.getX()) + "]" + " Player: " + "[" + Integer.toString(player.getY()) + "]" + "[" + Integer.toString(player.getX()) + "]" + " Hunter: " + "[" + Integer.toString(hunter.getY()) + "]" + "[" + Integer.toString(hunter.getX()) + "]");
		Direction moveDir = hound.move(map);
		hound.setPosition(map.getNextTileInDir(hound.getPosition(), moveDir));
		System.out.println(Integer.toString(hound.getY()) + "," + Integer.toString(hound.getX()) + "\n");
		assertTrue(hound.getPosition() == map.getTile(2, 0));
		player.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void distantAmushTest() {
		//Test 2. Show the property above works at a distance
		Map map = new Map(10, 10);
		Hunter hunter = new Hunter(map.getTile(3, 3));
		Hound hound = new Hound(map.getTile(1, 9), hunter);
		Player player = new Player(map.getTile(4, 6));
		map.getTile(4, 6).setPlayer(player);
		
		System.out.println("Hound: [" + Integer.toString(hound.getY()) + "]" + "[" + Integer.toString(hound.getX()) + "]" + " Player: " + "[" + Integer.toString(player.getY()) + "]" + "[" + Integer.toString(player.getX()) + "]" + " Hunter: " + "[" + Integer.toString(hunter.getY()) + "]" + "[" + Integer.toString(hunter.getX()) + "]");
		while(hound.getPosition() != map.getTile(4, 7)) {
			Direction moveDir = hound.move(map);
			hound.setPosition(map.getNextTileInDir(hound.getPosition(), moveDir));
			System.out.println(Integer.toString(hound.getY()) + "," + Integer.toString(hound.getX()) + "\n");
		}
		assertTrue(hound.getPosition() == map.getTile(4, 7));
	}
	
	@Test
	void holdPincerPosition() {
		//Test 3. Once in this ambush position the hound will not attempt to move into the player, acting more as a trap to keep the player in position for the hunter.
		Map map = new Map(10, 10);
		Hunter hunter = new Hunter(map.getTile(3, 3));
		Hound hound = new Hound(map.getTile(4, 7), hunter);
		Player player = new Player(map.getTile(4, 6));
		map.getTile(4, 6).setPlayer(player);
		Direction moveDir = hound.move(map);
		
		System.out.println("Target Reached: Should not move.");
		hound.setPosition(map.getNextTileInDir(hound.getPosition(), moveDir));
		System.out.println(Integer.toString(hound.getY()) + "," + Integer.toString(hound.getX()) + "\n");
		assertTrue(hound.getPosition() == map.getTile(4, 7));
		player.getPosition().removePlayer();
		System.out.println("===================");
	}	
	
	void revengeTest() {
		//Test 4. If the Hunter is killed mid-game, the hound will instead just directly attack the player to get revenge.
		Map map = new Map(10, 10);
		Hunter hunter = new Hunter(map.getTile(3, 3));
		Hound hound = new Hound(map.getTile(9, 4), hunter);
		hound.leaderUpdate(null);
		Player player = new Player(map.getTile(2, 7));
		map.getTile(2, 7).setPlayer(player);

		System.out.println("Hunter Dead: [" + Integer.toString(hound.getY()) + "]" + "[" + Integer.toString(hound.getX()) + "]" + " to " + "[" + Integer.toString(player.getY()) + "]" + "[" + Integer.toString(player.getX()) + "]");
		while(hound.getPosition() != player.getPosition()) {
			Direction moveDir = hound.move(map);
			hound.setPosition(map.getNextTileInDir(hound.getPosition(), moveDir));
			System.out.println(Integer.toString(hound.getY()) + "," + Integer.toString(hound.getX()) + "\n");
		}
		assertTrue(hound.getPosition() == player.getPosition());
		System.out.println("===================");
		
	}

}
