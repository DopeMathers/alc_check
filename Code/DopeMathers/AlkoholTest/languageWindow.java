package DopeMathers.AlkoholTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;

import java.util.Properties;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

public class languageWindow
{
	FileOutputStream propOut;
	private static Stage stage;

	boolean showAgain;
	private Properties prob = new Properties();
	private File propFile;

	
    private static Scene scene;
    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private static FXMLLoader fxmlLoader;
    
    public void startApp() throws IOException, URISyntaxException
    {
    propFile = new File(System.getProperty("user.home"));
    String finaTest = propFile+"/config.properties";
	finaTest = finaTest.replaceAll("%20", " ");
    stage = new Stage();	
    
	InputStream in = new FileInputStream(finaTest);
	prob.load(in);
	showAgain = Boolean.parseBoolean(prob.getProperty("showAgain"));
	
		if(showAgain == true)
		{
			scene = new Scene(loadFXML("languageWindow"));
    
			fontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(150));
   
			scene.getRoot().styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
    
			stage.getIcons().add(new Image(getClass().getResource("/images/logo.png").toURI().toString()));
			stage.setTitle("Alc-Check");
			stage.setScene(scene);          
    
			stage.show();

		}
		else
		{
			primaryWindow.startMainApp();
		}
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
