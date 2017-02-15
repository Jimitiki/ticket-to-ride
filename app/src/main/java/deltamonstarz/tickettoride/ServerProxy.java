package deltamonstarz.tickettoride;

import java.util.HashMap;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;

public class ServerProxy implements IServerProxy {

    private String _url;
    private String _port;
    private final String _pathRegister = "/register";
    private final String _pathLogin = "/login";
    private final String _pathCreateGame = "/create";
    private final String _pathCommand = "/command";
	private final String _pathListGames = "/game";
	private final String _pathLogout = "/logout";
    private final String _pathJoin = "/join";

    private static ServerProxy _instance = null;

    private ServerProxy() {}


    public static ServerProxy getInstance() {
        if (_instance == null) {
            _instance = new ServerProxy();
        }
        return _instance;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    public void setPort(String _port) {
        this._port = _port;
    }

    public void executeCommand(BaseCommand command) throws Exception {

    }

    @Override
    public void register(String username, String password) {
        Args args = new Args(username, password);
        String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathRegister, "", ser);
    }

    @Override
    public void login(String username, String password) {
	    Args args = new Args(username, password);
	    String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathLogin, "", ser);
    }

	@Override
	public void logout(String auth, String username) {
		String ser = SerDes.serialize(username);
		ClientCommunicator.POST(_url, _port, _pathLogout, auth, ser);
	}

	@Override
    public void createGame(String username, String game_name, String auth) {
        Args args = new Args(username, game_name);
        String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathCreateGame, auth, ser);
    }

    @Override
    public void listGames(String auth, String username) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
		ClientCommunicator.GET(_url, _port, _pathListGames, auth, query);
    }

    @Override
    public void sendCommand(String auth, BaseCommand command) {
		String ser = SerDes.serialize(command);
	    ClientCommunicator.POST(_url, _port, _pathCommand, auth, ser);
    }

    @Override
    public void listCommands(String auth, int gameID, String username, int curCommand) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
        query.put("gameID", Integer.toString(gameID));
        query.put("curCommand", Integer.toString(curCommand));
        ClientCommunicator.GET(_url, _port, _pathCommand, auth, query);
    }

    @Override
    public void joinGame(String auth, String gameID, String username) {
        String ser = SerDes.serialize(new Args(username, gameID));
        ClientCommunicator.POST(_url, _port, _pathJoin, auth, ser);
    }
}
