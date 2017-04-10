package SicAssembler;

import java.io.IOException;
import java.util.LinkedList;

public class SicAssembler {

	
	public static void main(String[] args) throws IOException
    {
	    LinkedList<DataLine> x = new  LinkedList<DataLine>();
	    new Pass1(x);
	    new Pass2(x);	
	    //ErrorHandling.Errorhandling.keys();
	    //ErrorHandling.Errorhandling.values();
	    
		
	}
}
