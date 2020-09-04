package DopeMathers.AlkoholTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javafx.stage.Stage;

public class informationController 
{
	private languageWindow win = new languageWindow();
	private String language;
	private String country;
	private Locale currentLocale;
	private ResourceBundle Alkohol;
	private Properties prob = new Properties();
	private File propFile;
	FileOutputStream propOut;
	
	// Formel: Reinalkohol = amountML * (alkGehalt / percent) * alkGewicht
		// Blutalkohol = reinalkohol / (gewicht * reduktionGeschlecht) / 100 * 80 (20% wird abgezogen da nicht alles aufgenommen wird) - alkoholVerlustÜberZeit
	private Stage stage;

	@FXML
	private Label infFeld1;
	@FXML
	private Label infFeld2;
	@FXML
	private Label infFeld3;
	@FXML
	private Label infFeld4;
	@FXML
	private Label infFeld5;
	@FXML
	private Label infFeld6;
	@FXML
	private Label infFeld7;
	@FXML
	private Label infFeld8;
	
	@FXML
	private Menu programm;
	@FXML
	private MenuItem willkommensDialog;
	@FXML
	private Menu sprache;
	@FXML
	private MenuItem ger;
	@FXML
	private MenuItem eng;
	@FXML
	private MenuItem close;
	
	//Initzialisiseren, sprache wird eingelesen und alle Labels entsprechend gesetzt
	@FXML
	public void initialize() throws IOException
	{
		propFile = new File(System.getProperty("user.home"));
		String finaTest = propFile+"/config.properties";
    	finaTest = finaTest.replaceAll("%20", " ");
		InputStream in = new FileInputStream(finaTest);
		prob.load(in);
		
		if(App.Language.equalsIgnoreCase("german") || App.Language.equalsIgnoreCase("deutsch"))
		{
			language = new String("de");
			country = new String("DE");	
			setCurrentLocale(new Locale(getLanguage(), getCountry()));
			setAlkohol(ResourceBundle.getBundle("AlkoholBundle", getCurrentLocale()));
		}
		else
		{
			language = new String("en");
			country = new String("US");	
			setCurrentLocale(new Locale(getLanguage(), getCountry()));
			setAlkohol(ResourceBundle.getBundle("AlkoholBundle", getCurrentLocale()));
		}
		
		programm.setText(Alkohol.getString("programm"));
		willkommensDialog.setText(Alkohol.getString("welDia"));
		sprache.setText(Alkohol.getString("spra"));
		ger.setText(Alkohol.getString("gerLen"));
		eng.setText(Alkohol.getString("engLen"));
		close.setText(Alkohol.getString("close"));
		
		infFeld1.setText(Alkohol.getString("inf1"));
		infFeld2.setText(Alkohol.getString("inf2"));
		infFeld3.setText(Alkohol.getString("inf3"));
		infFeld4.setText(Alkohol.getString("inf4"));
		infFeld5.setText(Alkohol.getString("inf5"));
		infFeld6.setText(Alkohol.getString("inf6"));
		infFeld7.setText(Alkohol.getString("inf7"));
		infFeld8.setText(Alkohol.getString("inf8"));
		
	}
	
	//Methode um Fenster zu schließen
	public void closePressed()
	{
		stage = (Stage)infFeld1.getScene().getWindow();
		stage.close();
	}
	
	//Willkommensfenster erneut aufrufen
	public void languagePressed() throws Exception
	{
		propFile = new File(System.getProperty("user.home"));
		String finaTest = propFile+"/config.properties";
    	finaTest = finaTest.replaceAll("%20", " ");
		propOut = new FileOutputStream(finaTest);
		prob.remove("showAgain");
		prob.put("showAgain", "true");
		prob.store(propOut, "");
		win.startApp();
		Stage stage1 = (Stage)infFeld1.getScene().getWindow();
		stage1.close();
	}
	
	//method choice language english
	public void engPressed() throws IOException, URISyntaxException
	{

		App.Language = "english";
		Stage stage1 = (Stage)infFeld1.getScene().getWindow();
		stage1.close();
		InformationWindow win = new InformationWindow();
		win.infWindow();
	}
	
	//methode um Sprache zu wechseln auf Deutsch
	public void gerPressed() throws IOException, URISyntaxException
	{

		App.Language = "german";
		Stage stage1 = (Stage)infFeld1.getScene().getWindow();
		stage1.close();
		InformationWindow win = new InformationWindow();
		win.infWindow();
	}

	//Getters und Setters
	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getCountry() 
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public Locale getCurrentLocale()
	{
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale)
	{
		this.currentLocale = currentLocale;
	}

	public ResourceBundle getAlkohol() 
	{
		return Alkohol;
	}

	public void setAlkohol(ResourceBundle alkohol) 
	{
		Alkohol = alkohol;
	}
	
}
