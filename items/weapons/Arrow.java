package items.weapons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import characters.*;
import environment.Map;
import environment.Movable;
import environment.Tile;
import items.Item;
import items.objective.Key;
import javafx.scene.image.Image;

/*
 * A class to represent arrows
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Arrow extends Weapon {
	
	private Image image;
	
	public Arrow() {
		super();
		try {
			image = new Image(new FileInputStream("src/images/arrow.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Pick up the arrow and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up arrow	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		
		// Pick up the arrow
		player.addItem(this);
		
		// Remove the item from the map
		map.removeItem(this);
		//map.removeWeapon(this);
	}
	
	/*
	 * Fire the arrow in a given direction
	 * 
	 * @param	map		Current map
	 * @param 	player	Player firing the arrow
	 * @param	dir		Direction the player wishes to shoot
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {
		
		// Get the player's tile
		Tile curr = map.getPlayerTile();
		
		// Get the tile that the player is aiming at
		Tile dest = map.getNextTileInDir(curr, dir);
		
		// Fire arrow in a given direction
		while(dest != null) {
			
			// Arrow has hit a structure
			if(!map.stepOnable(dest)) {
				player.removeItem(this);
				break;
			}
			
			// Arrow has hit an enemy
			Enemy e = map.hasEnemy(dest);
			if(e != null) {
				this.killEnemy(map, e);
			}

			// Check the next tile on arrow's path
			dest = map.getNextTileInDir(dest, dir);
		}

		// Remove the item from the player's inventory
		player.removeItem(this);
		map.removeKiller(this);
	}
	
	public boolean usable() {
		return true;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new Arrow();
	}
	
	@Override
	public void addTargetMovable(Movable m) {}
	
	@Override
	public void removeTargetMovable(Movable m) {}
}
