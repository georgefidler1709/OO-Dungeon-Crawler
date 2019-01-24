package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Direction;
import javafx.scene.image.Image;

public class Boulder implements Movable {
	
	private Image image;
	
	public Boulder(){
		try {
			image = new Image(new FileInputStream("src/images/boulder.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	* checks whether a boulder is pushable in a given direction
	* @param map the associated map
	* @param dir direction that boulder will be pushed in
	* @return whether or not that boulder can be push in provided direction
	*/
	@Override
	public boolean pushable(Map map, Direction dir) {
		// TODO Auto-generated method stub
		Tile target = map.getNextTileInDir(map.getPlayerTile(), dir);
		target = map.getNextTileInDir(target, dir);
		if (target.stepOnable() == false){ // assumes u can push boulder through open doors
			return false;
		} else if (target.getEnemy() != null) {
			return false;
		} else if (target.hasMovable()) {
			return false;
		}
		return true;
	}


	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}

	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Movable createNew() {
		return new Boulder();
	}
	
}
