package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SearchController implements Initializable {
	
	private Router router = new Router();
	private User loggedInUser = null;

	@FXML
	private Label usernameLabel;
	
	@FXML
	private AnchorPane searchCard;
	
	@FXML
	private CheckBox petCheckbox;
	
	@FXML
	private Spinner<Integer> peopleCountSpinner;
	
	int currentValue;
	
	@FXML
	private DatePicker fromDate;
	private LocalDate fromDateValue = null;
	
	
	@FXML
	private DatePicker toDate;
	private LocalDate toDateValue = null;;
	
	@FXML
	private TextField destinationField;
	
	@FXML
	private Button searchBtn;
	
	@FXML
	private Label errorText;
	
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
		
		if(this.loggedInUser.getGender().equals("male")) {			
			usernameLabel.setText("Welcome back Mr. " + this.loggedInUser.getFirstName() + "!");
		}
		else {
			usernameLabel.setText("Welcome back Mrs. " + this.loggedInUser.getFirstName() + "!");
		}
	}
	
	public void logout(ActionEvent e) {
		this.loggedInUser = null;
		try {
			this.router.navigate(e, "Login", null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void getDate(ActionEvent e) {
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
		}
		else {
			this.toDateValue = toDate.getValue();
		}
	}
	
	public void submitSearch(ActionEvent e) {
		
		errorText.setText("");
		
		String destination = destinationField.getText();
		Boolean petCheck = petCheckbox.isSelected();
		
		
		if(this.fromDateValue != null && this.toDateValue != null && destination != "") {
			List<Object> parameters = new ArrayList<Object>();
			
			
			SearchForm sf = new SearchForm(destination,currentValue,petCheck,fromDateValue,toDateValue);
			parameters.add(loggedInUser);
			parameters.add(sf);
			
			try {
				this.router.navigate(e, "Results", parameters);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		else {
			errorText.setText("Please fill all the fields.");
		}
	}
	
	public void goToAbout(ActionEvent e) {
		
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(loggedInUser);
		
		try {
			this.router.navigate(e, "About", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		fromDate.getEditor().setEditable(false);
		toDate.getEditor().setEditable(false);
		
		toDate.setEditable(false);
		
		toDate.setOnMouseClicked(e -> {
		     if(!toDate.isEditable())
		    	 toDate.hide();
		});
		
		fromDate.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) < 0 );
	        }
		});
		
		
	    Image img = new Image(getClass().getResourceAsStream("tabler_search.png"));
	    ImageView view = new ImageView(img);
	    searchBtn.setGraphic(view);
		
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(searchCard);
		translate.setDelay(Duration.millis(300));
		translate.setDuration(Duration.millis(400));
		translate.setByY(-50);
		
		FadeTransition fade = new FadeTransition();
		fade.setNode(searchCard);
		fade.setDelay(Duration.millis(300));
		fade.setDuration(Duration.millis(400));
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		
		fade.play();
		translate.play();
		
		SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30);
		vf.setValue(1);
		peopleCountSpinner.setValueFactory(vf);
		this.currentValue = peopleCountSpinner.getValue();
		
		peopleCountSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValue = peopleCountSpinner.getValue();
			}
		});
	}
	
}
