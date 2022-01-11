package UNOFunctionality;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.io.TempDir;

public class Players {
	/**
	cards in hand
	 */
	private List<Card> CardInHand = new ArrayList<Card>();
	/**
	where player is at in the players list
	 */
	public int index;
	
	/**
	 what player choose to be the new color after he played a wild card
	 */
	public String newWildColor;
	/**
	constructor for creating one player.
	 * @param allCards
	 * @param setIndex
	 */
	public Players(List<Card> allCards, int setIndex) {
		index = setIndex;
		//getting startup cards
		int i = 0;
		while (i < 7) {
			Card temp = allCards.remove(0);
			CardInHand.add(temp);
			i++;
		}
	}
	/**
	draw one card
	 * @param cur_game
	 */
	public void DrawCard( Game_state cur_game) {
		Card drawnCard = cur_game.get_allCards().remove(0);
		drawnCard.set_state(1);
		CardInHand.add(drawnCard);
	}
	/**
	* @param i - How many times
	* @param cur_game - Cur game state
	 */
	public void Draw_many_cards(int i, Game_state cur_game) {
		while (i > 0) {
			DrawCard(cur_game);
			i--;
		}
	}
	/**
	if a player wants to play a card...     remember to check if cardIndex is valid!!!
	 * @param cardIndex
	 * @param cur_game
	 * @param chooseColor
	 */
	public Card PlayCard(int cardIndex, Game_state cur_game, String chooseColor) {
		//remove the card in hand and then add it to the dump stack
		Card tempCard = CardInHand.get(cardIndex);
		//this card is in dump pile now
		tempCard.set_state(2);
		cur_game.get_dump_pile().add(tempCard);
		if (CardInHand.get(cardIndex).get_type() == "WILD_CARD") {
			newWildColor = chooseColor;
		}
		CardInHand.remove(cardIndex);
		return tempCard;
	}
	/**
	* @return get hand cards
	 */
	public List<Card> get_hand_cards() { return CardInHand; }
	/**
	 * @return get hand size
	 */
	public int get_hand_size() { return CardInHand.size(); }

	/**
	 * @return set card state
	 */
	public void set_card_state(int state) {
		CardInHand.get(0).set_state(state);
	}
}
