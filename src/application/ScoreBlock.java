package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The Score Block class contains game information like the player's scores, turn status, and the buttons to
 * quit, switch to debugging mode, and to restart the current game.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 *
 */
public class ScoreBlock extends GridPane {
	
	/**Text field to display player one's score*/
	private TextField playerOneScore;
	
	/**Text field to display Player two's score*/
	private TextField playerTwoScore;
	
	/**Text field which contains who's turn it is*/
	private TextField playerTurn;
	
	/**Button which enables the user to quit*/
	private Button quit;
	
	/**Button which enables the user to restart the game*/
	private Button restart;
	
	/**Button which enables debugging mode*/
	private Button debuggingMode;
	

	/**
	 * Default Constructor
	 */
	public ScoreBlock(Controller control) {
		playerOneScore = new TextField();
		playerTwoScore = new TextField();
		playerTurn = new TextField();
		
		playerOneScore.setText(""+0);
		playerTwoScore.setText("0");
		playerTurn.setText("One's Turn");
		
		
		playerOneScore.setMaxSize(100,50);
		playerTwoScore.setMaxSize(100, 50);
		playerTurn.setMaxSize(100, 50);
		
		playerOneScore.setEditable(false);
		playerTwoScore.setEditable(false);
		playerTurn.setEditable(false);
		
		quit = new Button("Quit");
		quit.setOnAction(control);
		quit.setMinSize(100, 25);
		
		restart = new Button("Restart");
		restart.setOnAction(control);
		restart.setMinSize(100, 25);
		
		debuggingMode = new Button("Debug Mode");
		debuggingMode.setMinSize(100, 25);
		debuggingMode.setOnAction(control);
		
		
		add(new Label("Player One's Score"), 0, 0);
		add(playerOneScore, 0, 1);
		
		add(new Label("Player Two's Score"), 0, 2);
		add(playerTwoScore, 0, 3);
		
		add(new Label("Whose turn is it?"), 0, 4);
		add(playerTurn, 0,5);
		
		add(quit, 0, 7);
		add(restart,0,8);
		add(debuggingMode,0,9);
	}
	
	/**
	 * Method to retrieve the pointer to the area which displays player one's score
	 * @return Pointer to player one's score area
	 */
	public TextField getPlayerOneScoreArea() {
		return playerOneScore;
	}
	
	/**
	 * Method to retrieve pointer to the area which displays player two's score
	 * @return Pointer to player two's score area
	 */
	public TextField getPlayerTwoScoreArea() {
		return playerTwoScore;
	}
	
	/**
	 * Method to retrieve the pointer to the field which will display who's turn it is
	 * @return Pointer to the play turn display field
	 */
	public TextField getPlayerTurnArea() {
		return playerTurn;
	}
	
	/**
	 * Method to get the pointer to the restart button
	 * @return The restart button pointer
	 */
	public Button getRestart() {
		return restart;
	}
	
	/**
	 * Method to get the pointer to the quit button
	 * @return The quit button
	 */
	public Button getQuit() {
		return quit;
	}
	
	/**
	 * Method to get the pointer to the debugging mode toggle
	 * @return Turns the debugging mode on/off
	 */
	public Button getDebug() {
		return debuggingMode;
	}

}
