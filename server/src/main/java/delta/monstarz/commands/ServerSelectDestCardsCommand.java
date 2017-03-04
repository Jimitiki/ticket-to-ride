package delta.monstarz.commands;

import java.util.List;

import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;

/**
 * @author bradcarter
 */
public class ServerSelectDestCardsCommand extends SelectDestCardsCommand
{
	//Constructors
	public ServerSelectDestCardsCommand(String username, int gameID, List<DestCard> selection, List<DestCard> discard)
	{
		super(username, gameID, selection, discard);
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
