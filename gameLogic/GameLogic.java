package gameLogic;
import java.util.ArrayList;

import behaviours.TeamLeader;
import behaviours.TeamPlayer;
import characters.Strategist;
import environment.Map;

public class GameLogic implements TeamLeader{
	
	private ArrayList<LogicCondition> winConditions;
	private LogicCondition lossCondition;
	
	private ArrayList<Strategist> strategists;
	
	
	/**
	 * Creates the GameLogic object
	 */
	public GameLogic() {
		winConditions = new ArrayList<LogicCondition>();
		lossCondition = new DeathCondition();
		strategists = new ArrayList<Strategist>();
	}
	
	/**
	 * @param map
	 * @return boolean win
	 * Determines from the map if the game logic is satisfied
	 */
	public boolean checkWin(Map map) {
		boolean winLogicMet = true;
		for(LogicCondition winCondition : winConditions) {
			if (!winCondition.checkLogicSatisfied(map)) {
				winLogicMet = false;
			}
		}
		return winLogicMet;
	}
	

	
	/**
	 * @param map
	 * @return boolean loss
	 * Determines from the map if the player has lost
	 */
	public boolean checkLoss(Map map) {
		return lossCondition.checkLogicSatisfied(map);
	}
	
	/**
	 * @param LogicCondition
	 * Adds a logic condition to the list
	 */
	public void addCondition (LogicCondition winCondition) {
		
		/**
		 * this loop ensures that you can't add a win conditions
		 * if the ExitLogic condition is set
		*/
		for (LogicCondition wC : winConditions) {
			if (wC instanceof ExitCondition) return;
		}
		
		winConditions.add(winCondition);
	}
	
	/**
	 * @param LogicCondition
	 * Removes a logic condition from the list
	 */
	public void removeCondition(LogicCondition toRemove) {
		for (int i = 0; i < winConditions.size(); i++) {
			if (winConditions.get(i) == toRemove) winConditions.remove(i);
		}
	}
	
	/**
	 * @param LogicCondition
	 * @return boolean
	 * Determines if a logic condition is set
	 */
	public boolean hasCondition(LogicCondition toCheck) {
		for (LogicCondition lc : winConditions) {
			if(lc.equals(toCheck)) return true;
		}
		return false;
	}
	
	/**
	 * Clears all the win conditions
	 */
	public void clearConditions () {
		winConditions.clear();
	}
	
	/**
	 * @param LogicCondition
	 * Sets the win condition
	 */
	public void setCondition (LogicCondition winCondition) {
		winConditions.clear();
		winConditions.add(winCondition);
	}
	
	/**
	 * @return List LogicCondition
	 * Returns a list of the win conditions
	 */
	public ArrayList<LogicCondition> getConditions () {
		return winConditions;
	}

	//Only allows strategists to observe it as they will need up-to-date win conditions
	@Override
	public boolean requestedTeamMate(TeamPlayer tp) {
		return (tp instanceof Strategist);
	}

	//Add strategist to team.
	@Override
	public void addToTeam(TeamPlayer tp) {
		strategists.add((Strategist)tp);
	}

	//Once logic conditions are set and the game is about to start, notify the strategists
	@Override
	public void notifyTeamMates() {
		for(Strategist s : strategists) s.leaderUpdate(this);
	}
	
}
