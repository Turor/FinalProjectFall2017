package application;
	


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This is the main class of the final project and is in charge of implementing a graphic user interface for
 * this game.
 * @author Matthew M
 * @version Fall 2017 - Computer Science 2 Final Project
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Define the width and size of the interface
			int width = 450;
			int height = 400;
			
			//I prefer groups to serve as my root node. Far more control
			Group root = new Group();
			//Initialize the game
			Game game = new Game();
			
			//Build the array which stores the data for the face up cards
			ImageView[][] faceUps = new ImageView[game.getRows()][game.getColumns()];
			for(int i = 0; i < faceUps.length;i++)
				for(int j = 0; j < faceUps[i].length;j++)
					faceUps[i][j] = new ImageView(new Image("Images/"+game.getCard(i, j).getValue()+".png"));
			
			//Make the array for storing all the face down images
			ImageView[][] faceDowns = new ImageView[game.getRows()][game.getColumns()];
			for(int row = 0; row < faceDowns.length;row++) 
				for(int column = 0; column< faceDowns[row].length;column++) 
					faceDowns[row][column] = new ImageView(new Image("Images/0.png"));
				
			//Initialize the game board representation
			BoardGameView cards = new BoardGameView(game.getRows(),game.getColumns(),faceDowns);
					
			//Initialize the controller
			Controller control = new Controller(game,faceUps,faceDowns);
			
			
			//Pass the pointers to the buttons to the controller
			control.setButtons(cards.getButtons());
			
			//Area which displays scores, turn status, and contains the buttons to quit, debug, and restart
			ScoreBlock gameInfo = new ScoreBlock(control);
			gameInfo.setLayoutX(340);
			gameInfo.setLayoutY(5);
			
			//Creating the game status log. This will convey game instructions without greatly hampering
			//the flow of the game.
			TextArea gameStatusLog = new TextArea();
			gameStatusLog.setMaxSize(width-10, 100);
			gameStatusLog.setLayoutY(295);
			gameStatusLog.setLayoutX(5);
			gameStatusLog.setEditable(false);
			gameStatusLog.setText("Welcome to my memory game! Your goal is to find more matching"
					+ " pairs than \nyour friend! Click a card to flip it over and try to match it to its "
					+ " partner some-\nwhere else on the board. The game is finished when all the pairs"
					+ " have been \nturned over.");

			//Pass the pointer to the gameInfo to the controller
			control.setScoreBlock(gameInfo);
			control.setGameStatusLog(gameStatusLog);
			
			//Produce the canvas to draw a random picture on
			Canvas random = new Canvas(width,height);
			random.setLayoutX(0);
			random.setLayoutY(0);
		
			//Grab the background's graphics context
			GraphicsContext rCon = random.getGraphicsContext2D();
			rCon.setFill(Color.LAVENDER);
			
			//Draw the random picture
			drawBackground(rCon,0);

			//Outline the ScoreInfo region
			rCon.fillRect(335, 5, 110, 205);
			
			//Pass the graphic context to the controller
			control.setGraphicsContext(rCon);
			
			//Add the background to the picture
			root.getChildren().add(random);
			
			//Add the buttons to the board
			root.getChildren().add(cards);
			
			//Add the displays for the game information to the application
			root.getChildren().add(gameInfo);
			
			//Add the text area which relays data like invalid moves to the user so that they can understand
			//what's happening.
			root.getChildren().add(gameStatusLog);
			
			
			
			Scene scene = new Scene(root,width,height);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void drawBackground(GraphicsContext gc, int background) {
		if(background%3 ==0)
			gc.drawImage(new Image("Images/Island.jpg"), -25, 0);
		else if(background%3 == 1)
			gc.drawImage(new Image("Images/Bridge.jpg"), -25, -300);
		else
			gc.drawImage(new Image("Images/Mountain.jpg"), -50, 0);
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
