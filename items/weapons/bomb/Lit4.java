package items.weapons.bomb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Player;
import environment.Map;
import javafx.scene.image.Image;

public class Lit4 implements BombState {

	@Override
	public void pickUp(Map m, Player p, Bomb b) {
		// Do not pick up lit bomb
		return;
	}
	
	@Override
	public boolean tick(Map m, Bomb b) {
		// Remove the exploded bomb from the map
		m.removeItem(b);
		m.removeKiller(b);
		return false;
	}

	@Override
	public Image getImage() {
		
		try {
			return new Image(new FileInputStream("src/images/bomb_lit_4.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
