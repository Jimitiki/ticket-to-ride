package deltamonstarz.tickettoride;

import deltamonstarz.tickettoride.tasks.GETAsyncTask;
import deltamonstarz.tickettoride.tasks.POSTAsyncTask;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ClientCommunicator {

    public static void POST(String serverHost, String serverPort, String path, String auth, String reqData) {
	    String address = "http://" + serverHost + ":" + serverPort + path;
        POSTAsyncTask task = new POSTAsyncTask();
	    task.execute(address, auth, reqData);
    }

    public static void GET(String serverHost, String serverPort, String path, String auth) {
	    String address = "http://" + serverHost + ":" + serverPort + path;
	    GETAsyncTask task = new GETAsyncTask();
	    task.execute(address, auth);
    }


}
