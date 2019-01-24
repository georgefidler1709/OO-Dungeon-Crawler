package environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import characters.Enemy;
import characters.Player;
import javafx.scene.image.Image;

public class Pit extends Structure {
	
	private Image image;
	
	public Pit() {
		try {
			image = new Image(new FileInputStream("src/images/pit.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* @inheritdoc
	* @return boolean
	*/
	public boolean stepOnable() {
		return true;
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
	@Override
	public void addToMap(Map m, Tile t) {
		m.setStructure(t.getY(), t.getX(), this);
		
		// Add any existing enemies to the list of targets
		ArrayList<Enemy> enemies = m.getEnemies();
		if(enemies != null) {
			for(Enemy e: enemies) {
				this.addTargetEnemy(e);
			}
		}
		
		// Add any existing enemies to the list of targets
		Player player = m.getPlayer();
		if(player != null) {
			this.addTargetPlayer(player);
		}
	}
	
	@Override
	public void notifyHover(Player p) {
		this.removeTargetPlayer(p);
	}
	
	@Override
	public void notifyUnhover(Player p) {
		this.addTargetPlayer(p);
	}
	
	@Override
	void movableInteraction(Tile t) {
		t.setMovable(null);
	}
	
	//Found in all entities, used to instantiate a new object of this class without breaking abstraction in map creation.
	@Override
	public Structure createNew() {
		return new Pit();
	}
	
	
}
