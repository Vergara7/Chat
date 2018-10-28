import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by milshinvs.18 on 08.02.2017.
 */
public class MainFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener{


    ArrayList<Integer> lineX = new ArrayList<>();
    ArrayList<Integer> lineY = new ArrayList<>();
    public ArrayList<Integer> unUsedX = new ArrayList<>();
    public ArrayList<Integer> unUsedY = new ArrayList<>();
    private ChatClient client;


    MainFrame(){
        setPreferredSize(new Dimension(800, 600));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        pack();
        createBufferStrategy(2);
    }

    public static void main(String[] args){
        MainFrame frame = new MainFrame();
    }

    @Override
    public void paint(Graphics g){
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g.setColor(new Color(255, 0, 0));
        int cur = 0;
        while (cur < lineX.size() - 1){
            g.drawLine(lineX.get(cur), lineY.get(cur), lineX.get(cur + 1), lineY.get(cur + 1));
            cur++;
        }
        g.dispose();
        bufferStrategy.show();
    }

    public void restart(){
        Graphics g;
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null){
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();
        g.setColor(getBackground());
        g.fillRect(1, 1, getWidth(), getHeight());
        g.dispose();
        bufferStrategy.show();
        lineX.clear();
        lineY.clear();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lineX.add(e.getX());
        lineY.add(e.getY());
        /*synchronized (client) {
            unUsedX.add(lineX.get(lineX.size() - 1));
            unUsedY.add(lineY.get(lineY.size() - 1));
        }*/
        repaint();
    }

    private long last = System.currentTimeMillis();

    @Override
    public void mouseMoved(MouseEvent e) {
        lineX.clear();
        lineY.clear();
        /*synchronized (client) {
            unUsedX.add(-1);
            unUsedY.add(-1);
        }*/
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setClient(ChatClient client) {
        this.client = client;
    }
}
