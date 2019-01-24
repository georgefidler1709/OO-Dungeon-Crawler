package items.objective;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Direction;
import characters.Player;
import environment.Map;
import environment.Tile;
import items.Item;
import javafx.scene.image.Image;

public class SkeletonKey extends Key {

	private Image image;
	
	/*
	 * Class constructor
	 */
	public SkeletonKey(){
		try {
			image = new Image(new FileInputStream("src/images/ske1.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void use(Map map, Player player, Direction dir) {

		Tile curr = map.getPlayerTile();
		Tile dest = map.getNextTileInDir(curr, dir);
		
		// Attempt to open door on adjacent tile
		if(map.openDoor(dest, this)){
			// Remove key from player's inventory
			//player.removeItem(this);
		}
		
	}

	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	
	@Override
	public void pickUp(Map map, Player player) {
		player.addItem(this);
		//System.out.println("picked up" + this.id);
		// Remove the item from the map
		map.removeItem(this);
	}
	
	@Override
	public boolean isSkeleton() {
		return true;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new SkeletonKey();
	}
	
}
