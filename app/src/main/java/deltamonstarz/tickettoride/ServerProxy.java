package deltamonstarz.tickettoride;

import java.util.List;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.ICallbackHandler;
import delta.monstarz.shared.IServer;
import delta.monstarz.shared.Person;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.Result;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerProxy implements IServer {

    private final String _url;
    private final String _port;
    private String _pathRegister = "/register";
    private String _pathLogin = "/login";

    private static ServerProxy _instance = null;

    public static ServerProxy getInstance(String url, String port) {
        if (_instance == null) {
            _instance = new ServerProxy(url, port);
        }
        return _instance;
    }

    private ServerProxy(String url, String port) {
        _url = url; //"127.0.0.1"
        _port = port; //"8080"
    }

    public void executeCommand(BaseCommand command) throws Exception {

    }

    @Override
    public String register(String username, String password) {
        Args args = new Args(username, password);
        String ser = SerDes.serialize(args);
        POSTAsyncTask task = new POSTAsyncTask();
        task.setCallbackHandler(new ICallbackHandler() {
            @Override
            public void execute(Result result) {
                Presenter.getInstance().updateView();
            }
        });
        task.execute(_url, _port, _pathRegister, "", ser);
        return "";
    }

    @Override
    public String login(String username, String password) {
	    Args args = new Args(username, password);
	    String ser = SerDes.serialize(args);
        POSTAsyncTask task = new POSTAsyncTask();
        task.setCallbackHandler(new ICallbackHandler() {
            @Override
            public void execute(Result result) {
                Presenter.getInstance().updateView();
            }
        });
        task.execute(_url, _port, _pathRegister, "", ser);
	    return "";
    }

    @Override
    public int createGame(String username, String game_name, String auth) {
        Args args = new Args(username, game_name);
        String ser = SerDes.serialize(args);
        Result res = ClientCommunicator.connectAndSend(_url, _port, _pathLogin, auth, ser);
        return res.getResultInt();
    }

    @Override
    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
