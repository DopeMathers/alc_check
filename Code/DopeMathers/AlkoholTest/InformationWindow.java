package DopeMathers.AlkoholTest;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.stage.Stage;

//Information Window öffnen
public class InformationWindow
{
	private static Stage stage = new Stage();
	private Scene scene;
	private DoubleProperty fontSize = new SimpleDoubleProperty(10);

	public void infWindow() throws IOException
	{
		
		FXMLLoader lod = new FXMLLoader(App.class.getResource("informationWindow.fxml"));
		scene = new Scene(lod.load());
	   
		fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(100));
	    scene.getRoot().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
	    
		stage.setTitle("Alc-Check");
	    stage.setScene(scene);
	    stage.show();
		
	}
	
	
}
