package SicAssembler;

import java.util.Hashtable;


public class OPTAB{
	
	private static  Hashtable<String, String> opTable = new Hashtable<String,String>();
	public static void main(String[] args){
		
		/*String term = "10+15".trim();
		ArrayList<Integer> valuesList = new ArrayList<Integer>();
		int sum = 0;
		String[]strings = term.split("\\+");
		 System.out.println(strings[0]); 

		for(String value : strings)
		{
			if(value.contains("-")){
				
				String[] strings2 = value.split("-");
				valuesList.add(Integer.parseInt(strings2[0] , 16));
				for (int i = 1; i < strings2.length; i++) {
					valuesList.add(Integer.parseInt(strings2[i] , 16) * -1);
				}
			}
			else{
				valuesList.add(Integer.parseInt(value , 16));

			}
				
		}
		
		for (int i = 0; i < valuesList.size(); i++) {
			sum += valuesList.get(i);
		}*/
		String term = String.format("%06x", Integer.parseInt("12" , 16)).toUpperCase();
		 System.out.println(term); 

	}
	public static String getopcode (String Key){		
		return (opTable.get(Key));
		
	}
	
	public static void intiOPTAB(){
		
		opTable = new Hashtable<String,String>();
		opTable.put("add","18");
		opTable.put("and","40");
		opTable.put("comp","28");
		opTable.put("div","24");
		opTable.put("j","3C");
		opTable.put("jeq","30");
		opTable.put("jgt","34");
		opTable.put("jlt","38");
		opTable.put("jsub","48");
		opTable.put("lda","00");
		opTable.put("ldch","50");
		opTable.put("ldl","08");
		opTable.put("ldx","04");
		opTable.put("mul","20");
		opTable.put("or","44");
		opTable.put("rd","D8");
		opTable.put("rsub","4C");
		opTable.put("sta","0C");
		opTable.put("stch","54");
		opTable.put("stl","14");
		opTable.put("stx","10");
		opTable.put("sub","1C");
		opTable.put("td","E0");
		opTable.put("tix","2C");
		opTable.put("wd","DC");
		
}
	
	
	
}