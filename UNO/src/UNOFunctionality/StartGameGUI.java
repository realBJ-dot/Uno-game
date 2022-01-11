package UNOFunctionality;
import com.sun.media.sound.AiffFileReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.concurrent.TimeUnit;

import static javax.swing.JOptionPane.showMessageDialog;


public class StartGameGUI {

    /**
     *  text field for empty placeholder
     */
    static JTextField t;
    /**
     * text for number of AIs
     */
    static JTextField AIt;

    /**
     * button for start
     */
    static JButton b;
    /**
     * num of strategic AIs
     */
    static JTextField SAIt;

    /**
     * constructor for start GUI
     */

    public StartGameGUI(UNOModel buffer) throws IOException {
        JFrame window = new JFrame("UNO");

        window.setSize(750, 750);
        JPanel myPanel = initializePanel(buffer);
        initializeButton(myPanel);
        setUpMenu(window);
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *
     * @param window
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game Start");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        menuBar.add(file);
        window.setJMenuBar(menuBar);
    }

    /**
     *
     * @param myPanel
     */
    private void initializeButton(JPanel myPanel) {
        JButton button = new JButton("Start");
        addStuff(myPanel, button, 375, 375);

    }

    /**
     * variables that used below
     */
    BufferedImage myPicture = ImageIO.read(new File("/Users/jinpeiyuan/eclipse-workspace/cs242/fa21-cs242-assignment1/UNO/src/UNOFunctionality/uno.png"));

    JLabel picLabel = new JLabel(new ImageIcon(myPicture));


    /**
     *
     * @return the panel that been initialized
     */
    private JPanel initializePanel(UNOModel buffer) {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(500,500));
        myPanel.setLayout(null);

        addToPanel(myPanel, buffer);

        return myPanel;
    }

    /**
     * the stuff that added to the panel
     * @param myPanel
     */
    private void addToPanel(JPanel myPanel, UNOModel buffer) {
        label:
        t = new JTextField("", 16);
        AIt = new JTextField("", 16);
        SAIt = new JTextField("", 16);
        b = new JButton("Start Game!");
        UserInput usrIn = new UserInput();
        String inputNumPlayer = usrIn.get_numPlayer();
        t.setText(inputNumPlayer);

        String inputNumAI = usrIn.get_numAIs();
        AIt.setText(inputNumAI);

        String inputNumSAI = usrIn.get_numStrategicAI();
        SAIt.setText(inputNumSAI);
        // when the start game button is clicked
        if (!isNumeric(inputNumPlayer) || !isNumeric(inputNumAI) || !isNumeric(inputNumSAI)) {
            showMessageDialog(null, "Please enter integers!");
            return;
        }
        buffer.setNumPlayers(Integer.parseInt(inputNumPlayer));
        buffer.setNumBaseAIs(Integer.parseInt(inputNumAI));
        buffer.setNumStrategicAIs(Integer.parseInt(inputNumSAI));

        b.addActionListener( new ActionListener()
        {
            @Override

            public void actionPerformed(ActionEvent e)
            {
                //this is to check if the user had inputted numbers

                try {
                    Game_state CurGame = new Game_state(buffer.getNumPlayers(), 0, buffer.getNumBaseAIs(), buffer.getNumStrategicAIs());
                    new InGameGUI(CurGame);
                    CurGame.print_status();

                    ////////////// i commenting this out since with only AI playing, it would be an infinite loop


                    // just to test GUI of the winner window, it worked great!
                    //CurGame.winner_set(CurGame, 0);
                    //new WinnerGUI(CurGame);

                    while (true) {
                        if (CurGame.winner_check(CurGame) != null) {
                            // if winner is found, display this and end the game.
                            new WinnerGUI(CurGame);
                            break;
                        }
                        CurGame.print_status();

                        GameLogic(CurGame);
                        // this is for when changing the interface, to not let the next player see the current player's hands
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        });


        //refactored!
        addStuff(myPanel, picLabel, 200, 200);
        addStuff(myPanel, t, 200, 600);
        addStuff(myPanel, new JLabel("Num Players: "), 100, 600);
        addStuff(myPanel, b, 450, 625);
        addStuff(myPanel, AIt, 200, 630);
        addStuff(myPanel, new JLabel("Num Strategic AI players: "), 45, 666);
        addStuff(myPanel, SAIt, 200, 670);
        addStuff(myPanel, new JLabel("Num AI Players: "), 100, 630);


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

    /**
     * check if the input is an int
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }
    /**
     * how the actual game is running
     * @param CurGame*
     */

    public static void GameLogic(Game_state CurGame) {
        Players curPlayer = CurGame.get_all_players().get(CurGame.whoseTurn_get());
        if (curPlayer instanceof BaseLineAI) {
            ((BaseLineAI) curPlayer).AIAlgorithm(CurGame);
        }

        CurGame.turns_changer(CurGame);
        CurGame.updateStates(CurGame);

        CurGame.if_allCards_empty();

    }

}
