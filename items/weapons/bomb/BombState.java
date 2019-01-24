package items.weapons.bomb;

import characters.Player;
import environment.Map;
import javafx.scene.image.Image;

public interface BombState {

	public void pickUp(Map m, Player p, Bomb b);
	public boolean tick(Map m, Bomb b);
	public Image getImage();
	
}
