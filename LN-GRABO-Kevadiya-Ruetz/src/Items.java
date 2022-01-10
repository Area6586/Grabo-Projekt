
public class Items {

	//name of Item
	 private String name;
	  
	
	// preis of Item
	private double preis = 0;

	
	//get Method for name of item
	  public String getName() { 
		  return name; 
	  }
	
	//get method for pries of item
	public double getPreis() {
		return preis;
	}

	public Items(String name, double preis) {
		this.name = name;
		this.preis = preis;
	}
	



}
