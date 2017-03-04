package delta.monstarz.model.game.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import delta.monstarz.shared.model.Player;

/**
 * @author bradcarter
 */
public class PlayerManager
{
	//Class Fields
	public static final int MAX_PLAYERS = 5;

	//Instance Variables
	List<Player> players;
	Player owner;
	Player current;

	//Constructors

	public PlayerManager()
	{
		players = new ArrayList<>();
	}


	//Object Methods

	//Getters and Setters
	public TreeSet<String> getPlayerNames()
	{
		TreeSet<String> names = new TreeSet<>();
		for(Player p : players)
		{
			names.add(p.getUsername());
		}
		return names;
	}


	//Public Methods
	public int size()
	{
		return players.size();
	}

	public void add(Player player)
	{
		players.add(player);
	}

	//Internal Methods

	//Sub Class Methods-------------------------------------------------------
	//Protected Methods

	//Abstract Methods
}
