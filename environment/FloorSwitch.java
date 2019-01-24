package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Enemy;
import characters.Player;
import javafx.scene.image.Image;

public class FloorSwitch extends Structure{
	private boolean triggered;
	private Image image;
	
	/**
	* constructor for FloorSwitch
	*/
	public FloorSwitch() {
		super();
		triggered = false;
		try {
			image = new Image(new FileInputStream("src/images/floor_switch.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	* @return boolean
	*/
	public boolean triggered(){
		return triggered;
	}
	
	/**
	* @inheritdoc
	* @return boolean
	*/
	public boolean stepOnable() {
		return true;
	}
	
	// this tile structure does not kill players when stepped on
	public boolean isLethal() {
		return false;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}

	//triggers if this floor switch has a movable on it
	@Override
	void movableInteraction(Tile t) {
		if (t.hasMovable()){
			this.triggered = true;
		} else {
			this.triggered = false;
		}
	}
	
	@Override
	public void addTargetEnemy(Enemy e) {}
	
	@Override
	public void addTargetPlayer(Player p) {}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Structure createNew() {
		return new FloorSwitch();
	}
	
	
}
