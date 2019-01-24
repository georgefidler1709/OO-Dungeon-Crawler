package items.weapons.bomb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import characters.Direction;
import characters.Enemy;
import characters.Player;
import environment.Map;
import environment.Movable;
import environment.Tile;
import javafx.scene.image.Image;

public class Lit3 implements BombState {
	
	private Image image;
	
	public Lit3() {
		try {
			image = new Image(new FileInputStream("src/images/bomb_lit_3.png"));
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
		
		// Detonate bomb
		
		//  Kill players or enemies on current tile
		Tile curr = b.getPosition();
		this.explode(m, curr, b);
		curr.setMovable(null);
		
		// Kill players of enemies in blast radius
		Tile radius;
		radius = m.getNextTileInDir(curr, Direction.UP);
		this.explode(m, radius, b);
		radius.setMovable(null);
		radius = m.getNextTileInDir(curr, Direction.DOWN);
		this.explode(m, radius, b);
		radius.setMovable(null);
		radius = m.getNextTileInDir(curr, Direction.LEFT);
		this.explode(m, radius, b);
		radius.setMovable(null);
		radius = m.getNextTileInDir(curr, Direction.RIGHT);
		this.explode(m, radius, b);
		radius.setMovable(null);
		
		b.setBombState(new Lit4());
		return true;
	}
	
	// Kill player and enemy on a given tile
	private void explode(Map m, Tile curr, Bomb b) {
		Player p = curr.getPlayer();
		if(b.canKillPlayer(p)) {
			b.killPlayer(m, p);
		}
		Enemy e = curr.getEnemy();
		if(b.canKillEnemy(e)) {
			b.killEnemy(m, e);
		}
		Movable mov = curr.getMovable();
		if(b.canKillMovable(mov)) {
			b.killMovable(m, mov);
		}
	}

	@Override
	public Image getImage() {
		return image;
	}

}