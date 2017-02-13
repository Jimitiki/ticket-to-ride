package delta.monstarz.server.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HandleLogout extends ServerHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		super.handle(exchange);
	}

	@Override
	protected String readString(InputStream is) throws IOException {
		return super.readString(is);
	}

	@Override
	protected void writeString(String str, OutputStream os) throws IOException {
		super.writeString(str, os);
	}
}
