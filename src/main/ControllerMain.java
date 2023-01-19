package main;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerMain {

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnReset;

	@FXML
	private Button btnExit;

	@FXML
	private GridPane gridPane;

	@FXML
	private Text lblTimer;

	@FXML
	private Text lblTitle;

	@FXML
	private AnchorPane rootPane;
	
	private int[] x = new int[15];
	private int[] y = new int[15];
	
	public void initialize()
	{
		
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
