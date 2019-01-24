package items.weapons.bomb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Player;
import environment.Map;
import javafx.scene.image.Image;

public class Lit1 implements BombState {
	
	private Image image;
	
	public Lit1() {
		try {
			image = new Image(new FileInputStream("src/images/bomb_lit_1.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void pickUp(Map m, Player p, Bomb b) {
		// Do not pick up lit bomb
		return;
	}
	
	@Override
	public boolean tick(Map m, Bomb b) {
		b.setBombState(new Lit2());
		return true;
	}

	@Override
	public Image getImage() {
		return image;
	}

}
