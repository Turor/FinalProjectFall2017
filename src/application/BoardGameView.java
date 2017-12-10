package application;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class is responsible for creating the graphical representation of all of the cards on the board. It
 * does this by creating a double array of buttons.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 *
 */
public class BoardGameView extends GridPane {

	/**Instance variable which contains the pointers to all the buttons in the gridPane/board*/
	private Button[][] buttons;

	/**
	 * BoardGameView Constructor - Builds the button board
	 * @param rows The number of rows in the button board
	 * @param columns The number of columns in the button board
	 * @param faceDowns The images to be used for the face down state of all the cards
	 */
	public BoardGameView(int rows, int columns, ImageView[][] faceDowns) {

		buttons = new Button[rows][columns];
		
		for(int row = 0; row < rows;row++) {
			for(int column = 0; column < columns;column++) {
				buttons[row][column] = new Button();
				buttons[row][column].setGraphic(faceDowns[row][column]);
				add(buttons[row][column], column, row);
			}
		}
	}
	
	/**
	 * Method which returns the pointer to the double array containing the pointer to the buttons stored in
	 * the BoardGameView class
	 * @return Pointer to a double array containing the pointers to the buttons in this Board
	 */
	public Button[][] getButtons(){
		return buttons;
	}
}
