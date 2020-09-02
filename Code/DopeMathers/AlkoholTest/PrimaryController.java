package DopeMathers.AlkoholTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;

public class PrimaryController
{
	
	private languageWindow win = new languageWindow();
	
	private String language;
	private String country;
	private Locale currentLocale;
	private ResourceBundle Alkohol;
	private Properties prob = new Properties();
	private File propFile;
	
	FileOutputStream propOut;

	Date date = new Date();
	Daten daten1;
	
	//Reinalkohol = amountML * (alkGehalt / percent) * alkGewicht
	final private double alkGewicht = 0.8;
	final private double percent = 100; 
	
	// Blutalkohol = reinalkohol / (gewicht * reduktionGeschlecht) / 100 * 80 (20% wird abgezogen da nicht alles aufgenommen wird) - alkoholVerlustÜberZeit
	private double gewicht;
	final private double redMan = 0.7;
	final private double redWom = 0.6;
	
	final private double alkLooseOverTime = 0.15;
	private double finalAlkLooseOverTime;
	
	//@FXML deklarationen um auf daten zuzugreiffen.
	@FXML
	private ChoiceBox<String> geschlecht;
	@FXML
	private ChoiceBox<String> stunden;
	@FXML
	private TextField gewichtFeld;
	@FXML
	private TextField menge1Feld;
	@FXML
	private TextField menge2Feld;
	@FXML
	private TextField menge3Feld;
	@FXML
	private TextField menge4Feld;
	@FXML
	private TextField menge5Feld;
	@FXML
	private TextField ml1Feld;
	@FXML
	private TextField ml2Feld;
	@FXML
	private TextField ml3Feld;
	@FXML
	private TextField ml4Feld;
	@FXML
	private TextField ml5Feld;
	@FXML
	private TextField pro1Feld;
	@FXML
	private TextField pro2Feld;
	@FXML
	private TextField pro3Feld;
	@FXML
	private TextField pro4Feld;
	@FXML
	private TextField pro5Feld;
	@FXML
	private Button berechnen;
	@FXML
	private Button speichern;
	@FXML
	private Label zeit;
	@FXML
	private Label tBar;
	@FXML
	private Label stundenDrunk;
	@FXML
	private Label kgGewicht;
	@FXML
	private Label genderChoose;
	@FXML
	private Label alcGehalt;
	@FXML
	private Label mlPer;
	@FXML
	private Label mengEin;
	@FXML
	private Label datum;
	@FXML
	private Label blutAlkohol;
	@FXML
	private Label bakText;
	@FXML
	private Label konsequenzen;
	@FXML
	private Label kons1;
	@FXML
	private Label kons2;
	@FXML
	private Label kons3;
	@FXML
	private Label kons4; 
	@FXML
	private Label kons5;
	@FXML
	private Label tippSatz;
	@FXML
	private Label tipp;
	@FXML
	private ImageView image;
	@FXML
	private AnchorPane paneImage;
	@FXML
	private ProgressBar bar;
	@FXML
	private MenuBar menu;
	
	@FXML
	private Menu programm;
	@FXML
	private MenuItem willkommensDialog;
	@FXML
	private MenuItem inform;
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
	
	private String einlesen;
	private String einlesen1;
	private String einlesen2;
	private String einlesen3;
	private String einlesen4;
	private String einlesen5;
	private String einlesen6;
	private String einlesen7;
	private String einlesen8;
	private String einlesen9;
	private String einlesen10;
	private String einlesen11;
	private String einlesen12;
	private String einlesen13;
	private String einlesen14;
	private String einlesen15;
	private String einlesen16;
	private String einlesen17;
	
	private final String bild1 = "/images/nullfuenfbiseins.png";
	private final String bild2 = "/images/einbiszwei.png";
	private final String bild3 = "/images/zweibisdrei.png";
	private final String bild4 = "/images/dreibisvier.png";
	private final String bild5 = "/images/vierbisfuenf.png";
	
	Drink drink;
	Drink drink1;
	Drink drink2;
	Drink drink3;
	Drink drink4;
	Drink drink5;
	
	Stage stage;
	
	@FXML
	public void initialize() throws IOException
	{
	      
	propFile = new File(System.getProperty("user.home"));
	String finaTest = propFile+"/config.properties";
    finaTest = finaTest.replaceAll("%20", " ");
	InputStream in = new FileInputStream(finaTest);
	prob.load(in);

	initClock();
	
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
	inform.setText(Alkohol.getString("inform"));
	sprache.setText(Alkohol.getString("spra"));
	ger.setText(Alkohol.getString("gerLen"));
	eng.setText(Alkohol.getString("engLen"));
	close.setText(Alkohol.getString("close"));
	ueber.setText(Alkohol.getString("ueber"));
	ueberWin.setText(Alkohol.getString("ueberWin"));
	
	geschlecht.getItems().set(0, Alkohol.getString("choiceMen")); //choiceBox
	geschlecht.getItems().set(1, Alkohol.getString("choiceWom"));
	geschlecht.setValue(Alkohol.getString("choiceWom"));
	berechnen.setText(Alkohol.getString("rechnenButton"));; //button
	speichern.setText(Alkohol.getString("speichernButton"));; //button
	zeit.setText(Alkohol.getString("zeitLabel"));; //lab
	tBar.setText(Alkohol.getString("barLabel"));; //lab
	stundenDrunk.setText(Alkohol.getString("stundenLabel"));; //lab
	kgGewicht.setText(Alkohol.getString("kgLabel"));; //lab
	genderChoose.setText(Alkohol.getString("geschlechtLabel"));; //lab
	alcGehalt.setText(Alkohol.getString("gehaltLabel"));; //lab
	mlPer.setText(Alkohol.getString("mlLabel"));; //lab
	mengEin.setText(Alkohol.getString("mengeLabel"));; //lab
	bakText.setText(Alkohol.getString("bakLabel"));
	konsequenzen.setText(Alkohol.getString("auswirkungenLabel"));; //lab
	tippSatz.setText(Alkohol.getString("tippLabel"));; //lab
	
	geschlecht.setTooltip(new Tooltip(Alkohol.getString("tool1")));
	berechnen.setTooltip(new Tooltip(Alkohol.getString("tool2")));
	speichern.setTooltip(new Tooltip(Alkohol.getString("tool3")));
	
	gewichtFeld.setTooltip(new Tooltip(Alkohol.getString("tool4")));
	
	menge1Feld.setTooltip(new Tooltip(Alkohol.getString("tool5")));
	menge2Feld.setTooltip(new Tooltip(Alkohol.getString("tool5")));
	menge3Feld.setTooltip(new Tooltip(Alkohol.getString("tool5")));
	menge4Feld.setTooltip(new Tooltip(Alkohol.getString("tool5")));
	menge5Feld.setTooltip(new Tooltip(Alkohol.getString("tool5")));
	
	ml1Feld.setTooltip(new Tooltip(Alkohol.getString("tool6")));
	ml2Feld.setTooltip(new Tooltip(Alkohol.getString("tool6")));
	ml3Feld.setTooltip(new Tooltip(Alkohol.getString("tool6")));
	ml4Feld.setTooltip(new Tooltip(Alkohol.getString("tool6")));
	ml5Feld.setTooltip(new Tooltip(Alkohol.getString("tool6")));
	
	pro1Feld.setTooltip(new Tooltip(Alkohol.getString("tool7")));
	pro1Feld.setTooltip(new Tooltip(Alkohol.getString("tool7")));
	pro3Feld.setTooltip(new Tooltip(Alkohol.getString("tool7")));
	pro4Feld.setTooltip(new Tooltip(Alkohol.getString("tool7")));
	pro5Feld.setTooltip(new Tooltip(Alkohol.getString("tool7")));
	
	}
	
	private void initClock() {

		//Den Code genauer analisieren
	    Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        datum.setText(LocalDateTime.now().format(formatter));
	    }), new KeyFrame(Duration.seconds(1)));
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
	}
	
	@FXML
	public void onEnter() throws Exception
	{
		fieldFilled();
	}
	
	@FXML
	public void informationPressed() throws IOException
	{
		InformationWindow win = new InformationWindow();
		win.infWindow();
	}
	
	@FXML
	public void exitPressed()
	{
		stage = (Stage)gewichtFeld.getScene().getWindow();
		stage.close();
	}//exitPressed() end
	
	@FXML
	public void aboutPressed() throws IOException
	{
		
		AboutWindow win = new AboutWindow();
		win.window();
		
	}//aboutPressed() end
	
	public void textSchreiben() throws Exception
	{
				
				String userHomeFolder = System.getProperty("user.home"); // User homefolder
				fieldFilled();
				
				boolean geschrieben = false; 
				
				String[] dk1 = daten1.getDrink1();
				String[] dk2 = daten1.getDrink2();
				String[] dk3 = daten1.getDrink3();
				String[] dk4 = daten1.getDrink4();
				String[] dk5 = daten1.getDrink5();
				String dat = daten1.getDatum();
				String bak = daten1.getBak();
				
				String fileName = Alkohol.getString("fileName");
				
				File file = new File(userHomeFolder+"/Desktop",fileName); // auf Desktop speichern (Achtung unsauber kann nur auf win ausgeführt werden). 
				
				//auf 2 nachkommastellen auf und abrunden
				char[] ss = new char[5];
			
				for(int i = 0; i < 5; i++)
				{
					ss[i] = bak.charAt(i);
				}
				
				int x = Character.getNumericValue(ss[4]);
				int y = Character.getNumericValue(ss[3]);
				
				if(x == 5 || x == 6 || x == 7 || x == 8 || x == 9)
				{
					y = y + 1;
				}
				
				String integer = Integer.toString(y);
				ss[3] = integer.charAt(0);
				
				bak = Character.toString(ss[0]) + Character.toString(ss[1]) + Character.toString(ss[2]) + Character.toString(ss[3]);
				
				if(!file.exists())
				{
					try 
					{
						file.createNewFile();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				
				FileWriter write;
				try 
				{
					write = new FileWriter(file.getAbsoluteFile(), true);
				} 
				catch (IOException e) {
					write = null;
					e.printStackTrace();
				}
				
				BufferedWriter wri;
				
				try
				{
				wri = new BufferedWriter(write);
				}
				catch(NullPointerException ne)
				{
					wri = null;
					ne.printStackTrace();
				}
				
				if(dat != null && !dat.isEmpty())
				{
				try 
				{
					wri.write(Alkohol.getString("date") + ": " + dat + " | ");
					geschrieben = true;
				}
				catch (IOException e2) 
				{
					e2.printStackTrace();
				}
				}
		
				if(!dk1[0].isEmpty() && !dk1[1].isEmpty() && !dk1[2].isEmpty()  &&
					!dk1[0].equals("0.0") && !dk1[1].equals("0.0") && !dk1[2].equals("0.0")  && 
					dk1[0] != null && dk1[1] != null && dk1[2] != null)
				{
				try
				{
					wri.write(Alkohol.getString("drink1") + "= " + Alkohol.getString("einheiten") + ": " + dk1[0] + " | " + Alkohol.getString("mJE") + ": " + dk1[1] + "ml" + " | " + Alkohol.getString("alkoholgehalt") + ": " + (Double.parseDouble(dk1[2]) / Double.parseDouble(dk1[0])) + "%");
					geschrieben = true;
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				}
				
				if(!dk2[0].isEmpty() && !dk2[1].isEmpty() && !dk2[2].isEmpty()  &&
						!dk2[0].equals("0.0") && !dk2[1].equals("0.0") && !dk2[2].equals("0.0")  && 
						dk2[0] != null && dk2[1] != null && dk2[2] != null)
				{
				try 
				{
					wri.write("\n" + Alkohol.getString("drink2") + "= " + Alkohol.getString("einheiten") + ": " + dk2[0] + " | " + Alkohol.getString("mJE") + ": " + dk2[1] +  "ml" +" | " + Alkohol.getString("alkoholgehalt") + ": " + (Double.parseDouble(dk2[2]) / Double.parseDouble(dk2[0]))+ "%");
					geschrieben = true;
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				}
				
				if(!dk3[0].isEmpty() && !dk3[1].isEmpty() && !dk3[2].isEmpty()  &&
						!dk3[0].equals("0.0") && !dk3[1].equals("0.0") && !dk3[2].equals("0.0")  && 
						dk3[0] != null && dk3[1] != null && dk3[2] != null)
				{
				try 
				{
					wri.write("\n" + Alkohol.getString("drink3") + "= " + Alkohol.getString("einheiten") + ": " + dk3[0] + " | " + Alkohol.getString("mJE") + ": " + dk3[1] + "ml" + " | " + Alkohol.getString("alkoholgehalt") + ": " + (Double.parseDouble(dk3[2]) / Double.parseDouble(dk3[0]))+ "%");
					geschrieben = true;
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				}
				
				if(!dk4[0].isEmpty() && !dk4[1].isEmpty() && !dk4[2].isEmpty()  &&
						!dk4[0].equals("0.0") && !dk4[1].equals("0.0") && !dk4[2].equals("0.0")  && 
						dk4[0] != null && dk4[1] != null && dk4[2] != null)
				{
				try 
				{
					wri.write("\n" + Alkohol.getString("drink4") + "= " + Alkohol.getString("einheiten") + ": " + dk4[0] + " | " + Alkohol.getString("mJE") + ": " + dk4[1] + "ml" + " | " + Alkohol.getString("alkoholgehalt") + ": " + (Double.parseDouble(dk4[2]) / Double.parseDouble(dk4[0]))+ "%");
					geschrieben = true;
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				}
				
				if(!dk5[0].isEmpty() && !dk5[1].isEmpty() && !dk5[2].isEmpty()  &&
						!dk5[0].equals("0.0") && !dk5[1].equals("0.0") && !dk5[2].equals("0.0")  && 
						dk5[0] != null && dk5[1] != null && dk5[2] != null)
				{
				try 
				{
					wri.write("\n" + Alkohol.getString("drink5") + "= " + Alkohol.getString("einheiten") + ": " + dk5[0] + " | " + Alkohol.getString("mJE") + ": " + dk5[1] + "ml" + " | " + Alkohol.getString("alkoholgehalt") + ": " + (Double.parseDouble(dk5[2]) / Double.parseDouble(dk5[0]))+ "%");
					geschrieben = true;
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				}
				
				if(bak != null && !bak.equals("0.0") && !bak.isEmpty())
				{
				try 
				{
					wri.write(" | " + Alkohol.getString("bloodalc") + "= " + bak + "‰" + " | " + Alkohol.getString("gen") + ": " + geschlecht.getValue().toString() + " | " + Alkohol.getString("uhave") + " " + stunden.getValue() + Alkohol.getString("hDrunk") + " " + Alkohol.getString("thisIs") + ": " + 0.15* Double.parseDouble(stunden.getValue()) + "‰ " + Alkohol.getString("abbau") + "|");
					geschrieben = true;
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				}
				
				if(geschrieben)
				{
					try 
					{
						// Zeilenumbruch
						wri.write("\n");
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
				try 
				{
					wri.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
	
				
		
	}//testSchreiben() end
	
	
	//Methode für Textfeld (zum Einlesen). 
	@FXML
	private void fieldFilled() throws Exception
	{
		
		//Border löschen falls vorhanden
		menge1Feld.setStyle("");
		menge2Feld.setStyle("");
		menge3Feld.setStyle("");
		menge4Feld.setStyle("");
		menge5Feld.setStyle("");
		ml1Feld.setStyle("");
		ml2Feld.setStyle("");
		ml3Feld.setStyle("");
		ml4Feld.setStyle("");
		ml5Feld.setStyle("");
		pro1Feld.setStyle("");
		pro2Feld.setStyle("");
		pro3Feld.setStyle("");
		pro4Feld.setStyle("");
		pro5Feld.setStyle("");
		gewichtFeld.setStyle("");
		
	//Hilfsvariabeln
	double alk1 = 0;
	double alk2 = 0;
	double alk3 = 0;
	double alk4 = 0;
	double alk5 = 0;
	
	//Felder leeren weil nicht jeder zustand alle Felder füllt.
	kons1.setText("");
	kons2.setText("");
	kons3.setText("");
	kons4.setText("");
	kons5.setText("");
	 
	//die meisten Menschen gehen immer nur an ihre Grenzen, aber das is mir nich genug - Max who am i 
	einlesen = geschlecht.getValue().toString();
	
	if(gewichtFeld.getText() != null && !gewichtFeld.getText().isEmpty())
	{
	einlesen1 = gewichtFeld.getText();
	}
	else
	{
	einlesen1 = "0.0";
	}
	
	einlesen17 = stunden.getValue().toString();
	
	double[] hour1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	
	finalAlkLooseOverTime = hour1[(int) Double.parseDouble(einlesen17)] * alkLooseOverTime;
	
	einlesen2 = menge1Feld.getText();
	einlesen3 = menge2Feld.getText();
	einlesen4 = menge3Feld.getText();
	einlesen5 = menge4Feld.getText();
	einlesen6 = menge5Feld.getText();
	
	einlesen1 = einlesen1.replace(',', '.');
	einlesen2 = einlesen2.replace(',', '.');
	einlesen3 = einlesen3.replace(',', '.');
	einlesen4 = einlesen4.replace(',', '.');
	einlesen5 = einlesen5.replace(',', '.');
	einlesen6 = einlesen6.replace(',', '.');
	
	einlesen7 = ml1Feld.getText();
	einlesen8 = ml2Feld.getText();
	einlesen9 = ml3Feld.getText();
	einlesen10 = ml4Feld.getText();
	einlesen11 = ml5Feld.getText();
	
	einlesen7 = einlesen7.replace(',', '.');
	einlesen8 = einlesen8.replace(',', '.');
	einlesen9 = einlesen9.replace(',', '.');
	einlesen10 = einlesen10.replace(',', '.');
	einlesen11 = einlesen11.replace(',', '.');
	
	einlesen12 = pro1Feld.getText();
	einlesen13 = pro2Feld.getText();
	einlesen14 = pro3Feld.getText();
	einlesen15 = pro4Feld.getText().replace(',', '.'); //leider is mir zu spät eingefallen das es auch so geht lol :D
	einlesen16 = pro5Feld.getText().replace(',', '.');
	
	einlesen12 = einlesen12.replace(',', '.');
	einlesen13 = einlesen13.replace(',', '.');
	einlesen14 = einlesen14.replace(',', '.');
	
	
	double d1a = 0;
	double d2a = 0;
	double d3a = 0;
	double d4a = 0;
	double d5a = 0;
	double d1m = 0;
	double d2m = 0;
	double d3m = 0;
	double d4m = 0;
	double d5m = 0;
	double d1p = 0;
	double d2p = 0;
	double d3p = 0;
	double d4p = 0;
	double d5p = 0;
	
	//muss hier gepased werden damit ich die exception einzel abfangen kann im dem nutzer anzuzeigen wo der fehler besteht.
	try
	{
		if(einlesen2 != null && !einlesen2.isEmpty()) d1a = Double.parseDouble(einlesen2);	
		if(!menge1Feld.getText().equalsIgnoreCase("0.0") && menge1Feld.getText() != null && !menge1Feld.getText().isEmpty()) menge1Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		menge1Feld.setText(Alkohol.getString("falscheEingabe"));
		menge1Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen3 != null && !einlesen3.isEmpty()) d2a = Double.parseDouble(einlesen3);
		if(!menge2Feld.getText().equalsIgnoreCase("0.0") && menge2Feld.getText() != null && !menge2Feld.getText().isEmpty()) menge2Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		menge2Feld.setText(Alkohol.getString("falscheEingabe"));
		menge2Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen4 != null && !einlesen4.isEmpty()) d3a = Double.parseDouble(einlesen4);
		if(!menge3Feld.getText().equalsIgnoreCase("0.0") && menge3Feld.getText() != null && !menge3Feld.getText().isEmpty()) menge3Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		menge3Feld.setText(Alkohol.getString("falscheEingabe"));
		menge3Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen5 != null && !einlesen5.isEmpty()) d4a = Double.parseDouble(einlesen5);
		if(!menge4Feld.getText().equalsIgnoreCase("0.0") && menge4Feld.getText() != null && !menge4Feld.getText().isEmpty()) menge4Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		menge4Feld.setText(Alkohol.getString("falscheEingabe"));
		menge4Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen6 != null && !einlesen6.isEmpty()) d5a = Double.parseDouble(einlesen6);
		if(!menge5Feld.getText().equalsIgnoreCase("0.0") && menge5Feld.getText() != null && !menge5Feld.getText().isEmpty()) menge5Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		menge5Feld.setText(Alkohol.getString("falscheEingabe"));
		menge5Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	
	try
	{
		if(einlesen7 != null && !einlesen7.isEmpty()) d1m = Double.parseDouble(einlesen7);
		if(!ml1Feld.getText().equalsIgnoreCase("0.0") && ml1Feld.getText() != null && !ml1Feld.getText().isEmpty()) ml1Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		ml1Feld.setText(Alkohol.getString("falscheEingabe"));
		ml1Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen8 != null && !einlesen8.isEmpty()) d2m = Double.parseDouble(einlesen8);
		if(!ml2Feld.getText().equalsIgnoreCase("0.0") && ml2Feld.getText() != null && !ml2Feld.getText().isEmpty()) ml2Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		ml2Feld.setText(Alkohol.getString("falscheEingabe"));
		ml2Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen9 != null && !einlesen9.isEmpty()) d3m = Double.parseDouble(einlesen9);
		if(!ml3Feld.getText().equalsIgnoreCase("0.0") && ml3Feld.getText() != null && !ml3Feld.getText().isEmpty()) ml3Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		ml3Feld.setText(Alkohol.getString("falscheEingabe"));
		ml3Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen10 != null && !einlesen10.isEmpty()) d4m = Double.parseDouble(einlesen10);
		if(!ml4Feld.getText().equalsIgnoreCase("0.0") && ml4Feld.getText() != null && !ml4Feld.getText().isEmpty()) ml4Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		ml4Feld.setText(Alkohol.getString("falscheEingabe"));
		ml4Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen11 != null && !einlesen11.isEmpty()) d5m = Double.parseDouble(einlesen11);
		if(!ml5Feld.getText().equalsIgnoreCase("0.0") && ml5Feld.getText() != null && !ml5Feld.getText().isEmpty()) ml5Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		ml5Feld.setText(Alkohol.getString("falscheEingabe"));
		ml5Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen12 != null && !einlesen12.isEmpty()) d1p = Double.parseDouble(einlesen12);
		if(!pro1Feld.getText().equalsIgnoreCase("0.0") && pro1Feld.getText() != null && !pro1Feld.getText().isEmpty()) pro1Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		pro1Feld.setText(Alkohol.getString("falscheEingabe"));
		pro1Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen13 != null && !einlesen13.isEmpty()) d2p = Double.parseDouble(einlesen13);
		if(!pro2Feld.getText().equalsIgnoreCase("0.0") && pro2Feld.getText() != null && !pro2Feld.getText().isEmpty()) pro2Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		pro2Feld.setText(Alkohol.getString("falscheEingabe"));
		pro2Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen14 != null && !einlesen14.isEmpty()) d3p = Double.parseDouble(einlesen14);
		if(!pro3Feld.getText().equalsIgnoreCase("0.0") && pro3Feld.getText() != null && !pro3Feld.getText().isEmpty()) pro3Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		pro3Feld.setText(Alkohol.getString("falscheEingabe"));
		pro3Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen15 != null && !einlesen15.isEmpty()) d4p = Double.parseDouble(einlesen15);
		if(!pro4Feld.getText().equalsIgnoreCase("0.0") && pro4Feld.getText() != null && !pro4Feld.getText().isEmpty()) pro4Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		pro4Feld.setText(Alkohol.getString("falscheEingabe"));
		pro4Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	try
	{
		if(einlesen16 != null && !einlesen16.isEmpty()) d5p = Double.parseDouble(einlesen16);
		if(!pro5Feld.getText().equalsIgnoreCase("0.0") && pro5Feld.getText() != null && !pro5Feld.getText().isEmpty()) pro5Feld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
	}
	catch(NumberFormatException e)
	{
		pro5Feld.setText(Alkohol.getString("falscheEingabe"));
		pro5Feld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ;-fx-text-inner-color: #C0392B;");
	}
	
	drink1 = new Drink(d1m, d1p, d1a, alkGewicht); //ml alk menge
	drink2 = new Drink(d2m, d2p, d2a, alkGewicht); // Ursprünglicher code: drink2 = new Drink(Double.parseDouble(einlesen8), Double.parseDouble(einlesen13), Double.parseDouble(einlesen3), alkGewicht);
	drink3 = new Drink(d3m, d3p, d3a, alkGewicht);
	drink4 = new Drink(d4m, d4p, d4a, alkGewicht);
	drink5 = new Drink(d5m, d5p, d5a, alkGewicht);

	if(einlesen1 != null && !einlesen1.isEmpty())
	{
		try
		{
	gewicht = Double.parseDouble(einlesen1);
	if(!gewichtFeld.getText().equalsIgnoreCase("0.0") && gewichtFeld.getText() != null && !gewichtFeld.getText().isEmpty()) gewichtFeld.setStyle("-fx-border-color: #27AE60; -fx-boarder-width: 4px ;-fx-text-inner-color: #27AE60;");
		}
		catch(NumberFormatException e)
		{
			gewicht = 0;
			gewichtFeld.setText("falscheEingabe");
			gewichtFeld.setStyle("-fx-border-color: #C0392B; -fx-boarder-width: 4px ; -fx-text-inner-color: #C0392B;");
		}
	}
	else
	{
	gewicht = 60;
	gewichtFeld.setText("60");
	}

	System.out.println("Gewicht= "+gewicht);
	
	//Reinalkohol = amountML * (alkGehalt / percent) * alkGewicht
	alk1 = drink1.getMl() * (drink1.getAlk() / percent) * alkGewicht;
	alk2 = drink2.getMl() * (drink2.getAlk() / percent) * alkGewicht;
	alk3 = drink3.getMl() * (drink3.getAlk() / percent) * alkGewicht;
	alk4 = drink4.getMl() * (drink4.getAlk() / percent) * alkGewicht;
	alk5 = drink5.getMl() * (drink5.getAlk() / percent) * alkGewicht;
	
	System.out.println("Alkohol= " + alk1);
	
	double endalkohol1;
	double endalkohol2;
	double endalkohol3;
	double endalkohol4;
	double endalkohol5;
	
	if(einlesen.equalsIgnoreCase(Alkohol.getString("choiceMen")))
	{
		if(alk1 != 0.0 && gewicht != 0.0)
		{
			endalkohol1 = alk1 / (gewicht * redMan); //implements Man
		}
		else
		{
			endalkohol1 = 0.0;
		}
		if(alk2 != 0.0 && gewicht != 0.0)
		{
			endalkohol2 = alk2 / (gewicht * redMan);
		}
		else
		{
			endalkohol2 = 0.0;
		}
		if(alk3 != 0.0 && gewicht != 0.0)
		{
			endalkohol3 = alk3 / (gewicht * redMan);
		}
		else
		{
			endalkohol3 = 0.0;
		}
		if(alk4 != 0.0 && gewicht != 0.0)
		{
			endalkohol4 = alk4 / (gewicht * redMan);
		}
		else
		{
			endalkohol4 = 0.0;
		}
		if(alk5 != 0.0 && gewicht != 0.0)
		{
			endalkohol5 = alk5 / (gewicht * redMan);
		}
		else
		{
			endalkohol5 = 0.0;
		}
	}
	else if(einlesen.equalsIgnoreCase(Alkohol.getString("choiceWom")))
	{
		if(alk1 != 0.0 && gewicht != 0.0)
		{
			endalkohol1 = alk1 / (gewicht * redWom); //implements Man
		}
		else
		{
			endalkohol1 = 0.0;
		}
		if(alk2 != 0.0 && gewicht != 0.0)
		{
			endalkohol2 = alk2 / (gewicht * redWom);
		}
		else
		{
			endalkohol2 = 0.0;
		}
		if(alk3 != 0.0 && gewicht != 0.0)
		{
			endalkohol3 = alk3 / (gewicht * redWom);
		}
		else
		{
			endalkohol3 = 0.0;
		}
		if(alk4 != 0.0 && gewicht != 0.0)
		{
			endalkohol4 = alk4 / (gewicht * redWom);
		}
		else
		{
			endalkohol4 = 0.0;
		}
		if(alk5 != 0.0 && gewicht != 0.0)
		{
			endalkohol5 = alk5 / (gewicht * redWom);
		}
		else
		{
			endalkohol5 = 0.0;
		}
	}
	else
	{
		endalkohol1 = 0.0; //Compiler befriedigen me me me local variable may not initilized  mi mi mi -.- xD
		endalkohol2 = 0.0;
		endalkohol3 = 0.0;
		endalkohol4 = 0.0;
		endalkohol5 = 0.0;
		System.err.println("ERROR!");
	}
	
	System.out.println("Endalkohol: "+endalkohol1);
	
	double finalEndergebnis = 0.0;
	finalEndergebnis = endalkohol1 + endalkohol2 + endalkohol3 + endalkohol4 + endalkohol5;
	
	if(finalEndergebnis != 0.0)
	{
	finalEndergebnis = (finalEndergebnis / 100) * 80; //ca. 20 % abrechnen da der Körper den Alkohol nicht vollständig aufnimmt. 
	}
	else
	{
		finalEndergebnis = 0.0;
	}
	
	finalEndergebnis = finalEndergebnis - finalAlkLooseOverTime;
	
	double xi = 0.001; //weird aber bei 0.000 wird zu 0.0 gemacht -.- btw 0.0001 führt zu einem ganz komischen fehler, rausfinden why...
	
	if(finalEndergebnis < 0.0 || finalEndergebnis == 0.0)
	{
		finalEndergebnis = xi; 
		System.out.println("finales endergebnis"+finalEndergebnis);
	}

	
	//auf 2 nachkommastellen auf und abrunden
	char[] ss = new char[5];
	
	String s = Double.toString(finalEndergebnis);
	s = s + "00"; //Härtefall abdecken wenn 0.0 rauskommt wird es zu 0.000
	System.out.println(s);
	
	for(int i = 0; i < 5; i++)
	{
		ss[i] = s.charAt(i);
	}
	
	int x = Character.getNumericValue(ss[4]);
	int y = Character.getNumericValue(ss[3]);
	
	if(x == 5 || x == 6 || x == 7 || x == 8 || x == 9)
	{
		y = y + 1;
	}
	
	String integer = Integer.toString(y);
	ss[3] = integer.charAt(0);
	
	s = Character.toString(ss[0]) + Character.toString(ss[1]) + Character.toString(ss[2]) + Character.toString(ss[3]);
	
	blutAlkohol.setText(s);
	
	if(finalEndergebnis <= 0.01)
	{
		
	}
	else if(finalEndergebnis < 0.5 && finalEndergebnis > 0.01)
	{
		kons1.setText(Alkohol.getString("aus"));
	}
	else if(finalEndergebnis < 1.0 && finalEndergebnis >= 0.5) // 05-1
	{
		kons1.setText(Alkohol.getString("aus1"));
		kons2.setText(Alkohol.getString("aus11"));
		kons3.setText(Alkohol.getString("aus111"));
		kons4.setText(Alkohol.getString("aus1111"));
		kons5.setText(Alkohol.getString("aus11111"));
		tippSatz.setText(getAlkohol().getString("tipp1"));
		
		Image image = new Image(getClass().getResource(bild1).toURI().toString());
		paneImage.setMinSize(-1, -1);
		BackgroundImage img = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(-1,-1,false,false,false,true));
		Background background = new Background(img);
		paneImage.setBackground(background);
	
		
	}
	else if(finalEndergebnis >= 1.0 && finalEndergebnis < 2.0 ) //1-2
	{
		kons1.setText(Alkohol.getString("aus2"));
		kons2.setText(Alkohol.getString("aus22"));
		kons3.setText(Alkohol.getString("aus222"));
		kons4.setText(Alkohol.getString("aus2222"));
		kons5.setText(Alkohol.getString("aus22222"));
		tippSatz.setText(getAlkohol().getString("tipp11"));
		
		Image image = new Image(getClass().getResource(bild2).toURI().toString());
		paneImage.setMinSize(-1, -1);
		BackgroundImage img = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(-1,-1,false,false,false,true));
		Background background = new Background(img);
		paneImage.setBackground(background);
		
	}
	else if(finalEndergebnis < 3.0 && finalEndergebnis >= 2.0) //2-3
	{
		kons1.setText(Alkohol.getString("aus3"));
		kons2.setText(Alkohol.getString("aus33"));
		kons3.setText(Alkohol.getString("aus333"));
		kons4.setText(Alkohol.getString("aus3333"));
		tippSatz.setText(getAlkohol().getString("tipp111"));
		
		Image image = new Image(getClass().getResource(bild3).toURI().toString());
		paneImage.setMinSize(-1, -1);
		BackgroundImage img = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(-1,-1,false,false,false,true));
		Background background = new Background(img);
		paneImage.setBackground(background);
		
	}
	else if(finalEndergebnis < 4.0 && finalEndergebnis >= 3.0) //3-4
	{
		kons1.setText(Alkohol.getString("aus4"));
		kons2.setText(Alkohol.getString("aus44"));
		kons3.setText(Alkohol.getString("aus444"));
		tippSatz.setText(getAlkohol().getString("tipp1111"));
		
		Image image = new Image(getClass().getResource(bild4).toURI().toString());
		paneImage.setMinSize(-1, -1);
		BackgroundImage img = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(-1,-1,false,false,false,true));
		Background background = new Background(img);
		paneImage.setBackground(background);
		
	}
	else if(finalEndergebnis <= 5.0 && finalEndergebnis >= 4.0) //4-5
	{
		kons1.setText(Alkohol.getString("aus5"));
		kons2.setText(Alkohol.getString("aus55"));
		kons3.setText(Alkohol.getString("aus555"));
		kons4.setText(Alkohol.getString("aus5555"));
		tippSatz.setText(getAlkohol().getString("tipp11111"));
		
		Image image = new Image(getClass().getResource(bild5).toURI().toString());
		paneImage.setMinSize(-1, -1);
		BackgroundImage img = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(-1,-1,false,false,false,true));
		Background background = new Background(img);
		paneImage.setBackground(background);
		
	}
	else
	{
		kons1.setText(Alkohol.getString("aus6"));
		kons2.setText(Alkohol.getString("aus66"));
		kons3.setText(Alkohol.getString("aus666"));
		kons4.setText(Alkohol.getString("aus6666"));
		tippSatz.setText(getAlkohol().getString("tipp111111"));
	}
	
	if(finalEndergebnis <= 5.0 && finalEndergebnis >= 0.0)
	{
	bar.setProgress((finalEndergebnis / 10) * 2);
	}
	else
	{
	bar.setProgress(-1);	
	}
	Daten datas = new Daten(drink1,drink2,drink3,drink4,drink5,finalEndergebnis);
	daten1 = datas;
	
	}//fieldFilled end
	
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
		Stage stage1 = (Stage)bar.getScene().getWindow();
		stage1.close();
	}
	
	public void engPressed() throws IOException, URISyntaxException
	{

		App.Language = "english";
		Stage stage1 = (Stage)bar.getScene().getWindow();
		stage1.close();
		primaryWindow.startMainApp();
	}
	
	public void gerPressed() throws IOException, URISyntaxException
	{

		App.Language = "german";
		Stage stage1 = (Stage)bar.getScene().getWindow();
		stage1.close();
		primaryWindow.startMainApp();
	}
	
	//Getter und Setter
	public String getCountry() 
	{
		return country;
	}

	public void setCountry(String country) 
	{
		this.country = country;
	}

	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language) 
	{
		this.language = language;
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
	 
	
	/*
	 * For Updates:
	 * Berechnen anhand von Körpergröße aufnehmen
	 * tooltips ???
	 */
	
	/*
	 * Whats to fix:
	 * rechtsklick windows leiste auf icon ???
	 * Main Window sprache aktualisieren wenn es in einem anderen Window aufgerufen wird. (Listener?)
	 */
	
	/*
	 * To-Do:
	 * Dok Anleitung schreiben
	 */
	
	// Jeah i am Dunk a bit wen i wrote this shit motherucker ||||| ONKELFICKEF ///// By DoPeMaThERs‼
	
	

} // PrimaryController end 
