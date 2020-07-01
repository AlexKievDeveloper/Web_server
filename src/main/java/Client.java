import java.io.*;
import java.net.Socket;

public class Client {
    private static final String LINE_END = "\n";

    private String host;
    private int port;

    public void sendRequest() {

        try (Socket socket = new Socket(host, port);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {


            while (true) {
                String line = consoleReader.readLine();
                socketWriter.write(line);
                socketWriter.write(LINE_END);
                socketWriter.flush();

                String lineFromSocket = socketReader.readLine();
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
