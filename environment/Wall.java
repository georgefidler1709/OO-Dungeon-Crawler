package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Enemy;
import characters.Player;
import javafx.scene.image.Image;

public class Wall extends Structure {
	
	Image image;
	
	
	/**
	* constructor for Wall
	*/
	public Wall() {
		super();
		try {
			image = new Image(new FileInputStream("src/images/wall.png"));
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
		return false;
	}
	
	public boolean isLethal() {
		return false;
	}

	public void print() {
		System.out.print("  Wall  ");
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}

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
		return new Wall();
	}
	
}
