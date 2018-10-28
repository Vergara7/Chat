import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by milshinvs.18 on 15.02.2017.
 */
public class Client {

    static String host = "127.0.0.1";
    static  int port = 2391;

    public static void main(String[] args) throws IOException {
        Socket socketToServer = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
        PrintWriter out = new PrintWriter(socketToServer.getOutputStream());
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter consoleOut = new PrintWriter(System.out);
        while (true) {
            String s = consoleIn.readLine();
            out.println(s);
            out.flush();
            consoleOut.flush();
            s = in.readLine();
            consoleOut.println(s);
            consoleOut.flush();
        }
        //socketToServer.close();
    }
}
