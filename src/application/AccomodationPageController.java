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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class AccomodationPageController implements Initializable{

	
	private Router router = new Router();
	private User loggedInUser = null;
	private SearchForm searchFormData = null;
	private Accomodation selectedListing = null;
	
	@FXML
	private Label usernameLabel;
	
	
	@FXML
	private Button goBackBtn;
	
	@FXML
	private Label hotelName;
	
	@FXML
	private Label hotelLocation;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private Label description;
	
	@FXML
	private ImageView showingImage;
	
	@FXML
	private ImageView image1;
	
	@FXML
	private ImageView image2;
	
	@FXML
	private ImageView image3;
	
	@FXML
	private ImageView image4;
	
	@FXML
	private ImageView image5;
	
	@FXML
	private AnchorPane amenitiesContainer;
	
	@FXML
	private Label price;
	
	@FXML
	private AnchorPane commentsPane;
	
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
	
	public void goToAbout(ActionEvent e) {
		
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(loggedInUser);
		
		try {
			this.router.navigate(e, "About", parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void setSelectedListing(Accomodation sl) {
		this.selectedListing = sl;
		
		hotelName.setText(this.selectedListing.getName());
		hotelLocation.setText(this.selectedListing.getAddress());
		description.setText(this.selectedListing.getDescription());
		price.setText(this.selectedListing.getPrice().toString() + "€");
		
		if(this.selectedListing.getPrice() < 99) {
			price.setLayoutX(618);
		}
		
		AnchorPane starsContainer = new AnchorPane();
		starsContainer.setLayoutX(405);
		starsContainer.setLayoutY(75);
		starsContainer.setPrefHeight(13);
		starsContainer.setPrefWidth(73);
		
		Image yellowStar = new Image(getClass().getResourceAsStream("YellowStar.png"));
		Image greyStar = new Image(getClass().getResourceAsStream("GreyStar.png"));
		
		if(this.selectedListing.getStars() > 0) {
			for(var i=0;i<this.selectedListing.getStars();i++) {
				ImageView star = new ImageView();
				star.setFitWidth(13);
				star.setFitHeight(13);
				star.setImage(yellowStar);
				starsContainer.getChildren().add(star);
				star.setX(15 * i);
			}
			for(var i=0;i<5-this.selectedListing.getStars();i++) {
				ImageView star = new ImageView();
				star.setFitWidth(13);
				star.setFitHeight(13);
				star.setImage(greyStar);
				starsContainer.getChildren().add(star);
				star.setX((15 * this.selectedListing.getStars()) + (15 * i));
			}
		}
		else {
			for(var i=0;i<5;i++) {
				ImageView star = new ImageView();
				star.setFitWidth(13);
				star.setFitHeight(13);
				star.setImage(greyStar);
				starsContainer.getChildren().add(star);
				star.setX(15 * i);
			}
		}
		
		pane.getChildren().add(starsContainer);
		
		setUpImages();
		
		for(var i=0;i<this.selectedListing.getAmenities().length;i++) {
			AnchorPane amenity = this.selectedListing.createAmenityLabel(i);	
			amenitiesContainer.getChildren().add(amenity);
		}
		
		for(var i=0;i<this.selectedListing.getComments().length;i++) {
			AnchorPane comment = this.selectedListing.getComments()[i].createComment();
			comment.setLayoutY((124 + 33) * i);
			commentsPane.getChildren().add(comment);
		}
		
	}
	
	private void setUpImages() {
		showingImage.setImage(this.selectedListing.getThumbnail());
	    final Rectangle clip = new Rectangle();
	    clip.setArcHeight(8);
	    clip.setArcWidth(8);
	    clip.setWidth(showingImage.getLayoutBounds().getWidth());
	    clip.setHeight(showingImage.getLayoutBounds().getHeight());
	    showingImage.setClip(clip);
	    
	    final Rectangle clip1 = new Rectangle();
	    clip1.setArcHeight(8);
	    clip1.setArcWidth(8);
	    clip1.setWidth(100);
	    clip1.setHeight(100);
	    final Rectangle clip2 = new Rectangle();
	    clip2.setArcHeight(8);
	    clip2.setArcWidth(8);
	    clip2.setWidth(100);
	    clip2.setHeight(100);
	    final Rectangle clip3 = new Rectangle();
	    clip3.setArcHeight(8);
	    clip3.setArcWidth(8);
	    clip3.setWidth(100);
	    clip3.setHeight(100);
	    final Rectangle clip4 = new Rectangle();
	    clip4.setArcHeight(8);
	    clip4.setArcWidth(8);
	    clip4.setWidth(100);
	    clip4.setHeight(100);
	    final Rectangle clip5 = new Rectangle();
	    clip5.setArcHeight(8);
	    clip5.setArcWidth(8);
	    clip5.setWidth(100);
	    clip5.setHeight(100);
	    
	    image1.setClip(clip1);
	    image2.setClip(clip2);
	    image3.setClip(clip3);
	    image4.setClip(clip4);
	    image5.setClip(clip5);

	    image1.setImage(this.selectedListing.getThumbnail());
	    image2.setImage(this.selectedListing.getImages()[0]);
	    image3.setImage(this.selectedListing.getImages()[1]);
	    image4.setImage(this.selectedListing.getImages()[2]);
	    image5.setImage(this.selectedListing.getImages()[3]);
	}
	
	public void setSelectedImage(MouseEvent e) {
		this.showingImage.setImage(((ImageView)e.getSource()).getImage());
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
	
	public void book(ActionEvent e) {
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(this.loggedInUser);
		parameters.add(this.searchFormData);
		parameters.add(this.selectedListing);
	
		try {
			this.router.navigate(e, "Book Form", parameters);
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
