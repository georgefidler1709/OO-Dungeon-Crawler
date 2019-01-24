package items.weapons;

import java.util.ArrayList;

import characters.Direction;
import characters.Enemy;
import characters.Player;
import environment.Map;
import environment.Tile;
import gameLogic.Killer;
import items.Item;
import environment.Movable;

public abstract class Weapon extends Killer implements Item {

	public Weapon() {
		super();
	}
	
	@Override
	abstract public void pickUp(Map map, Player player);

	@Override
	abstract public void use(Map map, Player player, Direction dir);
	
	@Override
	public void addToMap(Map m, Tile t) {
		m.addItem(t, this);
		m.addKiller(this);
		
		// Add any enemies on the map to the list of targets in the arrow
		ArrayList<Enemy> e = m.getEnemies();
		for(Enemy enemy: e) {
			this.addTargetEnemy(enemy);
		}
	}

}
