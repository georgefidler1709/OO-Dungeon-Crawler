package behaviours;

//Used by the hunter to be the observed in the observer pattern with it's hounds.
//Interface allows other characters to engage in teamwork in future, more extensible.
public interface TeamLeader{
	public boolean requestedTeamMate(TeamPlayer tp);
	public void addToTeam(TeamPlayer tp);
	public void notifyTeamMates();

}
