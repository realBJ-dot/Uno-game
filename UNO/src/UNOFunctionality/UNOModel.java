package UNOFunctionality;

/**
 * this class is used as a data buffer for game state
 */
//this is a class for buffer information
public class UNOModel {

    /**
     *
     */
    private int numPlayers;
    /**
     *
     */
    private int numBaseAIs;
    /**
     *
     */
    private int numStrategicAIs;


    /**
     *
     * @return numPlayers
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     *
     * @param n
     */
    public void setNumPlayers(int n) {
        numPlayers = n;
    }

    /**
     *
     * @return numBaseAIs
     */
    public int getNumBaseAIs() {
        return numBaseAIs;
    }

    /**
     *
     * @param n
     */
    public void setNumBaseAIs(int n) {
        numBaseAIs = n;
    }

    /**
     *
     * @return numStrategicAIs
     */
    public int getNumStrategicAIs() {
        return numStrategicAIs;
    }

    /**
     *
     * @param n
     */
    public void setNumStrategicAIs(int n) {
        numStrategicAIs = n;
    }
}
