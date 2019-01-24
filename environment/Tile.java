package environment;

import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import characters.Direction;
import characters.Enemy;
import characters.Innocent;
import characters.Player;
import items.Item;
import items.objective.Key;
import items.objective.Treasure;

public class Tile {
	private final int y;
	private final int x;
	private Player player;
	private Enemy enemy;
	private Movable movable;
	private Structure structure;
	private Item item;
	
	/**
	* constructor for tile makes it ground
	* @param int x coord of map
	* @param int y coord of map
	*/
	public Tile(int y, int x) {
		this.y = y;
		this.x = x;
		this.player = null;
		this.enemy = null;
		this.structure = new Ground();
		this.movable= null;
		this.item = null;
	}

	/**
	* fetches y coord
	* @return int y coord of tile
	*/
	public int getY() {
		return y;
	}
	
	/**
	* fetches x coord
	* @return int x coord of tile
	*/
	public int getX() {
		return x;
	}
	
	// Player functions
	
	/**
	* fetches tile player
	* @return Player
	*/
	public Player getPlayer() {
		return player;
	}
	
	/**
	* checks tile player
	* @return boolean
	*/
	public boolean hasPlayer() {
		if(this.player != null) {
			return true;
		}
		return false;
	}
	
	/**
	* sets tile player
	* @return
	*/
	public void setPlayer(Player player) {
		this.player = player;
		if (player != null) {
			player.setPosition(this);
		}
	}
	
	/**
	* removes tile player
	* @return boolean
	*/
	public boolean removePlayer() {
		if(this.player == null) return false;
		this.player = null;
		return true;	
	}
	
	/**
	* checks if player is killed on tile
	* @return boolean
	*/
	public boolean checkPlayerDeath() {
		
		// No player on this tile
		if(this.hasPlayer() == false) {
			return false;
		}
		
		/*
		// Player is standing on a lethal structure
		if(this.getStructure().getLethality()) {
			return true;
		}
		*/
		// Player has collided with enemy
		if(this.getEnemy() != null) {
			return true;
		}

		return false;
	}
	
	/**
	* fetches enemy
	* @return enemy on tile
	*/
	public Enemy getEnemy() {
		return enemy;
	}
	
	/**
	* checks tile enemy
	* @return boolean
	*/
	public boolean hasEnemy() {
		return enemy != null;
	}
	
	/**
	* sets enemy
	* @param enemy placed on tile
	*/
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	/**
	* fetches tile structure
	* @return structure on tile
	*/
	public Structure getStructure() {
		return structure;
	}

	/**
	* sets structure
	* @param structure placed on tile
	*/
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	
	/**
	* fetches tile item
	* @return item
	*/
	public Item getItem() {
		return item;
	}
	
	/**
	* checks tile enemy
	* @return boolean
	*/
	public boolean hasItem() {
		return item != null;
	}
	
	/**
	* sets tile item
	* @return
	*/
	public void setItem(Item item) {
		if (structure.stepOnable()) {
			this.item = item;
		} else {
			System.out.println("can't move here");
		}
	}
	
	/**
	* opens door on tile given key
	* @return boolean
	*/
	public boolean openDoor(Key key) {
		
		if(this.getStructure() instanceof Door) {
			Door d = (Door)this.getStructure();
			if(d.unlock(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	* checks tile kills player
	* @return boolean
	*/
	/*
	public boolean killPlayer() {
		if (structure != null) {
			if (structure.killsPlayer() == true) {
				return true;
			} else if (this.enemy != null) {
				return true;
			}
		}
		return false;
	}
	*/
	
	// Apply deaths resulting from coexisting entities
	public void resolveDeaths(Map m) {
		
		// Structures killing enemies or players
		if(this.structure.canKillEnemy(this.enemy)) {
			this.structure.killEnemy(m, this.enemy);
		}
		if(this.structure.canKillPlayer(this.player)) {
			this.structure.killPlayer(m, this.player);
		}
		
		// Enemy killing player
		if(this.enemy != null && this.enemy.canKillPlayer(this.player)) {
			this.enemy.killPlayer(m, this.player);
		}
		
		// Player killing the enemy
		if(this.player != null && this.player.canKillEnemy(this.enemy)) {
			this.player.killEnemy(m, this.enemy);
		}
		
	}
	
	public boolean killsEnemy(Enemy e) {
		return this.structure.canKillEnemy(e);
	}
	
	public boolean hasPit() {
		return (structure instanceof Pit);
	}
	
	/*
	public void removePlayer(Player p) {
		if(this.player == p) {
			this.player = null;
		}
	}
	
	public void removeEnemy(Enemy e) {
		if(this.enemy == e) {
			this.enemy = null;
		}
	}
	*/
	
	/**
	* checks tile can be stepped on by player
	* @return boolean
	*/
	public boolean stepOnable() {
		return (this.structure.stepOnable());
	}
	
	public boolean pushable(Map map, Direction dir) {
		if (this.hasMovable()) {
			return (this.movable.pushable(map, dir));
		} 
		return true;
	}
	
	public boolean stepOnable(Map map) {
		return this.structure.stepOnable();
	}

	
	public Movable getMovable() {
		return this.movable;
	}
	
	/*
	public void setLethality(boolean lethality) {
		if(this.getStructure() != null) {
			this.getStructure().setLethality(lethality);
		}
	}
	*/
	
	// Return true if an enemy was killed on this tile
	// Return false otherwise
	/**
	* checks if enemy is killed on tile
	* @return boolean
	*/
	/*
	public boolean killEnemy() {
		
		// If tile has enemy, remove them
		if(this.enemy != null) {
			this.setEnemy(null);
			return true;
		}
		
		return false;
	}
	*/	
	
	/**
	* checks if can be stepped on
	* @return boolean
	*/
	public boolean accessable(){
	    return structure.stepOnable();
	}
	
	/**
	* checks if tile has treasure
	* @return boolean
	*/
	public boolean hasTreasure() {
		return (getItem() instanceof Treasure);
	}
	
	public boolean hasStructure() {
		return !(structure instanceof Ground);
	}

	/**
	* checks if tile has open switch
	* @return boolean
	*/
	
	public boolean hasOpenSwitch() {
		if (getStructure() instanceof FloorSwitch){
			FloorSwitch fs = (FloorSwitch)structure;
		    return (! fs.triggered());
		}
		return false;
	}
	
	
	/**
	* checks if tile has exit on it
	* @return boolean
	*/
	public boolean hasExit() {
		return (getStructure() instanceof Exit);
	}
	
	public boolean hasInnocent() {
		return (getEnemy() instanceof Innocent);
	}
	
	public boolean isEmpty() {
		return !(hasEnemy() || hasPlayer() || hasItem() || hasMovable() || this.structure.getClass() != Ground.class);
	}
	
	public void clear() {
		this.enemy = null;
		this.item = null;
		this.player = null;
		this.movable = null;
		this.structure = new Ground();
	}
	
	public void setMovable(Movable m) {
		this.movable = m;
	}
	
	public boolean hasMovable(){
		return (this.movable != null);
	}
	
	public void notifyHover(Player p) {
		this.structure.notifyHover(p);
	}
	public TeamPlayer getTeamPlayer() {
		if(this.enemy instanceof TeamPlayer) return (TeamPlayer)this.enemy;
		else if(this.item instanceof TeamPlayer) return (TeamPlayer)this.item;
		else if(this.structure instanceof TeamPlayer) return (TeamPlayer)this.structure;
		else if(this.movable instanceof TeamPlayer) return (TeamPlayer)this.movable;
		else return null;
	}
	public TeamLeader getTeamLeader() {
		if(this.enemy instanceof TeamLeader) return (TeamLeader)this.enemy;
		else if(this.item instanceof TeamLeader) return (TeamLeader)this.item;
		else if(this.structure instanceof TeamLeader) return (TeamLeader)this.structure;
		else if(this.movable instanceof TeamLeader) return (TeamLeader)this.movable;
		else return null;
	}
}
	

