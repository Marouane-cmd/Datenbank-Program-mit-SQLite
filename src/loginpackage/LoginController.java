package loginpackage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import databaseutility.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modelpackage.PopUpWindow;
import modelpackage.ToolTipWindow;

public class LoginController implements Initializable{

    @FXML
    private Label dbStatusLabel;

    @FXML
    private Circle dbStatusLight;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;
    
    // Database Objekt
    Database database = new Database() ;
    
    // Login Modell 
    LoginModel loginMdel = new LoginModel();
    
    
    // Login Button klickt 
    @FXML
    void loginButtonAction(ActionEvent event) {

    	String uString = usernameTextField.getText();
    	String pString = passwordTextField.getText();
    	
    	Connection connection = database.getConnection();
    	
    	try {
			
    		if(loginMdel.isLogin(uString, pString, connection)) {
    			
    			System.out.println("Einloggen erfolgreich ");
    			PopUpWindow.display("Login erfolgreich ");
    			
    			// Das Login Fenster schlißen 
    			Stage stage = (Stage)loginButton.getScene().getWindow();
    			stage.close();
    			
    			// Öffne das neue Fenster
    			sucessLogin();
    		}else {
    			System.out.println("Lgin fehlgeschlagen");
    			PopUpWindow.display("Login fehlgeschlagen \n Username oder Passwort falsch");
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    
    // username und password- TextField ToolTip Erstellen 
    @FXML
    void enteredMouseAction(MouseEvent event) {
    	TextField textField = (TextField) event.getSource();
    	String prompText = textField.getPromptText();
    	
    	if (prompText.equals("Username")){
    		usernameTextField.setTooltip(ToolTipWindow.crateToolTip("username mind. 4 Zeichen"));
    	}else if (prompText.equals("Password")) {
    		passwordTextField.setTooltip(ToolTipWindow.crateToolTip("password mind. 4 Zeichen"));
    	}
    }
    
    public void sucessLogin() {
    	try {
			
    		Stage stage = new Stage();
    		FXMLLoader fxmlLoader = new FXMLLoader();
    		
    		Pane root = fxmlLoader.load(getClass().getResource("/kontaktpackage/Kontakt.fxml").openStream());
    		
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setTitle("Daschboard");
    		stage.setResizable(false);
    		stage.show();
    		
    		// Database Verbindung schließen 
    		
    		database.getConnection().close();
		} catch (Exception e) {

		}
    }
    
    // Überprüfen ob sich im username und password- TextField Daten befinden 
    @FXML
    void keyReleasedPoperty(KeyEvent event) {
       
    	String uString = usernameTextField.getText();
    	String pString = passwordTextField.getText();
    	
    	boolean isDisabled = ( uString.isEmpty() || uString.trim().isEmpty() || uString.length() < 4 ) || 
    			             ( pString.isEmpty() || pString.trim().isEmpty() || pString.length() < 4 )   ;
    	
    	loginButton.setDisable(isDisabled);

    }
    
  
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

      loginButton.setDisable(true);  
      
      boolean dbConnection = database.open();
      System.out.println(dbConnection);
      
      if (dbConnection) {
    	  dbStatusLabel.setText("Ok");
    	  dbStatusLight.setFill(Color.GREEN);
    	  PopUpWindow.display("Verbindung zur Datenbank aufgebaut");
      }else {
    	  dbStatusLabel.setText("Error");
    	  dbStatusLight.setFill(Color.RED);
    	  PopUpWindow.display("keine Verbindung zur Datenbank aufgebaut");
      }
    }

}
