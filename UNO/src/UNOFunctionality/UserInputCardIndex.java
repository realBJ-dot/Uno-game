package UNOFunctionality;

import javax.swing.*;
//this class is for user input entering a card index
public class UserInputCardIndex {
    /**
     * obvious
     */
    private String cardIndex;

    /**
     * constructor for user input dialog
     */
    public UserInputCardIndex() {
        JFrame frame = new JFrame("Choose a card index to play");
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        String n = JOptionPane.showInputDialog(null, "Please enter the index of card that you wanna play");
        cardIndex = n;


        //System.exit(0);
    }

    /**
     *
     * @return numberPlayers
     */
    public String get_cardIndex() {
        return cardIndex;
    }
}
