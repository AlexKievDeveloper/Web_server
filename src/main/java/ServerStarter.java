import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setPort(3000);
        server.setPathToResources("src/main/resources/webapp");
        server.start();
    }
}

