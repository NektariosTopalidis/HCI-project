package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class BookFormController implements Initializable{
	
	private User loggedInUser;
	private SearchForm searchFormData;
	private Accomodation selectedListing;
	private Router router = new Router();
	
	@FXML
	private Button goBackBtn;
	
	@FXML 
	private DatePicker fromDate;

	@FXML 
	private DatePicker toDate;
	
	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;
	
	@FXML
	private Spinner<Integer> peopleCountSpinner;
	private Integer currentValue;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField phoneNumberField;
	
	@FXML
	private ChoiceBox<String> arrivalTimeChoiceBox;
	private String[] arrivalTimes = new String[30];

	@FXML
	private ChoiceBox<String> edMonthSelect;
	private String[] months = {"January","February","March","April","May","June","July","August","September","Octomber","November","December"};

	@FXML
	private ChoiceBox<String> edYearSelect;
	private String[] years = {"2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033"};
	
	@FXML
	private CheckBox petCheckbox;
	
	@FXML
	private RadioButton radioBtnCash;

	@FXML
	private RadioButton radioBtnCard;
	
	@FXML
	private TextField cardNumberField;

	@FXML
	private TextField ccvField;
	
	@FXML
	private AnchorPane cardInformationPane;
	
	@FXML
	private Label priceLabel;
	
	@FXML
	private Label amountOfNightsLabel;
	
	@FXML
	private Label totalPriceLabel;
	
	@FXML
	private Label errorLabel;
	
	private LocalDate fromDateValue = null;
	private LocalDate toDateValue = null;
	
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	
		this.firstNameField.setText(this.loggedInUser.getFirstName());
		this.lastNameField.setText(this.loggedInUser.getLastName());
		
		
		this.emailField.setText(this.loggedInUser.getEmail());
		
		this.phoneNumberField.setText(this.loggedInUser.getPhoneNumber());
	}
	
	public void setSearchFormData(SearchForm sf,Integer price) {
		this.searchFormData = sf;
		
		this.fromDateValue = this.searchFormData.getFromDate();
		this.toDateValue = this.searchFormData.getToDate();
		
		toDate.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            
	            setDisable(empty || date.compareTo(fromDateValue.plusDays(1)) < 0 );
	        }
		});
		
		this.fromDate.setValue(fromDateValue);
		this.toDate.setValue(toDateValue);
		
		this.petCheckbox.setSelected(this.searchFormData.getPetCheck());
		
		SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30);
		vf.setValue(this.searchFormData.getPeopleAmount());
		peopleCountSpinner.setValueFactory(vf);
		this.currentValue = (Integer) peopleCountSpinner.getValue();
		
		peopleCountSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValue = (Integer) peopleCountSpinner.getValue();
				searchFormData.setPeopleAmount(currentValue);
			}
		});
		
		Integer amountOfNights = (int) ChronoUnit.DAYS.between(fromDateValue, toDateValue);
		
		this.amountOfNightsLabel.setText(amountOfNights.toString() + " nigths");
		this.totalPriceLabel.setText((price * amountOfNights) + "€");
	}
	
	public void setSelectedListing(Accomodation sl) {
		this.selectedListing = sl;
		
		priceLabel.setText(this.selectedListing.getPrice().toString() + "€");
	}
	
	public void backToListingPage(ActionEvent e) {
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(this.loggedInUser);
		parameters.add(this.searchFormData);
		parameters.add(this.selectedListing);
	
		try {
			this.router.navigate(e, "Accomodation Page", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void getPaymentMethod(ActionEvent e) {
		this.errorLabel.setOpacity(0);
		if(e.getSource().equals(radioBtnCash)) {
			this.cardInformationPane.setVisible(false);
			
			if(this.radioBtnCash.getStyleClass().contains("radioButtonNotSelected")) {				
				this.radioBtnCash.getStyleClass().removeAll("radioButtonNotSelected");
			}
			if(!this.radioBtnCash.getStyleClass().contains("radioButtonSelected")) {
				this.radioBtnCash.getStyleClass().add("radioButtonSelected");				
			}
			else {
				this.radioBtnCash.getStyleClass().removeAll("radioButtonSelected");
				this.radioBtnCash.getStyleClass().add("radioButtonNotSelected");	
			}
			
			if(this.radioBtnCard.isSelected()) {
				this.radioBtnCard.setSelected(false);
			}
			
			if(this.radioBtnCard.getStyleClass().contains("radioButtonSelected")) {
				this.radioBtnCard.getStyleClass().removeAll("radioButtonSelected");
				this.radioBtnCard.getStyleClass().add("radioButtonNotSelected");
			}
		}
		else {
			this.cardInformationPane.setVisible(true);
			
			if(this.radioBtnCard.getStyleClass().contains("radioButtonNotSelected")) {				
				this.radioBtnCard.getStyleClass().removeAll("radioButtonNotSelected");
			}
			
			if(!this.radioBtnCard.getStyleClass().contains("radioButtonSelected")) {
				this.radioBtnCard.getStyleClass().add("radioButtonSelected");				
			}
			else {
				this.radioBtnCard.getStyleClass().removeAll("radioButtonSelected");
				this.radioBtnCard.getStyleClass().add("radioButtonNotSelected");	
			}
			
			if(this.radioBtnCash.isSelected()) {
				this.radioBtnCash.setSelected(false);
			}
			
			if(this.radioBtnCash.getStyleClass().contains("radioButtonSelected")) {
				this.radioBtnCash.getStyleClass().removeAll("radioButtonSelected");
				this.radioBtnCash.getStyleClass().add("radioButtonNotSelected");
			}
		}
	}
	
	public void getDate(ActionEvent e) {
		this.errorLabel.setOpacity(0);
		if(e.getSource().equals(fromDate)) {
			this.fromDateValue = fromDate.getValue();
			
			toDate.setDayCellFactory(picker -> new DateCell() {
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            
		            setDisable(empty || date.compareTo(fromDateValue.plusDays(1)) < 0 );
		        }
			});
			
			toDate.setEditable(true);
			toDate.setValue(fromDateValue.plusDays(1));
			
			this.fromDate.hide();
			this.toDate.show();
			
			this.searchFormData.setFromDate(fromDateValue);
		}
		else {
			this.toDateValue = toDate.getValue();
			
			this.searchFormData.setToDate(toDateValue);
		}
		
		Integer amountOfNights = (int) ChronoUnit.DAYS.between(fromDateValue, toDateValue);
		
		this.amountOfNightsLabel.setText(amountOfNights.toString() + " nigths");
		this.totalPriceLabel.setText((this.selectedListing.getPrice() * amountOfNights) + "€");
	}
	
	private String deleteLastChar(String text) {
    	StringBuffer stringBuffer = new StringBuffer(text);

    	if (stringBuffer.length() > 0) {
    	    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
    	}
    	
    	return stringBuffer.toString();
	}
	
	public void formatCCV(KeyEvent e) {
		this.errorLabel.setOpacity(0);
		
		String acceptableCharacters = "0123456789\r\n";
		String currentText = this.ccvField.getText();
		String typedCharacter = e.getCharacter();
		
		Boolean flag = acceptableCharacters.contains(typedCharacter) || e.isControlDown();
		
	    if(flag) {
	    	Integer charactersAmount = currentText.length();
	    	if(charactersAmount > 3) {	    		
	    		String newText = this.deleteLastChar(currentText);
	    		
		    	this.ccvField.textProperty().setValue(newText);
		    	this.ccvField.positionCaret(newText.length());
	    	}
	    }
	    else {
	    	String newText = this.deleteLastChar(currentText);

	    	this.ccvField.textProperty().setValue(newText);
	    	this.ccvField.positionCaret(newText.length());
	    	
	    }
	}
	
	
	public void formatCardNumber(KeyEvent e) {
		this.errorLabel.setOpacity(0);

		String acceptableCharacters = "0123456789\r\n";
		String currentText = this.cardNumberField.getText();
		String typedCharacter = e.getCharacter();
		
		
		Boolean flag = acceptableCharacters.contains(typedCharacter) || e.isControlDown();
		
	    if(flag) {
	    	Integer charactersAmount = currentText.length();
	    	if(charactersAmount > 16) {	    		
	    		String newText = this.deleteLastChar(currentText);
	    		
		    	this.cardNumberField.textProperty().setValue(newText);
		    	this.cardNumberField.positionCaret(newText.length());
	    	}
	    }
	    else {
	    	String newText = this.deleteLastChar(currentText);

	    	this.cardNumberField.textProperty().setValue(newText);
	    	this.cardNumberField.positionCaret(newText.length());
	    	
	    }
	}

	public void completeBooking(ActionEvent e) {
		String errorText = "There was an error with ";
		
		Boolean paymentMethodSelected = true;
		if(!this.radioBtnCard.isSelected() && !this.radioBtnCash.isSelected()) {
			paymentMethodSelected = false;
			errorText += "the payment method";
		}
		
		Boolean paymentInfoFilled = true;
		if(this.radioBtnCard.isSelected()) {
			if(this.cardNumberField.getText().length() != 16 || this.edMonthSelect.getValue() == null || this.edYearSelect.getValue() == null || this.ccvField.getText().length() != 3) {
				paymentInfoFilled = false;
				errorText += "the card info";
			}
		}
		
		Boolean mainFormFilled = true;
		if(this.fromDateValue == null || this.toDateValue == null || this.currentValue < 1 || this.arrivalTimeChoiceBox.getValue() == null) {
			mainFormFilled = false;
			if(paymentInfoFilled || paymentMethodSelected) {				
				errorText += " and the booking information";
			}
			else {
				errorText += "the booking information";
			}
		}
		
		if(paymentMethodSelected && paymentInfoFilled && mainFormFilled) {
			Integer amountOfNights = (int) ChronoUnit.DAYS.between(fromDateValue, toDateValue);
			Integer min = 100000;  
			Integer max = 999999;
			Integer bookingIDNumber = (int)(Math.random()*(max-min+1)+min);
			String bookingID = "BID" + bookingIDNumber.toString();
			
			try {
				FileWriter fw = new FileWriter("C:/Users/nekta/OneDrive/Υπολογιστής/Σχολή/Αλληλεπίδαρση Ανρθώπου-Μηχανής/Εργασία/HCI-project/src/resources/bookings.txt",true);
				PrintWriter pw = new PrintWriter(fw);
				
				String text = "Booking ID: " + bookingID + "\r\n" +
							  "User:\r\n" +
							  "  -> First Name: " + this.loggedInUser.getFirstName() + "\r\n" +
							  "  -> Last Name: " + this.loggedInUser.getLastName() + "\r\n" +
							  "  -> Gender: " + this.loggedInUser.getGender() + "\r\n" +
							  "  -> Username: " + this.loggedInUser.getUsername() + "\r\n" +
							  "  -> Email: " + this.loggedInUser.getEmail() + "\r\n" +
							  "  -> Phone Number: " + this.loggedInUser.getPhoneNumber() +
							  "\r\n\r\n" +
							  "Accomodation:\r\n" +
							  "  -> Name: " + this.selectedListing.getName() + "\r\n" +
							  "  -> City: " + this.selectedListing.getCity() + "\r\n" +
							  "  -> Address: " + this.selectedListing.getAddress() + "\r\n" +
							  "  -> Accomodation Type: " + this.selectedListing.getType() + "\r\n" +
							  "  -> Stars: " + this.selectedListing.getStars().toString() + "\r\n" +
							  "  -> Price per night: " + this.selectedListing.getPrice().toString() +
							  "\r\n\r\n" +
							  "Booking Information:\r\n" +
							  "  -> From Date: " + this.fromDateValue.toString() + "\r\n" +
							  "  -> To Date: " + this.toDateValue.toString() + "\r\n" +
							  "  -> Total Days Staying: " + amountOfNights.toString() + "\r\n" +
							  "  -> Total Price: " + amountOfNights*this.selectedListing.getPrice() + "\r\n" +
							  "  -> Arrival Time: " + this.arrivalTimeChoiceBox.getValue() + "\r\n" +
							  "  -> Bringing Pets: " + this.petCheckbox.isSelected() + "\r\n" +
							  "  -> Payment Method: " + (this.radioBtnCard.isSelected() ? "Credit/Debit Card" : "Cash Upon Arrival") + "\r\n" +
							  "  -> Amount Of People Coming: " + this.currentValue.toString() +
							  "\r\n\r\n\r\n\r\n";
				
				pw.write(text);

				
				pw.close();
				
				
				List<Object> parameters = new ArrayList<Object>();
				
				parameters.add(bookingID);
				parameters.add(loggedInUser);
				
				this.router.navigate(e, "Completion Page", parameters);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			this.errorLabel.setOpacity(1);
			this.errorLabel.setText(errorText);
		}
	}
	
	public void setArrivalTime(ActionEvent e) {
		this.errorLabel.setOpacity(0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		fromDate.getEditor().setEditable(false);
		toDate.getEditor().setEditable(false);

		
		fromDate.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) < 0 );
	        }
		});
		
		this.errorLabel.setWrapText(true);
		
	    Image img = new Image(getClass().getResourceAsStream("ArrowBlack.png"));
	    ImageView view = new ImageView(img);
	    goBackBtn.setGraphic(view);
	    
	    Integer index = 0;
	    
	    for(Integer i=9;i<24;i++) {
	    	String time = "";
	    	
	    	time = i.toString();
	    	
	    	this.arrivalTimes[index] = time + ":00";
	    	index += 1;
	    	this.arrivalTimes[index] = time + ":30";
	    	index += 1;
	    	
	    }
	    
	    this.arrivalTimeChoiceBox.getItems().addAll(this.arrivalTimes);
	    
	    this.arrivalTimeChoiceBox.setOnAction(this::setArrivalTime);
	    
	    this.edMonthSelect.getItems().addAll(months);
	    this.edYearSelect.getItems().addAll(years);
	    
	    cardInformationPane.setVisible(false);
	}
}
