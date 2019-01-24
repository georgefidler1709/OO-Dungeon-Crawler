package environment;

import frontDev.Displayable;
import gameLogic.Killer;
import items.potions.hover.ObserveHover;

public abstract class Structure extends Killer implements Displayable, ObserveHover, AddToMap {
	
	public Structure() {
		
	}
	
	/**
 	* whether this structure is accessible by a character
 	*/
	abstract boolean stepOnable();

	@Override
	public void addTargetMovable(Movable m) {}
	
	@Override
	public void removeTargetMovable(Movable m) {}
	
	abstract void movableInteraction(Tile t);

	@Override
	public void addToMap(Map m, Tile t) {
		m.setStructure(t.getY(), t.getX(), this);
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	public abstract Structure createNew();
}
