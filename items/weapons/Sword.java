package items.weapons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import characters.Direction;
import characters.Enemy;
import characters.Player;
import environment.Map;
import environment.Movable;
import environment.Tile;
import items.Item;
import items.objective.Key;
import javafx.scene.image.Image;

/*
 * A class to represent swords
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Sword extends Weapon {

	private int health;
	private Image image;
	
	/*
	 * Class constructor
	 */
	public Sword() {
		super();
		this.setHealth(5);
		try {
			image = new Image(new FileInputStream("src/images/sword.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Get the number of remaining sword uses
	 * 
	 * @return	Health of the sword
	 */
	public int getHealth() {
		return health;
	}

	/*
	 * Set the number of sword uses
	 * 
	 * @param	health	Number of uses
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/*
	 * Pick up the sword and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up sword	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		
		// Do not pick up sword if player is already holding one
		if(player.hasItem(this)) {
			return;
		}
		
		// Pick up sword
		player.addItem(this);
		
		// Remove the item from the map
		map.removeItem(this);
		//map.removeWeapon(this);
	}
	
	/*
	 * Strike with the sword in a given direction
	 * 
	 * @param	map		Current map
	 * @param 	player	Player attacking with the sword
	 * @param	dir		Direction the player wishes to attack
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {

		// Get the player's tile
		Tile curr = map.getPlayerTile();
		
		// Get the tile that the player is attacking
		Tile dest = map.getNextTileInDir(curr, dir);		
		
		// Kill an adjacent enemy, if possible
		Enemy e = map.hasEnemy(dest);
		
		// Sword killed an enemy
		if(this.canKillEnemy(e)) {
						
			this.killEnemy(map, e);
			
			// Reduce the health of the sword
			this.health -= 1;
			
			// If the sword has been used max times, it disappears
			if(this.health == 0) {
				player.removeItem(this);
				map.removeKiller(this);
			}
		}
		
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
		return new Sword();
	}
	
	@Override
	public void addTargetMovable(Movable m) {}
	
	@Override
	public void removeTargetMovable(Movable m) {}
}
