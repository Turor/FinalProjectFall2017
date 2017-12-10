package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the Controller class for the game. It handles all of the action events that are generated through-
 * out the course of play.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 *
 */
public class Controller implements EventHandler<ActionEvent> {


	/**Pointer to the buttons of the game board*/
	private Button[][] buttons;

	/**Pointer to the game class*/
	private Game game;

	/**Pointer to where all the face up images are stored*/
	private ImageView[][] faceUps;

	/**Pointer to where all the face down images are stored*/
	private ImageView[][] faceDowns;

	/**Pointer to the score block class*/
	private ScoreBlock scoreBlock;

	/**Pointer to the text area which relays information regarding the game*/
	private TextArea gameInfo;
	
	/**Pointer to the graphics context which enables drawing on the background*/
	private GraphicsContext background;
	
	/**Keeps track of what background number the game is on*/
	private int backgroundNumber;

	/**
	 * Constructor for the Controller class
	 * @param newGame Pointer to the game object
	 * @param newFaceUps The pointer to where the face up images are stored
	 * @param newFaceDowns The pointer to where the face down images are stored
	 */
	public Controller( Game newGame, ImageView[][] newFaceUps, ImageView[][] newFaceDowns) {
		game = newGame;
		faceDowns = newFaceDowns;
		faceUps = newFaceUps;
		backgroundNumber = 0;
	}

	/**
	 * Method to set the pointer to the data structure which stores the button information
	 * @param newButtons The button information
	 */
	public void setButtons(Button[][] newButtons) {
		buttons = newButtons;
		for(int i = 0; i < buttons.length;i++)
			for(int j = 0; j<buttons[i].length;j++)
				buttons[i][j].setOnAction(this);
	}

	/**
	 * Method to give the controller access to the text area which relays information regarding any sort of
	 * errors and also update the player on the end of game.
	 * @param gameStatus A text area
	 */
	public void setGameStatusLog(TextArea gameStatus) {
		gameInfo = gameStatus;
	}

	/**
	 * Method to give the controller class access to the score block
	 * @param newBlock The score block pointer
	 */
	public void setScoreBlock(ScoreBlock newBlock) {
		scoreBlock = newBlock;
	}

	/**
	 * Event Handler class
	 */
	public void handle(ActionEvent event) {
		try {
			
			if(event.getSource().equals(scoreBlock.getQuit())) {
				Platform.exit();
			}else if(event.getSource().equals(scoreBlock.getRestart())) 
				restart();
			else if(event.getSource() == scoreBlock.getDebug()) {
				game.setDebugging(!game.isDebugging());
				if(game.isDebugging()) 
					gameInfo.setText("The game is now in its debugging mode.\n" + game.toString());
				else
					gameInfo.setText("The game is no longer in its debugging mode.");
			}else
				pickCardUpdates(event);
			
		}catch(Exception e) {
			gameInfo.setText(e.getMessage());
		}

	}

	/**
	 * Method for updating the graphics of each of the buttons
	 */
	private void updateButtons() {
		for(int i = 0; i < buttons.length;i++) {
			for(int j = 0; j < buttons[i].length;j++) {
				if(game.getCard(i, j).getValue() != 0)
					if(game.getCard(i,j).getState()) 
						buttons[i][j].setGraphic(faceUps[i][j]);
					else {
						buttons[i][j].setGraphic(faceDowns[i][j]);
					}		
				else {
					buttons[i][j].setVisible(false);
				}
			}
		}
	}

	/**
	 * Method which handles the events when a card is picked
	 * @param event Any game board button press
	 * @throws Exception Thrown if a user tries to press the same card twice
	 */
	private void pickCardUpdates(ActionEvent event) throws Exception {
		for(int i = 0; i < buttons.length;i++)
			for(int j = 0; j < buttons[i].length;j++) 
				if(buttons[i][j] == event.getSource()) {
					game.pickCard(i, j);
					//Update Score
					scoreBlock.getPlayerOneScoreArea().setText(""+game.getPlayerOneScore());
					scoreBlock.getPlayerTwoScoreArea().setText(""+game.getPlayerTwoScore());
					//Update Turn
					String s = "";
					if(game.isPlayerOne())
						s = "One's Turn";
					else
						s = "Two's Turn";
					scoreBlock.getPlayerTurnArea().setText(s);
					updateButtons();
				}
		
		if(game.isDebugging())
			gameInfo.setText(game.toString());
		if(game.determineFinish()) 
			gameFinished();
		
	}
	
	/**
	 * Method which handles game restarts
	 */
	private void restart() {
		game = new Game();

		//Re-populate the face ups array
		for(int i = 0; i < faceUps.length;i++)
			for(int j = 0; j < faceUps[i].length;j++)
				faceUps[i][j] = new ImageView(new Image("Images/"+game.getCard(i, j).getValue()+".png"));
		

		//Reset the graphics on the buttons to face ups
		for(int i = 0; i < buttons.length;i++)
			for(int j = 0; j < buttons[i].length;j++) {
				buttons[i][j].setGraphic(faceDowns[i][j]);
				buttons[i][j].setVisible(true);
			}

		//Reset the score block area
		scoreBlock.getPlayerOneScoreArea().setText("0");
		scoreBlock.getPlayerTwoScoreArea().setText("0");
		scoreBlock.getPlayerTurnArea().setText("One's Turn");
		
		gameInfo.setText("Welcome to my memory game! Your goal is to find more matching"
				+ " pairs than \nyour friend! Click a card to flip it over and try to match it to its "
				+ " partner some-\nwhere else on the board. The game is finished when all the pairs"
				+ " have been \nturned over. ");
		
		backgroundNumber++;
		//Draw a new background
		if(backgroundNumber%3 == 0) 
			background.drawImage(new Image("Images/Island.jpg"), -25, 0);
		else if(backgroundNumber%3 == 1)
			background.drawImage(new Image("Images/Bridge.jpg"), -25, -300);
		else
			background.drawImage(new Image("Images/Mountain.jpg"), -50, 0);
		
		//Re-outline the ScoreInfo region
		background.fillRect(335, 5, 110, 205);
		
		
	}
	
	/**
	 * Method which runs when the game is finished
	 */
	private void gameFinished() {
		String s = "";
		if(game.getPlayerOneScore()>game.getPlayerTwoScore())
			s = "Congratulations Player One on your victory!";
		else if(game.getPlayerOneScore()<game.getPlayerTwoScore())
			s = "Congratulations Player Two on your victory!";
		else
			s = "Well it looks like you two tied! That's more fun anyways.";
		gameInfo.setText(s + " \nTo play again, simply click the restart button. If you guys are done"
				+ " click the \nquit button and I hope you see you again soon :) All of the sprites"
				+ " I used \nwere created by Sithjester and he/she is gracious enough to allow free"
				+ " use \nof them so long as he/she is credited. Unfortunately, I do not know which "
				+ " \nartists created different backgrounds so I cannot give them due credit.");
	}
	
	/**
	 * Method to give access to a canvas' graphics context to enable the controller to draw on it
	 * @param gc The background's graphic's context.
	 */
	public void setGraphicsContext(GraphicsContext gc) {
		background = gc;
	}
	
	

}
