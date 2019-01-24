package items;

import characters.Direction;
import characters.Player;
import environment.AddToMap;
import environment.Map;
import environment.Tile;
import frontDev.Displayable;
import gameLogic.Tick;

/*
 * An interface to represent items on the map
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public interface Item extends Displayable, AddToMap {

	/*
	 * Pick up the item and add it to the player's
	 * inventory
	 * 
	 * @param	map		Current map
	 * @param 	player	Player picking up item	
	 */
	public void pickUp(Map map, Player player);
	
	/*
	 * Use an item
	 * 
	 * @param	map		Current map
	 * @param 	player	Player using the item
	 * @param	dir		Direction the player wishes to use the item
	 */
	public void use(Map map, Player player, Direction dir);

	@Override
	default public void addToMap(Map m, Tile t) {
		m.addItem(t, this);
	}
	
	public boolean usable();

	/* 
	 * Test if an item is of the same type as another item.
	 * 
	 * @param	item	Item to compare for equality of class.
	 */
	default public boolean equals(Item item) {
		if (item.getClass() == this.getClass()) {
			return true;
		}
		return false;
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	public abstract Item createNew();
	
}

