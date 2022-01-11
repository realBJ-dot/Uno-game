package UNOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.print.Printable;

import org.junit.jupiter.api.Test;

import UNOFunctionality.*;
class UNOTestCase {
	/**
	 * test if correctly created number of deck of cards
	 */
	@Test
	void test_allCards() {
		Game_state temp = new Game_state(0, 0, 0, 0);
		assertEquals(108, temp.get_allCards().size(), "Initialized card number incorrect");
	}

	/**
	 * test if drawCard() is well implemented
	 */


	@Test
	void test_drawCards() {
		Game_state temp = new Game_state(2, 0, 0, 0);
		//now we can get players from the list
		Players one = temp.get_all_players().get(0);
		//Players two = temp.players_List.get(1);
		one.DrawCard(temp);
		assertEquals(8, one.get_hand_cards().size(), "not properly drawn a card!");
		assertEquals(108-15, temp.get_allCards().size(), "cards drawn is not removed from the stack pile");
	}

	/**
	 * check if playCards() is correctly implemented
	 */
	@Test
	void test_playCards() {
		Game_state temp = new Game_state(2, 0, 0, 0);
		//now we can get players from the list
		Players one = temp.get_all_players().get(0);
		Players two = temp.get_all_players().get(1);
		//System.out.println(one.Cards_in_hand.get(0));
		one.PlayCard(4, temp, null);
		
		//check if playcard() is proper
		assertEquals(6, one.get_hand_cards().size(), "not properly played a card!");
		
		//check if card been discarded
		assertEquals(1, temp.get_dump_pile().size(), "card played not in discarded pile!");
		
	}

	/**
	 * test that if deck is empty, discarded pile got it replaced
	 */
	
	@Test
	void test_if_allCards_empty() {

		Game_state temp = new Game_state(2, 0, 0, 0);
		Players one = temp.get_all_players().get(0);
		Players two = temp.get_all_players().get(1);
		int i = 0;
		while (i < 94) {
			one.DrawCard(temp);
			i++;
		}
		two.PlayCard(4, temp, null);
		temp.if_allCards_empty();
		assertEquals(temp.get_dump_pile().size(), temp.get_allCards().size(), "cards not been refilled !!!");
		//System.out.println(temp.all_cards.get(0).type);
		
	}

	/**
	 * check if check rule correctly working
	 */
	@Test
	void checkCardRule() {
		Game_state tempGame = new Game_state(4, 0, 0, 0);
		tempGame.set_last_card(new NormalCards("NORMAL", "BLUE", 9));
		//Card third = tempGame.get_all_players().get(1).get_hand_cards().get(3);
		tempGame.get_all_players().get(1).get_hand_cards().set(3, new NormalCards("NORMAL", "BLUE", 0));
		boolean checkRules = tempGame.check_card_rules(3, tempGame, 1);

		assertEquals(true, checkRules);
	}

	/**
	 * check if card effect function works properly
	 */
	@Test
	void check_cardEffect() {
		Game_state tempGame = new Game_state(4, 0, 0, 0);
		tempGame.set_last_card(new NormalCards("REVERSE", "BLUE", 9));
		tempGame.check_card_effect(tempGame);
		assertFalse(tempGame.getGameFlow());
		tempGame.set_last_card(new NormalCards("SKIP", "BLUE", 9));
		tempGame.check_card_effect(tempGame);
		//test for reversed game flow: that the turn now should be the last player!
		assertEquals(3, tempGame.whoseTurn_get());

	}

	/**
	 * check if winner is as expected
	 */
	@Test

	void checkWinner() {
		Game_state tempGame = new Game_state(4, 0, 0, 0);
		Players first = tempGame.get_all_players().get(0);
		int i = 0;
		while (i < 7) {
			first.PlayCard(0, tempGame, null);
			i++;
		}
		assertEquals(first, tempGame.winner_check(tempGame), "");
	}
	/**
	 * test check new rules
	 */
	@Test
	void  check_newRules() {
		Game_state tempGame = new Game_state(4, 0, 0, 0);
		Players first = tempGame.get_all_players().get(0);
		//
		tempGame.set_last_card(new NormalCards("NORMAL", "BLUE", 9));
		first.get_hand_cards().set(0, new NormalCards("NORMAL", "YELLOW", 4));
		first.get_hand_cards().set(1, new NormalCards("NORMAL", "YELLOW", 5));
		boolean check = tempGame.check_addition_rule(0,1,tempGame,0);
		assertEquals(true, check, "check new rule function not working for the first rule");

		int i = 0;
		while (i < 6) {
			first.PlayCard(0, tempGame, null);
			i++;
		}
		boolean anotherCheck = tempGame.check_uno_face_up(tempGame);
		assertEquals(true, anotherCheck, "not correctly detect da one player!!");
		assertEquals(3, first.get_hand_cards().get(0).get_state(), "the state of the card not properly changed");
	}

	/**
	 * check if correctly initialized the baseline AI and its functionality
	 */
	@Test
	void check_BaseLineAI() {
		Game_state tempGame = new Game_state(0, 0, 4, 0);
		Players player1 = tempGame.get_all_players().get(0);
		//test that all cards in AI's hand should be playable at the start of the game
		assertEquals(7, ((BaseLineAI)player1).check_penalty(tempGame).size());
		assertEquals(true, player1 instanceof BaseLineAI);
	}

	/**
	 * check if correctly initialized the strategic AI and its functionality
	 */
	@Test
	void check_StrategicAI() {
		Game_state tempGame = new Game_state(0, 0, 0, 4);
		Players player1 = tempGame.get_all_players().get(0);
		assertEquals(7, ((StrategicAI)player1).check_penalty(tempGame).size());
		assertEquals(true, player1 instanceof StrategicAI);
	}

}
