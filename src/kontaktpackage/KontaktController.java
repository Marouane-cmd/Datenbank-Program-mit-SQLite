package kontaktpackage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import databaseutility.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class KontaktController implements Initializable {

	@FXML
	private Button cancelButton;

	@FXML
	private Button createButton;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField phoneTextField;
	
	@FXML
    private TableColumn<Kontakt,String> emailColumn;

    
    @FXML
    private TableColumn<Kontakt,String> firstNameColumn;
    
	@FXML
    private TableColumn<Kontakt,String> lastNameColumn;

    @FXML
    private TableColumn<Kontakt,String> phoneColumn;

    @FXML
    private Circle dbStatusLight;

	@FXML
	private TableView<Kontakt> kontakTableView;

	// Variabeln
	private Database database = new Database ();
	private KontaktModel kontaktModel= new KontaktModel ();

	private ObservableList<Kontakt> kontakte ;

	@FXML
	void cancelButtonTapped(ActionEvent event) {

		clearAllTextFields();
	}
	
	public void clearAllTextFields () {
		
		firstNameTextField.clear();
		lastNameTextField.clear();
		phoneTextField.clear();
		emailTextField.clear();

		cancelButton.setDisable(true);
		createButton.setDisable(true);
	}

	@FXML
	void createButtonTapped(ActionEvent event) {
      
		createContact();
		loadContacts();
		clearAllTextFields();
	}

	@FXML
	void keyReleasedProerty(KeyEvent event) {

		String fString = firstNameTextField.getText();
		String lString = lastNameTextField.getText();
		String pString = phoneTextField.getText();
		String eString = emailTextField.getText();

		boolean createButtonDisable= 
				(fString.isEmpty() || fString.trim().isEmpty()) ||
				(lString.isEmpty() || lString.trim().isEmpty()) ||
				(pString.isEmpty() || pString.trim().isEmpty()) ||
				(eString.isEmpty() || eString.trim().isEmpty()) ;

		boolean cancelButtonDisable= 
				(fString.isEmpty() || fString.trim().isEmpty()) &&
				(lString.isEmpty() || lString.trim().isEmpty()) &&
				(pString.isEmpty() || pString.trim().isEmpty()) &&
				(eString.isEmpty() || eString.trim().isEmpty()) ;

		//1. Fall Abrechen erscheint sofort sobald Text geschrieben wird
		if (!cancelButtonDisable) {
			cancelButton.setDisable(false);

		}else cancelButton.setDisable(true);

		//2. Fall Erstellen erscheint nur wenn alle Daten eingegeben sind 

		if (!createButtonDisable) {
			createButton.setDisable(false);

		}else createButton.setDisable(true);


	}

	@FXML
	void loadContactOndatabase(ActionEvent event) {

        loadContacts();
        clearAllTextFields();
	}
	
	// Laden der Kontakte
	public void loadContacts() {
		try {
			kontakte = kontaktModel.loadContacts(database.getStatement(), kontakte);
			
		} catch (SQLException e) {
           e.printStackTrace();
		}
		
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Kontakt,String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Kontakt,String>("lastName"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<Kontakt,String>("phoneNumber"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Kontakt,String>("email"));
		
		kontakTableView.setItems(kontakte);
		
		
	}
	// Erstellen von Kontakten
	public void createContact () {
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String phonNumber = phoneTextField.getText();
		String email = emailTextField.getText();

		try {
			kontaktModel.createContact(database.getStatement(), firstName, lastName, phonNumber, email);
			
		} catch (SQLException e) {
           e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		createButton.setDisable(true);
		cancelButton.setDisable(true);
        
		boolean dbConnection = database.open();
		
		if (dbConnection) {
			dbStatusLight.setFill(Color.GREEN);
			
			// Lade die Kontakte aus der Tabelle(Datenbank) in die Tableview
			loadContacts();
		}else {
			dbStatusLight.setFill(Color.RED);
		}
	}

}
