
public class BullsAndCows {

	public int bulls = 0;
	public int cows = 0;
	
	public int getBulls() {
		return bulls;
	}
	public void setBulls(int bulls) {
		this.bulls = bulls;
	}
	public int getCows() {
		return cows;
	}
	public void setCows(int cows) {
		this.cows = cows;
	}
	
	public String printBullsAndCows(){
		String str = "  Bulls: " + this.getBulls() + " Cows: " + this.getCows();
		return str;
	}
	
}
