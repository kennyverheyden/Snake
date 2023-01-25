package main;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class ControllerPlay {

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnReset;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane board;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Rectangle rectangle;

	@FXML
	private Text txtScore;

	@FXML
	private Text txtTailHit;

	@FXML
	private Text txtWallHit;

	//Parameters
	private int numberOfRows=15; 	// Board size rows
	private int numberOfColumns=15; // Board size columns
	private int snakeLength=3; 		// Snake length
	private int x=0;				// Init start x horizontal position
	private int y=4;				// Init start y vertical position
	private String setDirection="right"; // Set start direction

	private Random random= new Random();
	int rdX,rdY; // Store random values

	//Counters
	public Integer score=0;			// Set start score
	public Integer tailHit=0;
	public Integer wallHit=0;

	private ArrayList<Node> snake = new ArrayList<Node>(); // Store snake blocks

	ArrayList<Integer> listX = new ArrayList<>(); // Store actual x horizontal snake position
	ArrayList<Integer> listY = new ArrayList<>(); // Store actual y vertical snake position

	private Rectangle[][] boardElements = null;

	public void initialize()
	{
		txtScore.setText(score.toString());
		txtTailHit.setText(tailHit.toString());
		txtWallHit.setText(wallHit.toString());
		fillBoard();
		initializeGridPaneArray();
		setCandy();
		setSnake();
		controls();
		run();
	}

	// Run the snake
	private void run() {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() { // with anonymous class
			@Override
			public void run()
			{
				moveSnake();

			}
		};
		timer.schedule(task, 500,150);
	}

	// Set candy
	private void setCandy() {
		rdX = random.nextInt(numberOfRows-1);
		rdY = random.nextInt(numberOfColumns-1);
		Rectangle rectCandy= (Rectangle)boardElements[rdX][rdY];
		rectCandy.setFill(Color.GREEN);
	}

	// Set snake start position
	private void setSnake() {

		for (int i = 0; i < snakeLength; i++) {
			Rectangle rect= (Rectangle) boardElements[i][y];
			x=i;
			AddSnakeBlocks(rect);
			listX.add(x);
			listY.add(y);
			x++;
		}
	}

	private void AddSnakeBlocks(Rectangle rect) {
			((Shape) rect).setFill(Color.DARKKHAKI);
			snake.add(rect);
			if (snake.size()== snakeLength+1) {
				removeSnakeBlocks();
			}
	}

	private void removeSnakeBlocks() {
		Rectangle rect2= (Rectangle)snake.get(0);
		rect2.setFill(Color.web("#ffe494"));
		snake.remove(0);
	}

	public void moveSnake() {
		nextPosition();
		
		// Put the candy back in place when the snake body erase the block color
		Rectangle rectCandy= (Rectangle)boardElements[rdX][rdY];
		rectCandy.setFill(Color.GREEN);
	}

	private void nextPosition() {

		// Got the candy
		if(x== rdX && y== rdY) {
			snakeLength++;
			score++;
			txtScore.setText(score.toString());
			setCandy();
		}

		AddSnakeBlocks(boardElements[x][y]);

		// Record occupied coordinates of snake, to check collision
		listX.add(x);
		listY.add(y);
		if(listX.size() > snakeLength && listY.size() > snakeLength)
		{
			listX.remove(0);
			listY.remove(0);
		}
		for(int i=0;i<listX.size()-1;i++)
		{
			if(listX.get(i)==x && listY.get(i)==y)
			{
				tailHit++;
				txtTailHit.setText(tailHit.toString());
			}
		}

		if(setDirection=="left")
		{
			if(x==0)
			{
				x=numberOfColumns-1;
				wallHit++;
				txtWallHit.setText(wallHit.toString());
			}
			else
			{
				x--;
			}
		}
		if(setDirection=="right")
		{
			if(x==numberOfColumns-1)
			{
				x=0;
				wallHit++;
				txtWallHit.setText(wallHit.toString());
			}
			else
			{
				x++;
			}
		}
		if(setDirection=="down")
		{
			if(y==numberOfRows-1)
			{
				y=0;
				wallHit++;
				txtWallHit.setText(wallHit.toString());
			}
			else
			{
				y++;
			}
		}
		if(setDirection=="up")
		{
			if(y==0)
			{
				y=numberOfRows-1;
				wallHit++;
				txtWallHit.setText(wallHit.toString());
			}
			else
			{
				y--;
			}
		}
	}

	// Controls
	private void controls() {
		// Snake Controls
		rootPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				switch (event.getCode()) {
				case UP:
					setDirection="up";
					break;
				case DOWN:
					setDirection="down";
					break;
				case LEFT:
					setDirection="left";
					break;
				case RIGHT:
					setDirection="right";
					break;
				default:
					break;
				}
			}
		});
	}

	// Init board array
	public void initializeGridPaneArray()
	{
		boardElements = new Rectangle[numberOfRows][numberOfColumns];
		for(Node node:board.getChildren())
		{
			boardElements[GridPane.getColumnIndex(node)][GridPane.getRowIndex(node)]=(Rectangle) node;
		}
	}

	// Fill the board
	private void fillBoard() {
		int numberOfRows = 15;
		int numberOfColumns=15;
		for(int i=0;i<numberOfRows;i++)
		{
			for(int j=0;j<numberOfColumns;j++)
			{
				Rectangle rc = new Rectangle(28,28);
				rc.setFill(Color.web("#ffe494"));
				board.add(rc,i,j);
			}
		}
	}

	// Exit the game
	@FXML
	void onClickbtnExit(ActionEvent event) {
		// Box ask confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to exit the program?");
		alert.setTitle("Confirm exit");
		alert.getButtonTypes().remove(0,2);
		alert.getButtonTypes().add(0, ButtonType.YES);
		alert.getButtonTypes().add(1,ButtonType.NO);
		Optional<ButtonType> confirmationResponse = alert.showAndWait();
		if(confirmationResponse.get() == ButtonType.YES)
		{
			// Exit the program
			System.exit(0);
		}
	}

	// Enum Directions
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

}
