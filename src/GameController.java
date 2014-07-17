import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;




public class GameController {

	Set<Game> games = new HashSet<Game>();
	
	public Game findFreeBreakerSlot(Set<Game> games){
		for(Game game: games){
			if(game.isGameOn == false && game.wordBreakerPlayer == null){
				return game;
			}
		}
		return null;
	}
	
	public void handleSocketInput(Session session, String message){
		
		if (message.equals("maker")){
			System.out.println("maker trying to connect");
			Game game = new Game();
			game.wordMakerPlayer = new WordMakerPlayer(session);
			session.getRemote().sendStringByFuture("maker started");
			games.add(game);
			
		}
    	if(message.equals("breaker")){
    		System.out.println("breaker trying to connect");
    		Game game = findFreeBreakerSlot(games);
    		
    		wordBreakerPlayer = new WordBreakerPlayer(session);
    		session.getRemote().sendStringByFuture("breaker started");
    	}
    	
    	if ( (wordMakerPlayer != null) && (wordBreakerPlayer != null) && !gameStarted){
			System.out.println("opponents set");
			wordMakerPlayer.setOpponent(wordBreakerPlayer);
			wordBreakerPlayer.setOpponent(wordMakerPlayer);
			session.getRemote().sendStringByFuture("opponents set");
			wordMakerPlayer.start();
			wordBreakerPlayer.start();
			gameStarted = true;
		}
		
		if (message.startsWith("MakerInput")) {
			String makerString = null;
			makerString = message.substring(10);
			wordMakerPlayer.processString(makerString);

		}

    	
    	if (message.startsWith("BreakerInput")) {
    		String breakerString;
    		breakerString = message.substring(12);
    		wordBreakerPlayer.processBreakerInput(breakerString);
			

		}

		
	}
	
	public static boolean isGameOver(BullsAndCows bullsAndCows){
		if (bullsAndCows.getBulls() == 4) 
			return true;
		else return false;
	}
	
	public static String displayMessageForPlayer(WordBreakerPlayer wordBreakerPlayer, BullsAndCows bullsAndCows, Boolean gameOver){
		String currentDisplay = wordBreakerPlayer.getWordBreakerString();

		String toDisplay= currentDisplay.concat(" Bulls: " + bullsAndCows.getBulls() + " Cows: " + bullsAndCows.getCows());
		
		if(gameOver){
			toDisplay = toDisplay.concat(" You Won!!");
		}
		
		return toDisplay;
	}
	
	public static void main(String[] args){
		GameController gc = new GameController();
	//	gc.startNewGame();
	}
	
}
