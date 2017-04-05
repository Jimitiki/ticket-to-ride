package delta.monstarz.shared.model;

import java.util.Comparator;

/**
 * Created by oliphaun on 3/25/17.
 */

public class PlayerResult implements Comparable<PlayerResult>{
	private String username;
	private PlayerColor playerColor;
	private int score;
	private int route_score;
	private int finished_dests_score;
	private int unfinished_dests_score;
	private int completed_dest_cards;
	private boolean hasLongestRoute;

	public PlayerResult(String username, PlayerColor playerColor, int score, int route_score,
					  int finished_dests_score, int unfinished_dests_score, int completed_dest_cards, boolean hasLongestRoute) {
		this.username = username;
		this.playerColor = playerColor;
		this.score = score;
		this.route_score = route_score;
		this.finished_dests_score = finished_dests_score;
		this.unfinished_dests_score = unfinished_dests_score;
		this.completed_dest_cards = completed_dest_cards;
		this.hasLongestRoute = hasLongestRoute;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRoute_score() {
		return route_score;
	}

	public void setRoute_score(int route_score) {
		this.route_score = route_score;
	}

	public int getFinished_dests_score() {
		return finished_dests_score;
	}

	public void setFinished_dests_score(int finished_dests_score) {
		this.finished_dests_score = finished_dests_score;
	}

	public int getUnfinished_dests_score() {
		return unfinished_dests_score;
	}

	public void setUnfinished_dests_score(int unfinished_dests_score) {
		this.unfinished_dests_score = unfinished_dests_score;
	}

	public int getCompleted_dest_cards() {
		return completed_dest_cards;
	}

	public void setCompleted_dest_cards(int completed_dest_cards) {
		this.completed_dest_cards = completed_dest_cards;
	}

	public boolean isHasLongestRoute() {
		return hasLongestRoute;
	}

	public void setHasLongestRoute(boolean hasLongestRoute) {
		this.hasLongestRoute = hasLongestRoute;
	}

	public String getLongestRoute() {
		if (hasLongestRoute) {
			return "10";
		} else {
			return "0";
		}
	}

	/**
	 * The ordering is defined as following.
	 * If one player has a lower score, it comes first.
	 * In the case of a tie, the player with the most destination cards goes first
	 * In the case of a tie, the player with the longest route goes first
	 * In the case of a tie, they are equal.
	 */
	@Override
	public int compareTo(PlayerResult playerResult)
	{
		if(this.getScore() > playerResult.getScore())
		{
			return -1;
		}
		else if(this.getScore() < playerResult.getScore())
		{
			return 1;
		}

		if(this.getCompleted_dest_cards() > playerResult.getCompleted_dest_cards())
		{
			return -1;
		}
		else if(this.getCompleted_dest_cards() < playerResult.getCompleted_dest_cards())
		{
			return 1;
		}

		if(this.isHasLongestRoute() && !playerResult.isHasLongestRoute())
		{
			return -1;
		}
		else if(!this.isHasLongestRoute() && playerResult.isHasLongestRoute())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		if(! (o instanceof PlayerResult))
		{
			return false;
		}
		else
		{
			return compareTo((PlayerResult)o) == 0;
		}
	}

	@Override
	public int hashCode() {
		int result = username != null ? username.hashCode() : 0;
		result = 31 * result + (playerColor != null ? playerColor.hashCode() : 0);
		result = 31 * result + score;
		result = 31 * result + route_score;
		result = 31 * result + finished_dests_score;
		result = 31 * result + unfinished_dests_score;
		result = 31 * result + completed_dest_cards;
		result = 31 * result + (hasLongestRoute ? 1 : 0);
		return result;
	}
}
