package characters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import environment.AddToMap;
import environment.Map;
import environment.Movable;
import environment.Tile;
import frontDev.Displayable;
import gameLogic.Killer;
import items.*;
import items.inventory.Inventory;
import items.potions.*;
import javafx.scene.image.Image;

//Player's character
//Would like to give the Player Object more autonomy in future 
//but for now it is primarily handled by the gameEngine.

public class Player extends Killer implements Displayable, AddToMap {
	private Inventory inventory;
	private Tile position;
	private ArrayList<Effect> effects;

	public Player(Tile position) {
		this.position = position;
		this.inventory = new Inventory();
		this.effects = new ArrayList<Effect>();
	}
	
	
	public Tile getPosition() {
		return position;
	}
	
	public int getY() {
		return position.getY();
	}
	
	public int getX() {
		return position.getX();
	}
	

	public void setPosition(Tile position) {
		this.position = position;
	}

	public void removeItem(Item i) {
		inventory.removeItem(i);
	}
	
	public void addItem(Item i) {
		inventory.addItem(i);
	}

	public boolean hasItem(Class<? extends Item> i) {
		return this.inventory.hasItem(i);
	}
	
	public boolean hasItem(Item i) {
		return this.inventory.hasItem(i);
	}
	
	//public Item getItem(Class<? extends Item> i) {
	//	return this.inventory.getItem(i);
	//}

	public void useItem(int itemNumber, Direction direction, Map map, Player player) {
		this.inventory.useItem(itemNumber, direction, map, player);
		
	}
	
	public void addEffect(Effect e) {
		this.effects.add(e);
	}
	
	public void removeEffect(Effect e) {
		this.effects.remove(e);
	}
	
	@Override
	public void addToMap(Map m, Tile t) {
		m.addPlayer(t, this);
	}
	
	@Override
	public void addTargetMovable(Movable m) {}
	
	@Override
	public void removeTargetMovable(Movable m) {}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		try {
			return new Image(new FileInputStream("src/images/player.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

}
