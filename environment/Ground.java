package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Enemy;
import characters.Player;
import javafx.scene.image.Image;

public class Ground extends Structure {
	
	private Image image;
	
	/**
	* constructor for Ground
	*/
	public Ground() {
		super();
		try {
			image = new Image(new FileInputStream("src/images/ground.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* @inheritdoc
	* @return boolean
	*/
	public boolean stepOnable() {
		return true;
	}
	
	public boolean isLethal() {
		return false;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	// doesnt interact with any movables on it
	@Override
	void movableInteraction(Tile t) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void addTargetEnemy(Enemy e) {}
	
	@Override
	public void addTargetPlayer(Player p) {}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Structure createNew() {
		return new Ground();
	}
	

}
