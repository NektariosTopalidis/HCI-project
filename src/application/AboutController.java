package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {

	private Router router = new Router();
	private User loggedInUser = null;
	
	@FXML
	private Label usernameLabel;
	
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
	
	public void goToSearch(ActionEvent e) {
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(this.loggedInUser);
		
		try {
			this.router.navigate(e, "Search",parameters);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
}
