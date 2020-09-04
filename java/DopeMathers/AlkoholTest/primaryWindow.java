package DopeMathers.AlkoholTest;


import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;

import java.net.URISyntaxException;

public class primaryWindow
{

    private static Scene scene;
    private static DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private static FXMLLoader fxmlLoader;
    private static Stage stage = new Stage();
    
	public static void startMainApp() throws IOException, URISyntaxException
	{
		scene = new Scene(loadFXML("primary"));
        
        fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(150));
       
        scene.getRoot().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        
        stage.getIcons().add(new Image(primaryWindow.class.getResource("/images/logo.png").toURI().toString()));
        stage.setTitle("Alc-Check");
        stage.setScene(scene);          
        
        stage.show();
	}
	
    static void setRoot(String fxml) throws IOException 
    {
        scene.setRoot(loadFXML(fxml));
    }//setRoot end

    private static Parent loadFXML(String fxml) throws IOException
    {
    	fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }//loadFXML end

}
