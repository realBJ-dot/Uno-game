package UNOFunctionality;

public abstract class Card {
	//PROPERTIES OF CARDS
	/**
	 * skip, reverse, draw 2, wild, wild draw 4...And Normal
	 */
	private String type;

	/**
	 *0: in draw stack. 1: in users' hand. 2: in discard pile. 3: visible to everyone and only in a player's hand
	 */
	private int state = 0;

	/**
	 *color of the card
	 */
	private String color;

	/**
	 * constructor for cards
	 */
	public Card(String setType, String setColor) {
	
		type = setType;
		color = setColor;
		state = 0;
	}

	/**
	@return get the type
	 */
	public String get_type() {
		return type;
	}
	/**
	@return get the color
	 */
	public String get_color() {
		return color;
	}
	/**
	@return set card state
	 */
	public void set_state(int setState) {
		state = setState;
	}
	/**
	 * get the state
	 */
	public int get_state() {
		return state;
	}

	/**
	 *
	 * @return if this card is wild
	 */
	public boolean isWild() {
		if (this.get_type() == "WILD_CARD" || this.get_type() == "WILD_CARD_DRAW4") {
			return true;
		}
		return false;
	}





	
}
