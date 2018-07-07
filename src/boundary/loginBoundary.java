package boundary;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import DAO.UtenteDAO;

public class loginBoundary {

	@FXML
	private TextField usernameText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private Button loginButton;

	@FXML
	private ImageView imageFilamento;

	@FXML
	private Label errorLabel;

	@FXML
	void onLoginClick(ActionEvent event) {
		login();
	}

	@FXML
	public void onEnter(ActionEvent ae) {
		login();
	}

	@SuppressWarnings("deprecation")
	public void login() {
		// da sostituire la variabile login con la chiamata a funzione
		Boolean login = true;
		Boolean admin = false;
		Stage currentStage = (Stage) usernameText.getScene().getWindow();
		if (usernameText.getText().equals("") || passwordText.getText().equals("")) {
			setErrorMsg("Username o password vuoti non ammessi");
		} else if (UtenteDAO.login(usernameText.getText(), passwordText.getText())) {
			if (UtenteDAO.getType(usernameText.getText())) {
				currentStage.close();
				Stage stage = new Stage();
				URL url;
				Pane mainPane = null;
				try {
					url = new File("src/xfml/Admin.fxml").toURL();
					mainPane = (Pane) FXMLLoader.load(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String css = this.getClass().getResource("/User.css").toExternalForm();
				Scene scene = new Scene(mainPane);
				scene.getStylesheets().add(css);
				stage.setScene(scene);
				stage.setTitle("Admin Page");
				stage.setResizable(false);
				stage.show();
			} else {
				currentStage.close();
				Stage stage = new Stage();
				URL url;
				Pane mainPane = null;
				try {
					url = new File("src/xfml/User.fxml").toURL();
					mainPane = (Pane) FXMLLoader.load(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String css = this.getClass().getResource("/User.css").toExternalForm();
				Scene scene = new Scene(mainPane);
				scene.getStylesheets().add(css);
				stage.setScene(scene);
				stage.setTitle("User Page");
				stage.setResizable(false);
				stage.show();
			}
		} else {
			setErrorMsg("Username o password errati");
		}
	}

	public void setErrorMsg(String error) {
		errorLabel.setText(error);
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				errorLabel.setText("");
			}
		}));
		fiveSecondsWonder.setCycleCount(1);
		fiveSecondsWonder.play();
		usernameText.setText("");
		passwordText.setText("");
	}

}
