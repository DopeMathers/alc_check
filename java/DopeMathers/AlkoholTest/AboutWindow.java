package DopeMathers.AlkoholTest;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Class AboutWindow zum öffnen des Fensters
public class AboutWindow 
{
	private static Scene scene;
	private DoubleProperty fontSize = new SimpleDoubleProperty(10); // font size bestimmen
	
	public void window() throws IOException
	{	
	
	FXMLLoader lod = new FXMLLoader(App.class.getResource("aboutWindow.fxml"));
	scene = new Scene(lod.load());
   
	//Fontsize binding damit sich größe ändert beim Vergrößern Verkleinern des Fensters
	fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(100));
    scene.getRoot().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
	
    //standart shit
	Stage stage = new Stage();
	
	stage.setTitle("Alc-Check");
	stage.setScene(scene);
	stage.show();
	
	}//window() end
	
}//AboutWindow end
