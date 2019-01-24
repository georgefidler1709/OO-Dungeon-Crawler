package items.potions.hover;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import characters.Player;
import environment.Map;
import environment.Structure;
import items.potions.Effect;
import javafx.scene.image.Image;

/*
 * A class to represent a hovering effect gained
 * from consuming a potion
 * 
 * @author 	Nicholas Roper 
 * @version 1.0
 */
public class Hover extends Effect{
	
	private Image image;
	private ArrayList<Structure> structures;

	
	/*
	 * Class constructor
	 */
	public Hover() {
		super(true, 0);
		this.structures = new ArrayList<Structure>();
		try {
			image = new Image(new FileInputStream("src/images/hover_potion.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Apply the Hover effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	@Override
	public void applyEffect(Map map, Player p) {
		
		// Get a list of all structures from the map
		structures = map.getStructures();
		
		// Notify them of a hovering player
		for(Structure s: structures) {
			s.notifyHover(p);
		}
		
		p.addEffect(this);
		map.addTick(this);
	}
	
	/*
	 * Reverse the Hover effect
	 * 
	 * @param	m	Current map
	 * @param 	p	Player experiencing the effect
	 */
	@Override
	public void reverseEffect(Map map, Player p) {
				
		// Notify structures that a player is no longer hovering
		for(Structure s: structures) {
			s.notifyUnhover(p);
		}
		
		p.removeEffect(this);
	}

	//Image to be displayed on frontend
	@Override
	public Image getImage() {
		return image;
	}
	
}
