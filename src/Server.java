import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by milshinvs.18 on 15.02.2017.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverDoorMan = new ServerSocket(2391);
        Socket socketToClient = serverDoorMan.accept();
        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketToClient.getInputStream()));
            PrintWriter out = new PrintWriter(socketToClient.getOutputStream(), true);
            String s = in.readLine();
            if (s == null)
                continue;
            StringTokenizer tokenizer = new StringTokenizer(s);
            String token = tokenizer.nextToken();
            if (token.equals("sum")){
                int summa = 0;
                while (tokenizer.hasMoreElements()){
                    token = tokenizer.nextToken();
                    summa += Integer.parseInt(token);
                }
                out.println(summa);
                out.flush();
            }
            if (token.equals("print")){
                while (tokenizer.hasMoreElements()){
                    token = tokenizer.nextToken();
                    out.print(token + " ");
                }
                out.println();
                out.flush();
            }
        }
    }
}
