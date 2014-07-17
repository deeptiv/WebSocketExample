import org.eclipse.jetty.websocket.api.Session;


public class WordMakerPlayer extends Player{

	

	public WordMakerPlayer(Session session) {
		super(session);
		
	}



	public String wordMakerString;

	public String getWordMakerString() {
		return wordMakerString;
	}

	public void setWordMakerString(String wordMakerString) {
		this.wordMakerString = wordMakerString;
	}

	public void start() {

		// The thread is only started after everyone connects.
		session.getRemote().sendStringByFuture("Maker connected");

		// Tell the first player that it is her turn.

		session.getRemote().sendStringByFuture("maker input a four letter word");
	}

	public void processString(String makerString){
		this.setWordMakerString(makerString);
		System.out.println("received maker string :"+ makerString);
	}

}





