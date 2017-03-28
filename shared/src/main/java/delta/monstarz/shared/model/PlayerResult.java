package delta.monstarz.shared.model;

/**
 * Created by oliphaun on 3/25/17.
 */

public class PlayerResult {
	private String username;
	private PlayerColor playerColor;
	private int score;
	private int route_score;
	private int finished_dests_score;
	private int unfinished_dests_score;
	private boolean hasLongestRoute;

	public PlayerResult(String username, PlayerColor playerColor, int score, int route_score,
					  int finished_dests_score, int unfinished_dests_score, boolean hasLongestRoute) {
		this.username = username;
		this.playerColor = playerColor;
		this.score = score;
		this.route_score = route_score;
		this.finished_dests_score = finished_dests_score;
		this.unfinished_dests_score = unfinished_dests_score;
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
}
