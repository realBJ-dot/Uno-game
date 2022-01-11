package UNOFunctionality;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseLineAI extends Players {
    /**
     * constructor for creating one baselineAI
     *
     * @param allCards
     * @param setIndex
     */
    public BaseLineAI(List<Card> allCards, int setIndex) {
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
            //say this ai plays a card from index list
            int cardIndex = cardIndexList.get(0);
            if (this.get_hand_cards().get(cardIndex).isWild()) {
                //if this is a wild card, choose a random next color
                this.PlayCard(cardIndex, curGame, getRandColor());
            } else {
                this.PlayCard(cardIndex, curGame, "");
            }
        }
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

