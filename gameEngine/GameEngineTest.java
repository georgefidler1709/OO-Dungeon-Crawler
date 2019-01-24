package gameEngine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import characters.Coward;
import characters.Direction;
import characters.Hunter;
import characters.Player;
import environment.Exit;
import environment.Ground;
import environment.Map;
import environment.Pit;
import environment.Tile;
import gameLogic.BoulderSwitchesCondition;
import gameLogic.EnemiesDestroyedCondition;
import gameLogic.ExitCondition;
import gameLogic.GameLogic;
import gameLogic.TreasureCollectedCondition;
import items.Item;
import items.objective.Treasure;
import items.weapons.Sword;

class GameEngineTest {
	
	/*
	 * THINGS TO TEST
	 * 
	 * 	boulder switches win condition (Boulders not implemented)
	 * 
	 * 	enemy movement
	 * 
	 * 
	 */
	
	
	/*
	 * These tests check for the following
	 * 		Ability to move the player
	 * 		Ability for the enemy to move
	 * 		Ability to pick up and item
	 * 		Ability to use picked up item
	 */
	@Test
	void testMovePlayer() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), exitsExistLogic());
		Map map = gameEngine.getMap();
		
		Tile tile0 = map.getTile(0,0);
		Tile tile1 = map.getTile(0,1);
		tile0.setPlayer(new Player(tile0));
		

		Input inputMoveRight = new Input();
		inputMoveRight.setType(InputType.MOVE);
		inputMoveRight.setDirection(Direction.RIGHT);

		
		//map.print();
		Status status = gameEngine.tick(inputMoveRight);
		//map.print();
		assertEquals(status, Status.CONTINUE);
		assertTrue(map.getPlayerTile().equals(tile1));
		
	}
	
	@Test
	void testEnemiesMove() {
		
		
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), enemiesDestroyedLogic());
		Map map = gameEngine.getMap();
		Tile tile0 = map.getTile(0,0);
		Tile tile2 = map.getTile(0,2);
		Tile tile3 = map.getTile(0,3);
		
		tile3.setEnemy(new Hunter(tile3));
		tile0.setPlayer(new Player(tile0));
		
		Input inputMoveRight = new Input();
		inputMoveRight.setType(InputType.MOVE);
		inputMoveRight .setDirection(Direction.RIGHT);
		
		
		Status status = gameEngine.tick(inputMoveRight);
		assertEquals(status, Status.CONTINUE);
		assertTrue(tile2.getEnemy() instanceof Hunter);
		assertFalse(tile2.getPlayer() instanceof Player);

	}
	
	
	
	@Test
	void testItemPickup() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), treasureCollectedLogic());
		Map map = gameEngine.getMap();
		Tile tile1 = map.getTile(0,2);
		Tile tile0 = map.getTile(0,0);
		
		Item treasure = new Treasure();
		tile1.setItem(treasure);
		tile0.setPlayer(new Player(tile0));
		
		
		
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.RIGHT);
		
		
		Status status = gameEngine.tick(input);
		assertFalse(map.getPlayerTile().getPlayer().hasItem(treasure));
		status = gameEngine.tick(input);
		assertTrue(map.getPlayerTile().getPlayer().hasItem(treasure));
		assertTrue(status == Status.CONTINUE);
		
	}
	
	@Test
	void testItemUse() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), enemiesDestroyedLogic());
		Map map = gameEngine.getMap();
		Tile tile0 = map.getTile(0,0);
		Tile tile1 = map.getTile(0,1);
		Tile tile2 = map.getTile(0,2);
		
		Item sword = new Sword();
		tile0.setPlayer(new Player(tile0));
		tile1.setItem(sword);
		
		Input inputMoveRight = new Input();
		inputMoveRight.setType(InputType.MOVE);
		inputMoveRight.setDirection(Direction.RIGHT);
		
		Input inputAttackRight = new Input();
		inputAttackRight.setType(InputType.USE);
		inputAttackRight.setDirection(Direction.RIGHT);
		inputAttackRight.setItemNumber(0);
		
		
		Status status = gameEngine.tick(inputMoveRight);
		tile2.setEnemy(new Coward(tile2));

		status = gameEngine.tick(inputAttackRight);

		assertEquals(status, Status.WIN);


	}

	/*
	 * These tests check for the following
	 * 		Logic Conditions Met
	 * 			Win Conditions
	 * 				Exits exist
	 * 				Treasure Collected
	 * 				Enemies Destroyed	
	 * 				Boulder Switches (Not available)
	 * 			Loss Conditions
	 * 				Death From Structure
	 * 				Death From Enemy
	 */
	@Test
	void testGameShowsWinExitConditon() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), exitsExistLogic());
		Map map = gameEngine.getMap();
		Tile tile0 = map.getTile(0,0);
		Tile tile1 = map.getTile(0,2);
		tile1.setStructure(new Exit());
		tile0.setPlayer(new Player(tile0));
		
		
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.RIGHT);
		Status status = gameEngine.tick(input);
		assertEquals(status, Status.CONTINUE);
		status = gameEngine.tick(input);
		assertEquals(status, Status.WIN);
	}
	
	@Test
	void testGameShowsLossOnDeadPlayerFromStructure() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), exitsExistLogic());
		Map map = gameEngine.getMap();
		Tile tile0 = map.getTile(0,0);
		Tile tile1 = map.getTile(0,2);
		tile1.setStructure(new Pit());
		tile0.setPlayer(new Player(tile0));

		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.RIGHT);
		

		Status status = gameEngine.tick(input);

		assertEquals(status, Status.CONTINUE);
		status = gameEngine.tick(input);

		assertEquals(status, Status.LOSE);
		
	}
	
	@Test
	void testGameShowsLossOnDeadPlayerFromEnemy() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), exitsExistLogic());
		Map map = gameEngine.getMap();
		
		Tile tile0 = map.getTile(0,0);
		tile0.setPlayer(new Player(tile0));
		
		Tile tile1 = map.getTile(0,3);
		tile1.setEnemy(new Hunter(tile1));

		Input inputMoveRight = new Input();
		inputMoveRight.setType(InputType.MOVE);
		inputMoveRight.setDirection(Direction.RIGHT);
		
		Input inputMoveUp = new Input();
		inputMoveUp.setType(InputType.MOVE);
		inputMoveUp.setDirection(Direction.UP);
		Status status = gameEngine.tick(inputMoveRight);
		assertEquals(status, Status.CONTINUE);
		
		
		status = gameEngine.tick(inputMoveRight);
		assertEquals(status, Status.LOSE);
		
	}
	
	
	@Test
	void testGameLogicBoulderSwitchesCondition() {
		/*
		 * Not included yet
		 */
		assertTrue(true);
	}
	
	
	
	@Test
	void testGameLogicTreasureCollectedCondition() {
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), treasureCollectedLogic());
		Map map = gameEngine.getMap();
		Tile tile1 = map.getTile(0,2);
		Tile tile0 = map.getTile(0,0);
		
		tile1.setItem(new Treasure());
		tile0.setPlayer(new Player(tile0));
		
		
		
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.RIGHT);
		Status status = gameEngine.tick(input);
		assertEquals(status, Status.CONTINUE);
		status = gameEngine.tick(input);
		assertEquals(status, Status.WIN);
		
		
	}
	
	@Test
	void testGameLogicEnemiesDestroyedCondition() {
		
		/*
		 * Enemies are currently not moving to player square
		 * Will need to retest once that is done
		 * 
		 */
		
		
		GameEngine gameEngine = new GameEngine(newGroundMap(4,4), enemiesDestroyedLogic());
		Map map = gameEngine.getMap();
		Tile tile0 = map.getTile(0,0);
		Tile tile1 = map.getTile(0,1);
		Tile tile2 = map.getTile(0,3);

		
		tile2.setEnemy(new Hunter(tile2));
		tile0.setPlayer(new Player(tile0));
		tile1.setItem(new Sword());

		
		Input inputMoveRight = new Input();
		inputMoveRight.setType(InputType.MOVE);
		inputMoveRight .setDirection(Direction.RIGHT);
		
		Input inputAttackRight = new Input();
		inputAttackRight.setType(InputType.USE);
		inputAttackRight.setDirection(Direction.RIGHT);
		inputAttackRight.setItemNumber(0);
		
		
		Status status = gameEngine.tick(inputMoveRight);
		assertEquals(status, Status.CONTINUE);

		
		status = gameEngine.tick(inputAttackRight);
		assertEquals(status, Status.WIN);


	}
	
	
	//Helper functions for set up
	Map newGroundMap(int x, int y) {
		Map map = new Map(x,y);
		Iterator<Tile> mapIterator = map.iterator();
		while(mapIterator.hasNext()) {
			Tile tile = mapIterator.next();
			tile.setStructure(new Ground());
		}
		return map;
	}
	
	GameLogic exitsExistLogic() {
		GameLogic logic = new GameLogic();
		logic.setCondition(new ExitCondition());
		return logic;
	}
	
	GameLogic boulderSwitchesLogic() {
		GameLogic logic = new GameLogic();
		logic.setCondition(new BoulderSwitchesCondition());
		return logic;
	}
	
	GameLogic enemiesDestroyedLogic() {
		GameLogic logic = new GameLogic();
		logic.setCondition(new EnemiesDestroyedCondition());
		return logic;
	}
	
	GameLogic treasureCollectedLogic() {
		GameLogic logic = new GameLogic();
		logic.setCondition(new TreasureCollectedCondition());
		return logic;
	}
	

}
