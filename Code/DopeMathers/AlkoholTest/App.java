package DopeMathers.AlkoholTest;

import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * JavaFX App
 */
public class App extends Application {
	
	public static String Language;
	
	
	
	languageWindow win = new languageWindow();
	private Properties prob = new Properties();
	private String propTrue;
	private FileOutputStream propOut;

	// Programmstart, hier wird lediglich die Sprache abgerufen und danach wird das
	// erste Fenster geöffnet
	@SuppressWarnings("exports")
	@Override
    public void start(Stage stage) throws IOException, Exception 
    {
		
    	String finaTest = System.getProperty("user.home")+"/config.properties";
    	finaTest = finaTest.replaceAll("%20", " ");
    	
    	try {
    	InputStream in = new FileInputStream(finaTest); //config einlesen
    	prob.load(in);
    	}
    	catch(FileNotFoundException e)
    	{
        propOut = new FileOutputStream(finaTest);
        propOut.close();
    	}
    	catch(Exception end)
    	{
    		System.out.println("FatalERR");
    		System.exit(1);
    	}

    	if(prob.isEmpty())
    	{
    	prob.put("language", "english");
    	prob.put("defaultLanguage", "english");
    	prob.put("propTrue", "false");
    	prob.put("showAgain", "true");
    	prob.put("fileWrite", "true");
    	}

    	
    	propOut = new FileOutputStream(finaTest);
    	propTrue = prob.getProperty("propTrue"); // Einlesen ob Standartsprache gesetzt ist
    	System.out.println("propTrue" + propTrue);
    	
    	if(propTrue.equalsIgnoreCase("false")) // wenn standartsprache nicht gesetzt default sprache laden
    	{
    		prob.remove("language");
    		prob.put("language", prob.getProperty("defaultLanguage"));
    	}
    	
    	prob.store(propOut, ""); // config neu schreiben
    	propOut.close();
    	Language = prob.getProperty("language");

		win.startApp(); // willkommensfenster Starten
		
    }// start end

	public static void main(String[] args) {
		launch();
	}// main() end

} // App end