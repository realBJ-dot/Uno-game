package UNOFunctionality;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class InGameGUI {
    /**
     *
     * @param curGame
     * @throws IOException
     */
    public InGameGUI(Game_state curGame) throws IOException {
        JFrame window = new JFrame("In Game");
        window.setSize(750, 750);
        JPanel myPanel = initializePanel(curGame);
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * these are variables which are used in below function
     */
    List<JLabel> labels = new ArrayList<JLabel>();
    JLabel cardUHave = new JLabel("Your Cards:");

    BufferedImage myPicture = ImageIO.read(new File("/Users/jinpeiyuan/eclipse-workspace/cs242/fa21-cs242-assignment1/UNO/src/UNOFunctionality/unoback.png"));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));

    /**
     *
     * @param curGame
     * @return
     */
    private JPanel initializePanel(Game_state curGame) {
        cardUHave.setFont(new Font("Serif", Font.BOLD, 20));
        cardUHave.setForeground(Color.DARK_GRAY);
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(500,500));
        myPanel.setLayout(null);

        Players curPlayer = curGame.get_all_players().get(curGame.whoseTurn_get());


        for (int i = 0; i < curPlayer.get_hand_size(); i ++) {
            Card thisCard = curPlayer.get_hand_cards().get(i);
            //display each card in hand
            if (thisCard.get_type() == "NORMAL") {
                int number = ((NormalCards) thisCard).get_number();
                labels.add(new JLabel(thisCard.get_type() + "  " + thisCard.get_color() + "  " + number));
            } else {
                labels.add(new JLabel(thisCard.get_type() + " " + thisCard.get_color()));
            }
        }
        for (int i = 0; i < labels.size(); i++) {
            JLabel it = labels.get(i);
            addStuff(myPanel, it, 300, 600 + (i * 15));
        }
        addToPanel(myPanel);
        Card lastCard = curGame.get_last_card();
        JLabel lastCardProperty;
        if (lastCard == null) {
            lastCardProperty = new JLabel("NONE");
        } else {
            if (lastCard.get_type() == "NORMAL") {
                int number = ((NormalCards) lastCard).get_number();
                lastCardProperty = new JLabel(lastCard.get_type() + "  " + lastCard.get_color() + "  " + number);
            } else {
                lastCardProperty = new JLabel(lastCard.get_type() + " " + lastCard.get_color());
            }
        }
        myPanel.add(lastCardProperty);
        addCardProperty(myPanel, lastCardProperty);

        JButton play = new JButton("Play a card!");


        play.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //to-do: get user input of card index and play it
                //curPlayer.PlayCard(curGame);
                UserInputCardIndex usrIn = new UserInputCardIndex();
                String cardToPlay = usrIn.get_cardIndex();
                if (!isNumeric(cardToPlay)) {
                    showMessageDialog(null, "Please enter integers!");
                }
                int cardNo = Integer.parseInt(cardToPlay);
                if (curGame.check_card_rules(cardNo, curGame, curGame.whoseTurn_get())) {
                    curPlayer.PlayCard(cardNo, curGame , null);
                } else {
                    showMessageDialog(null, "You cannot play this card!");
                }
            }
        });
        addStuff(myPanel, play, 480, 620);
        //if a player click on draw, he draws
        JButton draw = new JButton("Draw a card");
        draw.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               curPlayer.DrawCard(curGame);
            }
        });
        addStuff(myPanel, draw, 485, 650);
        //addStuff(myPanel, new JTextField(cardToPlay), 530, 600);
        addStuff(myPanel, new JButton("End turn"), 490, 680);

        return myPanel;
    }

    /**
     *
     * @param myPanel
     * @param lastCardProperty the property of each card added to the panel for display sake
     */
    private void addCardProperty(JPanel myPanel, JLabel lastCardProperty) {
        lastCardProperty.setLocation(500, 480);
        lastCardProperty.setSize(lastCardProperty.getPreferredSize());
        JLabel lastC = new JLabel("Last Card");
        myPanel.add(lastC);
        lastC.setLocation(500, 500);
        lastC.setSize(lastC.getPreferredSize());
    }

    /**
     *
     * @param myPanel to refactor the super long initializePanel function
     */
    private void addToPanel(JPanel myPanel) {
        addStuff(myPanel, cardUHave, 100, 600);
        JLabel pile = new JLabel("Pile");
        pile.setFont(new Font("Serif", Font.BOLD, 20));

        addStuff(myPanel, picLabel, 100, 50);
        addStuff(myPanel, pile, 130, 350);
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
}
