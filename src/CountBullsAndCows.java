import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;






public class CountBullsAndCows {


	//Map<Character, Integer> makerHash = new HashMap<Character, Integer>();

	//MultiMap<Character, Set<Integer>> makerHash = new MultiValueMap<Character, Set<Integer>>();
	char[] makerArray;
	
//	public void initializeMakerHash(WordMakerPlayer wordMakerPlayer){
//
//		char[] makerArray = wordMakerPlayer.getWordMakerString().toCharArray(); 
//
//		for (int i=0; i<4; i++){
//			makerHash.put(makerArray[i], i);
//		}
//
//		//		Set<Character> keys = makerHash.keySet();
//		//
//		//		for (Character key : keys){
//		//			System.out.println("key:" + key + " value: " + makerHash.get(key));
//		//		}
//		//
//		//
//
//	}
	public void initializeMakerArray(WordMakerPlayer wordMakerPlayer){

		makerArray = wordMakerPlayer.getWordMakerString().toCharArray(); 

	}

//	public BullsAndCows countBullsAndCowsByHash( WordBreakerPlayer wordBreakerPlayer){
//		BullsAndCows  bullsAndCows = new BullsAndCows();
//
//		char[] breakerArray = wordBreakerPlayer.getWordBreakerString().toCharArray();
//		MultiMap<Character, Set<Integer>> tmpHash = new MultiValueMap<Character, Set<Integer>>();
//		tmpHash.putAll(makerHash);
//		for (int i=0; i<4; i++){
//			@SuppressWarnings("unchecked")
//			ArrayList<Integer> positions = (ArrayList<Integer>) tmpHash.get(breakerArray[i]);
//			if(null != positions && positions.size() >0){
//				if(positions.contains(i)){
//
//					int currentbulls = bullsAndCows.getBulls();
//					bullsAndCows.setBulls(++currentbulls);
//				}
//				else{
//					int currentCows = bullsAndCows.getCows();
//					bullsAndCows.setCows(++currentCows);
//				}
//
//
//				if(positions.size() == 1){
//					tmpHash.remove(breakerArray[i]);
//				} else{
//					tmpHash.remove(breakerArray[i]);
//					for (Integer pos: positions){
//						if (pos != i){
//							tmpHash.put(breakerArray[i], pos);
//						}
//					}
//				}
//
//
//			}
//
//		}
//
//
//		return bullsAndCows;
//	}
	public BullsAndCows countBullsAndCows(WordMakerPlayer wordMakerPlayer, WordBreakerPlayer wordBreakerPlayer){
		BullsAndCows  bullsAndCows = new BullsAndCows();
		String ms = "fine";
		char[] makerArray = wordMakerPlayer.getWordMakerString().toCharArray();
		char[] breakerArray = wordBreakerPlayer.getWordBreakerString().toCharArray();

		for (int i=0; i<4; i++){
			if(breakerArray[i] == makerArray[i]){
				int currentbulls = bullsAndCows.getBulls();
				bullsAndCows.setBulls(++currentbulls);
				makerArray[i] = '0';
				breakerArray[i] = '0';
			}
		}
		
		for (int i=0; i<4; i++){
			if(breakerArray[i] != '0'){
				for (int j=0; j<4; j++){
					if(breakerArray[i] == makerArray[j]){
						int currentCows = bullsAndCows.getCows();
						bullsAndCows.setCows(++currentCows);
						makerArray[j] = '0';
					}
				}
			}
		}

		return bullsAndCows;
	}
}
