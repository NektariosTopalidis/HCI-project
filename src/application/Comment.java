package application;

public class Comment {
	private String username;
	private Integer stars;
	private String text;
	
	public Comment(String username,Integer stars,String text) {
		this.username = username;
		this.stars = stars;
		this.text = text;
	}
}
