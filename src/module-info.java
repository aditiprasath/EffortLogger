module FXML_Tutorial {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.net.http;
	requires org.json;
	
	opens application to javafx.graphics, javafx.fxml;
}
