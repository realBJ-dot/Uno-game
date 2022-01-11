package UNOFunctionality;
//import java.util.*;

import com.sun.media.sound.AiffFileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game_state {
	//STATE OF CURRENT GAME
	
	/**
	*index of each players
	 */
	private int numPlayers;
	/**
	*indicating whose turn it is now,
	 */

	private int whoseTurn = -1;
	
	/**
	*initial all cards
	 */
	private List<Card> allCards = new ArrayList<Card>();

	/**
	*	keep track of all discarded ones
	 */
	private List<Card> discardedCards = new ArrayList<Card>();

	/**
	  * keep track of the last card that played
	 */
	private Card lastPlayedCard = null;

	/**
	 * 	true if players take turns from 0 to 10, otherwise they take turns from 10 to 0
	 */
	private boolean gameFlow = true;
	
	/**
	 * all players in the game
	 */
	private List<Players> playersList = new ArrayList<Players>();

	/**
	 * Winner of the game!!
	 */
	private Players Winner;
	
	
	/**
	 * Initializing a game state of default cards and players as needed
	 */
	
	public Game_state(int set_numPlayer, int set_whoseTurn, int numAIs, int numSAIs) {

		//all four colors
		String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};
		//set No. of players
		numPlayers = set_numPlayer;
		//initializing players_list
		int i = 0;
		//setting where to start off
		whoseTurn = set_whoseTurn;
		while (i < 4) { 
			//creating all wild cards
			allCards.add(new WildCards("WILD_CARD", null));
			allCards.add(new WildDraw4Cards("WILD_CARD_DRAW4", null));
			i++;	
		}
		
		i = 0;
		while (i < 4) {
			//create all normal cards with 0.
			allCards.add(new NormalCards("NORMAL", colors[i], 0));
			i++;
		}
		//creating 2 sets of 1~9 normal cards
		for (i = 1; i <= 9; i++) {
			int j = 0;
			while(j < 4) {
				allCards.add(new NormalCards("NORMAL", colors[j], i));
				allCards.add(new NormalCards("NORMAL", colors[j], i));
				j++;
			}
		}
		i = 0;
		//creating 2 sets of functional cards
		while (i < 4) {
			allCards.add(new ReverseCards("REVERSE", colors[i]));
			allCards.add(new SkipCards("SKIP", colors[i]));
			allCards.add(new Draw2Cards("DRAW2", colors[i]));
			
			allCards.add(new ReverseCards("REVERSE", colors[i]));
			allCards.add(new SkipCards("SKIP", colors[i]));
			allCards.add(new Draw2Cards("DRAW2", colors[i]));
			i++;
		}
		//shuffle all cards at random
		Collections.shuffle(allCards);
		
		i = 0;
		while (i < numPlayers) {
			playersList.add(new Players(allCards, i));
			i++;
		}
		// say from 0~i is actual players and from i~end is baseline AIs
		int NumNow = i;
		while (i < numAIs + NumNow) {
			playersList.add(new BaseLineAI(allCards, i));
			i++;
		}
		int j = i;
		while (j < i + numSAIs) {
			playersList.add(new StrategicAI(allCards, i));
			j++;
		}
	}
	
	/**
	*if all_card is drawn out, replace it with discarded cards
	 * @return true if all cards is empty, false otherwise
	 */
	public boolean if_allCards_empty() {
		if (allCards.size() == 0) {
			Collections.shuffle(discardedCards);
			allCards = discardedCards;
			return false;
		}
		return true;
	}
	
	/**
	*check if the card that a player picked is playable, and the effect of the last player's card that he played.
	 * @param cardIndex - the index of the card in this player's hand
	 * @param cur_game - cur game state
	 * @param playerIndex - the index of the player at player list
	 * @return true if this player can play this card, false otherwise
	 */

	public boolean check_card_rules(int cardIndex, Game_state cur_game, int playerIndex) {

		Players thisPlayer = playersList.get(playerIndex);
		Players lastPlayer = null;
		if (playerIndex - 1 < 0) {
			lastPlayer = playersList.get(playersList.size() - 1);
		} else {
			lastPlayer = playersList.get(playerIndex - 1);
		}
		Card card = thisPlayer.get_hand_cards().get(cardIndex);
		//if the last card is normal
		if (lastPlayedCard == null) {
			return true;
		}
		if (lastPlayedCard.get_type() == "NORMAL" && card.get_type() == "NORMAL") {
			if (card.get_color() == lastPlayedCard.get_color() ||
					((NormalCards) card).get_number() == ((NormalCards) lastPlayedCard).get_number()) {
				return true;
			}
		}
		//if the last card is skip
		if (lastPlayedCard.get_type() == "SKIP") {
			cur_game.whoseTurn++;
			return false;
		}
		//if the last card is Draw2Cards
		if (lastPlayedCard.get_type() == "DRAW2") {
			if (lastPlayedCard.get_color() == card.get_color()) {
				return true;
			}
		}
		if (lastPlayedCard.get_type() == "REVERSE") {
			return false;
		}
		if (lastPlayedCard.get_type() == "WILD_CARD") {
			//if last player chose a new color to play with, we have to check if this card is of the same color
			String newColor = lastPlayer.newWildColor;
			//this is the color last player chose since he played a wild type card
			if (newColor == card.get_color()) {
				return true;
			}
		}
		if (lastPlayedCard.get_type() == "WILD_CARD_DRAW4") {
			String newColor = lastPlayer.newWildColor;
			//this is the color last player chose since he played a wild type card
			if (newColor == card.get_color()) {
				return true;
			}

		}
		return false;
	}
	/**
	 * @param curGame
	 * this function is responsible for the last card effect that played out as in the game state
	 */
	public void check_card_effect(Game_state curGame) {
		Card lastCard = curGame.lastPlayedCard;
		Players curPlayer = curGame.playersList.get(whoseTurn);
		if (lastCard == null) {
			return;
		}
		if (lastCard.get_type() == "REVERSE") {
			curGame.gameFlow = false;
		}
		if (lastCard.get_type() == "SKIP") {
			curGame.turns_changer(curGame);
		}
		if (lastCard.get_type() == "WILD_CARD_DRAW4") {
			//draw four cards for last played card is draw 4
			curPlayer.Draw_many_cards(4, curGame);
		}
		if (lastPlayedCard.get_type() == "DRAW2") {
			curPlayer.Draw_many_cards(2, curGame);
		}
	}

	/**
	* penalty if this player has no valid card to play
	 */
	public void penalty(int PlayerIndex, Game_state curGame) {
		playersList.get(PlayerIndex).DrawCard(curGame);
	}

    /**
    * @return - check if theres a winner!
     */
	public Players winner_check(Game_state cur_game) {
		for (int i = 0; i < cur_game.playersList.size(); i++) {
			if (cur_game.playersList.get(i).get_hand_cards().size() == 0) {
				cur_game.Winner = cur_game.playersList.get(i);
				return cur_game.Winner;
			}
		}
		return null;
	}
	/**
	 * set winner, for debugging only
	 */
	public void winner_set(Game_state curGsme, int index) {
		curGsme.Winner = curGsme.playersList.get(index);
	}
	/**
	 * get winner
	 */
	public Players winner_get(Game_state curGame) {
		return curGame.Winner;
	}
	/**
	* @return - get allCards
	 */
	public List<Card> get_allCards() {
		return allCards;
	}

	/**
	* @return - get player list
	 */
	public List<Players> get_all_players() {
		return playersList;
	}

	/**
	 *
	 * @return get players list's size
	 */
	public int get_Players_size() {
		return playersList.size();
	}
	/**
	* @return - get last played card
	 */
	public Card get_last_card() {
		return lastPlayedCard;
	}
	/**
	* set last played card, for testing purpose only
	 */
	public void set_last_card(Card setCard) {
		lastPlayedCard = setCard;
	}
	/**
	*@return - dump pile
	 */
	public List<Card> get_dump_pile() {
		return discardedCards;
	}
	/**
	*change whose turn
	 */
	public void turns_changer(Game_state curGame) {
		if (gameFlow) {
			if (whoseTurn + 1 >= curGame.get_Players_size()) {
				whoseTurn = 0;
			} else {
				whoseTurn++;
			}
		} else {
			if (whoseTurn - 1 < 0) {
				whoseTurn = curGame.get_Players_size() - 1;
			} else {
				whoseTurn--;
			}
		}
	}
	/**
	 * get whose turn
	 */
	public int whoseTurn_get() {
		return whoseTurn;
	}

	/**
	* print the current state's status
	 */
	public void print_status() {
		System.out.println("No. of players now currently playing: " + numPlayers);
		Players curPlayer = playersList.get(whoseTurn);
		System.out.println("Now it is " +  curPlayer + " 's turn, which is labeled as " + whoseTurn);
		System.out.println("And his hand size is: " + curPlayer.get_hand_size());
		System.out.println("Now the remaining No. of cards in the stack is: " + allCards.size());
		if (lastPlayedCard != null) {
			System.out.println("The last played card's type is: " + lastPlayedCard.get_type() + ", and its color is: " + lastPlayedCard.get_color());
		}

		String GameFlow;
		if (gameFlow) {
			GameFlow = "Clockwise";
		} else {
			GameFlow = "Anticlockwise";
		}
		System.out.println("The game flow now is: " + GameFlow);

		System.out.println("The dump pile size is: " + discardedCards.size());
		System.out.println("######################");
		System.out.println("######################");
	}

//////////////below are for custom rules////////////////
	/**
	*check for Addition rule
	 * @param cardIndexFirst
	 * @param cardIndexSecond
	 * @param cur_game
	 * @param playerIndex
	 * @return true if valid to play, false otherwise
	 */
	public boolean check_addition_rule(int cardIndexFirst, int cardIndexSecond, Game_state cur_game, int playerIndex) {
		Players curPlayer = cur_game.playersList.get(playerIndex);
		Card firstCard = curPlayer.get_hand_cards().get(cardIndexFirst);
		Card secondCard = curPlayer.get_hand_cards().get(cardIndexSecond);
		//addition rule: if this player has two cards of the same color and their number add up to the last one's, both are playable
		if (firstCard.get_type() == "NORMAL" && secondCard.get_type() == "NORMAL" && lastPlayedCard.get_type() == "NORMAL") {
			if (firstCard.get_color() == secondCard.get_color()) {
				if (((NormalCards) firstCard).get_number() + ((NormalCards) secondCard).get_number()
						== ((NormalCards) lastPlayedCard).get_number()) { //too long
					return true;
				}
			}
		}


		return false;
	}
	/**
	 * check for uno card face up rule
	 * @param curGame
	 * @return true if there is a player with only one card in hand, false otherwise
	 */
	public boolean check_uno_face_up(Game_state curGame) {
		for (int i = 0; i < curGame.playersList.size(); i++) {
			if (playersList.get(i).get_hand_size() == 1) {
				playersList.get(i).set_card_state(3); //3 if visible to everyone and only in a player's hand
				return true;
			}
		}
		return false;
	}

	/**
	 * this function update the states each turn
	 * @param curGame
	 */
	public void updateStates(Game_state curGame) {
		if (discardedCards.size() > 0) {
			lastPlayedCard = discardedCards.get(discardedCards.size() - 1);
		}
		if (lastPlayedCard != null) {
			lastPlayedCard.set_state(3);
		}
	}

	/**
	 *
	 * @return the game flow of the game
	 */
	public boolean getGameFlow() {
		return gameFlow;
	}


}

	
	
	

