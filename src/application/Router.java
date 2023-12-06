package application;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {

	private Stage stage;
	private Scene scene;
	
	public void navigate(ActionEvent e, String destination, List<Object> parameters) throws IOException {
		
		
		System.out.println();
		
		Parent root = null;
		
		switch (destination) {
		case "Login": {
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			break;
		}
		case "Search": {
				
			User loggedInUser = ((User)parameters.get(0));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
			root = loader.load();
			
			SearchController searchController = loader.getController();
			searchController.setLoggedInUser(loggedInUser);
			
			break;
		}
		case "Results": {
			
			User loggedInUser = ((User)parameters.get(0));
			SearchForm searchFormData = ((SearchForm)parameters.get(1));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));
			root = loader.load();
			
			ResultsController resultsController = loader.getController();
			resultsController.setLoggedInUser(loggedInUser);
			resultsController.setSearchFormData(searchFormData);
			
			break;
		}
		case "Accomodation Page":{
			
			User loggedInUser = ((User)parameters.get(0));
			SearchForm searchFormData = ((SearchForm)parameters.get(1));
			Accomodation selectedListing = ((Accomodation)parameters.get(2));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("AccomodationPage.fxml"));
			root = loader.load();
			
			AccomodationPageController apc = loader.getController();
			apc.setLoggedInUser(loggedInUser);
			apc.setSearchFormData(searchFormData);
			apc.setSelectedListing(selectedListing);
			
			break;
		}
		case "Book Form":{
			User loggedInUser = ((User)parameters.get(0));
			SearchForm searchFormData = ((SearchForm)parameters.get(1));
			Accomodation selectedListing = ((Accomodation)parameters.get(2));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("BookForm.fxml"));
			root = loader.load();
			
			BookFormController bfc = loader.getController();
			bfc.setLoggedInUser(loggedInUser);
			bfc.setSearchFormData(searchFormData,selectedListing.getPrice());
			bfc.setSelectedListing(selectedListing);
			
			break;
		}
		case "Completion Page": {
			
			String bookingID = ((String)parameters.get(0));
			User loggedInUser = ((User)parameters.get(1));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CompletionPage.fxml"));
			root = loader.load();
			
			CompletionPageController cpc = loader.getController();
			cpc.getBookingID(bookingID);
			cpc.setUser(loggedInUser);
			
			break;
		}
		case "About": {
			User loggedInUser = ((User)parameters.get(0));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
			root = loader.load();
			
			AboutController ac = loader.getController();
			ac.setLoggedInUser(loggedInUser);
		}
		default:
			break;
		}
		
		if(root != null) {
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			
			scene = new Scene(root);
			
			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			stage.setScene(scene);
			stage.show();
		}

	}
}
