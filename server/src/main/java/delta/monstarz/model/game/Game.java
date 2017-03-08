package delta.monstarz.model.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import delta.monstarz.commands.ServerDrawDestCardsCommand;
import delta.monstarz.commands.ServerDrawTrainCardCommand;
import delta.monstarz.model.CommandManager;
import delta.monstarz.model.game.manager.DestinationCardManager;
import delta.monstarz.model.game.manager.PlayerManager;
import delta.monstarz.model.game.manager.StateManager;
import delta.monstarz.model.game.manager.TrainCardManager;
import delta.monstarz.shared.GameInfo;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.Segment;
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
	private StateManager stateManager;
	private TrainCardManager trainDeck;
	private DestinationCardManager destDeck;
	private Board board;
	private int nextID = 0;
	private List<BaseCommand> history = new ArrayList<>();
	private List<BaseCommand> oneTimeUseCommands = new ArrayList<>();

	//Constructor
	public Game(String gameName, String ownerName) {
		playerManager = new PlayerManager();
		stateManager = new StateManager();
		trainDeck = new TrainCardManager();
		destDeck = new DestinationCardManager();
		board = new Board();

		this.name = gameName;
		this.ownerName = ownerName;
		this.gameID = nextNewGameID;
		nextNewGameID++;
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

	//Public Methods
	public void addCommand(BaseCommand command) {
		command.setId(nextID++);
		if (command.expires()){
			oneTimeUseCommands.add(command);
		}
		else{
			history.add(command);
		}
		int i = 1;
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
			parseConfigurations(trainDeck, destDeck, board);

			for(Player p : playerManager.getPlayers())
			{
				for(int i = 0; i < 4; i++)
				{
					ServerDrawTrainCardCommand trainCommand = new ServerDrawTrainCardCommand(p.getUsername(), gameID, -1);
					CommandManager.execute(trainCommand);
				}
			}

			gameStarted = true;
			startTime = new Date(); // All new dates start with the current time
		}
	}

	/**
	 * Add a player to the game as long as the game is not full
	 * @param username
	 */
	public void addPlayer(String username){
		if (playerManager.size() < playerManager.MAX_PLAYERS && !gameStarted){
			Player player = new Player(username);
			PlayerColor color = PlayerColor.getColorByValue(playerManager.size());
			player.setPlayerColor(color);
			playerManager.add(player);
		}
	}

	public void removePlayer(String username) {
		//TODO Should players be a map of usernames to player objects?
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

	//Internal Methods
	private void parseConfigurations(TrainCardManager trainManager, DestinationCardManager destinationManager, Board board)
	{
		//Get the file contents
		String fileName = "server/src/main/assets/preferences.json";
		String contents;
		try
		{
			contents = new String(Files.readAllBytes(Paths.get(fileName)));//TODO Fix file access
		}
		catch(IOException e)
		{
			//Log.debug("The preferences file could not be opened.");
			int i = 0;
			return;
		}


		//Parse JSON
		JsonParser parser = new JsonParser();
		JsonObject preferenceObject = parser.parse(contents).getAsJsonObject();

		JsonObject mapObject = preferenceObject.getAsJsonObject("Map");
		parseMap(mapObject, board);

		JsonArray routeList = preferenceObject.getAsJsonArray("RouteList");
		parseRoutes(routeList);

		JsonArray trainCardList = preferenceObject.getAsJsonArray("TrainCards");
		parseTrainCards(trainCardList, trainManager);

		JsonArray destinationCardList = preferenceObject.getAsJsonArray("DestinationCards");
		parseDestinationCards(destinationCardList, destinationManager);
	}

	private void parseMap(JsonObject mapObject, Board board)
	{
		String fileName = mapObject.get("file").getAsString();
		board.setImageID(fileName);
	}

	private void parseRoutes(JsonArray routeList)
	{
		for(int i = 0; i < routeList.size(); i++) {
			JsonObject routeObject = routeList.get(i).getAsJsonObject();

			//Parse the Endpoints
			JsonArray endpointArray = routeObject.get("endpoints").getAsJsonArray();
			String endpoint1 = endpointArray.get(0).getAsString();
			String endpoint2 = endpointArray.get(1).getAsString();

			//Parse the Segments
			// TODO fix this
//			List<Segment> segments = parseSegments(routeObject.get("segmants").getAsJsonArray());
			List<Segment> segments = new ArrayList<>();

			//Parse color
			String color = routeObject.get("color").getAsString();
			CardColor c = CardColor.fromString(color);

			//			"segmants":[{"x":20, "y":62, "rotation":0}, {"x":62, "y":35, "rotation":90}],

			Route route = new Route(i, endpoint1, endpoint2, segments.size(), c, null, segments); //first null is c
			board.getRoutes().add(route);
		}
	}

	private List<Segment> parseSegments(JsonArray segmentArray)
	{
		List<Segment> output = new ArrayList<>();
		for(int i = 0; i < segmentArray.size(); i++)
		{
			JsonObject segment = segmentArray.get(i).getAsJsonObject();
			int x = segment.get("x").getAsInt();
			int y = segment.get("y").getAsInt();
			int r = segment.get("rotation").getAsInt();
			Segment s = new Segment(x, y, r);
			output.add(s);
		}
		return output;
	}

	private void parseTrainCards(JsonArray trainCardList, TrainCardManager manager)
	{
		int index = 0;
		for(int i = 0; i < trainCardList.size(); i++)
		{
			JsonObject card = trainCardList.get(i).getAsJsonObject();
			String color = card.get("color").getAsString();
			CardColor c = CardColor.fromString(color);
			String image = card.get("image").getAsString();
			int count = card.get("count").getAsInt();
			for(int j = 0; j < count; j++)
			{
				TrainCard trainCard = new TrainCard(c);
				manager.addCard(trainCard);
				index++;
			}
		}
	}

	private void parseDestinationCards(JsonArray destinationCardList, DestinationCardManager destManager)
	{
		for(int i = 0; i < destinationCardList.size(); i++)
		{
			JsonObject destCard = destinationCardList.get(i).getAsJsonObject();

			JsonArray endpointArray = destCard.get("endpoints").getAsJsonArray();
			String endpoint1 = endpointArray.get(0).getAsString();
			String endpoint2 = endpointArray.get(1).getAsString();
			int points = destCard.get("points").getAsInt();

			DestCard card = new DestCard(endpoint1, endpoint2, points);
			destManager.addCard(card);
		}
	}

	public static void main(String[] args)
	{

		Game game = new Game("Game", "Brad");
		game.playerManager.add(new Player("Brad"));
		game.playerManager.add(new Player("Alex"));
		game.playerManager.add(new Player("Chris"));
		game.start();
	}
}
