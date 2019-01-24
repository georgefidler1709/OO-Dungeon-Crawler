package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import behaviours.MoveStrategy;
import behaviours.StrategicAttack;
import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import environment.Structure;
import environment.Tile;
import gameLogic.GameLogic;
import gameLogic.LogicCondition;
import javafx.scene.image.Image;

public class Strategist extends Enemy implements TeamPlayer {
	
	private Image image;
	
	StrategicAttack stratAttack;
	//Strategist knows about the current win conditions of the game so that
	//It can anticipate where the player will want to move.
	ArrayList<LogicCondition>winCons = new ArrayList<>();
	
	public Strategist(Tile position) {
		super(position);
		stratAttack = (StrategicAttack)moveStrat;
		this.winCons = new ArrayList<LogicCondition>();
		stratAttack.setExpectedTargets(winCons);
		try {
			image =  new Image(new FileInputStream("src/images/strategist.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MoveStrategy setStartingStrat() {
		return new StrategicAttack();
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Receives the winconditions from the gameLogic
	@Override
	public void leaderUpdate(TeamLeader tl) {
		GameLogic gl = (GameLogic)tl;
		winCons = gl.getConditions();
		stratAttack.setExpectedTargets(winCons);	
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Enemy createNew() {
		return new Strategist(position);
	}
	
}
