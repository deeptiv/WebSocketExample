
 
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
 
/**
 * Example of a simple Echo Client.
 */
public class BreakerClient {
 
    public static void main(String[] args) {
        //String destUri = "ws://echo.websocket.org";
        String destUri = "ws://localhost:8080/echo";
        
        if (args.length > 0) {
            destUri = args[0];
        }
        WebSocketClient client = new WebSocketClient();
        BreakerSocket socket = new BreakerSocket();
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);
            socket.awaitClose(5000, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}