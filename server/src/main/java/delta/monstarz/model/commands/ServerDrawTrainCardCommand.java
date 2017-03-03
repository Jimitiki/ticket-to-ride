package delta.monstarz.model.commands;

import delta.monstarz.shared.commands.DrawTrainCardCommand;

/**
 * @author bradcarter
 */
public class ServerDrawTrainCardCommand extends DrawTrainCardCommand
{
	//Constructors
	public ServerDrawTrainCardCommand(String username, int gameID, int drawPileID)
	{
		super(username, gameID, drawPileID);
	}

	//Object Methods

	//Getters and Setters

	//Public Methods
	@Override
	public void execute()
	{
		//TODO Implement this
	}


	//Internal Methods
}
