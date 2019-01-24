package characters;

import static org.junit.jupiter.api.Assertions.assertTrue;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import characters.Direction;
import environment.Exit;
import environment.Map;
import environment.Structure;
import gameLogic.ExitCondition;
import gameLogic.GameLogic;
import gameLogic.LogicCondition;

class StrategistTest {

	@Test
	void cutoffTest() {
		//Test 1. Strategist should move to cut off the player from the win condition (in this case the exit)
		Map map = new Map(10, 10);
		Strategist s = new Strategist(map.getTile(0, 1));
		Player p = new Player(map.getTile(1, 0));
		map.getTile(1, 0).setPlayer(p);
		GameLogic gl = new GameLogic();
		gl.addCondition(new ExitCondition());
		gl.addToTeam(s);
		gl.notifyTeamMates();
		map.getTile(1, 2).setStructure(new Exit());
		
		System.out.println("Exit at [1][2]: s = [" + Integer.toString(s.getY()) + "]" + "[" + Integer.toString(s.getX()) + "]" + " p = " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = s.move(map);
		s.setPosition(map.getNextTileInDir(s.getPosition(), moveDir));
		System.out.println(Integer.toString(s.getY()) + "," + Integer.toString(s.getX()) + "\n");
		assertTrue(s.getPosition() == map.getTile(1, 1));
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void smartCutOffTest() {
		//Test 2. When there are multiple win conditions on the map, the strategist will cut the player off from the one closest to the the player.
		Map map = new Map(10, 10);
		Strategist s = new Strategist(map.getTile(0, 8));
		Player p = new Player(map.getTile(1, 6));
		map.getTile(1, 6).setPlayer(p);
		GameLogic gl = new GameLogic();
		gl.addCondition(new ExitCondition());
		gl.addToTeam(s);
		gl.notifyTeamMates();
		map.getTile(1, 2).setStructure(new Exit());
		map.getTile(1, 9).setStructure(new Exit());
		
		System.out.println("Exit at [1][2] & [1][9]: s = [" + Integer.toString(s.getY()) + "]" + "[" + Integer.toString(s.getX()) + "]" + " p = " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		Direction moveDir = s.move(map);
		s.setPosition(map.getNextTileInDir(s.getPosition(), moveDir));
		System.out.println(Integer.toString(s.getY()) + "," + Integer.toString(s.getX()) + "\n");
		assertTrue(s.getPosition() == map.getTile(1, 8));
		p.getPosition().removePlayer();
		System.out.println("===================");
	}
	
	@Test
	void distantCutOffTest() {
		//Test 3. Ensures the above tests hold at a distance.
		
		Map map = new Map(10, 10);
		Strategist s = new Strategist(map.getTile(9, 9));
		Player p = new Player(map.getTile(1, 6));
		map.getTile(1, 6).setPlayer(p);
		GameLogic gl = new GameLogic();
		gl.addCondition(new ExitCondition());
		gl.addToTeam(s);
		gl.notifyTeamMates();
		map.getTile(1, 2).setStructure(new Exit());
		map.getTile(1, 9).setStructure(new Exit());
		
		System.out.println("Exit at [1][2] & [1][9]: s = [" + Integer.toString(s.getY()) + "]" + "[" + Integer.toString(s.getX()) + "]" + " p = " + "[" + Integer.toString(p.getY()) + "]" + "[" + Integer.toString(p.getX()) + "]");
		while(true) {
			Direction moveDir = s.move(map);
			s.setPosition(map.getNextTileInDir(s.getPosition(), moveDir));
			System.out.println(Integer.toString(s.getY()) + "," + Integer.toString(s.getX()) + "\n");
			if(s.getPosition() == map.getTile(1, 7)) break;
		}
		assertTrue(s.getPosition() == map.getTile(1, 7));
		System.out.println("===================");
	}
	
	@Test
	void holdPositionTest() {
		//Test 4. Once the Strategist is in position to cut off the player it should not move. This makes it a harder obstacle than one that just runs directly at you.
		Map map = new Map(10, 10);
		Strategist s = new Strategist(map.getTile(1, 7));
		Player p = new Player(map.getTile(1, 6));
		map.getTile(1, 6).setPlayer(p);
		GameLogic gl = new GameLogic();
		gl.addCondition(new ExitCondition());
		gl.addToTeam(s);
		gl.notifyTeamMates();
		map.getTile(1, 2).setStructure(new Exit());
		map.getTile(1, 9).setStructure(new Exit());
		
		System.out.println("Cutoff Location reached for Player's current position, therefore should not move.");
		Direction moveDir = s.move(map);
		s.setPosition(map.getNextTileInDir(s.getPosition(), moveDir));
		System.out.println(Integer.toString(s.getY()) + "," + Integer.toString(s.getX()) + "\n");
		assertTrue(s.getPosition() == map.getTile(1, 7));
		System.out.println("===================");
	}

}
