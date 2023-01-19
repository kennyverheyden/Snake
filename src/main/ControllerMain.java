package main;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ControllerMain {

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

	private int numberOfRows=15;
	private int numberOfColumns=15;

	private Rectangle[][] boardElements = null;

	public void initialize()
	{
		fillBoard();
		initializeGridPaneArray();
		Rectangle rctRectangle = (Rectangle) boardElements[1][0];
		rctRectangle.setFill(Color.DARKKHAKI);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() { // with anonymous class
			int i=0;

			@Override
			public void run()
			{
				if(i!=0)
				{
					boardElements[1][i-1].setFill(Color.web("#ffe494"));
				}
				boardElements[1][i].setFill(Color.DARKKHAKI);
				i++;
			}
		};
		timer.schedule(task, 2000,500);
	}

	public void initializeGridPaneArray()
	{
		boardElements = new Rectangle[numberOfRows][numberOfColumns];
		for(Node node:board.getChildren())
		{
			boardElements[GridPane.getColumnIndex(node)][GridPane.getRowIndex(node)]=(Rectangle) node;
		}
	}

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

}
