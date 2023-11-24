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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookFormController implements Initializable{
	
	private User loggedInUser;
	private SearchForm searchFormData;
	private Accomodation selectedListing;
	private Router router = new Router();
	
	@FXML
	private Button goBackBtn;
	
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}
	
	public void setSearchFormData(SearchForm sf) {
		this.searchFormData = sf;
	}
	
	public void setSelectedListing(Accomodation sl) {
		this.selectedListing = sl;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	    Image img = new Image(getClass().getResourceAsStream("ArrowBlack.png"));
	    ImageView view = new ImageView(img);
	    goBackBtn.setGraphic(view);
		
	}
}
