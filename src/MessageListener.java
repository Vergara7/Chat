/**
 * Created by milshinvs.18 on 16.02.2017.
 */

public abstract class MessageListener {

    abstract void onMessage(int x, int y);

    abstract void onDisconnect();

    void onException(Exception e) {
        e.printStackTrace();
    }

}
