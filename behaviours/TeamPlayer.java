package behaviours;

import environment.Tile;

//Used by the hounds so that they can observe the hunter.
//Interface allows other characters to engage in teamwork in future, more extensible.
public interface TeamPlayer {
	
	public void leaderUpdate(TeamLeader tl);

}
