package UNOFunctionality;

import javax.swing.*;

public class UserInput {

    /**
     * obvious
     */
    private String numberPlayers;
    /**
     * obvious
     */
    private String numAIPlayers;

    /**
     * obvious
     */

    private String numStrategicAI;

    /**
     * constructor for user input dialog
     */
    public UserInput() {
        JFrame frame = new JFrame("Get Player Number");
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        String n = JOptionPane.showInputDialog(null, "Please enter number of players");
        numberPlayers = n;
        String n1 = JOptionPane.showInputDialog(null, "Please enter number of AI players");
        numAIPlayers = n1;
        String n2 = JOptionPane.showInputDialog(null, "Please enter number of Strategic AI players");
        numStrategicAI = n2;

        //System.exit(0);
    }

    /**
     *
     * @return numberPlayers
     */
    public String get_numPlayer() {
        return numberPlayers;
    }
    /**
     * @return number AIs
     */
    public String get_numAIs() {
        return numAIPlayers;
    }

    /**
     *
     * @return number good AIs
     */

    public String get_numStrategicAI() {
        return numStrategicAI;
    }
}
