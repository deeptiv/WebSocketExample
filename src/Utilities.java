
public class Utilities {

	public static boolean checkInputStringLenghtAndValidDictWord(String inputString){
		if (inputString != null && inputString.length() == 4){
			return true;
		}
		
		return false;
	}
}
