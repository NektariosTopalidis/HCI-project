package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AccomodationPageController implements Initializable{

	
	private Router router = new Router();
	private User loggedInUser = null;
	private SearchForm searchFormData = null;
	private Accomodation selectedListing = null;
	
	@FXML
	private Label usernameLabel;
	
	
	@FXML
	private Button goBackBtn;
	
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
		
		if(this.loggedInUser.getGender().equals("male")) {			
			usernameLabel.setText("Welcome back Mr. " + this.loggedInUser.getFirstName() + "!");
		}
		else {
			usernameLabel.setText("Welcome back Mrs. " + this.loggedInUser.getFirstName() + "!");
		}
				
	}
	
	public void setSearchFormData(SearchForm sf) {
		this.searchFormData = sf;
	}
	
	public void setSelectedListing(Accomodation sl) {
		this.selectedListing = sl;
		
		System.out.println(this.selectedListing.getId());
	}
	
	public void logout(ActionEvent e) {
		this.loggedInUser = null;
		this.searchFormData = null;
		try {
			this.router.navigate(e, "Login", null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void backToSearchResults(ActionEvent e) {
		this.selectedListing = null;
		
		List<Object> parameters = new ArrayList<Object>();
		
		
		parameters.add(loggedInUser);
		parameters.add(searchFormData);
		
		try {
			this.router.navigate(e, "Results", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	    Image img = new Image(getClass().getResourceAsStream("Arrow.png"));
	    ImageView view = new ImageView(img);
	    goBackBtn.setGraphic(view);
		
	}
	
}
