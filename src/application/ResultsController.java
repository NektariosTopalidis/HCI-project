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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ResultsController implements Initializable{
	
	String lorem = "Jorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis.";
	Accomodation listing1 = new Accomodation("Mirragio", 4, "Thessaloniki", "Konstantinou Palaiologou 26", lorem, 120, null, null, null, null);
	Accomodation listing2 = new Accomodation("Mirragio", 4, "Thessaloniki", "Konstantinou Palaiologou 26", lorem, 120, null, null, null, null);
	Accomodation listing3 = new Accomodation("Mirragio", 4, "Thessaloniki", "Konstantinou Palaiologou 26", lorem, 120, null, null, null, null);
	Accomodation listing4 = new Accomodation("Mirragio", 2, "Volos", "Konstantinou Palaiologou 26", lorem, 120, null, null, null, null);
	
	Accomodation[] listings = {listing1,listing2,listing3,listing4};
	ArrayList<Accomodation> listingsToShow = new ArrayList<Accomodation>();
	
	private Router router = new Router();
	private User loggedInUser = null;
	private SearchForm searchFormData = null;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private Label resultsCounter;
	
	@FXML
	private Button newSearchBtn;
	
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
		
		String uppercaseDestination = this.searchFormData.getDestination().toUpperCase();
		
		for(Accomodation listing: listings) {
			String uppercaseListingCity = listing.getCity().toUpperCase();
			if(uppercaseListingCity.equals(uppercaseDestination)) {
				this.listingsToShow.add(listing);
			}
		}
		
		if(this.listingsToShow.size() > 0) {			
			var order = 0;
			for(Accomodation listing: listingsToShow) {
				pane.getChildren().add(listing.createListing(order));
				order++;
			}
		}
		else {
			
		}
		
		resultsCounter.setText("Results: " + listingsToShow.size());
	}
	
	public void newSearch(ActionEvent e) {
		
		this.searchFormData = null;
		
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(this.loggedInUser);
		
		try {
			this.router.navigate(e, "Search", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	    Image img = new Image(getClass().getResourceAsStream("tabler_search.png"));
	    ImageView view = new ImageView(img);
	    newSearchBtn.setGraphic(view);
	    
	}
}
