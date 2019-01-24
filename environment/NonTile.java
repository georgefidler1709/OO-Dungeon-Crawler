package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;


//Non-interactive structure. 
//Used to remove tiles from the map.
public class NonTile extends Structure {
	
	Image image; 
	
	public NonTile() {
		try {
			image = new Image(new FileInputStream("src/images/nontile.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	boolean stepOnable() {
		return false;
	}

	@Override
	void movableInteraction(Tile t) {	
	}

	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Structure createNew() {
		return new NonTile();
	}

}
