package gameLogic;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class TestGameLogic {

	@Test
	void testAddExitCondition() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new ExitCondition());
		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof ExitCondition);
	}
	
	@Test
	void testAddBoulderCondition() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new BoulderSwitchesCondition());
		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof BoulderSwitchesCondition);
	}
	
	@Test
	void testAddEnemiesCondition() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new EnemiesDestroyedCondition());
		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof EnemiesDestroyedCondition);
	}
	
	@Test
	void testAddTresureCondition() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new TreasureCollectedCondition());
		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof TreasureCollectedCondition);
	}
	
	@Test
	void testAddConditionWhenConditionIsExit() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new ExitCondition());
		gameLogic.addCondition(new BoulderSwitchesCondition());
		gameLogic.addCondition(new EnemiesDestroyedCondition());
		gameLogic.addCondition(new TreasureCollectedCondition());
		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof ExitCondition);
	}
	
	@Test
	void testClearConditions() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new BoulderSwitchesCondition());
		gameLogic.addCondition(new EnemiesDestroyedCondition());
		gameLogic.addCondition(new TreasureCollectedCondition());
		
		gameLogic.clearConditions();
		
		assertTrue(gameLogic.getConditions().size() == 0);
	}

	@Test
	void testSetCondition() {
		GameLogic gameLogic = new GameLogic();
		
		gameLogic.addCondition(new BoulderSwitchesCondition());
		gameLogic.setCondition(new EnemiesDestroyedCondition());

		
		assertTrue(gameLogic.getConditions().size() == 1);
		assertTrue(gameLogic.getConditions().get(0) instanceof EnemiesDestroyedCondition);
		assertFalse(gameLogic.getConditions().get(0) instanceof BoulderSwitchesCondition);
	}
}
