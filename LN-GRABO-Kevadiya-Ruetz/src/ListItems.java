/**
 * @author kevad
 *
 */

public class ListItems {


	private Items[] obst = 	{	new Items("Apfel", 2.5), 
								new Items("Melone", 2.0),
								new Items("Erdbeere", 3.0), 
								new Items("Kiwi", 5.0),
								new Items("Mango", 7.0), 
								new Items("Avacado", 6.5),
								new Items("Orange", 3.0), 
								new Items("Banana", 2.0),
								new Items("Ananas", 4.0)} ;
	private Items[] gemuese = {	new Items("Bohne", 2.0),
								new Items("Kohl", 1.0),
								new Items("Karotte", 1.5),
								new Items("Bluemenkohl", 2.0),
								new Items("Gurke", 3.0),
								new Items("Kartoffeln", 2.5),
								new Items("Zwiebel", 4.0),
								new Items("Tomate", 2.5),
								new Items("Zitrone", 4.5)} ;
	private Items[] eis = 	{	new Items("Chocolate", 1.0),
								new Items("Vanilla", 1.0),
								new Items("EspressoFlake", 1.0),
								new Items("BlackCherry", 1.0),
								new Items("Pistachio", 1.0),
								new Items("FrenchVanilla", 1.0),
								new Items("MindChocolate", 1.0),
								new Items("Coconut", 1.0),
								new Items("Mango", 1.0)} ;
	private Items[] other = {	new Items("Kitkat", 1.0),
								new Items("Mars", 1.0),
								new Items("M & M", 1.5),
								new Items("Candy", 2.0),
								new Items("RitterSport", 1.5),
								new Items("OrangeSaft", 1.0),
								new Items("Cola", 2.0),
								new Items("Pizza", 3.0),
								new Items("Bier", 5.0)};

	
	public Items[] getObstItems() {
		return obst;
	}
	public Items[] getGemueseItems() {
		return gemuese;
	}
	
	public Items[] getEisItems() {
		return eis;
	}
	
	public Items[] getOtherItems() {
		return other;
	}


}
