package application;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginController {

	private Router router = new Router();
	
	User[] users = {new User("nektop","nektop123","Nektarios","Topalidis","male","nektariostopalides@info.gr","6900000000"),new User("user2","user2","User","2","male","user2@info.gr","6900000000"),new User("user3","user3","User","3","female","user3@info.gr","6900000000")};
	private Boolean usernameHasError = false;
	private Boolean passwordHasError = false;
	
	@FXML
	private PasswordField passwordInput;
	
	@FXML
	private TextField usernameInput;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private Label errorText;
	
	public void checkError(KeyEvent e) {
		if(e.getTarget().equals(passwordInput)) {
			if(this.passwordHasError) {
				this.passwordHasError = false;
				
				if(!this.usernameHasError) {
					errorText.setText("");
				}
				
				passwordInput.setStyle(null);
				passwordInput.getStyleClass().clear();
				passwordInput.getStyleClass().addAll("text-field", "text-input", "input");
			}
		}
		else {
			if(this.usernameHasError) {
				this.usernameHasError = false;
				
				if(!this.passwordHasError) {
					errorText.setText("");
				}
				
				usernameInput.setStyle(null);
				usernameInput.getStyleClass().clear();
				usernameInput.getStyleClass().addAll("text-field", "text-input", "input");
			}
		}
	}
	
	public void login(ActionEvent e) {
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		
		if(username != "" && password != "") {
			User matchingUser = null;
			
			
			for(User user: users) {
				if(user.getUsername().equals(username)) {
					matchingUser = user;
					break;
				}
			}
			
			if(matchingUser != null) {				
				
				if(matchingUser.getPassword().equals(password)) {

					
					List<Object> parameters = new ArrayList<Object>();
					
					parameters.add(matchingUser);
					
					try {
						this.router.navigate(e, "Search",parameters);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					errorText.setText("The password you typed is wrong.");
					passwordInput.setStyle("-fx-border-color:red;");
					this.passwordHasError = true;
				}
				
			}
			else {
				errorText.setText("The username you typed is wrong.");
				usernameInput.setStyle("-fx-border-color:red;");
				this.usernameHasError = true;
			}
			
		}
		else {
			errorText.setText("All fields must be filled.");
			
			if(username == "") {
				usernameInput.setStyle("-fx-border-color:red;");
				this.usernameHasError = true;
			}
			if(password == "") {				
				passwordInput.setStyle("-fx-border-color:red;");
				this.passwordHasError = true;
			}
		}
	}

}
