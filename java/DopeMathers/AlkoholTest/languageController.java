package DopeMathers.AlkoholTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class languageController 
{
	private static Stage stage;
	
	FileOutputStream propOut;

	private String language;
	private String country;
	private Locale currentLocale;
	private ResourceBundle Alkohol;
	private Properties prob = new Properties();
	private File propFile;
	
	//@FXML filds
	@FXML
	private Label lab1;
	@FXML
	private Label lab2;
	@FXML
	private Label lab3;
	@FXML 
	private Label lab4;
	@FXML
	private Label lab5;
	@FXML
	private ChoiceBox<String> box;
	@FXML
	private CheckBox check1;
	@FXML
	private CheckBox check2;
	@FXML
	private Button btn1;
	
	@FXML
	private Menu programm;
	@FXML
	private Menu sprache;
	@FXML
	private MenuItem ger;
	@FXML
	private MenuItem eng;
	@FXML
	private MenuItem close;
	@FXML
	private Menu ueber;
	@FXML
	private MenuItem ueberWin;
	
	public void initialize() throws IOException
	{
		propFile = new File(System.getProperty("user.home"));
		String finaTest = propFile+"/config.properties";
    	finaTest = finaTest.replaceAll("%20", " ");
		InputStream in = new FileInputStream(finaTest);
		prob.load(in);

		if(App.Language.equalsIgnoreCase("german") || App.Language.equalsIgnoreCase("deutsch"))
		{
			setLanguage(new String("de"));
			setCountry(new String("DE"));	
			setCurrentLocale(new Locale(getLanguage(), getCountry()));
			setAlkohol(ResourceBundle.getBundle("AlkoholBundle", getCurrentLocale()));
		}
		else
		{
			setLanguage(new String("en"));
			setCountry(new String("US"));	
			setCurrentLocale(new Locale(getLanguage(), getCountry()));
			setAlkohol(ResourceBundle.getBundle("AlkoholBundle", getCurrentLocale()));
		}
		
		programm.setText(Alkohol.getString("programm"));
		sprache.setText(Alkohol.getString("spra"));
		ger.setText(Alkohol.getString("gerLen"));
		eng.setText(Alkohol.getString("engLen"));
		close.setText(Alkohol.getString("close"));
		
		ueber.setText(Alkohol.getString("ueber"));
		ueberWin.setText(Alkohol.getString("ueberWin"));
		
		lab1.setText(Alkohol.getString("willkommenLabel"));
		lab2.setText(Alkohol.getString("spracheLabel"));
		lab3.setText(Alkohol.getString("standartLabel"));
		lab4.setText(Alkohol.getString("merkenLabel"));
		lab5.setText(Alkohol.getString("confirmLabel"));
		
		List<String> ls = new ArrayList<String>();
		ls.add(Alkohol.getString("gerLen"));
		ls.add(Alkohol.getString("engLen"));
		ObservableList<String> obList = FXCollections.observableArrayList(ls);
		
		box.setItems(obList);
		
		//in Main Window brauch ich keine if else anweisung da kann ich einfach setValue(properties.getString("Key"); eingeben ???
		if(App.Language.equalsIgnoreCase("german") || App.Language.equalsIgnoreCase("deutsch"))
		{
			box.setValue(obList.get(0));
		}
		else
		{
			box.setValue(obList.get(1));	
		}
		
		check1.setText(Alkohol.getString("standartCheck"));
		check2.setText(Alkohol.getString("merkenCheck"));
		
		btn1.setText(Alkohol.getString("confirmButton"));
	}
	
	public void clickOnButton() throws IOException, URISyntaxException
	{
		propFile = new File(System.getProperty("user.home"));
		String finaTest = propFile+"/config.properties";
    	finaTest = finaTest.replaceAll("%20", " ");
		
		propOut = new FileOutputStream(finaTest);
		
		boolean checked1 = check1.isSelected(); 
		boolean checked2 = check2.isSelected();
		String language = box.getValue();
		
		App.Language = language;
		
		if(checked1 == true)
		{
			prob.remove("language");
			prob.put("language", language);
			
			prob.remove("propTrue");
			prob.put("propTrue", "true");
			
			System.out.println(language);
		}
		else
		{
			prob.remove("language");
			prob.put("language", language);
			System.out.println(language);
		}
			
		if(checked2 == true)
		{
			prob.remove("showAgain");
			prob.put("showAgain", Boolean.toString(!checked2));
			System.out.println(Boolean.toString(!checked2));
		}
		else
		{
			prob.remove("showAgain");
			prob.put("showAgain", Boolean.toString(!checked2));
			System.out.println(Boolean.toString(!checked2));
		}
		
		prob.store(propOut, "");
		propOut.close();
		
		stage = (Stage)btn1.getScene().getWindow();
		stage.close();
		
		primaryWindow.startMainApp();
	}
	
	public void closePressed()
	{
		stage = (Stage)check1.getScene().getWindow();
		stage.close();
	}

	//Klassenintern regeln mit static value ??? 
	public void engPressed() throws IOException, URISyntaxException
	{

		App.Language = "english";
		Stage stage1 = (Stage)check1.getScene().getWindow();
		stage1.close();
		languageWindow win = new languageWindow();
		win.startApp();
	}
	
	public void gerPressed() throws IOException, URISyntaxException
	{

		App.Language = "german";
		Stage stage1 = (Stage)check1.getScene().getWindow();
		stage1.close();
		languageWindow win = new languageWindow();
		win.startApp();
	}
	
	@FXML
	public void aboutPressed() throws IOException
	{
		
		AboutWindow win = new AboutWindow();
		win.window();
		
	}//aboutPressed() end
	
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


	public ResourceBundle getAlkohol() 
	{
		return Alkohol;
	}


	public void setAlkohol(ResourceBundle alkohol) 
	{
		Alkohol = alkohol;
	}


	public Locale getCurrentLocale() 
	{
		return currentLocale;
	}


	public void setCurrentLocale(Locale currentLocale) 
	{
		this.currentLocale = currentLocale;
	}
	
}
