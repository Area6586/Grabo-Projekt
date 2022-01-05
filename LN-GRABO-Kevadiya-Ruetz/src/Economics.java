
//For sum,change and other economic purposes

public class Economics {
	
	//Current sum, starts with value 0
	private float sum = 0;


	//Can be used for increasing/decreasing the sum
	//Called by ActionHandler of corresponding item-button with its value as the difference parameter
	//(or ActionHandler of the +/- buttons)
	public void changeSum(float difference) {
	
		sum = sum + difference;
	
	}
	
	public float getChange(float given) {
		
		return given - sum;
		
	}
	
	
	public void resetSum() {
		sum = 0;
	}
	
	public float getSum() {
		
		return sum;
	}
	
	
	
}
