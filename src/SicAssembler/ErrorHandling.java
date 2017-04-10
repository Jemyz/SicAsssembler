package SicAssembler;

import java.util.Hashtable;
import java.util.LinkedList;

public class ErrorHandling {

	public static  Hashtable<String,LinkedList<String>> Errorhandling = new Hashtable<String,LinkedList<String>>();
	
	public static LinkedList<String> getError (String Key){		
		return (Errorhandling.get(Key));
	}
	public static void setError (String Key , String Error){		
		if(Errorhandling.containsKey(Key)) Errorhandling.get(Key).add(Error);
		else{
			Errorhandling.put(Key, new LinkedList<String>());
			Errorhandling.get(Key).add(Error);
		}
	}

}
