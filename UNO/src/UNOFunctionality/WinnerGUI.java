package UNOFunctionality;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerGUI  {
    /**
     *
     * @param curGame
     * @throws IOException
     */

    public WinnerGUI(Game_state curGame) throws IOException {
        //indicating who had won
        JFrame window = new JFrame("Winner");
        window.setSize(750, 750);

        JPanel myPanel = initializePanel(curGame);
        initializeButton(myPanel);
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *
     * @param myPanel pretty obvious
     */
    private void initializeButton(JPanel myPanel) {
        JButton button = new JButton("Start");

        button.setSize(5, 5);
        myPanel.add(button);
        button.setLocation(375, 375);
        button.setBackground(Color.BLUE);

    }

    BufferedImage myPicture = ImageIO.read(new File("/Users/jinpeiyuan/eclipse-workspace/cs242/fa21-cs242-assignment1/UNO/src/UNOFunctionality/congrats.png"));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));

    /**
     *
     * @param curGame
     * @return the panel that been initialized
     */
    private JPanel initializePanel(Game_state curGame) {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(500,500));
        myPanel.setLayout(null);
        JLabel label1 = new JLabel(curGame.winner_get(curGame) + " has won!!!!");
        addStuff(myPanel, label1, 200, 600);
        addStuff(myPanel, picLabel, 100, 50);

        return myPanel;
    }
    /**
     *
     * @param myPanel the panel to add on
     * @param o the object to add
     * @param x the x position of o
     * @param y the y position of o
     */
    private void addStuff(JPanel myPanel, Component o, int x, int y) {
        myPanel.add(o);
        o.setSize(o.getPreferredSize());
        o.setLocation(x, y);
    }

}
