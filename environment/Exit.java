package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Enemy;
import characters.Player;
import javafx.scene.image.Image;

public class Exit extends Structure {

	private Image image;
	
	/**
	* constructor for Exit
	*/
	public Exit() {
		super();
		try {
			image = new Image(new FileInputStream("src/images/exit.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* @inheritdoc
	* @return boolean
	*/
	@Override
	public boolean stepOnable() {
		return true;
	}
	
	// this structure doesnt kill players when stepped on 
	public boolean isLethal() {
		return false;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	// this structure does not interact with movables
	@Override
	void movableInteraction(Tile t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addTargetEnemy(Enemy e) {}
	
	@Override
	public void addTargetPlayer(Player p) {}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	public Structure createNew() {
		return new Exit();
	}
	
	
}
