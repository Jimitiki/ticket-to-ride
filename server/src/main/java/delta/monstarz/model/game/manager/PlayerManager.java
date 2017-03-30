package delta.monstarz.model.game.manager;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.NotifyPlayersCommand;
import delta.monstarz.shared.commands.StartTurnCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.PlayerInfo;

/**
 * This class manages the players for a game. It also keeps track of whose turn it is.
 *
 * @author bradcarter
 */
public class PlayerManager
{
	//Class Fields
	public static final int MAX_PLAYERS = 5;

	//Instance Variables
	/**
	 * An ordered collection of players. Used to implement turn switching.
	 */
	private List<Player> players;

	/**
	 * The index of the currentPlayerIndex player. Defaults to -1.
	 */
	private int currentPlayerIndex;

	/**
	 * The number of trains that each player starts with.
	 */
	private int startingNumberOfTrains;

	private Game mGame;
	private int turnsUntilEnd;

	//Constructors

	public PlayerManager()
	{
		players = new ArrayList<>();
		currentPlayerIndex = -1;
		turnsUntilEnd = -1;
	}

	//Getters and Setter
	public List<Player> getPlayers()
	{
		return players;
	}

	/**
	 * Returns the player with the given username or null if it is not present.
	 */
	public Player getPlayerByName(String username) {
		for (Player p : players) {
			if (p.getUsername().equals(username)) {
				return p;
			}
		}
		return null;
	}

	public void setGame(Game pGame)
	{
		mGame = pGame;
	}

	/**
	 * Returns the number of players in the game.
	 */
	public int size()
	{
		return players.size();
	}

	//Public Methods
	/**
	 * Adds a player to the game if the game is not full, or does nothing if the game is full.
	 */
	public void add(Player player)
	{
		//If the game is full, do nothing.
		if(players.size() == MAX_PLAYERS)
		{
			return;
		}
		//Otherwise, add the player and set their starting trains.
		else
		{
			player.setNumTrains(startingNumberOfTrains);
			players.add(player);
		}
	}

	/**
	 * Advances the currentPlayerIndex turn.
	 * Sets the currentPlayerIndex player if unset.
	 */
	public void advanceTurn()
	{
		if (turnsUntilEnd > 0) {
			turnsUntilEnd--;
		} else if (turnsUntilEnd == 0) {
			mGame.endGame();
			return;
		}
		//Increment the currentPlayerIndex player index
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

		//Notify the players of the change.
		Player nextPlayer = players.get(currentPlayerIndex);
		nextPlayer.startTurn();
		PlayerInfo info = nextPlayer.playerInfo();
		UpdatePlayerInfoCommand command = new UpdatePlayerInfoCommand(nextPlayer.getUsername(), mGame.getGameID(), info);
		StartTurnCommand command2 = new StartTurnCommand(nextPlayer.getUsername(), mGame.getGameID());

		mGame.addCommand(command);
		mGame.addCommand(command2);
	}

	//TODO I don't feel like this data belongs to the player manager.
	public void setStartingNumberOfTrains(int startTrains) {
		this.startingNumberOfTrains = startTrains;
	}

	public void updateLongest(List<Player> longestRouteOwners) {
		for (Player player : players) {
			if (longestRouteOwners.contains(player)) {
				player.setHasLongest(true);
			} else {
				player.setHasLongest(false);
			}
		}
	}

	public void oneTurnLeftEach() {
		if (turnsUntilEnd < 0) {
			turnsUntilEnd = size() -1;
			String message = "The game is ending soon. Everyone gets one more turn.";
			NotifyPlayersCommand command = new NotifyPlayersCommand("It doesn't matter", mGame.getGameID(), message);
			mGame.addCommand(command);
		}
	}
}
