import java.io.*;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 * Created by milshinvs.18 on 16.02.2017.
 */
public class StreamWorker implements Runnable, Closeable {

    private final BufferedReader in;
    private final PrintWriter out;

    private final MessageListener listener;

    private final Object outputLock = new Object();
    private final Object listenerLock = new Object();

    public StreamWorker(InputStream input, OutputStream output, MessageListener listener){
        this.listener = listener;
        this.in = new BufferedReader(new InputStreamReader(input));
        this.out = new PrintWriter(output, true);
    }


    @Override
    public void run(){
        try {
            String s;
            while ((s = in.readLine()) != null)
                synchronized (listenerLock) {
                    StringTokenizer tokenizer = new StringTokenizer(s);
                    int x = Integer.parseInt(tokenizer.nextToken());
                    int y = Integer.parseInt(tokenizer.nextToken());
                    this.listener.onMessage(x, y);
                }
        }
        catch (SocketException e){
            if (e.getMessage().equals("Connection reset")){
                synchronized (listenerLock){
                    this.listener.onDisconnect();
                }
            }
            else{
                synchronized (listenerLock){
                    this.listener.onException(e);
                }
            }
        } catch (IOException e) {
            synchronized (listenerLock) {
                this.listener.onException(e);
            }
        }
    }

    public void start(){
        Thread thread = new Thread(this, "StreamWorker");
        thread.start();
    }

    public void sendMessage(int x, int y){
        synchronized (outputLock){
            out.println(x + " " + y);
        }
    }

    @Override
    public void close() throws IOException{
        in.close();
        out.close();
    }

}
