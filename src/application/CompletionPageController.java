package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CompletionPageController implements Initializable {

	private Router router = new Router();
	private User loggedInUser = null;
	private String successText;
	
	@FXML
	private Label textLabel;
	
	public void getBookingID(String id) {
		
		this.successText = "Your reservation was completed successfully. Your booking number is " + id + ". A confirmation email has also been sent to you, containing all the booking information and a receipt for your payment.";
		this.textLabel.setText(this.successText);
	}
	
	public void setUser(User user) {
		this.loggedInUser = user;
	}
	
	public void continueSearching(ActionEvent e) {
		
		List<Object> parameters = new ArrayList<Object>();
		
		parameters.add(loggedInUser);
		
		try {
			this.router.navigate(e, "Search", parameters);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.textLabel.setWrapText(true);
	}
}
