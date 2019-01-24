package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import behaviours.CowardlyAttack;
import behaviours.MoveStrategy;
import behaviours.Retreat;
import environment.Map;
import environment.Tile;
import javafx.scene.image.Image;

public class Coward extends Enemy {
	
	//range at which the coward will retreat from the player
	private final int distToRetreat = 3;
	
	//2 possible strategies depending on state.
	private CowardlyAttack fakeAttack;
	private Retreat retreat;
	private Image image;
	
	public Coward(Tile startPos) {
		super(startPos);
		retreat = new Retreat();
		fakeAttack = (CowardlyAttack)moveStrat;
		fakeAttack.setDistToRetreat(distToRetreat);	
		try {
			image = new Image(new FileInputStream("src/images/coward.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Both MoveStrategies return NONE to request a state change.
	//This function allows that to occur.
	@Override
	public Direction move(Map map) {
		Tile playerPos = map.getInitialPlayerPos();
		Direction nextMove = moveStrat.chooseMove(position, playerPos, map);
		if(nextMove == Direction.NONE) changeStrat();
		nextMove = moveStrat.chooseMove(position, playerPos, map);
		if(nextMove != Direction.NONE) return nextMove;
		return Direction.NONE;
	}
	
	//Switch between MoveStrategies
	public void changeStrat() {
		if(moveStrat instanceof CowardlyAttack) moveStrat = retreat;
		else moveStrat = fakeAttack;
	}
	
	public MoveStrategy setStartingStrat() {
		return new CowardlyAttack();
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Enemy createNew() {
		return new Coward(position);
	}
}
