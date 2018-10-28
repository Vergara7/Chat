import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by milshinvs.18 on 22.02.2017.
 */
public class MainServer extends MessageListener{

    private final Object segmentsModification = new Object();

    public static void main(String[] args) throws IOException {
        MainServer server = new MainServer();
        server.run();
    }

    static ArrayList<StreamWorker> postmans = new ArrayList<>();
    static ArrayList<Boolean> isConect = new ArrayList<>();


    public void run() throws IOException {
        ServerSocket server = new ServerSocket(2391);
        while (true){
            Socket client = server.accept();
            StreamWorker postman = new StreamWorker(client.getInputStream(), client.getOutputStream(), this);
            postman.start();
            postmans.add(postman);
            isConect.add(true);
        }
    }


    @Override
    void onMessage(int x, int y) {
        synchronized (segmentsModification) {
            for (int i = 0; i < postmans.size(); i++){
                if (isConect.get(i))
                    postmans.get(i).sendMessage(x, y);
            }
        }
    }

    @Override
    void onDisconnect() {

    }
}
