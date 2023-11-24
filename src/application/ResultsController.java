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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ResultsController implements Initializable{
	String lorem = "Jorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis.";
	String[] amenities = {"Parking","Breakfast","Bar","No Smoking","Wifi","Room Service"};
	Comment comment1 = new Comment("User1",3,"Yorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis.");
	Comment comment2 = new Comment("User2",4,"Yorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis.");
	Comment comment3 = new Comment("User3",2,"Yorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis.");
	Comment[] comments = {comment1,comment2,comment3};
	
	Image listing1Thumbnail = new Image(getClass().getResourceAsStream("hotel1Thumbnail.jpg"));
	Image[] listing1Images = {new Image(getClass().getResourceAsStream("hotel1Image1.jpg")),new Image(getClass().getResourceAsStream("hotel1Image2.jpg")),new Image(getClass().getResourceAsStream("hotel1Image3.jpg")),new Image(getClass().getResourceAsStream("hotel1Image4.jpg"))};
	Accomodation listing1 = new Accomodation("l1","Hotel1", 2, "Thessaloniki", "Address of hotel1", lorem, 40, listing1Thumbnail, "Hotel Room", listing1Images, amenities, comments);
	
	Image listing2Thumbnail = new Image(getClass().getResourceAsStream("hotel2Thumbnail.jpg"));
	Image[] listing2Images = {new Image(getClass().getResourceAsStream("hotel2Image1.jpg")),new Image(getClass().getResourceAsStream("hotel2Image2.jpg")),new Image(getClass().getResourceAsStream("hotel2Image3.jpg")),new Image(getClass().getResourceAsStream("hotel2Image4.jpg"))};
	Accomodation listing2 = new Accomodation("l2","Hotel2", 3, "Thessaloniki", "Address of hotel2", lorem, 75, listing2Thumbnail, "Whole House", listing2Images, amenities, comments);
	
	Image listing3Thumbnail = new Image(getClass().getResourceAsStream("hotel3Thumbnail.jpg"));
	Image[] listing3Images = {new Image(getClass().getResourceAsStream("hotel3Image1.jpg")),new Image(getClass().getResourceAsStream("hotel3Image2.jpg")),new Image(getClass().getResourceAsStream("hotel3Image3.jpg")),new Image(getClass().getResourceAsStream("hotel3Image4.jpg"))};
	Accomodation listing3 = new Accomodation("l3","Hotel3", 4, "Thessaloniki", "Address of hotel3", lorem, 120, listing3Thumbnail, "Apartment", listing3Images, amenities, comments);
	
	Accomodation listing4 = new Accomodation("l4","Hotel4", 2, "Volos", "Address of hotel4", lorem, 26, listing3Thumbnail, "Apartment", listing3Images, amenities, comments);
	
	Accomodation[] listings = {listing1,listing2,listing3,listing4};
	ArrayList<Accomodation> listingsToShow = new ArrayList<Accomodation>();
	ArrayList<Accomodation> tempListingsToShow = new ArrayList<Accomodation>();
	
	private Boolean starsFilterIsActive = false;
	private Boolean accTypeFilterIsActive = false;
	private CheckBox activeStarFilter = null;
	private CheckBox activeAccTypeFilter = null;
	
	private Router router = new Router();
	private User loggedInUser = null;
	private SearchForm searchFormData = null;
	
	@FXML
	private CheckBox starFilter1;
	
	@FXML
	private CheckBox starFilter2;
	
	@FXML
	private CheckBox starFilter3;
	
	@FXML
	private CheckBox starFilter4;
	
	@FXML
	private CheckBox starFilter5;
	
	ArrayList<CheckBox> starFilters = new ArrayList<CheckBox>();
	
	@FXML
	private CheckBox accTypeWH;
	
	@FXML
	private CheckBox accTypeA;
	
	@FXML
	private CheckBox accTypeHR;
	
	ArrayList<CheckBox> accTypeFilters = new ArrayList<CheckBox>();
	
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
		
		tempListingsToShow.addAll(listingsToShow);
		
		setupScrollPane();

		resultsCounter.setText("Results: " + listingsToShow.size());
	}
	
	public void setupScrollPane() {
		if(this.tempListingsToShow.size() > 0) {	
			
			if(this.listingsToShow.size() > 0) {				
				var order = 0;
				for(Accomodation listing: listingsToShow) {
					pane.getChildren().add(listing.createListing(order));
					listing.seeMoreBtn.setOnAction(e -> {
						this.seeListing(listing, e);
					});
					order++;
				}
			}
			else {
				createNoResultsText();
			}
		}
		else {
			createNoResultsText();
		}
	}
	
	public void createNoResultsText() {
		Label noResultsText = new Label();
		noResultsText.setText("No results");
		noResultsText.getStyleClass().add("noResultsText");
		
		pane.getChildren().add(noResultsText);
		
		noResultsText.setLayoutX(155);
		noResultsText.setLayoutY(260);
		
	    Image img = new Image(getClass().getResourceAsStream("noResults.png"));
	    ImageView view = new ImageView(img);
	    
	    pane.getChildren().add(view);
	    
	    view.setX(270);
	    view.setY(260);		
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
	
	public void changeStarsFilter(ActionEvent e) {
		
		String selectedText = ((CheckBox)e.getTarget()).getText();
		Integer starsAmount = Integer.parseInt(selectedText.split(" ")[0]);
		
		starsFilterIsActive = checkFilterIsActive("stars",e);
		
		if(tempListingsToShow.size() > 0) {
			
			pane.getChildren().clear();
			
			listingsToShow.clear();
			listingsToShow.addAll(tempListingsToShow);
			
			if(starsFilterIsActive) {				
				for(CheckBox starFilter: starFilters) {
					if(!starFilter.equals(e.getTarget())) {
						starFilter.setSelected(false);
					}
				}
				

				
				for(Accomodation listing: tempListingsToShow) {
					if(listing.getStars() != starsAmount) {
						listingsToShow.remove(listing);
					}
				}
			}
			
			
			if(listingsToShow.size()>0 && accTypeFilterIsActive) {
				ArrayList<Accomodation> remainingListings = new ArrayList<Accomodation>();
				remainingListings.addAll(listingsToShow);
				
				for(Accomodation listing: remainingListings) {
					if(!listing.getType().equals(activeAccTypeFilter.getText())) {
						listingsToShow.remove(listing);
					}
				}
			}

			
			
			setupScrollPane();
		}
		else {
			((CheckBox)e.getTarget()).setSelected(false);
		}
		
		
	}
	
	public void changeAccomodationTypeFilter(ActionEvent e) {
		String selectedText = ((CheckBox)e.getTarget()).getText();
		
		accTypeFilterIsActive = checkFilterIsActive("accType",e);	
		
		if(tempListingsToShow.size() > 0) {
			
			pane.getChildren().clear();
			
			listingsToShow.clear();
			listingsToShow.addAll(tempListingsToShow);
			
			if(accTypeFilterIsActive) {
				for(CheckBox accType: accTypeFilters) {
					if(!accType.equals(e.getTarget())) {
						accType.setSelected(false);
					}
				}
				
				
				for(Accomodation listing: tempListingsToShow) {
					if(!listing.getType().equals(selectedText)) {
						listingsToShow.remove(listing);
					}
				}
			}
			

			if(listingsToShow.size()>0 && starsFilterIsActive) {

				ArrayList<Accomodation> remainingListings = new ArrayList<Accomodation>();
				remainingListings.addAll(listingsToShow);
				Integer starsAmount = Integer.parseInt(activeStarFilter.getText().split(" ")[0]);

				for(Accomodation listing: remainingListings) {
					if(listing.getStars() != starsAmount) {
						listingsToShow.remove(listing);
					}
				}
			}
			
			setupScrollPane();
		}
		else {
			((CheckBox)e.getTarget()).setSelected(false);
		}
	}
	
	
	public boolean checkFilterIsActive(String filterType,ActionEvent e) {
		
		switch (filterType) {
		case "stars": {
			
			if(((CheckBox)e.getTarget()).isSelected()) {
				activeStarFilter = (CheckBox)e.getTarget();

				return true;
			}
			
			
			activeStarFilter = null;
			return false;
		}
		case "accType":{
			
			if(((CheckBox)e.getTarget()).isSelected()) {
				activeAccTypeFilter = (CheckBox)e.getTarget();
				
				return true;
			}
			
			activeAccTypeFilter = null;
			return false;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + filterType);
		}
	}
	
	public void seeListing(Accomodation listing,ActionEvent e) {
		
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(this.loggedInUser);
		parameters.add(this.searchFormData);
		parameters.add(listing);
	
		try {
			this.router.navigate(e, "Accomodation Page", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	    Image img = new Image(getClass().getResourceAsStream("tabler_search.png"));
	    ImageView view = new ImageView(img);
	    newSearchBtn.setGraphic(view);
	    
	    starFilters.add(starFilter1);
	    starFilters.add(starFilter2);
	    starFilters.add(starFilter3);
	    starFilters.add(starFilter4);
	    starFilters.add(starFilter5);
	    
	    accTypeFilters.add(accTypeA);
	    accTypeFilters.add(accTypeHR);
	    accTypeFilters.add(accTypeWH);
	    
	    
	}
}
