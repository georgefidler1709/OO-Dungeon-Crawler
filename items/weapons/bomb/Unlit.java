package items.weapons.bomb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Player;
import environment.Map;
import javafx.scene.image.Image;

public class Unlit implements BombState {
	
	private Image image;
	
	public Unlit() {
		try {
			image = new Image(new FileInputStream("src/images/bomb_unlit.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void pickUp(Map m, Player p, Bomb b) {
		
		// Pick up bomb
		p.addItem(b);
		
		// Remove the item from the map
		m.removeItem(b);
		//m.removeWeapon(b);
	}
	
	@Override
	public boolean tick(Map m, Bomb b) {
		// Do nothing for unlit bombs
		return true;
	}

	@Override
	public Image getImage() {
		return image;
	}

}
