package gameLogic;

import java.util.ArrayList;
import characters.Enemy;
import characters.Player;
import environment.Map;
import environment.Movable;

public abstract class Killer {
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Player> players;
	private ArrayList<Movable> movables;
	
	/**
	 * A constructor for the Killer object
	 */
	public Killer() {
		this.enemies = new ArrayList<Enemy>();
		this.players = new ArrayList<Player>();
		this.movables = new ArrayList<Movable>();
	}
	
	/**
	 * @param Enemy
	 * Adds an Enemy to the list
	 */
	public void addTargetEnemy(Enemy e) {
		this.enemies.add(e);
	}
	
	/**
	 * @param Player
	 * Adds an Player to the list
	 */
	public void addTargetPlayer(Player p) {
		this.players.add(p);
	}
	
	/**
	 * @param Movable
	 * Adds an Movable to the list (default behaviour is to do nothing)
	 */
	public void addTargetMovable(Movable m) {
		this.movables.add(m);
	}
	
	/**
	 * @param Enemy
	 * Removes an Enemy from the list
	 */
	public void removeTargetEnemy(Enemy e) {
		this.enemies.remove(e);
	}
	
	/**
	 * @param Player
	 * Removes a Player from the list
	 */
	public void removeTargetPlayer(Player p) {
		this.players.remove(p);
	}
	
	/**
	 * @param Movable
	 * Removes Movable from the list (default behaviour is to do nothing)
	 */
	public void removeTargetMovable(Movable m) {
		this.movables.remove(m);
	}
	
	/**
	 * @param Enemy
	 * @return boolean
	 * Checks if the Enemy can be killed 
	 */
	public boolean canKillEnemy(Enemy e) {
		return this.enemies.contains(e);
	}
	
	/**
	 * @param Player
	 * @return boolean
	 * Checks if the Player can be killed 
	 */
	public boolean canKillPlayer(Player p) {
		return this.players.contains(p);
	}
	
	
	/**
	 * @param Player
	 * @return boolean
	 * Checks if the Movable can be destroyed 
	 */
	public boolean canKillMovable(Movable m) {
		return this.movables.contains(m);
	}
	
	
	/**
	 * @param Enemy
	 * Kills the Enemy
	 */
	public void killEnemy(Map m, Enemy e) {
		if(this.canKillEnemy(e)) {
			this.removeTargetEnemy(e);
			e.setPosition(null);
			//t.setEnemy(null);
			m.removeEnemy(e);
		}
	}
	
	/**
	 * @param Player
	 * Kills the Player
	 */
	public void killPlayer(Map m, Player p) {
		if(this.canKillPlayer(p)) {
			this.removeTargetPlayer(p);
			//t.setPlayer(null);
			m.removePlayer(p);
		}
	}
	
	/**
	 * @param Player
	 * Kills the Player
	 */
	public void killMovable(Map m, Movable mov) {
		if(this.canKillMovable(mov)) {
			this.removeTargetMovable(mov);
			m.removeMovable(mov);
		}
	}
	
}
