
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
 
/**
 * Basic Echo Client Socket
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class BreakerSocket {
 
    private final CountDownLatch closeLatch;
 
    @SuppressWarnings("unused")
    private Session session;
 
    public BreakerSocket() {
        this.closeLatch = new CountDownLatch(1);
    }
 
    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }
 
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
        try {
            Future<Void> fut;
            Scanner scan = new Scanner(System.in);
            System.out.println("want to join as maker or breaker");
            String userInput = scan.nextLine();
            fut = session.getRemote().sendStringByFuture(userInput);
//            fut.get(2, TimeUnit.SECONDS);
//            fut = session.getRemote().sendStringByFuture("Thanks for the conversation. (from client)");
//            fut.get(2, TimeUnit.SECONDS);
//            session.close(StatusCode.NORMAL, "I'm done");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
 
    @OnWebSocketMessage
    public void onMessage(String msg) {
    	Scanner scan = new Scanner(System.in);
    	System.out.println( msg);
        if(msg.equals("next guess") || msg.startsWith("breaker guess the four")){
    		String breakerString = scan.nextLine();
    		breakerString = "BreakerInput" + breakerString;
    		session.getRemote().sendStringByFuture(breakerString);
    	}
        if(msg.startsWith("maker input a four")){
    		String makerString = scan.nextLine();
    		makerString = "MakerInput" + makerString;
    		session.getRemote().sendStringByFuture(makerString);
    	}
    }
}