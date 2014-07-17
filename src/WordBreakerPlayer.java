import java.io.IOException;
import java.util.Scanner;

import org.eclipse.jetty.websocket.api.Session;


public class WordBreakerPlayer extends Player{

	
	WordBreakerPlayer(Session session){
		super(session);
	}

	public String wordBreakerString;

	public static int MAX_NUMBER_OF_TRIES = 12;
	int numTrials = 0;
	boolean gameOver = false;
	public String wordBreakerDisplayString;

	public String getWordBreakerString() {
		return wordBreakerString;
	}

	public void setWordBreakerString(String wordBreakerString) {
		this.wordBreakerString = wordBreakerString;
	}

	String appendTowordBreakerDisplayString(String str){
		String returnString = wordBreakerDisplayString.concat(str);
		return returnString;
	}

	public void start() {



		// The thread is only started after everyone connects.
		session.getRemote().sendStringByFuture("Word Breaker Player");

		// Tell the first player that it is her turn.


		session.getRemote().sendStringByFuture("breaker guess the four letter dictionary word:");




	}
	public void processBreakerInput(String breakerString){
		{
			if(Utilities.checkInputStringLenghtAndValidDictWord(breakerString)) 
			{	
				//System.out.println("received breaker string" + breakerString);
				this.setWordBreakerString(breakerString);
				
				//						
				CountBullsAndCows countBullsAndCows = new CountBullsAndCows();
				BullsAndCows bullsAndCows = countBullsAndCows.countBullsAndCows((WordMakerPlayer)(this.getOpponent()),this);

				gameOver = GameController.isGameOver(bullsAndCows);

				String result = GameController.displayMessageForPlayer(this, bullsAndCows, gameOver);
				System.out.println("result: " + result);
				session.getRemote().sendStringByFuture(result  );
				((WordMakerPlayer)(this.getOpponent())).session.getRemote().sendStringByFuture(result  );
				numTrials ++;
			}
			if (numTrials < WordBreakerPlayer.MAX_NUMBER_OF_TRIES && !gameOver){
				session.getRemote().sendStringByFuture("next guess");
			}
		}
	}








}
