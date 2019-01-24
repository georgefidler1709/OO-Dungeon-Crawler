package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import behaviours.DirectAttack;
import behaviours.MoveStrategy;
import behaviours.TeamAmbush;
import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import environment.Tile;
import javafx.scene.image.Image;

public class Hound extends Enemy implements TeamPlayer {
	
	//hound observes/subscribes to hunter. Both to know it's current location and to be informed of it's death.
	private TeamAmbush ambushWithHunter;
	private DirectAttack revengeAttack;
	private Image image;
	
	//Ensures that a hunter must exist to initialise a hound.
	public Hound(Tile startPos, Hunter h) {
		super(startPos);
		ambushWithHunter = (TeamAmbush)moveStrat;
		ambushWithHunter.setLeaderPos(h.getPosition());
		h.addToTeam(this);
		
		revengeAttack = new DirectAttack();
		try {
			image = new Image(new FileInputStream("src/images/hound.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Hound(Tile startPos) {
		super(startPos);
		ambushWithHunter = (TeamAmbush)moveStrat;		
		revengeAttack = new DirectAttack();
		try {
			image = new Image(new FileInputStream("src/images/hound.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void changeStrategy() {
		moveStrat = revengeAttack;
	}

	//Used to get updates on hunter's position. When the hunter dies it will return null
	//Prompting the hound to switch to it's revenge state.
	@Override
	public void leaderUpdate(TeamLeader tl) {
		
		Hunter h = (Hunter)tl;
		Tile leaderPos = h.getPosition();
		if(leaderPos == null) {
			changeStrategy();
			return;
		}
		ambushWithHunter.setLeaderPos(leaderPos);
	}

	@Override
	public MoveStrategy setStartingStrat() {
		return new TeamAmbush();
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Enemy createNew() {
		return new Hound(position);
	}

}
