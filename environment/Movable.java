package environment;

import characters.Direction;
import frontDev.Displayable;

public interface Movable extends Displayable {
	
	// abstract method to check whether a movable can be moved in a certain direction
	public abstract boolean pushable(Map map, Direction dir);
	
	//constructor for movables
	public abstract Movable createNew();
	
	// used to add movables to a tile 
	public default void addToMap(Map m, Tile t) {
		m.setMovable(t.getY(), t.getX(), this);
	}
}
