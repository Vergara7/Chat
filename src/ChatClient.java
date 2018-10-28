import sun.applet.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by milshinvs.18 on 22.02.2017.
 */
public class ChatClient extends MessageListener{


    public static void main(String[] args) throws IOException {
        MainFrame frame = new MainFrame();
        ChatClient client = new ChatClient("127.0.0.1", 2391, frame);
        frame.setClient(client);
        client.run(client);
    }

    private final String host;
    private final int port;
    private boolean isDisconnected;
    public MainFrame frame;


    public ChatClient(String host, int port, MainFrame frame){
        this.host = host;
        this.port = port;
        this.isDisconnected = false;
        this.frame = frame;
    }

    @Override
    void onMessage(int x, int y) {
        this.frame.lineX.add(x);
        this.frame.lineY.add(y);
        if (x == -1 && y == -1){
            this.frame.lineX.clear();
            this.frame.lineY.clear();
        }
        this.frame.repaint();
    }

    @Override
    void onDisconnect() {
        System.out.println("Disconnected from server!");
        this.isDisconnected = true;
    }

    public void run(ChatClient client) throws IOException {
        System.out.println("Connecting to " + this.host + ":" + this.port + "...");
        Socket socket = new Socket(this.host, this.port);
        System.out.println("Connected");
        StreamWorker postman = new StreamWorker(socket.getInputStream(), socket.getOutputStream(), this);
        postman.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            synchronized (this) {
                if (this.isDisconnected) {
                    System.out.println("Can't send message: disconnected from server!");
                    break;
                }
                if (client.frame.unUsedX.size() > 0) {
                    postman.sendMessage(client.frame.unUsedX.get(client.frame.unUsedX.size() - 1), client.frame.unUsedY.get(client.frame.unUsedY.size() - 1));
                    client.frame.unUsedX.remove(client.frame.unUsedX.size() - 1);
                    client.frame.unUsedY.remove(client.frame.unUsedY.size() - 1);
                }
            }
        }
        System.out.println("Closing...");
        in.close();
        postman.close();
        System.out.println("Finish");
    }
}
