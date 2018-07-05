package boundary;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		@SuppressWarnings("deprecation")
		URL url = new File("src/xfml/Login.fxml").toURL();
		Pane mainPane = (Pane) FXMLLoader.load(url);
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setTitle("Login Page");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}