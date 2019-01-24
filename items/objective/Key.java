package items.objective;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import characters.*;
import environment.Map;
import environment.Tile;
import items.*;
import javafx.scene.image.Image;

/*
 * A class to represent keys
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Key implements Item, TeamPlayer{


	private Image image;
		
	/*
	 * Class constructor
	 */
	public Key(){
		try {
			image = new Image(new FileInputStream("src/images/key.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	/*
	 * Get the key ID that corresponds to a door
	 * 
	 * @return		Key/door ID
	 */
	
	/*
	 * Pick up the key and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up key	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		Key drop = null;
		// Do not pick up key if player is already holding one
		if(player.hasItem(this.getClass())) {
			drop = player.getInventory().getKey();
			//System.out.println("dropped" + drop.id);
			System.out.println("dropped");
			player.removeItem(drop);
		} 
		// Pick up key
		player.addItem(this);
		//System.out.println("picked up" + this.id);
		// Remove the item from the map
		map.removeItem(this);
		if (drop != null ) {
			map.getPlayerTile().setItem(drop);
		}
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	/*
	 * Try to open a door in a given direction
	 * 
	 * @param	map		Current map
	 * @param 	player	Player using the key
	 * @param	dir		Direction the player wishes to use the key
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {

		Tile curr = map.getPlayerTile();
		Tile dest = map.getNextTileInDir(curr, dir);
		
		// Attempt to open door on adjacent tile
		if(map.openDoor(dest, this)){
			// Remove key from player's inventory
			player.removeItem(this);
		}
		
	}
	
	public boolean isSkeleton() {
		return false;
	}
	
	
	public boolean equals(Key k) {
		return this == k;
	}
	
	public boolean usable() {
		return true;
	}

	@Override
	public void leaderUpdate(TeamLeader tl) {	
	}

	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new Key();
	}
}
