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

//eigentlich selbsterklärend, identisch zu info fenster
public class aboutController 
{
	private languageWindow win = new languageWindow();
	private String language;
	private String country;
	private Properties prob = new Properties();
	private File propFile;
	FileOutputStream propOut;
	
	private Locale currentLocale;
	private ResourceBundle Alkohol;
	
	//FXML Labels 
	@FXML
	private Label feld1;
	@FXML
	private Label feld2;	
	@FXML
	private Label feld3;	
	@FXML
	private Label feld4;
	@FXML
	private Label feld5;
	@FXML
	private Label feld6;
	@FXML
	private Label feld7;
	@FXML
	private Label feld8;
	@FXML
	private Label feld9;
	
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
	

	private static Stage stage;
	
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
		
		feld1.setText(Alkohol.getString("fild1"));
		feld2.setText(Alkohol.getString("fild2"));
		feld3.setText(Alkohol.getString("fild3"));
		feld4.setText(Alkohol.getString("fild4"));
		feld5.setText(Alkohol.getString("fild5"));
		feld6.setText(Alkohol.getString("fild6"));
		feld7.setText(Alkohol.getString("fild7"));
		feld8.setText(Alkohol.getString("fild8"));
		feld9.setText(Alkohol.getString("fild9"));
		
	}// initialize() end
	
	@FXML
	public void exitPressed()
	{
		stage = (Stage)feld1.getScene().getWindow();
		stage.close();
	}//exitPressed() end
	
	@FXML
	public void informationPressed() throws IOException
	{
		InformationWindow win = new InformationWindow();
		win.infWindow();
	}//informationPressed() end
	
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
		Stage stage1 = (Stage)feld1.getScene().getWindow();
		stage1.close();
	}
	
	public void engPressed() throws IOException, URISyntaxException
	{

		App.Language = "english";
		Stage stage1 = (Stage)feld1.getScene().getWindow();
		stage1.close();
		AboutWindow win = new AboutWindow();
		win.window();
	}
	
	public void gerPressed() throws IOException, URISyntaxException
	{

		App.Language = "english";
		Stage stage1 = (Stage)feld1.getScene().getWindow();
		stage1.close();
		AboutWindow win = new AboutWindow();
		win.window();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ResourceBundle getAlkohol() {
		return Alkohol;
	}

	public void setAlkohol(ResourceBundle alkohol) {
		Alkohol = alkohol;
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

}//aboutController end
