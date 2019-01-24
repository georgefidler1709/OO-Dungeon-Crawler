package items.potions;

import characters.Direction;
import characters.Player;
import environment.Map;
import environment.Tile;
import gameLogic.Tick;
import items.*;
import javafx.scene.image.Image;

/*
 * A class to represent potions
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Potion implements Item {

	private Effect effect;
	
	/*
	 * Class constructor
	 */
	public Potion(Effect effect) {
		this.effect = effect;
	}
	
	/*
	 * Pick up the potion and immediately consume it
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up potion	
	 */
	@Override
	public void pickUp(Map map, Player player) {
		
		// Do not add to inventory, use immediately
		this.use(map, player, Direction.NONE);
		
		// Remove the item from the map
		map.removeItem(this);
	}
	
	/*
	 * Consume the potion
	 * 
	 * @param	map		Current map
	 * @param 	player	Player consuming the potion
	 * @param	dir		Direction of the action, irrelevant
	 */
	@Override
	public void use(Map map, Player player, Direction dir) {
		
		// Apply potion effect
		this.effect.applyEffect(map, player);
	}
	
	@Override
	public Image getImage() {
		return this.effect.getImage();
	}

	@Override
	public boolean usable() {
		return true;
	}
	
	@Override
	public void addToMap(Map m, Tile t) {
		m.addItem(t, this);
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Item createNew() {
		return new Potion(effect);
	}

}
