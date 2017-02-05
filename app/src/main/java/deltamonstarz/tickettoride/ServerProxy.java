package deltamonstarz.tickettoride;

import java.util.List;

import delta.monstarz.shared.GameInfo;
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
    public String register(Person peep) {
        String ser = SerDes.serialize(peep);
        Result res = ClientCommunicator.connectAndSend(_url, _port, _pathRegister, ser);
        if (res.isError()) {
            res.throwResultErr();
        }
        String auth = res.getResultStr();
        return auth;
    }

    @Override
    public String login(Person peep) {
        String ser = SerDes.serialize(peep);
        Result res = ClientCommunicator.connectAndSend(_url, _port, _pathLogin, ser);
        if (res.isError()) {
            res.throwResultErr();
        }
        String auth = res.getResultStr();
        return auth;
    }

    @Override
    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
