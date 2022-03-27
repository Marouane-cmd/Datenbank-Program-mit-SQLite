package kontaktpackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KontaktModel {

	private final String  CONTACT_TABLE = "KontaktTable";
	private final String QUERY_DATA_FROM_CONTACT_TABLE = " SELECT * FROM " + CONTACT_TABLE  ;
	
	// Erstelle Kontakt
	public void createContact (Statement statement , String fString , String lString , String pString , String eString) throws SQLException {
		statement.execute(" INSERT INTO " + CONTACT_TABLE + " VALUES " + "('" + fString +"' , '" + lString + "' , '" + pString + "' , '" + eString + "')");
		statement.close();
	}
	
	// Lade Kontakte 
	
	public ObservableList<Kontakt> loadContacts (Statement statement , ObservableList<Kontakt> list) throws SQLException{
		
		list = FXCollections.observableArrayList();
		
		ResultSet resultSet = statement.executeQuery(QUERY_DATA_FROM_CONTACT_TABLE);
		
		while (resultSet.next()) {
			String firstName = resultSet.getString(1);
			String lasttName = resultSet.getString(2);
			String phoneNumber = resultSet.getString(3);		
			String email = resultSet.getString(4);
			
			list.add(new Kontakt(firstName, lasttName, phoneNumber, email));
		}
		
		statement.close();
		return list;
	}
}
