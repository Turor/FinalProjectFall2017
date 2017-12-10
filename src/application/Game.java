package application;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * The Game class stores and manipulates the data of n rows times c columns Cards arranged in a grid in order
 * to facilitate the playing of a memory matching game. The goal is to match more pairs than your opponent.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 *
 */
public class Game {

	/**Instance variable which stores whether it is player one's turn or not*/
	private boolean playerOne;

	/**Instance variable which determines whether or not a card has been picked yet on this turn*/
	private int cardsPicked;

	/**Instance variable containing the game board*/
	private ArrayList<ArrayList<Card>> board;

	/**Instance variable which stores the score of playerOne*/
	private int playerOneScore;

	/**Instance variable which stores the score of player two*/
	private int playerTwoScore;

	/**Instance variable which keeps track of how many cards there are total*/
	private int totalCards;

	/**Instance variable which tracks whether or not the game is currently in debug mode*/
	private boolean debugging;

	/**Instance variable which contains the value of the card that was selected one card ago*/
	private Card previousCard;

	/**Instance variable which tracks which card has most recently been selected*/
	private Card selectedCard;

	/**Instance variable containing the width of the board*/
	private int columns;

	/**Instance variable containing the height of the board*/
	private int rows;

	/**
	 * Default Constructor
	 */
	public Game() {
		playerOne = true;
		cardsPicked = 0;
		playerOneScore = 0;
		playerTwoScore = 0;
		setColumns(5);
		setRows(4);
		setDebugging(false);
		previousCard = null;
		selectedCard = null;
		generateBoard();
	}

	/**
	 * Sets how many columns the game board will have
	 * @param newColumns The number of columns in the game board
	 */
	private void setColumns(int newColumns) {
		columns = newColumns;
	}

	/**
	 * Sets how many rows the game board will have
	 * @param newRows The number of rows in the game board
	 */
	private void setRows(int newRows) {
		rows = newRows;
	}

	/**
	 * Sets whether or not the game is running in the debug mode
	 * @param state Debugging mode is either on or off
	 */
	public void setDebugging(boolean state) {
		debugging = state;
	}
	
	/**
	 * Getter method for retrieving how many rows there are in the game board
	 * @return The number of rows in the game board
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Getter method for retrieving the number of columns in the game board
	 * @return The number of columns in the game board
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Getter method for retrieving the card stored at the given row and column in the board game
	 * @param row The row that the card is stored at
	 * @param column The column that the card is stored at
	 * @return The card stored there
	 */
	public Card getCard(int row, int column) {
		return board.get(row).get(column);
	}
	
	/**
	 * Method to retrieve player one's score
	 * @return Player one's score
	 */
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	/**
	 * Method to retrieve player two's score
	 * @return Player Two's Score
	 */
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
	


	/**
	 * Method which generates the game board
	 */
	private void generateBoard() {
		totalCards = rows*columns;
		int max = totalCards/2;

		board = new ArrayList<ArrayList<Card>>();
		for(int i = 0; i < columns; i++)
			board.add(new ArrayList<Card>());



		//This array will track how many times a value has been picked
		int [] chosenCount = new int[max+1];
		for(int i = 0; i < chosenCount.length;i++) {
			chosenCount[i] = 0;
		}

		//This work around makes it so a zero value will never be added to the stack
		chosenCount[0] = 2;

		Stack<Integer> values = new Stack<Integer>(); 

		Random rand = new Random();
		while(values.size() < totalCards) {
			int currentTest = rand.nextInt(max+1);
			if(chosenCount[currentTest] < 2) {
				values.push(currentTest);
				chosenCount[currentTest]++;
			}			
		}

		//Populates the game board
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				board.get(i).add(new Card(values.pop()));
			}
		}		
	}

	/**
	 * toString Method
	 */
	public String toString() {
		String s = "";
		if(debugging)
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++)
					if(board.get(i).get(j).getValue() !=0)
						s += board.get(i).get(j).getValue() + "\t";
					else
						s += "\t";
				if(i!=rows-1)
					s += "\n";
			}
		else
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++)
					s += board.get(i).get(j);
				s += "\n";
			}
		return s;
	}

	/**
	 * Method which removes cards from the game
	 * @param a The first card to be removed
	 * @param b The second card to be removed
	 */
	private void removeCards(Card a, Card b) {
		
		a.setTurnt(false);
		a.setValue(0);
		b.setTurnt(false);
		b.setValue(0);
	}

	/**
	 * Method which controls what happens when cards are picked
	 * @param row The row of the card to be selected
	 * @param column The column of the card selected
	 */
	public void pickCard(int row, int column) throws Exception {
		Card pickedCard = board.get(row).get(column);

		if(cardsPicked == 1) {
			if(previousCard == pickedCard)
				throw new Exception("You may not select the same card twice as your move.");
			if(previousCard.getValue() == pickedCard.getValue()) {
				//If player one picks the right second card on their turn, they get a point
				//and vice versa
				if(playerOne)
					playerOneScore++;
				else
					playerTwoScore++;
				//Removes the cards from the board
				removeCards(pickedCard,previousCard);
				cardsPicked = 0;
			}else {
				//Switches to the other player's turn
				playerOne = !playerOne;
				//Turns over the card just picked
				pickedCard.setTurnt(true);
				//Notifies the game that two cards have been picked so that it can flip both back over
				//while still displaying that the card picked was wrong.
				cardsPicked = 2;
			}
			//Remembers the most recently picked card
			selectedCard = pickedCard;
		}else {
			//Creates a delay between the wrong cards being revealed
			if(cardsPicked == 2) {
				previousCard.setTurnt(false);
				selectedCard.setTurnt(false);
				cardsPicked = 0;
			}

			//Turn over the picked card and also remember it
			previousCard = pickedCard;
			previousCard.setTurnt(true);
			cardsPicked++;	

		}

	}

	/**
	 * Method to determine if the game is finished yet
	 * @return The state of the game
	 */
	public boolean determineFinish() {
		if( totalCards/2 == playerOneScore + playerTwoScore)
			return true;
		return false;
	}

	/**
	 * Method to determine whether or not it is player one's turn
	 * @return Whose turn it is
	 */
	public boolean isPlayerOne() {
		return playerOne;
	}
	
	/**
	 * Method to determine whether or not the game is in its debugging state
	 * @return The Debug state
	 */
	public boolean isDebugging() {
		return debugging;
	}
}




