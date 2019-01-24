package characters;

import behaviours.MoveStrategy;
import behaviours.Retreat;
import environment.AddToMap;
import environment.Map;
import environment.Movable;
import environment.Tile;
import frontDev.Displayable;
import gameLogic.Killer;
import items.potions.invincibility.ObserveInvincible;

public abstract class Enemy extends Killer implements Displayable, ObserveInvincible, AddToMap{
	
	protected MoveStrategy moveStrat;
	protected Tile position;

	//Ensures every enemy must have a MoveStrategy and a position on the map
	public Enemy(Tile startPos) {
		this.moveStrat = setStartingStrat();
		this.position = startPos;
	}
	
	public Direction move(Map map) {
		return moveStrat.chooseMove(this.position, map.getInitialPlayerPos(), map);
	}
	
	public Tile getPosition() {
		return position;
	}
	
	public void setPosition(Tile position) {
		this.position = position;
	}
	
	public int getY() {
		return position.getY();
	}
	
	public int getX() {
		return position.getX();
	}
	
	public abstract MoveStrategy setStartingStrat();
	
	@Override
	public void addTargetMovable(Movable m) {}
	
	@Override
	public void removeTargetMovable(Movable m) {}
	
	@Override
	public void notifyInvincible(Player p) {
		this.removeTargetPlayer(p);
		this.moveStrat = new Retreat();
	}
	
	@Override
	public void notifyUninvincible(Player p) {
		this.addTargetPlayer(p);
		this.setStartingStrat();
	}
		
//	public void setSpecialBehaviour(MoveStrategy ms) {
//		this.moveStrat = ms;
//	}
//	
//	public void removeSpecialBehaviour() {
//		this.moveStrat = setStartingStrat();
//	}
//	
	@Override
	public void addToMap(Map m, Tile t) {
		m.addEnemy(t, this);
		
		// Add any players to the list of targets
		if(m.getPlayer() != null) {
			this.addTargetPlayer(m.getPlayer());
		}
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	public abstract Enemy createNew();

}
