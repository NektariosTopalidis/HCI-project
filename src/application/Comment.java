package application;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Comment {
	private String username;
	private Image profileImage;
	private Integer stars;
	private String text;
	
	
	public Comment(String username,Integer stars,String text) {
		this.username = username;
		this.stars = stars;
		this.text = text;
		this.profileImage = new Image(getClass().getResourceAsStream("UserImage.png"));
	}
	
	public AnchorPane createComment() {
		AnchorPane commentContainer = new AnchorPane();
		commentContainer.setPrefWidth(668);
		commentContainer.setPrefHeight(150);
		
		ImageView profilePic = new ImageView();
		profilePic.setImage(profileImage);
		profilePic.setFitHeight(50);
		profilePic.setFitWidth(50);
		profilePic.setY(0);
		profilePic.setX(0);
		
		Label username = new Label();
		username.setText(this.username);
		username.getStyleClass().add("commentUsername");
		username.setLayoutX(57);
		username.setLayoutY(6);
		
		AnchorPane starsContainer = new AnchorPane();
		starsContainer.setLayoutX(57);
		starsContainer.setLayoutY(26);
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
		
		Label commentText = new Label();
		commentText.setText(this.text);
		commentText.getStyleClass().add("commentText");
		commentText.setWrapText(true);
		commentText.setPrefWidth(668);
		commentText.setPrefHeight(60);
		commentText.setLayoutX(0);
		commentText.setLayoutY(76);
		
		commentContainer.getChildren().add(profilePic);
		commentContainer.getChildren().add(username);
		commentContainer.getChildren().add(starsContainer);
		commentContainer.getChildren().add(commentText);
		
		return commentContainer;
	}
}
