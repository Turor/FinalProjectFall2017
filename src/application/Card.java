package application;

/**
 * This Card class represents a game card and contains information on what value it has, and whether or not
 * it is currently revealed.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 */
public class Card {

	/**Instance variable containing the value which the object will be matched against*/
	private int value;

	/**Instance variable which stores whether or not the card has been turned over yet*/
	private boolean turnt;

	/**
	 * Constructor for the card class
	 * @param newvalue The new value which is going to be used for the card
	 * @param newImage The image that represents this card
	 */
	public Card(int newValue) {
		setValue(newValue);
		setTurnt(false);
	}

	/**
	 * Value mutator probably won't be used much and may be removed in future iterations
	 * @param newValue The value of the card
	 */
	public void setValue(int newValue) {
		value = newValue;
	}

	/**
	 * Sets the turned over state
	 * @param state Whether or not this card is turned over
	 */
	public void setTurnt(boolean state) {
		turnt = state;
	}

	/**
	 * Retrieves the value stored in this card
	 * @return The value of this card
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Retrieves whether or not the card is turned over
	 * @return The state of the card
	 */
	public boolean getState() {
		return turnt;
	}


	/**
	 * toString method override
	 */
	public String toString() {
		if(value == 0)
			return "\t";
		else
			if(turnt)
				return value + "\t";
			else
				return "X" + "\t";
	}


}
