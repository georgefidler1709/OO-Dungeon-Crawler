package items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import characters.*;
import environment.Door;
import environment.Ground;
import environment.Map;
import environment.Pit;
import environment.Tile;
import environment.Wall;
import items.potions.*;
import items.potions.hover.Hover;
import items.potions.invincibility.Invincibility;
import items.weapons.*;
import items.weapons.bomb.Bomb;
import items.objective.*;

/*
 * A class to test item functionality
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class TestItems {

	@org.junit.jupiter.api.Test
	public final void swordPickup() {
		
		// Initialize map
		Map m = new Map(2,2);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());
		
		// Add player to map
		Tile t2 = m.getTile(1, 1);
		Player p = new Player(t2);
		//t2.setPlayer(p);
		m.addPlayer(t2, p);
		
		// Add sword to map
		Sword s1 = new Sword();
		Tile t = m.getTile(1, 0);
		//t.setItem(s1);
		s1.addToMap(m, t);
		
		// Pick up sword - not holding one
		m.movePlayer(Direction.LEFT);
		assertTrue(p.hasItem(s1));
		assertNull(t2.getItem());
		
		// Add a second sword to the map
		Sword s2 = new Sword();
		//t2.setItem(s2);
		//m.addWeapon(t2, s2);
		s2.addToMap(m, t2);
		
		// Pick up sword - already holding one
		m.movePlayer(Direction.RIGHT);
		assertTrue(p.hasItem(s1));
		assertTrue(t2.getItem() == s2);
	}
	
	@org.junit.jupiter.api.Test
	public final void swordAttack() {

		// Initialize map
		Map m = new Map(2,2);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());

		// Add an enemy to the map
		Tile t1 = m.getTile(0, 0);
		Hunter h1 = new Hunter(t1);
		//t1.setEnemy(h1);
		m.addEnemy(t1, h1);
		
		// Add player to map
		Tile t2 = m.getTile(1, 1);
		Player p = new Player(t2);
		//t2.setPlayer(p);
		m.addPlayer(t2, p);
		
		// Add sword to map
		Sword s1 = new Sword();
		Tile t3 = m.getTile(1, 0);
		//t3.setItem(s1);
		//m.addWeapon(t3, s1);
		s1.addToMap(m, t3);
		
		// Pick up sword
		m.movePlayer(Direction.LEFT);
		
		// Attack nothing
		s1.use(m, p, Direction.RIGHT);
		assertTrue(s1.getHealth() == 5);
		
		// Attack enemy
		s1.use(m, p, Direction.UP);
		assertTrue(s1.getHealth() == 4);
		assertNull(t1.getEnemy());
		
		// Attack five times - check sword disappears
		int i;
		for(i = 4; i > 1; i--) {
			
			// Add another enemy to the map
			Tile t = m.getTile(0, 0);
			Hunter h = new Hunter(t);
			//t.setEnemy(h);
			m.addEnemy(t, h);
			
			// Attack enemy again
			s1.use(m, p, Direction.UP);
			
			assertTrue(s1.getHealth() == i-1);
			assertNull(t1.getEnemy());
		}
		
		// Add another enemy to the map
		Tile t = m.getTile(0, 0);
		Hunter h = new Hunter(t);
		//t.setEnemy(h);
		m.addEnemy(t, h);
		
		// Attack fifth enemy
		s1.use(m, p, Direction.UP);
		assertNull(t1.getEnemy());

		// Check player no longer has sword
		assertFalse(p.hasItem(s1));
	}
	
	@org.junit.jupiter.api.Test
	public final void arrowPickup() {
		
		// Initialize map
		Map m = new Map(3,3);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());
		m.setStructure(0, 2, new Ground());
		m.setStructure(2, 0, new Ground());
		m.setStructure(1, 2, new Ground());
		m.setStructure(2, 1, new Ground());
		m.setStructure(2, 2, new Wall());
		
		// Add player to map
		Tile t1 = m.getTile(1, 0);
		Player p = new Player(t1);
		m.addPlayer(t1, p);
		
		// Add arrow to map
		Arrow a1 = new Arrow();
		Tile t = m.getTile(2, 0);
		//m.addWeapon(t, a1);
		a1.addToMap(m,t);
		
		// Pick up arrow - not holding one
		m.movePlayer(Direction.DOWN);
		assertTrue(p.hasItem(a1));
		assertNull(t1.getItem());
		
		// Add a second sword to the map
		Arrow a2 = new Arrow();
		a2.addToMap(m, t1);
		//m.addWeapon(t1, a2);
		
		// Pick up arrow - already holding one
		m.movePlayer(Direction.UP);
		assertTrue(p.hasItem(a1));
		assertTrue(p.hasItem(a2));
		assertNull(t1.getItem());
	}
	
	@org.junit.jupiter.api.Test
	public final void arrowAttack() {
		
		// Initialize map
		Map m = new Map(3,3);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());
		m.setStructure(0, 2, new Ground());
		m.setStructure(2, 0, new Ground());
		m.setStructure(1, 2, new Ground());
		m.setStructure(2, 1, new Ground());
		m.setStructure(2, 2, new Wall());
		
		// Add player to map
		Tile t1 = m.getTile(1, 0);
		Player p = new Player(t1);
		m.addPlayer(t1, p);
		
		// Add an enemy to the map
		Tile t2 = m.getTile(0, 0);
		Hunter h1 = new Hunter(t2);
		m.addEnemy(t2, h1);
		
		// Add arrow to map
		Arrow a1 = new Arrow();
		Tile t = m.getTile(2, 0);
		a1.addToMap(m, t);
		//m.addWeapon(t, a1);
		
		// Pick up first arrow
		m.movePlayer(Direction.DOWN);
		
		// Fire arrow at structure
		a1.use(m, p, Direction.RIGHT);
		
		// Check player no longer has arrow
		assertFalse(p.hasItem(a1));

		// Add a second sword to the map
		Arrow a2 = new Arrow();
		a2.addToMap(m, t1);
		//m.addWeapon(t1, a2);
		
		// Pick up second arrow
		m.movePlayer(Direction.UP);

		// Fire arrow at enemy
		a2.use(m, p, Direction.UP);
		assertNull(t1.getEnemy());
		
		// Check player no longer has arrow
		assertFalse(p.hasItem(a2));
	}
	
	@org.junit.jupiter.api.Test
	public final void bombPickup() {
		
		// Initialize map
		Map m = new Map(2,2);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());
		
		// Add player to map
		Tile t1 = m.getTile(1, 1);
		Player p = new Player(t1);
		m.addPlayer(t1, p);
		
		// Add bomb to map
		Bomb b1 = new Bomb();
		Tile t2 = m.getTile(1, 0);
		//m.addWeapon(t2, b1);
		b1.addToMap(m, t2);
		
		// Pick up bomb - not holding one
		m.movePlayer(Direction.LEFT);
		assertTrue(p.hasItem(b1));
		assertNull(t1.getItem());
		assertTrue(!t1.hasItem());
		
		// Add a second bomb to the map
		Bomb b2 = new Bomb();
		//m.addWeapon(t1, b2);
		b2.addToMap(m, t1);
				
		// Pick up bomb - already holding one
		m.movePlayer(Direction.RIGHT);
		assertTrue(p.hasItem(b2));
		assertNull(t1.getItem());
		assertTrue(!t1.hasItem());
	}
	
	@org.junit.jupiter.api.Test
	public final void bombExplode() {
		
		// Initialize map
		Map m = new Map(3,3);
		Ground g = new Ground();
		m.getTile(0, 0).setStructure(g);
		m.getTile(1, 0).setStructure(g);
		m.getTile(0, 1).setStructure(g);
		m.getTile(1, 1).setStructure(g);
		m.getTile(2, 0).setStructure(g);
		m.getTile(2, 0).setStructure(g);
		m.getTile(2, 1).setStructure(g);
		m.getTile(1, 2).setStructure(g);
		m.getTile(2, 2).setStructure(g);
		
		// Add player to map
		Tile t = m.getTile(1, 2);
		Player p = new Player(t);
		p.addToMap(m, t);
		
		// Add bomb to map
		Bomb b1 = new Bomb();
		Tile tile = m.getTile(1, 1);
		b1.addToMap(m, tile);
		
		// Place enemies on the map
		Tile t1 = m.getTile(0, 0);
		Hunter h1 = new Hunter(t1);
		h1.addToMap(m, t1);
		
		Tile t2 = m.getTile(0, 1);
		Hunter h2 = new Hunter(t2);
		h2.addToMap(m, t2);
		
		Tile t3 = m.getTile(0, 2);
		Hunter h3 = new Hunter(t3);
		h3.addToMap(m, t3);
		
		Tile t4 = m.getTile(1, 0);
		Hunter h4 = new Hunter(t4);
		h4.addToMap(m, t4);
		
		Tile t5 = m.getTile(1, 2);
		Hunter h5 = new Hunter(t5);
		h5.addToMap(m, t5);
		
		Tile t6 = m.getTile(2, 0);
		Hunter h6 = new Hunter(t6);
		h6.addToMap(m, t6);
		
		Tile t7 = m.getTile(2, 1);
		Hunter h7 = new Hunter(t7);
		h7.addToMap(m, t7);
		
		Tile t8 = m.getTile(2, 2);
		Hunter h8 = new Hunter(t8);
		h8.addToMap(m, t8);
				
		// Pick up bomb
		m.movePlayer(Direction.LEFT);
		
		// Place bomb
		b1.use(m, p, Direction.NONE);
		assertFalse(p.hasItem(b1));
		assertTrue(b1.canKillEnemy(h1));
		assertTrue(b1.canKillPlayer(p));
		
		// Countdown fuse
		// Fuse == 2
		b1.tick(m);
		assertFalse(t1.getEnemy() == null);
		assertFalse(t2.getEnemy() == null);
		assertFalse(t3.getEnemy() == null);
		assertFalse(t4.getEnemy() == null);
		assertFalse(t5.getEnemy() == null);
		assertFalse(t6.getEnemy() == null);
		assertFalse(t7.getEnemy() == null);
		assertFalse(t8.getEnemy() == null);
		
		// Fuse == 1
		b1.tick(m);
		assertFalse(t1.getEnemy() == null);
		assertFalse(t2.getEnemy() == null);
		assertFalse(t3.getEnemy() == null);
		assertFalse(t4.getEnemy() == null);
		assertFalse(t5.getEnemy() == null);
		assertFalse(t6.getEnemy() == null);
		assertFalse(t7.getEnemy() == null);
		assertFalse(t8.getEnemy() == null);
		
		// Detonate
		b1.tick(m); // Fuse == 0
		
		// All enemies in adjacent squares dead
		assertNull(t2.getEnemy());
		assertNull(t4.getEnemy());
		assertNull(t5.getEnemy());
		assertNull(t7.getEnemy());
		
		// Enemies not in direct radius survive
		assertFalse(t1.getEnemy() == null);
		assertFalse(t3.getEnemy() == null);
		assertFalse(t6.getEnemy() == null);
		assertFalse(t8.getEnemy() == null);
		
		// Detonate
		b1.tick(m); // Fuse == -1
		
		// Bomb disappears from the map
		assertTrue(!t1.hasItem());
	}
	
	@org.junit.jupiter.api.Test
	public final void hoverPotion() {
		
		// Initialize map
		Map m = new Map(2,2);
		Ground g1 = new Ground();
		g1.addToMap(m, m.getTile(0, 0));
		Ground g2 = new Ground();
		g2.addToMap(m, m.getTile(1, 0));
		Ground g3 = new Ground();
		g3.addToMap(m, m.getTile(0, 1));
		Pit pit = new Pit();
		pit.addToMap(m, m.getTile(1, 1));

		// Add player to map
		Tile t1 = m.getTile(0, 0);
		Player p = new Player(t1);
		p.addToMap(m, t1);
		assertFalse(m.playerDead());
		assertTrue(pit.canKillPlayer(p));
		
		// Add hover potion to map
		Potion potion = new Potion(new Hover());
		Tile t = m.getTile(1, 0);
		potion.addToMap(m, t);

		// Pickup potion
		m.movePlayer(Direction.DOWN);
		assertNull(t.getItem());
		assertFalse(p.hasItem(potion));
		
		// Check potion effect
		assertFalse(pit.canKillPlayer(p));
		m.movePlayer(Direction.RIGHT);
		assertFalse(m.playerDead());
	}
	
	@org.junit.jupiter.api.Test
	public final void invincibilityPotion() {
		
		// Initialize map
		Map m = new Map(2,2);
		m.setStructure(0, 0, new Ground());
		m.setStructure(1, 0, new Ground());
		m.setStructure(0, 1, new Ground());
		m.setStructure(1, 1, new Ground());

		// Add player to map
		Tile t1 = m.getTile(0, 0);
		Player p = new Player(t1);
		p.addToMap(m, t1);
		assertFalse(m.playerDead());
		
		// Add invincibility potion to map
		Effect i = new Invincibility();
		Potion potion = new Potion(new Invincibility());
		Tile t = m.getTile(1, 0);
		potion.addToMap(m, t);
		
		// Add enemy to the map
		Hunter h = new Hunter(m.getTile(1, 1));
		h.addToMap(m, m.getTile(1, 1));
		assertTrue(h.canKillPlayer(p));
		
		// Add enemy to the map
		Hunter h2 = new Hunter(m.getTile(0, 1));
		h2.addToMap(m, m.getTile(0, 1));
		assertTrue(h2.canKillPlayer(p));

		// Pickup potion
		m.movePlayer(Direction.DOWN);
		assertNull(t.getItem());
		assertFalse(p.hasItem(potion)); // Potion is not in the player's inventory
		assertFalse(h.canKillPlayer(p));
		assertFalse(h2.canKillPlayer(p));
		assertTrue(p.canKillEnemy(h));
		assertTrue(p.canKillEnemy(h2));
		
		// Check potion effect
		m.movePlayer(Direction.RIGHT);
		m.resolveDeaths();
		assertFalse(m.playerDead());
		ArrayList<Enemy> enemies = m.getEnemies();
		assertFalse(enemies.contains(h)); // Enemy killed
		assertTrue(enemies.contains(h2)); // Enemy still alive	

		// Countdown invincibility potion
		for(int j = 0; j < 6; j++) {
			i.tick(m);
		}
		
		// Check potion effect
		//assertTrue(h.canKillPlayer(p));
		assertTrue(h2.canKillPlayer(p));
		m.movePlayer(Direction.UP);
		m.resolveDeaths();
		assertTrue(m.playerDead());
		assertTrue(m.getEnemies() != null);

	}

	@org.junit.jupiter.api.Test
	public final void pickUpTreasure() {
	
		// Initialize map
		Map m = new Map(2,2);
		Ground g = new Ground();
		m.getTile(0, 0).setStructure(g);
		m.getTile(1, 0).setStructure(g);
		m.getTile(0, 1).setStructure(g);
		Pit pit = new Pit();
		m.getTile(1, 1).setStructure(pit);

		// Add player to map
		Tile t1 = m.getTile(0, 0);
		Player p = new Player(t1);
		t1.setPlayer(p);
		
		// Add treasure to map
		Treasure treasure = new Treasure();
		Tile t = m.getTile(1, 0);
		t.setItem(treasure);
		
		// Pick up treasure
		m.movePlayer(Direction.DOWN);
		assertNull(t.getItem());
		assertTrue(p.hasItem(treasure));		
	}
	
	@org.junit.jupiter.api.Test
	public final void pickUpKey() {
	
		// Initialize map
		Map m = new Map(2,2);
		Ground g = new Ground();
		m.getTile(0, 0).setStructure(g);
		m.getTile(1, 0).setStructure(g);
		m.getTile(0, 1).setStructure(g);
		m.getTile(1, 1).setStructure(g);

		// Add player to map
		Tile t1 = m.getTile(0, 0);
		Player p = new Player(t1);
		t1.setPlayer(p);
		
		// Add key to map
		Key key = new Key();
		Tile t = m.getTile(1, 0);
		t.setItem(key);
		
		// Pick up key
		m.movePlayer(Direction.DOWN);
		assertNull(t.getItem());
		assertTrue(p.hasItem(key));		
	}
	
	@org.junit.jupiter.api.Test
	public final void useKey() {
	
		// Initialize map
		Map m = new Map(2,2);
		Ground g = new Ground();
		m.getTile(0, 0).setStructure(g);
		m.getTile(1, 0).setStructure(g);
		Door d1 = new Door(new Key());
		m.getTile(0, 1).setStructure(d1);

		// Add player to map
		Tile t1 = m.getTile(0, 0);
		Player p = new Player(t1);
		t1.setPlayer(p);
		
		// Add key to map
		Key key = new Key();
		Tile t = m.getTile(1, 0);
		t.setItem(key);
		
		Door d2 = new Door(key);		
		m.getTile(1, 1).setStructure(d2);
		
		// Pick up key
		m.movePlayer(Direction.DOWN);

		// Open door - no door
		key.use(m, p, Direction.UP);
		assertTrue(p.hasItem(key));		
		
		// Open door - ID mismatch
		m.movePlayer(Direction.UP);
		key.use(m, p, Direction.RIGHT);
		assertTrue(p.hasItem(key));
		assertTrue(d1.isDoorlocked());
		
		// Open door - door with matching ID
		m.movePlayer(Direction.DOWN);
		key.use(m, p, Direction.RIGHT);
		assertFalse(p.hasItem(key));
		assertFalse(d2.isDoorlocked());
		
	}
}