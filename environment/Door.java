package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import characters.Enemy;
import characters.Player;
import items.objective.Key;
import javafx.scene.image.Image;

public class Door extends Structure implements TeamLeader {
	
	
	/**
 	* door ID to link with key with same ID
 	*/
	private boolean locked;
	private Key matchedKey;
	private Image image;
	
	/**
	* constructor for Door
	* @param i  ID for the newly created door
	*/

	public Door(Key key) {
		super();
		this.matchedKey = key;
		this.locked = true;
		try {
			image = new Image(new FileInputStream("src/images/closed_door.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* fetcher for Door matched key
	* @param
	* @return boolean
	*/
	public Key getKey(){
		return this.matchedKey;
	}
	
	/**
	* fetcher for Door lock status
	* @param
	* @return boolean
	*/
	public boolean isDoorlocked() {
		return this.locked;
	}
	
	/**
	* attempts to unlock door given a key
	* @param key
	* @return boolean whether the door was unlocked or not
	*/
	public boolean unlock(Key key) {		
		if (key.isSkeleton()) {
			this.locked = false;
			try {
				image = new Image(new FileInputStream("src/images/open_door.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			if (matchedKey != null && this.matchedKey.equals(key)){
				this.locked = false;
				try {
					image = new Image(new FileInputStream("src/images/open_door.png"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	* @inheritdoc
	* @return boolean whether the door was locked or not
	*/
	public boolean stepOnable() {
		return (!isDoorlocked());
	}
	
	
	/**
	* setter for door lock state
	* @param boolean
	* @return
	*/
	public void setLocked (boolean state) {
		this.locked = state;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void addToTeam(TeamPlayer tp) {
		if(requestedTeamMate(tp))
		matchedKey = (Key)tp;
	}


	@Override
	public boolean requestedTeamMate(TeamPlayer tp) {
		if(tp instanceof Key) return true;
		return false;
	}
	
	@Override
	public void notifyTeamMates() {
	}

	@Override
	void movableInteraction(Tile t) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addTargetEnemy(Enemy e) {}
	
	@Override
	public void addTargetPlayer(Player p) {}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Structure createNew() {
		return new Door(null);
	}
	
	

}
