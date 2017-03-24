package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.PlayerInfo;

public class StartGameCommand extends BaseCommand {
	protected Board board;
	protected int trainCount = 45;

	public int getTrainCount() {
		return trainCount;
	}

	public void setTrainCount(int trainCount) {
		this.trainCount = trainCount;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public StartGameCommand(String username, int gameID) {
		super(username, gameID);
		name = "StartGameCommand";
		isGlobal = true;
	}

	@Override
	public void execute() {
	}
}
