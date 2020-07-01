import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ServerTest {

    String expectedIndexHtmlFile = new String(Files.readAllBytes(Paths.get("src/main/resources/webapp/index.html")));

    public ServerTest() throws IOException {
    }

    @Before
    public void setUp(){
        Server server = new Server();
        server.setPort(3000);
        server.setPathToResources("src/main/resources/webapp");
        server.start();
    }

   @Test
    public void testRequest() {
    Client client = new Client();
    client.setHost("127.0.0.1");
    client.setPort(3000);
    client.sendRequest();
    }
}