package deltamonstarz.tickettoride;

import java.util.HashMap;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;

/**
 * Created and javadoc'd by Alex Williams
 *
 * ServerProxy treated as the server by the client and takes care of all the extra work to
 * get information across the network correctly. Uses the ClientCommunicator to spin up async tasks
 * to do the actual sending and update the model when they get the response.
 *
 * @author Alex Williams
 * @invariants None
 *
 */

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

    /**
     * Creates an instance of the ServerProxy class if there's is not one yet,
     * but returns this instance every time it is called afterwards so that
     * there is only one instance, but it is easily obtainable from anywhere.
     *  A singleton implementation
     *
     * @pre none
     * @post none
     * @return      an instance of the ServerProxy class
     */
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

    /**
     * Takes a username and password and passes it on to the communicator which
     * starts an async task to send the info and update the model with the response
     * If successful an auth token is returned for future authentication
     * Possible errors that might be passed on from the model to the presenters include:
     * *username already in use
     * *username and password must not be blank.
     *
     * @pre none
     * @post none
     * @param  username a new username a person desires to register with
     * @param  password the person's new password
     */
    @Override
    public void register(String username, String password) {
        Args args = new Args(username, password);
        String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathRegister, "", ser);
    }

    /**
     * Takes a username and password and passes it on to the communicator which
     * starts an async task to send the info and update the model with the response
     * If successful an auth token is returned for future authentication
     * Possible errors that might be passed on from the model to the presenters include:
     * *username and password do not match
     *
     * @pre none
     * @post presentor eventually signalled by the model and switches to game list
     * @param  username a new username a person desires to register with
     * @param  password the person's new password
     */
    @Override
    public void login(String username, String password) {
	    Args args = new Args(username, password);
	    String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathLogin, "", ser);
    }

    /**
     * Passes the info on to the communicator to send to the server
     * starts an async task to send the info and update the model with the response
     * If successful, a new game is created with the specified name that others may view and join
     * and the creator automatically joins the game.
     * Possible errors that might be passed on from the model to the presenters include:
     * *auth token not valid - returns them to login screen
     * *game_name cannot be blank
     *
     * @pre none
     * @post automatically joins the user to the game as well as creates it
     * @param  username a new username a person desires to register with
     * @param  game_name an arbitrary string to name a new game
     * @param auth the auth token sent back to the model by register/login for authentication
     */
	@Override
    public void createGame(String username, String game_name, String auth) {
        Args args = new Args(username, game_name);
        String ser = SerDes.serialize(args);
        ClientCommunicator.POST(_url, _port, _pathCreateGame, auth, ser);
    }

    /**
     * Passes the info on to the communicator to send to the server
     * starts an async task to send the info and update the model with the response
     * If successful, the model receives a list of the current games the user is in to view and any other
     * games he could join if desired.
     * Possible errors that might be passed on from the model to the presenters include:
     * *auth token not valid - returns them to login screen
     *
     * @pre none
     * @post none
     * @param auth the auth token sent back to the model by register/login for authentication
     * @param  username a new username a person desires to register with
     */
    @Override
    public void listGames(String auth, String username) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
		ClientCommunicator.GET(_url, _port, _pathListGames, auth, query);
    }

    /**
     * Passes the info on to the communicator to send to the server
     * starts an async task to send the info and update the model with the response
     * If successful, the server sends back a corresponding client command to execute.
     * Possible errors that might be passed on from the model to the presenters include:
     * *The server may send back an error depending on what the command is if it was not allowed.
     * and the user will be notified of the error as necessary.
     *
     * @pre none
     * @post varies with the command, but very de-coupled
     * @param auth the auth token sent back to the model by register/login for authentication
     * @param  command any command that inherits from BaseCommand to facilitate abstract communication
     */
    @Override
    public void sendCommand(String auth, BaseCommand command) {
        String ser = SerDes.serialize(command);
        ClientCommunicator.POSTCommand(_url, _port, _pathCommand, auth, ser);
    }

    /**
     * Passes the info on to the communicator to send to the server
     * starts an async task to send the info and update the model with the response
     * If successful, the server passes back a response containing a list of all commands that
     * happened after the curCommand to bring the client up to speed
     * Possible errors that might be passed on from the model to the presenters include:
     * *invalid gameID for this user - takes user back to game list
     * *invalid auth token - takes user back to login page
     *
     * @pre none
     * @post none
     * @param auth the auth token sent back to the model by register/login for authentication
     * @param  gameID the id of a game a person has joined
     * @param  username a new username a person desires to register with
     * @param curCommand The id of the last command the client received for a game.
     */
    @Override
    public void listCommands(String auth, int gameID, String username, int curCommand) {
        HashMap<String, String> query = new HashMap<>();
        query.put("username", username);
        query.put("gameID", Integer.toString(gameID));
        query.put("curCommand", Integer.toString(curCommand));
        ClientCommunicator.GET(_url, _port, _pathCommand, auth, query);
    }

    /**
     * Passes the info on to the communicator to send to the server
     * starts an async task to send the info and update the model with the response
     * If successful, the user is joined to the game and is brought to the game play screen
     * Possible errors that might be passed on from the model to the presenters include:
     * *Game already full
     * *invalid auth token - user is sent to the login screen.
     *
     * @pre none
     * @post the user is then in a game and the presentor is signaled to switch the view
     * @param auth the auth token sent back to the model by register/login for authentication
     * @param  gameID the id of a game the user sees listed and wishes to join
     * @param  username a new username a person desires to register with
     */
    @Override
    public void joinGame(String auth, String gameID, String username) {
        String ser = SerDes.serialize(new Args(username, gameID));
        ClientCommunicator.POST(_url, _port, _pathJoin, auth, ser);
    }
}
