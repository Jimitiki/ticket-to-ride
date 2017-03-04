package delta.monstarz.services;

import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.GameInfo;

/**
 * @author bradcarter
 */
public class GameManagementService
{
	//Data Members
	private GameManager gameManager;

	//Singleton Implementation
	private GameManagementService()
	{
		gameManager = GameManager.getInstance();
	}

	private static GameManagementService instance;

	public static GameManagementService getInstance()
	{
		if(instance == null)
		{
			instance = new GameManagementService();
		}
		return instance;
	}

	//Public Methods
	public int createGame(String username, String game_name) {
		return gameManager.createGame(username, game_name);
	}

	public List<GameInfo> listGames(String username) {
		List<GameInfo> games = gameManager.getGamesIn(username);
		games.addAll(gameManager.getJoinableGames(username));
		return games;
	}

	public void joinGame(String username, int gameID) {
		gameManager.joinGame(username, gameID);
	}

	public boolean gameExists(int gameID) {
		if (gameManager.getGameByID(gameID) != null) {
			return true;
		}
		return false;
	}

	public void startGame(int gameID) {
		gameManager.startGame(gameID);
	}

	public Game getGamebyID(int id) {
		return gameManager.getGameByID(id);
	}
}
