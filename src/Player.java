import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.eclipse.jetty.websocket.api.Session;


public class Player {

	public String name;
	Session session;
	String inputString;
	Player opponent;

	/**
	 * Constructs a handler thread for a given socket and mark
	 * initializes the stream fields, displays the first two
	 * welcoming messages.
	 * @param session 
	 */
	public Player(Session session) {

		this.session = session;
		session.getRemote().sendStringByFuture("WELCOME ");
		session.getRemote().sendStringByFuture("MESSAGE Waiting for opponent to connect");

	}



	/**
	 * Accepts notification of who the opponent is.
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public Player getOpponent(){
		return this.opponent;
	}

}
