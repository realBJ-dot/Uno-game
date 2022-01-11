package UNOFunctionality;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategicAI extends Players {
    /**
     * constructor for creating one GOOD AI player.
     *
     *
     * @param allCards
     * @param setIndex
     */
    public StrategicAI(List<Card> allCards, int setIndex) {
        super(allCards, setIndex);
    }

    /**
     * return available cards to play that can fit the rules otherwise return null
     * @param curGame
     */

    public List<Integer> check_penalty(Game_state curGame) {
        //store the playable cards index as a list
        List<Integer> cardIndexList = new ArrayList<>();
        curGame.check_card_effect(curGame);
        for (int i = 0; i < this.get_hand_size(); i++) {
            if (curGame.check_card_rules(i, curGame, this.index)) {
                cardIndexList.add(i);
            }
        }
        if (cardIndexList.size() == 0) {
            this.DrawCard(curGame);
            return null;
        } else {
            return cardIndexList;
        }
    }

    /**
     * this function is for AI behavior
     * @param curGame
     */
    public void AIAlgorithm(Game_state curGame) {
        //if there is no card to play, draw card

        if (check_penalty(curGame) == null) {
            this.DrawCard(curGame);
        } else {
            List<Integer> cardIndexList = check_penalty(curGame);
            // strategic AI is suppose to play the card that his hand has the most color when he has multiple options
            int cardIndex = strategicAI(cardIndexList);
            if (this.get_hand_cards().get(cardIndex).isWild()) {
                //if this is a wild card, choose a random next color
                this.PlayCard(cardIndex, curGame, getRandColor());
            } else {
                this.PlayCard(cardIndex, curGame, "");
            }
        }
    }

    /**
     * this is a helper function for strategic AI's algorithm
     */
    public int strategicAI(List<Integer> cards) {
        List<String> colorFrequency = new ArrayList<>();
        // go over this AI's hand to check which color he got the most
        for (int i = 0; i < this.get_hand_size(); i++) {
            Card thisCard = this.get_hand_cards().get(i);
            String itsColor = thisCard.get_color();
            colorFrequency.add(itsColor);
        }
        int countRed = 0;
        int countYellow = 0;
        int countGreen = 0;
        int countBlue = 0;
        for (String i : colorFrequency) {

            if (i == "RED") {
                countRed++;
            } else if (i == "YELLOW") {
                countYellow++;
            } else if (i == "GREEN") {
                countGreen++;
            } else {
                countBlue++;
            }
        }
        int max = maxOf(countBlue, countGreen, countRed, countYellow);
        String colorToPlay = "";
        // for the max occurance of that color, ai should consider it as of the most priority to play
        if (max == countBlue) {
            colorToPlay = "BLUE";
        } else if (max == countGreen) {
            colorToPlay = "GREEN";
        } else if (max == countRed) {
            colorToPlay = "RED";
        } else {
            colorToPlay = "YELLOW";
        }
        // go over the given playable cards, select the card of the top priority to play
        for (int i = 0; i < cards.size(); i++) {
            if (this.get_hand_cards().get(cards.get(i)).get_color() == colorToPlay) {
                return i;
            }
        }
        //else play a random card
        Random rand = new Random();
        int upperBound = cards.size() - 1;
        int RandInt = rand.nextInt(upperBound);
        return RandInt;



    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @return the max of a,b,c,d
     */
    public int maxOf(int a, int b, int c, int d) {
        int max = 0;
        int[] array = {a, b, c, c};
        for (int i = 0; i < 3; i++) {
            if (array[i] >= max) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     *
     * @return s random color!
     */
    private String getRandColor() {
        String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};
        Random rand = new Random();
        int upperBound = 3;
        int RandInt = rand.nextInt(upperBound);
        return colors[RandInt];
    }

}
