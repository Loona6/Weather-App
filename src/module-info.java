module Weather {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.net.http;
	requires com.google.gson;
	requires javafx.fxml;
	
	 
	
	opens application to javafx.graphics, javafx.fxml, com.google.gson;
	exports application;
}
