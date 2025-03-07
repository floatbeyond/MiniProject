package gui;

import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.mysqlConnection;


/**
 * Controller for the SqlConnection GUI.
 * This class is responsible for setting up the SQL connection and starting the server.
 */
public class SqlConnectionController {
    
    @FXML private Button btnExit = null;
	@FXML private Button btnDone = null;
	
	@FXML private Label messageLabel;

    @FXML private TextField iptxt;
    @FXML private TextField usertxt;
    @FXML private TextField pwtxt;

    // Getters
    private String getip() { return iptxt.getText(); }
    private String getuser() { return usertxt.getText(); }
    private String getpw() { return pwtxt.getText(); }


    /**
     * Initializes the SqlConnection GUI if the SQL connection is set correctly.
     * @param event
     * @throws Exception
     */
    public void Done(ActionEvent event) throws Exception {
        String ip, user, pw;
        ip = getip();
        user = getuser();
        pw = getpw();

        if (ip.trim().isEmpty()) {
            displayMessage("Must enter IP");
        } else if (user.trim().isEmpty() || pw.trim().isEmpty()) {
            displayMessage("Must enter user and pw");
        } else if (!ip.matches("localhost:\\d{1,5}") && !ip.matches("127(?:\\.[0-9]{1,3}){3}:\\d{1,5}")) {
            displayMessage("Invalid SQL IP/Port");
        } else if (!user.matches("[a-zA-Z0-9]+")) {
            displayMessage("Invalid username");    
        } else if (!pw.matches("[a-zA-Z0-9!@#$%^&*()_+\\\\[\\\\]{}|;:',.<>?/]+")) {
            displayMessage("Invalid password");
        } else {
            Connection connection = mysqlConnection.connectToDB(ip, user, pw);
            if (connection == null) {
                displayMessage("Could not connect to DB");
            } else {
                ((Parent)event.getSource()).getScene().getWindow().hide(); //hiding primary window
                // Load and display the Server Port GUI
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerPort.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Server");
                stage.setResizable(false);
                stage.show();
            }
        }
    }
	
    /**
     * Exits the server UI.
     * @param event
     * @throws Exception
     */
	public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exit server UI");
		System.exit(1);	
	}

    /**
     * Starts the server UI.
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SqlConnection.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/SqlConnection.css").toExternalForm());
		primaryStage.setTitle("SQL Connector");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();		
	}

    /**
     * Displays a message on the GUI.
     * @param message
     */
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
    
}
