package items.weapons.bomb;

import java.util.ArrayList;

import characters.Direction;
import characters.Enemy;
import characters.Player;
import environment.Map;
import environment.Movable;
import environment.Tile;
import gameLogic.Tick;
import items.Item;
import items.objective.Key;
import items.weapons.Weapon;
import javafx.scene.image.Image;

/*
 * A class to represent bombs
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Bomb extends Weapon implements Tick{

	private Tile position; // Position once the bomb has been placed
	private BombState bombState;
	
	/*
	 * Class constructor
	 */
	public Bomb() {
		super();
		this.position = null;
		this.bombState = new Unlit();
	}
	
	/*
	 * Pick up the bomb and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up bomb	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		
		this.bombState.pickUp(map, player, this);

	}
	
	/*
	 * Place a bomb and light it
	 * 
	 * @param	map		Current map
	 * @param 	player	Player placing the bomb
	 * @param	dir		Direction the player wishes to place the bomb
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {
		
		Tile curr = map.getPlayerTile();
		Tile dest = map.getNextTileInDir(curr, dir);
		
		// Check if bomb can be placed
		if(map.stepOnable(dest) && map.emptyTile(dest)) {
						
			// Light bomb and place on map
			//this.setLit(true);
			
			map.addItem(dest, this);
			map.addTick(this);
			this.position = dest;
			this.bombState = new Lit1();
			
			// Remove bomb from the player's inventory
			player.removeItem(this);
		}
		
	}
	
	// Update the bomb according to a tick of the game state
	public boolean tick(Map m) {
		return this.bombState.tick(m, this);
	}
	
	public void setBombState(BombState b) {
		this.bombState = b;
	}
	
	public Tile getPosition() {
		return this.position;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return this.bombState.getImage();
	}

	@Override
	public boolean usable() {
		return true;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new Bomb();
	}
  
	public void addToMap(Map m, Tile t) {
		m.addItem(t, this);
		m.addKiller(this);
		
		// Add any enemies on the map to the list of targets in the arrow
		ArrayList<Enemy> e = m.getEnemies();
		for(Enemy enemy: e) {
			this.addTargetEnemy(enemy);
		}
		
		ArrayList<Movable> mov = m.getMovables();
		for(Movable movable: mov) {
			this.addTargetMovable(movable);
		}
		
		Player p = m.getPlayer();
		if(p != null) {
			this.addTargetPlayer(p);
		}
	}

}
