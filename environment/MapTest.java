package environment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import characters.*;
import items.objective.*;
import gameEngine.*;
import gameLogic.*;

class MapTest {
	
	// helper function
	private static Map createTestMap() {
		Map map = new Map(3,3);
		map.getTile(0, 0).setStructure(new Door(new Key()));
		map.getTile(0,1).setItem(new Key());
		map.getTile(1,1).setPlayer(new Player(map.getTile(1,1)));
		map.getTile(1, 0).setStructure(new Wall());
		map.getTile(1, 2).setStructure(new Pit());
		return map;
	}
	
	private static GameLogic exitsExistLogic() {
		GameLogic logic = new GameLogic();
		logic.setCondition(new ExitCondition());
		return logic;
	}
	
	
	@Test
	void wallTest() {
		GameEngine gameEngine = new GameEngine(createTestMap(),  exitsExistLogic());
		Map map = gameEngine.getMap();
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.LEFT);
		Status status = gameEngine.tick(input);
		assertTrue(map.getPlayerTile().getX() == 1 && map.getPlayerTile().getY() == 1);
		assertTrue(status == Status.CONTINUE);
	}
	
	@Test
	void pitTest() {
		GameEngine gameEngine = new GameEngine(createTestMap(), exitsExistLogic());
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.RIGHT);
		Status status = gameEngine.tick(input);
		assertEquals(status, Status.LOSE);
	}
	
	@Test
	void doorTest() {
		GameEngine gameEngine = new GameEngine(createTestMap(), exitsExistLogic());
		Map map = gameEngine.getMap();
		Input input = new Input();
		input.setType(InputType.MOVE);
		input.setDirection(Direction.UP);
		Status status = gameEngine.tick(input);
		input.setType(InputType.USE);
		input.setDirection(Direction.LEFT);
		input.setItemNumber(0);
		status = gameEngine.tick(input);
		input.setType(InputType.MOVE);
		input.setDirection(Direction.LEFT);
		status = gameEngine.tick(input);
		assertTrue(map.getPlayerTile().getStructure().stepOnable());
		assertTrue(status == Status.CONTINUE);
	}
}
