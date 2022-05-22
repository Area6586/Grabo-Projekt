
public class ListItems {

	private Items[] eis = { new Items("Chocolate", 1.0), new Items("Vanilla", 2.0), new Items("EspressoFlake", 1.5),
			new Items("BlackCherry", 4.0), new Items("Pistachio", 4.5), new Items("FrenchVanilla", 3.5),
			new Items("MindChocolate", 2.5), new Items("Coconut", 1.5), new Items("Mango", 4.0) };

	private Items[] shake = { new Items("Chocolate", 2.5), new Items("Vanilla", 2.0), new Items("EspressoFlake", 4.0),
			new Items("BlackCherry", 5.0), new Items("Pistachio", 2.0), new Items("FrenchVanilla", 1.0),
			new Items("MindChocolate", 5.0), new Items("Coconut", 3.0), new Items("Mango", 7.0) };

	public Items[] getEisItems() {
		return eis;
	}

	public Items[] getShakeItems() {
		return shake;
	}

}
