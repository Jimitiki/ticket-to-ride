package delta.monstarz.model.game;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.jar.JarEntry;

import delta.monstarz.commands.ServerDrawTrainCardCommand;
import delta.monstarz.model.CommandManager;
import delta.monstarz.model.game.manager.DestinationCardManager;
import delta.monstarz.model.game.manager.PlayerManager;
import delta.monstarz.model.game.manager.TrainCardManager;
import delta.monstarz.shared.GameInfo;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;

/**
 * The Game class holds all the data related to a ticket to ride game
 * Each time a game is made a game id is assigned
 * The id starts at 0 and is incremented for each additional game
 */

public class Game {
	//Class Fields
	private static int nextNewGameID = 0;

	//Data members
	private int gameID;
	private String name;
	private String ownerName;
	private Date startTime;
	private boolean gameStarted = false;

	private PlayerManager playerManager;
	private TrainCardManager trainDeck;
	private DestinationCardManager destDeck;
	private Board board;
	private int nextID = 0;
	private List<BaseCommand> history = new ArrayList<>();
	private List<BaseCommand> oneTimeUseCommands = new ArrayList<>();

	//Constructor
	public Game(JsonObject jsonGame, String name, String ownerName) {
		playerManager = new PlayerManager();
		this.name = name;
		this.ownerName = ownerName;
		this.gameID = nextNewGameID;
		nextNewGameID++;

		playerManager.setStartTrains(jsonGame.get("TrainCount").getAsInt());

		board = new Board(jsonGame.getAsJsonObject("Map"),
				jsonGame.getAsJsonArray("RouteList"));
		trainDeck = new TrainCardManager(jsonGame.getAsJsonArray("TrainCards"));
		destDeck = new DestinationCardManager(jsonGame.getAsJsonArray("DestinationCards"));
	}

	//Getters and Setters
	public int getGameID() {
		return gameID;
	}

	public String getName() {
		return name;
	}

	public int getNumPlayers() {
		return playerManager.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public DestinationCardManager getDestDeck()
	{
		return destDeck;
	}

	public Board getBoard() {
		return board;
	}

	public TrainCardManager getTrainDeck()
	{
		return trainDeck;
	}

	public List<BaseCommand> getHistory() {
		return history;
	}

	public List<BaseCommand> getOneTimeUseCommands() {
		return oneTimeUseCommands;
	}

	public TrainCard replaceFaceUpCard(int position) {
		return trainDeck.drawFaceUpCard(position);
	}

	public TrainCard getFaceUpCardByPosition(int position) {
		return trainDeck.drawFaceUpCard(position);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setTrainDeck(TrainCardManager trainDeck) {
		this.trainDeck = trainDeck;
	}

	public void setDestDeck(DestinationCardManager destDeck) {
		this.destDeck = destDeck;
	}

	//Public Methods

	public void addCommand(BaseCommand command) {
		command.setId(nextID++);
		if (command.expires()){
			oneTimeUseCommands.add(command);
		}
		else{
			history.add(command);
		}
	}

	public Player getPlayerByUsername(String username) {
		return playerManager.getPlayerByUsername(username);
	}

	/**
	 * Starts the game
	 * New players can no longer join the game
	 */
	public void start(){
		if (playerManager.size() > 1){
			trainDeck.initialize();
			destDeck.shuffle();

			for(Player p : playerManager.getPlayers())
			{
				for(int i = 0; i < 4; i++)
				{
					ServerDrawTrainCardCommand trainCommand = new ServerDrawTrainCardCommand(p.getUsername(), gameID, -1);
					CommandManager.execute(trainCommand);
				}
			}

			List<TrainCard> faceUpCards = trainDeck.getFaceUpCards();
			for (int i = 0; i < faceUpCards.size(); i++) {
				SelectTrainCardCommand command = new SelectTrainCardCommand(null, gameID, i);
				command.setReplacementCard(faceUpCards.get(i));
				addCommand(command);
			}

			gameStarted = true;
			startTime = new Date(); // All new dates start with the current time
		}
	}

	/**
	 * Add a player to the game as long as the game is not full
	 * @param username The unique string username of the user to add
	 */
	public void addPlayer(String username){
		if (playerManager.size() < PlayerManager.MAX_PLAYERS && !gameStarted && !hasPlayer(username)){
			Player player = new Player(username);
			PlayerColor color = PlayerColor.getColorByValue(playerManager.size());
			player.setPlayerColor(color);
			playerManager.add(player);
		}
	}

	/**
	 * Find out is a player is in the game by using a username as identification
	 * @param username A username of a player that may or may not be in the game
	 * @return A boolean value representing if the player is in the game
	 */
	public boolean hasPlayer(String username){
		return playerManager.getPlayerNames().contains(username);
	}

	/**
	 * Checks the claimed route against the list of routes the player can claim
	 * If the route is available, claims it for the player, removing the cards used
	 * from the player's hand, and adding them to the deck
	 * @return true if successful, else false
	 */
	public boolean claimRoute(int routeID, String username, CardColor cardsUsed) {
		Player player = playerManager.getPlayerByUsername(username);
//		boolean isAvailable = false;
//		Route selectedRoute = null;
//		for (Route availableRoute : board.getAvailableRoutes(player)) {
//			if (availableRoute.getID() == routeID) {
//				isAvailable = true;
//				selectedRoute = availableRoute;
//				break;
//			}
//		}
//		if (isAvailable) {
//			board.claimRoute(routeID, username, player.getPlayerColor());
//			player.claimRoute(selectedRoute, cardsUsed);
//		}
//		return isAvailable;
		return board.claimRoute(routeID, player, cardsUsed);
	}

	/**
	 *
	 * @return Returns a GameInfo object that represents the game
	 */
	public GameInfo getGameInfo(){

		TreeSet<String> playersNames = playerManager.getPlayerNames();

		return new GameInfo(
				name,
				ownerName,
				gameID,
				startTime,
				playerManager.size(),
				gameStarted,
				playersNames
		);
	}
}
