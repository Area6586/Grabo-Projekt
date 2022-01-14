/**
 * 
 */

/**
 * @author kevad
 *
 */
public class ListItems {

	private Items[] obst = 	{	new Items("Apfel", 2.5), 
								new Items("Melone", 2.0),
								new Items("Erdbeere", 2.0), 
								new Items("Kiwi", 2.0),
								new Items("Mango", 2.0), 
								new Items("Avacado", 2.0),
								new Items("Orange", 2.0), 
								new Items("Banana", 2.0),
								new Items("Ananas", 2.0)} ;
	private Items[] gemuese = {	new Items("Bohne", 2.0),
								new Items("Kohl", 2.0),
								new Items("Karotte", 2.0),
								new Items("Bluemenkohl", 2.0),
								new Items("Gurke", 2.0),
								new Items("Kartoffeln", 2.0),
								new Items("Zwiebel", 2.0),
								new Items("Tomate", 2.0),
								new Items("Zitrone", 2.0)} ;
	private Items[] eis = 	{	new Items("Chocolate", 2.0),
								new Items("Vanilla", 2.0),
								new Items("EspressoFlake", 2.0),
								new Items("BlackCherry", 2.0),
								new Items("Pistachio", 2.0),
								new Items("FrenchVanilla", 2.0),
								new Items("MindChocolate", 2.0),
								new Items("Coconut", 2.0),
								new Items("Mango", 2.0)} ;
	private Items[] other = {	new Items("Kitkat", 2.0),
								new Items("Mars", 2.0),
								new Items("M & M", 2.0),
								new Items("Candy", 2.0),
								new Items("RitterSport", 2.0),
								new Items("OrangeSaft", 2.0),
								new Items("Cola", 2.0),
								new Items("Pizza", 2.0),
								new Items("Bier", 4.0)};
	
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
