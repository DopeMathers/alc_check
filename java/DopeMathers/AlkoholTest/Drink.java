package DopeMathers.AlkoholTest;

public class Drink
{
	private double ml;
	private double alk;
	private double menge;
	private double alkGewicht;
	
	Drink(double ml, double alk, double menge, double alkGewicht)
	{
		this.ml = ml;
		this.setMenge(menge);
		this.setAlkGewicht(alkGewicht);
		this.alk = alk*menge;
		
	}//Drink (constructor) end
	
	public double getMl() 
	{
		return ml;
	}//getML() end
	
	public double getAlk() 
	{
		return alk;
	}//getAlk() end

	public double getMenge()
	{
		return menge;
	}//getMenge() end

	public void setMenge(double menge) 
	{
		this.menge = menge;
	}//setMenge() end

	public double getAlkGewicht() {
		return alkGewicht;
	}//getAlkGewicht() end

	public void setAlkGewicht(double alkGewicht) {
		this.alkGewicht = alkGewicht;
	}//setAlkGewicht() end
	
} // Class end 
