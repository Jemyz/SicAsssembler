package SicAssembler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Pass1 {
	
	 File f;
	 
	 public Pass1(LinkedList<DataLine>  dataline) throws IOException
	 {
		 ReadSrcfile(dataline);
		 

	 }
	 
	 
	 
	 public void ReadSrcfile (LinkedList<DataLine>  datalines) throws IOException
	    {
	 
	        f = new File("SRCFILE");
	        Scanner s = new Scanner(f);
	        
	        String line ;
	        DataLine temp;
	        String label;
	        String Instruction;
	        String operand;
	        String comment;
	        String LOCCTR;
	        String obcode = "";
	        int noOfBytes;
	        String PreLOCCTR = "";
	         
	        line = s.nextLine() + "                                                                                   ";
            label = line.substring(0,8);
            Instruction = line.substring(9,15);
            operand = line.substring(17,35);
            comment = line.substring(35,66); 

            
            if((Instruction.trim()).equalsIgnoreCase("start"))
            	{
            	if(operand.trim().equalsIgnoreCase("")){LOCCTR = "0";}
            	else LOCCTR = operand.trim();           	
            	}
         
            else LOCCTR = "0";
            
           
    		SYMTAB.setSymAddress(label.trim(),LOCCTR.trim());
            LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16));
            temp = new DataLine(label, Instruction, operand, LOCCTR, comment , obcode);
            datalines.add(temp);
            
	        
	        while (s.hasNext() && !Instruction.trim().equalsIgnoreCase("end"))
	        {
	        	obcode = "";
	        	noOfBytes = 1;
		        line = s.nextLine() + "                                                                              ";	            
	            label = line.substring(0,8);
	            Instruction = line.substring(9,15);
	            operand = line.substring(17,35);
	            comment = line.substring(35,66);
	            
	            
	            
	            if((Instruction.trim()).equalsIgnoreCase("EQU")){
	            	
	            	
	            	if(!label.trim().equalsIgnoreCase(""))
		            {
		            	if(SYMTAB.CheckSymExist(label.trim())){ErrorHandling.setError(LOCCTR, " **** duplicate label definition"); obcode = "      ";}
		            	else 
		            	{
		            	
		            		if(operand.trim().equalsIgnoreCase("*"))SYMTAB.setSymAddress(label.trim(),LOCCTR);
		            		else {
		            			String temp2 = CalcSymTerm(operand.trim(),LOCCTR);
		            			
		            			if (temp2 == null)obcode = "      ";
		            			else SYMTAB.setSymAddress(label.trim(),CalcSymTerm(operand.trim(),LOCCTR));		            		}
		            	}
	                
		            }
	            	else {ErrorHandling.setError(LOCCTR, " **** Missing label definition");obcode = "      ";}
	            		
	            	obcode = "      ";
	            }
	            
	            
	            
	            if((Instruction.trim()).equalsIgnoreCase("ORG")){
	            	
	            	temp = new DataLine("        " , Instruction , operand , LOCCTR , "      " , "      " );
		            datalines.add(temp);
	            	if(!operand.trim().equalsIgnoreCase(""))
	            	{
		            	if(!SYMTAB.CheckSymExist(operand.trim())){ErrorHandling.setError(LOCCTR, " **** Unlisted label definition");obcode = "      ";}
		            	else
		            	{   
		            		PreLOCCTR = LOCCTR;
		            		LOCCTR = SYMTAB.getSymAddress(operand.trim());
		            	}
		            	
	            	}
	            	else
	            	{
	            		if(PreLOCCTR.trim().equalsIgnoreCase("")){ErrorHandling.setError(LOCCTR, " **** No Previous ORG ");obcode = "      ";}
	            		else
	            		{
		            		LOCCTR = PreLOCCTR;
	            			PreLOCCTR = "";
	            			

	            		}
	            	}
	            	
		            continue;
	            }
	            
	            
	            if(operand.charAt(0) == '=')
	            {
	            	if(!LITTAB.CheckLitExist(operand.trim()))
	            	{
	            		if(operand.trim().equalsIgnoreCase("=*"))LITTAB.SetLiteral("="+LOCCTR);
	            		else if(LITTAB.SetLiteral(operand.trim()))
	            			{
	            			obcode = "      " ;
	    	            	ErrorHandling.setError(LOCCTR, " **** illegal format in operation field");
	            			
	            			}
	            		
	            	}
	            }
	            
	            if(Instruction.trim().equalsIgnoreCase("LTORG")){
	            	
	            	
		            temp = new DataLine("        " , Instruction , "      " , LOCCTR , "      " , "      " );
		            datalines.add(temp);
		            LOCCTR = LitAddresses(LOCCTR , datalines);
		            continue;
	            }
	            
	            if((Instruction.trim()).equalsIgnoreCase("start")){
	            	if(operand.trim().equalsIgnoreCase(""))LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16)+3).toUpperCase();
	            	else LOCCTR = operand.trim(); 	
	            	LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();	
	            	ErrorHandling.setError(LOCCTR, " **** duplicate or misplaced start statement");
	            	obcode = "      ";
	            	}
	            if(label.trim().equalsIgnoreCase(".")){
	            	temp = new DataLine(label , Instruction , operand , "    " , comment , "      " );	            	
	            	continue;}
	            if(!label.trim().equalsIgnoreCase("") && !(Instruction.trim().equalsIgnoreCase("equ")))
	            {
	            	if(SYMTAB.CheckSymExist(label.trim())){ ErrorHandling.setError(LOCCTR, " **** duplicate label definition");obcode = "      ";}
	            	else 
	            	{
	            		SYMTAB.setSymAddress(label.trim(),LOCCTR);
	            	}
                
	            }
	            
	            
	            
	            if(Instruction.trim().equalsIgnoreCase("byte")){
	            	
	            	
	            	String []strings = operand.trim().split("'");	            	
	            	if(strings[0].equalsIgnoreCase("c")){
	            	char []values =strings[1].toCharArray();
	        		for (int i = 0 ; i < values.length ; i++)
	        		{
	        			obcode = (obcode + Integer.toHexString((int)values[i]));	        		    
	        		}
	        		noOfBytes = values.length;
	        		
	              }
	        	    else if (strings[0].equalsIgnoreCase("x")){
	        			obcode = strings[1];
	        			noOfBytes = (int)(((strings[1].length())/2.0)+0.9);
	        	  }
	        	    else {ErrorHandling.setError(LOCCTR, " **** illegal operand in byte statement");obcode = "      ";}
	           
	            }
	            temp = new DataLine(label , Instruction , operand , LOCCTR , comment , obcode );
	            datalines.add(temp);
	            
	            if(Instruction.trim().equalsIgnoreCase("byte")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + noOfBytes).toUpperCase();
	            }
	            else if(Instruction.trim().equalsIgnoreCase("resb")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + Integer.parseInt(operand.trim())).toUpperCase();
	            }
	            else if(Instruction.trim().equalsIgnoreCase("resw")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + (3 * Integer.parseInt(operand.trim()))).toUpperCase();   
	            }
	            else{
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + 3).toUpperCase();
	            }
	            LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();

	            
	            
	        }
	           
	        while(s.hasNext())
	        {
	            ErrorHandling.setError(LOCCTR, " **** statement should not follow end statement");obcode = "      ";

	            obcode = "";
	        	noOfBytes = 1;
		        line = s.nextLine() + "                                                                              ";	            
	            label = line.substring(0,8);
	            Instruction = line.substring(9,15);
	            operand = line.substring(17,35);
	            comment = line.substring(35,66);
	            
	            
	            
	            if((Instruction.trim()).equalsIgnoreCase("EQU")){
	            	
	            	
	            	if(!label.trim().equalsIgnoreCase(""))
		            {
		            	if(SYMTAB.CheckSymExist(label.trim())){ErrorHandling.setError(LOCCTR, " **** duplicate label definition"); obcode = "      ";}
		            	else 
		            	{
		            	
		            		if(operand.trim().equalsIgnoreCase("*"))SYMTAB.setSymAddress(label.trim(),LOCCTR);
		            		else {
		            			String temp2 = CalcSymTerm(operand.trim(),LOCCTR);
		            			
		            			if (temp2 == null)obcode = "      ";
		            			else SYMTAB.setSymAddress(label.trim(),CalcSymTerm(operand.trim(),LOCCTR));		            		}
		            	}
	                
		            }
	            	else {ErrorHandling.setError(LOCCTR, " **** Missing label definition");obcode = "      ";}
	            		
	            	obcode = "      ";
	            }
	            
	            
	            
	            if((Instruction.trim()).equalsIgnoreCase("ORG")){
	            	
	            	temp = new DataLine("        " , Instruction , operand , LOCCTR , "      " , "      " );
		            datalines.add(temp);
	            	if(!operand.trim().equalsIgnoreCase(""))
	            	{
		            	if(!SYMTAB.CheckSymExist(operand.trim())){ErrorHandling.setError(LOCCTR, " **** Unlisted label definition");obcode = "      ";}
		            	else
		            	{   
		            		PreLOCCTR = LOCCTR;
		            		LOCCTR = SYMTAB.getSymAddress(operand.trim());
		            	}
		            	
	            	}
	            	else
	            	{
	            		if(PreLOCCTR.trim().equalsIgnoreCase("")){ErrorHandling.setError(LOCCTR, " **** No Previous ORG ");obcode = "      ";}
	            		else
	            		{
		            		LOCCTR = PreLOCCTR;
	            			PreLOCCTR = "";
	            			

	            		}
	            	}
	            	
		            continue;
	            }
	            
	            
	            if(operand.charAt(0) == '=')
	            {
	            	if(!LITTAB.CheckLitExist(operand.trim()))
	            	{
	            		if(LITTAB.SetLiteral(operand.trim()))
	            			{
	            			obcode = "      " ;
	    	            	ErrorHandling.setError(LOCCTR, " **** illegal format in operation field");
	            			
	            			}
	            		
	            	}
	            }
	            
	            if(Instruction.trim().equalsIgnoreCase("LTORG")){
	            	
	            	
		            temp = new DataLine("        " , Instruction , "      " , LOCCTR , "      " , "      " );
		            datalines.add(temp);
		            LOCCTR = LitAddresses(LOCCTR , datalines);
		            continue;
	            }
	            
	            if((Instruction.trim()).equalsIgnoreCase("start")){
	            	if(operand.trim().equalsIgnoreCase(""))LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16)+3).toUpperCase();
	            	else LOCCTR = operand.trim(); 	
	            	LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();	
	            	ErrorHandling.setError(LOCCTR, " **** duplicate or misplaced start statement");
	            	obcode = "      ";
	            	}
	            if(label.trim().equalsIgnoreCase(".")){
	            	temp = new DataLine(label , Instruction , operand , "    " , comment , "      " );	            	
	            	continue;}
	            if(!label.trim().equalsIgnoreCase("") && !(Instruction.trim().equalsIgnoreCase("equ")))
	            {
	            	if(SYMTAB.CheckSymExist(label.trim())){ ErrorHandling.setError(LOCCTR, " **** duplicate label definition");obcode = "      ";}
	            	else 
	            	{
	            		SYMTAB.setSymAddress(label.trim(),LOCCTR);
	            	}
                
	            }
	            
	            
	            
	            if(Instruction.trim().equalsIgnoreCase("byte")){
	            	
	            	
	            	String []strings = operand.trim().split("'");	            	
	            	if(strings[0].equalsIgnoreCase("c")){
	            	char []values =strings[1].toCharArray();
	        		for (int i = 0 ; i < values.length ; i++)
	        		{
	        			obcode = (obcode + Integer.toHexString((int)values[i]));	        		    
	        		}
	        		noOfBytes = values.length;
	        		
	              }
	        	    else if (strings[0].equalsIgnoreCase("x")){
	        			obcode = strings[1];
	        			noOfBytes = (int)(((strings[1].length())/2.0)+0.9);
	        	  }
	        	    else {ErrorHandling.setError(LOCCTR, " **** illegal operand in byte statement");obcode = "      ";}
	           
	            }
	            temp = new DataLine(label , Instruction , operand , LOCCTR , comment , obcode );
	            datalines.add(temp);
	            
	            if(Instruction.trim().equalsIgnoreCase("byte")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + noOfBytes).toUpperCase();
	            }
	            else if(Instruction.trim().equalsIgnoreCase("resb")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + Integer.parseInt(operand.trim())).toUpperCase();
	            }
	            else if(Instruction.trim().equalsIgnoreCase("resw")){
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + (3 * Integer.parseInt(operand.trim()))).toUpperCase();   
	            }
	            else{
	            	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + 3).toUpperCase();
	            }
	            LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();

	        }
	        


	        LitAddresses(LOCCTR,datalines);
	        
	        
	        s.close();
	  
	    }
	 
	 public String LitAddresses(String LOCCTR , LinkedList<DataLine>  datalines)
	 {
		 int length = 0;
		 DataLine temp = null;
		 DataLine temp2;
		 String label, Instruction, operand, address, comment, obcode;
		 if(datalines.get(datalines.size()-1).getInstruction().trim().equalsIgnoreCase("end"))
		 {
			 temp = datalines.get(datalines.size()-1);
			 LOCCTR =  datalines.get(datalines.size()-1).getAddress();
			 datalines.remove(datalines.size()-1);
		 }
		 for (String Key : LITTAB.LitTable.keySet()) {
	        	if(LITTAB.LitTable.get(Key).getAddress().equalsIgnoreCase(""))
	        	{
		           
		        length = LITTAB.LitTable.get(Key).getLength();            
	        	LITTAB.LitTable.get(Key).setAddress(LOCCTR);
	        	LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) + length).toUpperCase();
	            LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();
	             
	            
	            
	            label = "*       ";
	            Instruction = "byte  ";
	            operand = "x'" + Key + "'";
	            address = LITTAB.LitTable.get(Key).getAddress();
	            comment = "           ";
	           
	           // System.out.println(Key);
	            obcode = Key; //String.format("%06x", Integer.parseInt((String)Key.trim() , 16)).toUpperCase();
	            
	            temp2 = new DataLine(label, Instruction, operand, address, comment, obcode);
	            datalines.add(temp2);
	            
	        	}

			}
		 if(temp != null)
		 {
			 temp.setAddress(LOCCTR);
			datalines.add(temp); 
		 }
		 LOCCTR = Integer.toHexString(Integer.parseInt(LOCCTR.trim() , 16) - length + 3).toUpperCase();
         LOCCTR = String.format("%04x", Integer.parseInt(LOCCTR.trim() , 16)).toUpperCase();
         return LOCCTR;
		 
	        
		 
	 }
	
	 public String CalcTerm(String term)

	 {
		ArrayList<Integer> valuesList = new ArrayList<Integer>();
		int sum = 0;
		
		String[]strings = term.split("\\+");
		for(String value : strings)
		{
			if(!value.equalsIgnoreCase("")){
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
				
		}
		
		for (int i = 0; i < valuesList.size(); i++) {
			sum += valuesList.get(i);
		}
		 
		 return Integer.toHexString(sum);
	 }
	 
	 public String CalcSymTerm(String term , String LOCCTR)

	 {
		String sum = "";
		String temp;
		String[]strings = term.split("\\+");
		for(String value : strings)
		{
			if(value.contains("-")){
				
				String[] strings2 = value.split("-");
				
				try{
					Integer.parseInt(strings2[0] , 10);
					temp = strings2[0];
				
				}catch(NumberFormatException e){
				
	            if(!SYMTAB.CheckSymExist(strings2[0])){ErrorHandling.setError(LOCCTR, " **** Unlisted label definition");return null;}	
				else temp = SYMTAB.getSymAddress(strings2[0]);
					}
				sum += "+" + temp;
				
				for (int i = 1; i < strings2.length; i++) {
					
					try{
						Integer.parseInt(strings2[i] , 10);
						temp = strings2[i];
					
					}catch(NumberFormatException e){
			            if(!SYMTAB.CheckSymExist(strings2[i])){ErrorHandling.setError(LOCCTR, " **** Unlisted label definition");return null;}		
					else temp = SYMTAB.getSymAddress(strings2[i]);}
					
					sum += "-" + temp;
				}
			}
			else{
				
				try{
					Integer.parseInt(value , 10);
					temp = value;
				
				}catch(NumberFormatException e){
		            if(!SYMTAB.CheckSymExist(value)){ErrorHandling.setError(LOCCTR, " **** Unlisted label definition");return null;}						
				else temp = SYMTAB.getSymAddress(value);}
				
				sum += "+" + temp;

			}
				
		}
		
		 return CalcTerm(sum);
	 }
	 
	 
	 public static void main(String[] args) throws IOException
		{
		    LinkedList<DataLine> x = new  LinkedList<DataLine>();
		    new Pass1(x);
		    new Pass2(x);
		    for (int i = 0 ; i < x.size() ; i++)
			{
				System.out.println(x.get(i).getAddress()+" "+x.get(i).getObcode()+x.get(i).getLabel()+x.get(i).getInstruction()+x.get(i).getOperand());
			}
			//System.out.println(SYMTAB.SymTable.values());
			//System.out.println(ErrorHandling.Errorhandling.values());
			
			

		for(String Key : LITTAB.LitTable.keySet())
		{	
			System.out.println("HexValue: " + Key); 
			System.out.println("Length: " + LITTAB.LitTable.get(Key).Length); 
			System.out.println("Address: " + LITTAB.LitTable.get(Key).Address); 

			System.out.println(); 
		}
		
		for(String Key : SYMTAB.SymTable.keySet())
		{	
			System.out.println("Symbol: " + Key); 
			System.out.println("Address: " + SYMTAB.getSymAddress(Key)); 

			System.out.println(); 
		}
			
		
		
		}

	    

}
