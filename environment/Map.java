package environment;

import java.util.ArrayList;
import characters.Direction;
import characters.Enemy;
import characters.Player;
import gameLogic.Killer;
import gameLogic.Tick;
import items.*;
import items.inventory.Inventory;
import items.objective.Key;
import items.weapons.Weapon;

import java.util.Iterator;

public class Map {
	private Tile[][] tiles;
	private int rows;
	private int columns;
	private Tile initialPlayerPos;
	private Player deadPlayer;
	
	// Observer patterns:
	private ArrayList<Killer> killers;
	private ArrayList<Tick> tickers;
	
	/**
	* constructor for Map and populates all tiles with ground
	* @param int number of map rows
	* @param int number of map columns
	*/
	public Map(int rows, int columns) {
		this.tiles = new Tile[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				tiles[i][j] = new Tile(i,j);
			}
		}
		this.rows = rows;
		this.columns = columns;
		this.initialPlayerPos = null;
		this.killers = new ArrayList<Killer>();
		this.tickers = new ArrayList<Tick>();
	}
	
	/**
	* fetches a tile given x and y
	* @param int row number
	* @param int column number
	* @return tile with that number if found
	*/
	public Tile getTile(int y, int x) {
	    if (x < 0 || x >= this.columns) {
	        return null; 
	    }
	    if (y < 0 || y >= this.rows){
	        return null;
	    }
	    return tiles[y][x];
	}
	
	// sets structure for a given tile
	public void setStructure(int y, int x, Structure s) {
		this.getTile(y, x).setStructure(s);
		this.killers.add(s);
	}
	
	// set movable for a given tile
	public void setMovable(int y, int x, Movable s) {
		this.getTile(y, x).setMovable(s);
	}
	
	// gets arraylist of movables on map
	public ArrayList<Movable> getMovables(){
		
		ArrayList<Movable> m = new ArrayList<Movable>();
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			Movable mov = tile.getMovable();
			if(mov != null) {
				m.add(mov);
			}
		}
		
		return m;
	}
	
	// removes a specific movable from map
	public void removeMovable(Movable mvb){
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			Movable mov = tile.getMovable();
			if(mov != null && mov == mvb) {
				this.setMovable(tile.getY(), tile.getX(), null);
			}
		}
	}
	
	// fetches list of structures
	public ArrayList<Structure> getStructures(){
		
		ArrayList<Structure> s = new ArrayList<Structure>();
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			s.add(tile.getStructure());
		}
		
		return s;
	}
	
	/**
	* stores player position for game turn progression
	* @param
	* @return
	*/
	public void storePlayerPos() {
		initialPlayerPos = getPlayerTile();
	}
	
	/**
	* fetches player tile at beginning of turn
	* @param
	* @return tile
	*/
	public Tile getInitialPlayerPos() {
		return (initialPlayerPos != null) ? initialPlayerPos : getPlayerTile();
	}
	
	/**
	* fetches player
	* @param
	* @return Player
	*/
	public Player getPlayer() {
		
		if(this.playerDead()) {
			return this.deadPlayer;
		}
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasPlayer()) {
				return tile.getPlayer();
			}
		}
		return null;
	}
	
	// fetches tile that has player on it
	public Tile getPlayerTile() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasPlayer()) {
				return tile;
			}
		}
		System.out.println("no player found???");
		return null;
	}
	
	// Add a player to the map
	public void addPlayer(Tile t, Player p) {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if(tile == t) {
				t.setPlayer(p);
			}
		}
		
		// Update all killers of a new player
		for(Killer k: this.killers) {
			k.addTargetPlayer(p);
		}
	}
	
	// Add an enemy to the map
	public void addEnemy(Tile t, Enemy e) {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if(tile == t) {
				t.setEnemy(e);
			}
		}
		
		// Update all killers of a new enemy
		for(Killer k: this.killers) {
			System.out.println("ADDED TARGET ENEMY");
			k.addTargetEnemy(e);
		}
	}
	
	// Remove an enemy from the map
	public void removeEnemy(Enemy e) {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if(tile.getEnemy() == e) {
				tile.setEnemy(null);
			}
		}
		
		this.killers.remove(e);
	}
	
	// Remove the player from the map
	public void removePlayer(Player p) {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if(tile.getPlayer() == p) {
				tile.setPlayer(null);
				this.deadPlayer = p;
			}
		}
		
		this.killers.remove(p);
	}
	
	// Check if a tile has an enemy on it
	public Enemy hasEnemy(Tile t) {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if(tile == t && tile.getEnemy() != null) {
				return tile.getEnemy();
			}
		}
		return null;
	}
	
	// checks whether tile can be physically moved onto
	public boolean stepOnable(Tile t) {
		return t.stepOnable(this);
	}
	
	/**
	* fetches enemies on map
	* @param
	* @return ArrayList<Enemy>
	*/
	public ArrayList<Enemy> getEnemies() {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasEnemy()) enemies.add(tile.getEnemy()) ;
		}
		return enemies;
	}
	
	/**
	* given a starting tile will fetch next tile along in given direction
	* @param Tile starting tile 
	* @param Direction to fetch next tile in
	* @return tile
	*/
	public Tile getNextTileInDir(Tile t, Direction dir) {
		switch(dir) {
		case UP:
			return getTile(t.getY() - 1, t.getX());
		case DOWN:
			return getTile(t.getY() + 1, t.getX());
		case RIGHT:
			return getTile(t.getY(), t.getX() + 1);
		case LEFT:
			return getTile(t.getY(), t.getX() - 1);
		default: return t;
		}
	}
	
	/**
	* does the physical moving of the player in a given direction
	* @param direction to move player in
	* @return tile that player ends up on
	*/
	public Tile movePlayer(Direction direction) {
		Tile currentLocation = this.getPlayerTile();
		Player player = currentLocation.getPlayer();
		int newX = currentLocation.getX();
		int newY = currentLocation.getY();
		if (direction == Direction.UP) newY--;
		if (direction == Direction.RIGHT) newX++;
		if (direction == Direction.DOWN) newY++;
		if (direction == Direction.LEFT) newX--;
		
		// If the tile is invalid, don't do anything.
		if (this.validCoordinates(newY, newX)) {
			Tile newLocation = this.getTile(newY, newX);
			if (newLocation.getStructure().stepOnable()) {
				if(newLocation.hasMovable()) {
					Movable m = newLocation.getMovable();
					if (m.pushable(this, direction)) {
						Tile mLocation = this.getNextTileInDir(newLocation, direction);
						mLocation.setMovable(m);
						newLocation.setMovable(null);
					} else {
						return newLocation;
					}
				}
					newLocation.setPlayer(player);
					currentLocation.setPlayer(null);
				
				// Pick up item if there is one
				Item i = newLocation.getItem();
				if(i != null) {
					i.pickUp(this, player);
				}
				
				return newLocation;
			}
		}
		return null;
	}
	
	/**
	* given an enemy moves them in the given direction
	* @param Enemy that will be moved
	* @param Direction that the enemy will be moved in
	* @return
	*/
	public void moveEnemy(Enemy enemy, Direction direction) {
		// Find the current location of the enemy.

		Tile enemyTile = enemy.getPosition();
		if (enemyTile == null) {
			System.out.println("Error, there should be an enemy.");
		}
		int newX = enemyTile.getX();
		int newY = enemyTile.getY();
		if (direction == Direction.UP) newY--;
		if (direction == Direction.RIGHT) newX++;
		if (direction == Direction.DOWN) newY++;
		if (direction == Direction.LEFT) newX--;
		
		// If the tile is invalid, don't do anything.
		if (this.validCoordinates(newY, newX)) {
			Tile newLocation = this.getTile(newY, newX);
			if (newLocation.getStructure().stepOnable()) {
				if (!newLocation.hasEnemy()) {
					newLocation.setEnemy(enemy);
					enemy.setPosition(newLocation);
					enemyTile.setEnemy(null);
				}
			}
		}
	}
	
	/**
	* checks whether the provided x and y have a corresponding tile
	* @param int x coord of checked tile
	* @param int y coord of checked tile
	* @return boolean
	*/
	public boolean validCoordinates (int y, int x) {
		if (x < 0 || y < 0 || x >= this.columns || y >= this.rows) {
			return false;
		}
		return true; 
	}
	
	/**
	* fetches exit tile
	* @param
	* @return tile
	*/
	public Tile getExitTile() {
		//what if there are multiple exits?
		//what is this used for?
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasExit()) return tile ;
		}
		System.out.println("no exit found???");
		return null;
	}
	
	/**
	* map tile iterator
	* @param
	* @return Iterator
	*/
	public Iterator<Tile> iterator() {
		return new MapIterator(this);
	}
	
	/*
	 * The following functions are used to verify the win/lose conditions
	 */
	
	/**
	* checks if all treasures are collected
	* @param
	* @return boolean
	*/
	public boolean allTreasureCollected() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasTreasure()) return false ;
		}
		return true;
	}
	
	/**
	* checks if all enemies are killed
	* @param
	* @return boolean
	*/
	public boolean allEnemiesDestoyed() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasEnemy()) return false ;
		}
		return true;
	}
	
	/**
	* checks if all switches are triggered
	* @param
	* @return boolean
	*/
	public boolean allSwitchesCovered() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasOpenSwitch()) return false;
		}
		return true;
	}
	
	/**
	* checks if player has arrived on exit
	* @param
	* @return boolean
	*/
	public boolean playerOnExit() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasPlayer() && tile.hasExit()) return true;
		}
		return false;
	}
	
	//Game is won if the innocent is saved i.e. innocent at the exit.
	public boolean savedInnocent() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasExit() && tile.hasInnocent()) return true;
		}
		return false;
	}
	
	//Ensures the map contains exactly one player character before game will start.
	public boolean hasOnePlayer() {
		int players = 0;
		Iterator<Tile>mapIt = this.iterator();
		while(mapIt.hasNext()) {
			Tile t = mapIt.next();
			if(t.hasPlayer()) players++;
		} 
		if(players == 1) return true;
		else return false;	
	}
	
	// Check player death by looking for them on the map
	public boolean playerDead() {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasPlayer()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	* checks if door gets opened by provided key
	* @param Tile which door is located on
	* @param Key that will be used to open door
	* @return boolean
	*/
	public boolean openDoor(Tile t, Key key) {

		return t.openDoor(key);
	}
	
	/**
	* add an item to a tile
	* @param Tile which item will be added to
	* @param Item added to tile
	* @return 
	*/
	public void addItem(Tile t, Item item) {
		t.setItem(item);
	}
	
	/**
	* remove an item to a tile
	* @param Tile which item will be removed from
	* @param Item removed
	* @return boolean
	*/
	public boolean removeItem(Item item) {
		
		int i, j;
		for(i = 0; i < this.rows; i++ ) {
			for(j = 0; j < this.columns; j++ ) {
				if(this.tiles[i][j].getItem() == item) {
					this.tiles[i][j].setItem(null);
					return true;
				}
			}
		}
		return false;
	}

	// Resolve deaths across all tiles due to
	public void resolveDeaths() {
		
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			tile.resolveDeaths(this);
		}
	}
	
	// adds an entity that can kill characters to a map
	public void addKiller(Killer k) {
		this.killers.add(k);
	}
	
	// removes an entity that can kill characters from the maps killer list
	public void removeKiller(Killer k) {
		this.killers.remove(k);
	}
	
	// signifies teh passing of time in the map
	public void tick() {
		
		Iterator<Tick> iter = this.tickers.iterator();
		while(iter.hasNext()) {
			Tick t = iter.next();
			if(t.tick(this) == false) {
				iter.remove();
			}
		}
		
	}
	
	//adds an entity that needs to keep track of time through ticks to map
	public void addTick(Tick t) {
		this.tickers.add(t);
	}
	
	// removes an entity that needs to keep track of time
	public void removeTick(Tick t) {
		this.tickers.remove(t);
	}
	
	/**
	* check if given tile is empty
	* @param Tile which checked
	* @return boolean
	*/
	public boolean emptyTile(Tile t) {
		if(t.getItem() != null) {
			return false;
		}
		return true;
	}
	
	// fetches inventory associated with map
	public Inventory getInventory() {
		return getPlayer().getInventory();
	}

	// checks movable interaction for all structures on the map
	public void movablesUpdate() {
		Iterator<Tile> tileIterator = this.iterator();
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			if (tile.hasStructure()) {
				tile.getStructure().movableInteraction(tile);
			}
		}
	}
	
	// fetches number of rows
	public int getRows () {
		return this.rows;
	}
	
	// fetches numbers of columns 
	public int getColumns () {
		return this.columns;
	}
}
