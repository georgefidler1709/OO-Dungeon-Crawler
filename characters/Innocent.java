package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import behaviours.GetToExit;
import behaviours.MoveStrategy;
import environment.Tile;
import javafx.scene.image.Image;

// New class added for the game.
// Innocent will not attack the player but will instead move to the exit.
// This servers 2 purposes:
// 1) You either need to race him to the exit or kill him to complete the exit win condition.
// 2) Most Importantly, it allows for a new win condition where the goal is to ensure the innocent gets to the exit safely.
// This allows for new escort mission / puzzle maps.
public class Innocent extends Enemy {
	
	private Image image;

	public Innocent(Tile startPos) {
		super(startPos);
		try {
			image = new Image(new FileInputStream("src/images/innocent.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public MoveStrategy setStartingStrat() {
		return new GetToExit();
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Enemy createNew() {
		return new Innocent(position);
	}

}
