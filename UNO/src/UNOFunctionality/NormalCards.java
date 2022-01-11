package UNOFunctionality;

public class NormalCards extends Card {
	/**
	* 0~9
	 */
	private int number;
	
	/**
	* constructor for normal card
	 */
	public NormalCards(String setType, String setColor, int setNumber) {
		super(setType, setColor);
		number = setNumber;
	}
	/**
	* get number
	 */
	public int get_number() {
		return number;
	}

	
}
