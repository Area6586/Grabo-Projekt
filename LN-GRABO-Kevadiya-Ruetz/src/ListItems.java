/**
 * 
 */

/**
 * @author kevad
 *
 */
public class ListItems {
	//"Apple", "Orange","Banana", "Watermelon", "Strawberry", "Grape", "Mango", "Kiwi", "Avacado"
	private Items[] obst = {new Items("Apple", 2.5), new Items("Watermelon", 2.0) }  ;
	private String[] gemuese = {} ;
	private String[] eis;
	private String[] other;
	
	public Items[] getObstItems() {
		return obst;
	}
	public String[] getGemueseItems() {
		return gemuese;
	}
	
	public String[] getEisItems() {
		return eis;
	}
	
	public String[] getOtherItems() {
		return other;
	}

	
	
}
