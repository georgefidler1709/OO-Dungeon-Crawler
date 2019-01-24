package items.potions.invincibility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import characters.Enemy;
import characters.Player;
import environment.Map;
import items.potions.Effect;
import javafx.scene.image.Image;

/*
 * A class to represent an invincibility effect gained
 * from consuming a potion
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Invincibility extends Effect{
	
	private Image image;
	private ArrayList<Enemy> enemies;
	
	/*
	 * Class constructor
	 */
	public Invincibility() {
		super(false, 6);
		this.enemies = 	new ArrayList<Enemy>();
		try {
			this.image = new Image(new FileInputStream("src/images/invincibility_potion.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

	}

	/*
	 * Apply the Invincibility effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	@Override
	public void applyEffect(Map map, Player p) {
		
		// Get a list of all enemies from the map
		this.enemies = map.getEnemies();
		
		// Notify them of an invincible player
		for(Enemy e: enemies) {
			e.notifyInvincible(p);
		}
		
		// Notify the player that they can now kill enemies
		for(Enemy e: enemies) {
			p.addTargetEnemy(e);
		}
		
		p.addEffect(this);
		map.addTick(this);
	}
	
	/*
	 * Reverse the Invincibility effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	@Override
	public void reverseEffect(Map map, Player p) {
		
		// Get a list of all enemies from the map
		this.enemies = map.getEnemies();
		
		// Notify enemies of an invincible player
		for(Enemy e: enemies) {
			e.notifyUninvincible(p);
		}
		
		// Notify the player that they cannot kill enemies
		for(Enemy e: enemies) {
			p.removeTargetEnemy(e);
		}
		
		p.removeEffect(this);
	}
	
	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
}
