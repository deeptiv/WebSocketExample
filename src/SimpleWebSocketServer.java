import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class SimpleWebSocketServer
{
    
	@WebSocket
    public  static class EchoSocket
    {
		private static WordBreakerPlayer wordBreakerPlayer = null;
		private static WordMakerPlayer wordMakerPlayer = null;
		private static boolean gameStarted = false;
		@OnWebSocketMessage
        public void onMessage(Session session, String message)
        {
        	System.out.println( message);
        	if (message.equals("maker")){
				System.out.println("maker trying to connect");
				wordMakerPlayer = new WordMakerPlayer(session);
				session.getRemote().sendStringByFuture("maker started");
				
				
			}
        	if(message.equals("breaker")){
        		System.out.println("breaker trying to connect");
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
//        	message = message + " added by server";
//            session.getRemote().sendStringByFuture(message);
        }
    }

    /**
     * Servlet layer
     */
    @SuppressWarnings("serial")
    public static class EchoServlet extends WebSocketServlet
    {
        @Override
        public void configure(WebSocketServletFactory factory)
        {
            // Register the echo websocket with the basic WebSocketCreator
            factory.register(EchoSocket.class);
        }


    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add the echo socket servlet to the /echo path map
        context.addServlet(new ServletHolder(EchoServlet.class),"/echo");

        server.start();
        context.dumpStdErr();
        server.join();
    }
}