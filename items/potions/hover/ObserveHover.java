package items.potions.hover;

import characters.Player;

public interface ObserveHover {

	default public void notifyHover(Player p) {}
	default public void notifyUnhover(Player p) {}

}
