package items.potions;

import characters.Player;
import environment.Map;
import frontDev.Displayable;
import gameLogic.Tick;
import javafx.scene.image.Image;

/*
 * An abstract class to represent potion effects
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public abstract class Effect implements Tick, Displayable {

	boolean infinite;
	int duration;
	
	/*
	 * Class constructor given the nature of the effect's
	 * duration
	 */
	public Effect(boolean infinite, int duration) {
		this.infinite = infinite;
		this.duration = duration;
	}

	/*
	 * Change the state of the potion effect, given that one
	 * turn has passed
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	
	public boolean tick(Map m) {
				
		// If potion is infinite, do nothing
		if(this.infinite) {
			return true;
		}
		
		// Reduce potion duration
		this.duration -= 1;
		
		// Potion duration has ended, reverse its effect
		if(this.duration == 0) {
			this.reverseEffect(m, m.getPlayer());
		}
		
		return false;
	}
	
	/*
	 * Apply the changes caused by a potion effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	public abstract void applyEffect(Map map, Player p);
	
	/*
	 * Reverse the changes caused by a potion effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	public abstract void reverseEffect(Map map, Player p);
	
	//Image to be displayed on frontend
	@Override
	public abstract Image getImage();
	
}
