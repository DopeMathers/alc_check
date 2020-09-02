package DopeMathers.AlkoholTest;

import java.util.Date;

public class Daten 
{
	private String datum;
	private String bak;
	private String[] drink1 = {"","",""};//Menge, ML, Prozent
	private String[] drink2 = {"","",""};
	private String[] drink3 = {"","",""};
	private String[] drink4 = {"","",""};
	private String[] drink5 = {"","",""};
	
	Daten(Drink drink11, Drink drink22, Drink drink33, Drink drink44, Drink drink55, double bak)
	{
			this.drink1[0] = Double.toString(drink11.getMenge());
			this.drink1[1] = Double.toString(drink11.getMl());
			this.drink1[2] = Double.toString(drink11.getAlk());
			
			this.drink2[0] = Double.toString(drink22.getMenge());
			this.drink2[1] = Double.toString(drink22.getMl());
			this.drink2[2] = Double.toString(drink22.getAlk());
			
			this.drink3[0] = Double.toString(drink33.getMenge());
			this.drink3[1] = Double.toString(drink33.getMl());
			this.drink3[2] = Double.toString(drink33.getAlk());
			
			this.drink4[0] = Double.toString(drink44.getMenge());
			this.drink4[1] = Double.toString(drink44.getMl());
			this.drink4[2] = Double.toString(drink44.getAlk());
			
			this.drink5[0] = Double.toString(drink55.getMenge());
			this.drink5[1] = Double.toString(drink55.getMl());
			this.drink5[2] = Double.toString(drink55.getAlk());
			
			this.bak = Double.toString(bak);
	
			Date date = new Date();
			datum = date.toString();
		
	}//Daten constroctor end
	
	
	//Getter und Setter
	public String[] getDrink1()
	{
		return drink1;
	}

	public void setDrink1(String[] drink1) 
	{
		this.drink1 = drink1;
	}

	public String[] getDrink2()
	{
		return drink2;
	}

	public void setDrink2(String[] drink2) 
	{
		this.drink2 = drink2;
	}

	public String[] getDrink3() 
	{
		return drink3;
	}

	public void setDrink3(String[] drink3) 
	{
		this.drink3 = drink3;
	}

	public String[] getDrink4()
	{
		return drink4;
	}

	public void setDrink4(String[] drink4) 
	{
		this.drink4 = drink4;
	}

	public String[] getDrink5() 
	{
		return drink5;
	}

	public void setDrink5(String[] drink5) 
	{
		this.drink5 = drink5;
	}

	public String getDatum() 
	{
		return datum;
	}

	public void setDatum(String datum)
	{
		this.datum = datum;
	}

	public String getBak() 
	{
		return bak;
	}

	public void setBak(String bak) 
	{
		this.bak = bak;
	}
	
}
