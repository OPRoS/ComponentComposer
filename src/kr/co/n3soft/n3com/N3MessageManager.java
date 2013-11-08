package kr.co.n3soft.n3com;

import kr.co.n3soft.n3com.lang.N3Messages;



public class N3MessageManager {
	 private static N3MessageManager instance;
	 
	 private static boolean isEngLang = false;
	 private static N3Messages n3Message= null;
	 private static N3KorMessage n3korMessage= null;
	 private static N3EngMessage n3engMessage= null;
	 
	 public static N3MessageManager getInstance() {
	        if (instance == null) {
	            instance = new N3MessageManager();
	            return instance;
	        }
	        else {
	            return instance;
	        }
	    }
	 
	 public static N3Messages getN3Message(){
		 if(isEngLang){
			 return n3engMessage;
		 }
		 else{
			 return n3korMessage;
		 }
	 }
}
