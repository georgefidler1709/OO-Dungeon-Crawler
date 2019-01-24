package items.objective;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import characters.*;
import environment.Map;
import items.*;
import javafx.scene.image.Image;

/*
 * A class to represent treasure
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Treasure implements Item {
	
	private Image image;
	
	public Treasure() {
		try {
			image = new Image(new FileInputStream("src/images/treasure.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Pick up the treasure and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up treasure	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		
		// Pick up key
		player.addItem(this);
		
		// Remove the item from the map
		map.removeItem(this);
	}
	
	/*
	 * Treasure cannot be used
	 * 
	 * @param	map		Current map
	 * @param 	player	Player holding treasure
	 * @param	dir		Direction of action, irrelevant
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {
	}
	
	public boolean usable() {
		return false;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new Treasure();
	}
	
}
