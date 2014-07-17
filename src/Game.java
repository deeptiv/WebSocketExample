
/**
 * A two-player game.
 */
class Game {

  

    /**
     * The current player.
     */
	WordMakerPlayer wordMakerPlayer = null;
	WordBreakerPlayer wordBreakerPlayer = null;
	boolean isGameOn = false;

    
    

    /**
     * Called by the player threads when a player tries to make a
     * move.  This method checks to see if the move is legal: that
     * is, the player requesting the move must be the current player
     * and the square in which she is trying to move must not already
     * be occupied.  If the move is legal the game state is updated
     * (the square is set and the next player becomes current) and
     * the other player is notified of the move so it can update its
     * client.
     */
    public synchronized boolean legalInput(String inputString) {
    	if (Utilities.checkInputStringLenghtAndValidDictWord(inputString)){
			return true;
		};
        return false;
    }
}
