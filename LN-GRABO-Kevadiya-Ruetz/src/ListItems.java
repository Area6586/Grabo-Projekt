
public class ListItems {


	private Items[] eis = { new Items("Chocolate", 1.0), new Items("Vanilla", 1.0), new Items("EspressoFlake", 1.0),
			new Items("BlackCherry", 1.0), new Items("Pistachio", 1.0), new Items("FrenchVanilla", 1.0),
			new Items("MindChocolate", 1.0), new Items("Coconut", 1.0), new Items("Mango", 1.0) };
	
	private Items[] shake = { new Items("Chocolate", 1.0), new Items("Vanilla", 1.0), new Items("EspressoFlake", 1.0),
			new Items("BlackCherry", 1.0), new Items("Pistachio", 1.0), new Items("FrenchVanilla", 1.0),
			new Items("MindChocolate", 1.0), new Items("Coconut", 1.0), new Items("Mango", 1.0) };

	
	public Items[] getEisItems() {
		return eis;
	}

	public Items[] getShakeItems() {
		return shake;
	}


}
