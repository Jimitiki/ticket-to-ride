package delta.monstarz;

/**
 * Created by Trevor on 2/2/2017.
 */

public class Game {

	private static int nextNewGameID = 0;

	private int gameID;
	private String name;
	private int startTime;

	//ToDo: Make the command class
	// We need to make the command class before we can use this next line
	//private List<Command>() history = new ArrayList<Command>();

	public Game(){
		this.gameID = nextNewGameID;
		nextNewGameID++;
	}

	public int getGameID() {
		return gameID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTime() {
		return startTime;
	}

	/**
	 * The game starts
	 * New players can no longer join the game
	 */
	public void start(){

	}

}
