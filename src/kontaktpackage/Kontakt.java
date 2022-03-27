package kontaktpackage;

import javafx.beans.property.SimpleStringProperty;

public class Kontakt {

	
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty phoneNumber;
	private SimpleStringProperty email;
	
	public Kontakt (String firstName , String lastName , String phoneNumber , String email) {
		
		this.firstName =new SimpleStringProperty(firstName) ;
		this.lastName = new SimpleStringProperty(lastName);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.email = new SimpleStringProperty(email);
	}
	
	// Getter und Setter

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(SimpleStringProperty firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public void setPhoneNumber(SimpleStringProperty phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
	
	 
	
	
}


