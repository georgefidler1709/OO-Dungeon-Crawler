package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import behaviours.DirectAttack;
import behaviours.MoveStrategy;
import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import environment.Tile;
import javafx.scene.image.Image;

public class Hunter extends Enemy implements TeamLeader {
	
	private ArrayList<Hound> pets;
	private Image image;
	
	public Hunter(Tile startPos) {
		super(startPos);
		this.pets = new ArrayList<Hound>();
		try {
			image = new Image(new FileInputStream("src/images/hunter.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MoveStrategy setStartingStrat() {
		return new DirectAttack();
	}

	
	//Hunter notifies it's observers (pets) each turn
	@Override
	public void notifyTeamMates() {
		for(Hound pet : pets) {
			pet.leaderUpdate(this);
		}
		
	}
	
	//Hounds are notified whenever their hunter moves so they can ambush effectively
	@Override
	public void setPosition(Tile position) {
		this.position = position;
		notifyTeamMates();
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Used to ensure only hounds ever join the Hunter's team (observe the hunter)
	@Override
	public boolean requestedTeamMate(TeamPlayer tp) {
		if(tp instanceof Hound) return true;
		return false;
	}

	//Add a hound to the team.
	@Override
	public void addToTeam(TeamPlayer tp) {
		if(requestedTeamMate(tp)) pets.add((Hound)tp);
		
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Enemy createNew() {
		return new Hunter(position);
	}
	
}
