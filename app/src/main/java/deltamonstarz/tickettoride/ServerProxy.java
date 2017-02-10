package deltamonstarz.tickettoride;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.ICallbackHandler;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.Result;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerProxy implements IServerProxy {

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
    public void register(String username, String password) {
        Args args = new Args(username, password);
        String ser = SerDes.serialize(args);
        ClientCommunicator.connectAndSend(_url, _port, _pathRegister, "", ser);
//        POSTAsyncTask task = new POSTAsyncTask();
//        task.setCallbackHandler(new ICallbackHandler() {
//            @Override
//            public void execute(Result result) {
//                Presenter.getInstance().updateView();
//            }
//        });
//        task.execute(_url, _port, _pathRegister, "", ser);
    }

    @Override
    public void login(String username, String password) {
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
    }

    @Override
    public void createGame(String username, String game_name, String auth) {
        Args args = new Args(username, game_name);
        String ser = SerDes.serialize(args);
        Result res = ClientCommunicator.connectAndSend(_url, _port, _pathLogin, auth, ser);
//        return res.getResultInt();
    }

    @Override
    public void listGames(String auth) {

    }
}
