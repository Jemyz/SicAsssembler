package SicAssembler;

import java.util.Hashtable;

public class LITTAB {
	public static  Hashtable<String, LITNode> LitTable = new Hashtable<String, LITNode>();
	public static String getLitAddress(String Literal)
	{
		String key = "" ;
		String []strings = Literal.trim().split("'");	            	
    	if(strings[0].equalsIgnoreCase("=c"))
    	{
    	char []values =strings[1].toCharArray();
		for (int i = 0 ; i < values.length ; i++)
		{
			key = key + Integer.toHexString((int)values[i]);	        		    
		}
		
    	}
	    else if (strings[0].equalsIgnoreCase("=x")){
	    	key = strings[1];
	  }
	    else if (strings[0].equalsIgnoreCase(Literal))
	    {
	    	String x = Literal.substring(1);
	    	if(x.charAt(0) == '+')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	else if(x.charAt(0) == '-')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x)*-1);
	    		key = x;
	    		
	    	}
	    	else
	    	{
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	
	    	
	    	
	    }
		return LitTable.get(key).getAddress();
	}
	public static void setLitAddress(String Literal , String Address)
	{
		String key = "" ;
		String []strings = Literal.trim().split("'");	            	
    	if(strings[0].equalsIgnoreCase("=c"))
    	{
    	char []values =strings[1].toCharArray();
		for (int i = 0 ; i < values.length ; i++)
		{
			key = key + Integer.toHexString((int)values[i]);	        		    
		}
		
    	}
	    else if (strings[0].equalsIgnoreCase("=x")){
	    	key = strings[1];
	  }
	    else if (strings[0].equalsIgnoreCase(Literal))
	    {
	    	String x = Literal.substring(1);
	    	if(x.charAt(0) == '+')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	else if(x.charAt(0) == '-')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x)*-1);
	    		key = x;
	    		
	    	}
	    	else
	    	{
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	
	    	
	    	
	    }
		LitTable.get(key).setAddress(Address);
	}
	public static boolean CheckLitExist(String Literal)
	{
		String key = "" ;
		String []strings = Literal.trim().split("'");	            	
    	if(strings[0].equalsIgnoreCase("=c"))
    	{
    	char []values =strings[1].toCharArray();
		for (int i = 0 ; i < values.length ; i++)
		{
			key = key + Integer.toHexString((int)values[i]);	        		    
		}
		
    	}
	    else if (strings[0].equalsIgnoreCase("=x")){
	    	key = strings[1];
	  }
	    else if (strings[0].equalsIgnoreCase(Literal))
	    {
	    	String x = Literal.substring(1);
	    	if(x.charAt(0) == '+')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	else if(x.charAt(0) == '-')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x)*-1);
	    		key = x;
	    		
	    	}
	    	else
	    	{
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	
	    	
	    	
	    }
		return LitTable.containsKey(key);
	}
	
	public static LITNode get (String Literal)
	{
		//LITNode temp = new LITNode();
		String key = "" ;
		String []strings = Literal.trim().split("'");	            	
    	if(strings[0].equalsIgnoreCase("=c"))
    	{
    	char []values =strings[1].toCharArray();
		for (int i = 0 ; i < values.length ; i++)
		{
			key = key + Integer.toHexString((int)values[i]);	        		    
		}
		
    	}
	    else if (strings[0].equalsIgnoreCase("=x")){
	    	key = strings[1];
	  }
	    else if (strings[0].equalsIgnoreCase(Literal))
	    {
	    	String x = Literal.substring(1);
	    	if(x.charAt(0) == '+')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	else if(x.charAt(0) == '-')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x)*-1);
	    		key = x;
	    		
	    	}
	    	else
	    	{
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    	}
	    	
	    	
	    	
	    }
		return LitTable.get(key);
	}
	
	
	public static boolean SetLiteral (String Literal)
	{
		LITNode temp = new LITNode();
		String key = "";
		String []strings = Literal.trim().split("'");	            	
    	if(strings[0].equalsIgnoreCase("=c"))
    	{
    	char []values =strings[1].toCharArray();
		for (int i = 0 ; i < values.length ; i++)
		{
			key = key + Integer.toHexString((int)values[i]);	        		    
		}
		temp.Length = values.length;
		
		
		
    	}
	    else if (strings[0].equalsIgnoreCase("=x")){
	    	key = strings[1];
	    	temp.Length = (int)(((strings[1].length())/2.0)+0.9);
	    	
	    	try{
	    		Integer.parseInt(strings[1]);
	    	}catch(NumberFormatException e){return true;}
	    	
	  }
	    else if (strings[0].equalsIgnoreCase(Literal))
	    {
	    	String x = Literal.substring(1);
	    	
	    	try{
	    		Integer.parseInt(x);
	    	}catch(NumberFormatException e){return true;}
	    	if(x.charAt(0) == '+')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    		temp.Length = (int)(((x.length())/2.0)+0.9);
	    	}
	    	else if(x.charAt(0) == '-')
	    	{
	    		x = Literal.substring(2);
	    		x = Integer.toHexString(Integer.parseInt(x)*-1);
	    		key = x;
		    	temp.Length = (int)(((x.length())/2.0)+0.9);
	    		
	    	}
	    	else
	    	{
	    		x = Integer.toHexString(Integer.parseInt(x));
	    		key = x;
	    		temp.Length = (int)(((x.length())/2.0)+0.9);
	    	}
   	
	    	
	    }
	    else return true;
    	try{
    		if(strings[2] != null)return true;
    	}catch(ArrayIndexOutOfBoundsException e){}
    	LitTable.put(key, temp);
    	return false;
   

	}


}
