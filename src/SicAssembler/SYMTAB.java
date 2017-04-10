package SicAssembler;

import java.util.Hashtable;

public class SYMTAB {
	
	public static  Hashtable<String, String> SymTable = new Hashtable<String, String>();
	public static String getSymAddress(String Symbol)
	{
		return SymTable.get(Symbol);
	}
	public static void setSymAddress(String Symbol , String Address)
	{
		SymTable.put(Symbol, Address);
	}
	public static boolean CheckSymExist(String Symbol)
	{
		return SymTable.containsKey(Symbol);
	}

}
