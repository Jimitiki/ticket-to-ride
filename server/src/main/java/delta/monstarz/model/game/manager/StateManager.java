package delta.monstarz.model.game.manager;

import delta.monstarz.model.game.state.GameState;
import delta.monstarz.model.game.state.LobbyState;

/**
 * @author bradcarter
 */
public class StateManager
{
	//Instance Variables
	GameState currentState;

	//Constructors
	public StateManager()
	{
		currentState = new LobbyState();
	}

	//Object Methods

	//Getters and Setters

	//Public Methods

	//Internal Methods

}
