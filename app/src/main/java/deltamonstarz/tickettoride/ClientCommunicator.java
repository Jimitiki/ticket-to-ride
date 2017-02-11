package deltamonstarz.tickettoride;

import java.util.Map;

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

    public static void GET(String serverHost, String serverPort, String path, String auth, Map<String, String> queries) {
	    StringBuilder address = new StringBuilder("http://" + serverHost + ":" + serverPort + path);
	    address.append("?");
	    int i = 0;
	    for (Map.Entry<String, String> query: queries.entrySet()) {
		    address.append(query.getKey() + "="+ query.getValue());
		    i++;
		    if (i < queries.size()) {
			    address.append('&');
		    }
	    }
	    GETAsyncTask task = new GETAsyncTask();
	    task.execute(address.toString(), auth);
    }


}
