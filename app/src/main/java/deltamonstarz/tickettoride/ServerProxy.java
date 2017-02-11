package deltamonstarz.tickettoride;

import java.util.HashMap;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;

public class ServerProxy implements IServerProxy {

    private /*final*/ String _url;
    private /*final*/ String _port;
    private final String _pathRegister = "/register";
    private final String _pathLogin = "/login";
    private final String _pathCreateGame = "/create";
    private final String _pathCommand = "/command";
	private final String _pathGame = "/game";

    private static ServerProxy _instance = null;

//    public static ServerProxy getInstance(String url, String port) {
//        if (_instance == null) {
//            _instance = new ServerProxy(url, port);
//        }
//        return _instance;
//    }

//    private ServerProxy(String url, String port) {
//        _url = url; //"127.0.0.1"
//        _port = port; //"8080"
//    }

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
    public void createGame(String username, String game_name, String auth) {
        Args args = new Args(username, game_name);
        String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathCreateGame, auth, ser);
    }

    @Override
    public void listGames(String auth, String username) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
		ClientCommunicator.GET(_url, _port, _pathGame, auth, query);
    }

    @Override
    public void sendCommand(String auth, BaseCommand command) {

    }

    @Override
    public void listCommands(String auth, String gameID, String username, int curCommand) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
        query.put("gameID", gameID);
        query.put("curCommand", Integer.toString(curCommand));
        ClientCommunicator.GET(_url, _port, _pathGame, auth, query);

    }
}
