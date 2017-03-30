package delta.monstarz.model.game;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import delta.monstarz.commands.ServerDrawTrainCardCommand;
import delta.monstarz.model.CommandManager;
import delta.monstarz.model.game.manager.DestinationCardManager;
import delta.monstarz.model.game.manager.PlayerManager;
import delta.monstarz.model.game.manager.TrainCardManager;
import delta.monstarz.model.player.ServerPlayer;
import delta.monstarz.shared.GameInfo;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.EndGameCommand;
import delta.monstarz.shared.commands.NotifyPlayersCommand;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.PlayerResult;
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
	private boolean playHasStarted = false;
	private boolean gameEnded = false;

	private PlayerManager playerManager;
	private TrainCardManager trainDeck;
	private DestinationCardManager destDeck;
	private Board board;
	private int nextID = 0;
	private List<BaseCommand> history = new ArrayList<>();
	private List<BaseCommand> oneTimeUseCommands = new ArrayList<>();

	//Constructor
	private Game(JsonObject jsonGame, String name, String ownerName) {
		playerManager = new PlayerManager();
		this.name = name;
		this.ownerName = ownerName;
		this.gameID = nextNewGameID;
		nextNewGameID++;

		playerManager.setStartingNumberOfTrains(jsonGame.get("TrainCount").getAsInt());

		board = new Board( jsonGame.getAsJsonObject("Map"), jsonGame.getAsJsonArray("RouteList"),
				jsonGame.getAsJsonArray("Cities"));

		trainDeck = new TrainCardManager(jsonGame.getAsJsonArray("TrainCards"), this.gameID);
		trainDeck.initialize();
		destDeck = new DestinationCardManager(jsonGame.getAsJsonArray("DestinationCards"), board.getCities());
	}

	/**
	 * Pseudo-constuctor needed to pass a 'this' reference to an instance object.
	 */
	public static Game init(JsonObject jsonGame, String name, String ownerName)
	{
		Game g = new Game(jsonGame, name, ownerName);
		g.playerManager.setGame(g);
		return g;
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

	public boolean isPlayHasStarted() {
		return playHasStarted;
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

	public void replaceFaceUpCard(int position) {
		trainDeck.faceUpDestroyAndReplace(position);
	}

	public TrainCard getFaceUpCardByPosition(int position) {
		return trainDeck.getFaceUpCardByPosition(position);
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

	public boolean isGameEnded(){
		return gameEnded;
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
		return playerManager.getPlayerByName(username);
	}

	/**
	 * Starts the game
	 * New players can no longer join the game
	 */
	public void initGame(){
		if (playerManager.size() > 1){
			//trainDeck.initialize();
			//destDeck.shuffle();

			for (Player p : playerManager.getPlayers()) {
				for (int i = 0; i < 4; i++) {
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

			board.setNumPlayers(playerManager.size());

			gameStarted = true;
			startTime = new Date(); // All new dates initGame with the current time
		}
	}

	/**
	 * Add a player to the game as long as the game is not full
	 * @param username The unique string username of the user to add
	 */
	public void addPlayer(String username){
		if (playerManager.size() < PlayerManager.MAX_PLAYERS && !gameStarted && !hasPlayer(username)){
			Player player = new ServerPlayer(username, gameID);
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
		return playerManager.getPlayerByName(username) != null;
	}

	public boolean gameReadyToStart(){

		for (Player player: playerManager.getPlayers()){
			if (player.getDestCards().size() < 2){
				return false;
			}
		}

		return true;
	}

	public void startFirstTurn(){
		if (!playHasStarted) {
			playerManager.advanceTurn();
		}
		playHasStarted = true;
	}

	public void startNextPlayersTurn(){
		if (playHasStarted) {
			playerManager.advanceTurn();
		}
	}

	/**
	 * Checks the claimed route against the list of routes the player can claim
	 * If the route is available, claims it for the player, removing the cards used
	 * from the player's hand, and adding them to the deck
	 * @return true if successful, else false
	 */
	public boolean claimRoute(int routeID, String username, CardColor cardsUsed, int goldCardCount) {
		Player player = playerManager.getPlayerByName(username);

		Route route = board.getRouteByID(routeID);
		if (player.isTakingTurn() && player.canPlaceRoute(route) && board.claimRoute(routeID, player, cardsUsed, goldCardCount)) {
			for (int i = 0; i < route.getLength(); i++) {
				if (i < goldCardCount) {
					trainDeck.returnCard(new TrainCard(CardColor.GOLD));
				} else {
					trainDeck.returnCard(new TrainCard(cardsUsed));
				}
			}
			player.claimRoute(board.getRouteByID(routeID), cardsUsed, goldCardCount);
			if (player.getNumTrains() <= 2) {
				playerManager.oneTurnLeftEach();
			}
			List<Player> longestRouteOwners = board.findLongestRouteOwners(playerManager.getPlayers());
			playerManager.updateLongest(longestRouteOwners);
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return Returns a GameInfo object that represents the game
	 */
	public GameInfo getGameInfo(){
		TreeSet<String> playersNames = new TreeSet<>();
		for(Player p : playerManager.getPlayers())
		{
			playersNames.add(p.getUsername());
		}

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

	public List<PlayerResult> getGameResults() {
		List<PlayerResult> results = new ArrayList<>();
		List<Player> players = playerManager.getPlayers();
		for (Player player : players) {
			PlayerResult result = player.getBasePlayerResult();
			int score = player.getScore();
			int finished_dest_score = 0;
			int unfinished_dest_score = 0;
			for (DestCard dest : player.getDestCards()) {
				if (board.isDestDone(dest, player.getUsername())) {
					finished_dest_score += dest.getValue();
				} else {
					unfinished_dest_score -= dest.getValue();
				}
			}
			score += finished_dest_score + unfinished_dest_score;
			result.setScore(score);
			result.setFinished_dests_score(finished_dest_score);
			result.setUnfinished_dests_score(unfinished_dest_score);
			results.add(result);
		}
		return results;
	}

	public void addPlayerInfoCommands() {
		for (Player player : playerManager.getPlayers()) {
			addCommand(new UpdatePlayerInfoCommand(player.getUsername(), gameID, player.playerInfo()));
		}
	}

	public void endGame() {
		List<PlayerResult> results = getGameResults();
		EndGameCommand command = new EndGameCommand(null, gameID, results);
		addCommand(command);
		gameEnded = true;
	}
}
