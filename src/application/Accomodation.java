package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class Accomodation {
	private String id;
	private String name;
	private Integer stars;
	private String city;
	private String address;
	private String description;
	private Integer price;
	private String thumbnail;
	private String type;
	private String[] images;
	private String[] amenities;
	private Comment[] comments;
	
	Button seeMoreBtn = new Button();

	
	
	public String getName() {
		return name;
	}

	public Integer getStars() {
		return stars;
	}


	public String getDescription() {
		return description;
	}

	public Integer getPrice() {
		return price;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String[] getImages() {
		return images;
	}

	public String[] getAmenities() {
		return amenities;
	}

	public Comment[] getComments() {
		return comments;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public Accomodation(String id, String name, Integer stars, String city, String address, String description, Integer price,String thumbnail, String type, String[] images, String[] amenities, Comment[] comments) {
		this.id = id;
		this.name = name;
		this.stars = stars;
		this.city = city;
		this.address = address;
		this.description = description;
		this.price = price;
		this.thumbnail = thumbnail;
		this.type = type;
		this.images = images;
		this.amenities = amenities;
		this.comments = comments;

		seeMoreBtn.getStyleClass().add("btn");
		seeMoreBtn.setText("SEE MORE");
	}
	
	
	public AnchorPane createListing(Integer order) {
		AnchorPane listing = new AnchorPane();

		listing.getStyleClass().add("listing");
		
		listing.setLayoutY(242*order);
		
		//create image
		Rectangle rect = new Rectangle();
		rect.getStyleClass().add("listingThumbnail");

		rect.setHeight(175);
		rect.setWidth(175);
		rect.setArcHeight(8);
		rect.setArcWidth(8);
		
		listing.getChildren().add(rect);
		
		rect.setX(18);
		rect.setY(15);
		
		
		//create name
		Label name = new Label();
		name.setText(this.name);
		name.getStyleClass().add("listingName");
		
		listing.getChildren().add(name);
		
		name.setLayoutX(212);
		name.setLayoutY(15);
		
		
		//create stars
		AnchorPane starsContainer = new AnchorPane();
		starsContainer.setLayoutX(320);
		starsContainer.setLayoutY(20);
		starsContainer.setPrefHeight(13);
		starsContainer.setPrefWidth(73);
		
		
		Image yellowStar = new Image(getClass().getResourceAsStream("YellowStar.png"));
		Image greyStar = new Image(getClass().getResourceAsStream("GreyStar.png"));
		
		if(this.stars > 0) {
			for(var i=0;i<this.stars;i++) {
				ImageView star = new ImageView();
				star.setFitWidth(13);
				star.setFitHeight(13);
				star.setImage(yellowStar);
				starsContainer.getChildren().add(star);
				star.setX(15 * i);
			}
			for(var i=0;i<5-this.stars;i++) {
				ImageView star = new ImageView();
				star.setFitWidth(13);
				star.setFitHeight(13);
				star.setImage(greyStar);
				starsContainer.getChildren().add(star);
				star.setX((15 * this.stars) + (15 * i));
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
		
		listing.getChildren().add(starsContainer);
		
		//create location
		Image locationPin = new Image(getClass().getResourceAsStream("locationPin.png"));
		ImageView lp = new ImageView();
		lp.setImage(locationPin);
		lp.setFitWidth(14);
		lp.setFitHeight(14);
		
		listing.getChildren().add(lp);
		
		lp.setX(212);
		lp.setY(39);
		
		Label location = new Label();
		location.getStyleClass().add("listingLocation");
		location.setText(this.address + ", " + this.city);
		
		listing.getChildren().add(location);
		
		location.setLayoutX(229);
		location.setLayoutY(41);
		
		//create description
		Label description = new Label();
		description.getStyleClass().add("listingDescription");
		description.setText(this.description);
		description.setPrefWidth(224);
		description.setWrapText(true);
		
		
		listing.getChildren().add(description);
		
		description.setLayoutX(212);
		description.setLayoutY(60);
		
		//create price
		Label price = new Label();
		price.getStyleClass().add("listingPriceNumber");
		price.setText(this.price.toString() + "€");
		
		listing.getChildren().add(price);
		
		price.setLayoutX(212);
		price.setLayoutY(148);
		
		Label priceText = new Label();
		priceText.getStyleClass().add("listingPriceText");
		priceText.setText("/night");
		
		listing.getChildren().add(priceText);
		
		priceText.setLayoutX(259);
		priceText.setLayoutY(151);
		
		
		listing.getChildren().add(seeMoreBtn);
		
		seeMoreBtn.setLayoutX(320);
		seeMoreBtn.setLayoutY(145);
				
		
		return listing;
	}
	
}
