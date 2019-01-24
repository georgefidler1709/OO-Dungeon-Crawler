package items.potions.invincibility;

import characters.Player;

public interface ObserveInvincible {

	default public void notifyInvincible(Player p) {}
	default public void notifyUninvincible(Player p) {}
	
}
