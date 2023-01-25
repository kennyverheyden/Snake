package main;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerStart {

	@FXML
	private GridPane board;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnStart;

	@FXML
	private Text lblTitle;

	@FXML
	private ImageView logo;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private Text txtScore;

	@FXML
	private Text txtTailHit;

	@FXML
	private Text txtWallHit;

	@FXML
	void onClickbtnStart(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Play.fxml"));
		rootPane.getChildren().setAll(pane); // load in same window
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
