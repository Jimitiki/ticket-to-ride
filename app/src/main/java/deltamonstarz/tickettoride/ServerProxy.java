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

    private final String _url = "127.0.0.1";
    private final String _port = "8080";
    private String _path;

    private static ServerProxy _instance = null;

    public static ServerProxy getInstance(String url, String port) {
        if (_instance == null) {
            _instance = new ServerProxy(url, port);
        }
        return _instance;
    }

    ServerProxy(String url, String port) {
        _url = url;
        _port = port;
    }

    public void executeCommand(BaseCommand command) throws Exception {

    }

    @Override
    public String register(Person peep) {
        String ser = SerDes.serialize(peep);
        Result res = ClientCommunicator.connectAndSend(_url, _port, _path, ser);

        if (res.status() == 1) {
            System.out.println(res.getResultStr());
        } else if (res.status() == 2) {
            System.out.println(res.getResultInt());
        } else if (res.status() == 3) {
            res.throwResultErr();
        }
        return res;
        return null;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
