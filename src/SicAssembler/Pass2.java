package SicAssembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Pass2 {
	
	File f;
	private String size;
	public Pass2(LinkedList<DataLine> datalines) throws IOException
	{
		calcObcode(datalines);
		writeLisFile(datalines);
		writeOBJFILE(datalines);
		
	}

	public void calcObcode(LinkedList<DataLine> datalines)
	{
		String obcode=null;
		String operandAddress=null;
		boolean flag;
		boolean flag2;
		OPTAB.intiOPTAB();
		if(!(datalines.get(0).getInstruction().trim().equalsIgnoreCase("start"))){ErrorHandling.setError(datalines.get(0).getAddress(), " **** missing or misplaced start statement"); datalines.get(0).setObcode("      "); }
		for (int i = 0 ; i < datalines.size() ; i++)
		{

			flag2 = false;
			obcode = OPTAB.getopcode(datalines.get(i).getInstruction().trim()) ;
			
			
			if(datalines.get(i).getOperand().charAt(0) == '=')
			{
				if(LITTAB.CheckLitExist(datalines.get(i).getOperand().trim()))

				{
					operandAddress = LITTAB.getLitAddress(datalines.get(i).getOperand().trim());
					datalines.get(i).setObcode(obcode+operandAddress);
					obcode = null;
					continue;
					
				}
				continue;
	
					
			}
			
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("LTORG"))continue;
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("ORG"))continue;
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("EQU"))continue;


			
			if (datalines.get(i).getInstruction().equalsIgnoreCase("LTORG")){datalines.get(i).setObcode("      ");continue;}
			
			if(datalines.get(i).getLabel().contains("\t") || datalines.get(i).getInstruction().contains("\t") || datalines.get(i).getOperand().contains("\t"))
			{
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal format in label field");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing operation code");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal format in operation field");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** unrecognized operation code");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing or misplaced operand in instruction");
				continue;
			}
			
			if(datalines.get(i).getOperand().trim().equalsIgnoreCase("") && datalines.get(i).getInstruction().trim().equalsIgnoreCase("start")){ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing or misplaced operand in instruction"); datalines.get(i).setObcode("      "); continue;}
			if(datalines.get(i).getOperand().trim().equalsIgnoreCase("") || datalines.get(i).getOperand().charAt(0) == ' '){ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing or misplaced operand in instruction"); datalines.get(i).setObcode(obcode+"0000"); flag2 = true;}
			if(!datalines.get(i).getLabel().trim().equalsIgnoreCase("") && (datalines.get(i).getLabel().charAt(0) == ' ' ||( (int)datalines.get(i).getLabel().charAt(0) >= 48 && (int)datalines.get(i).getLabel().charAt(0) <= 57 ))){ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal format in label field"); datalines.get(i).setObcode("      "); flag2 = true;}
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("")){ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing operation code"); datalines.get(i).setObcode("      "); flag2 = true;}
			if(!datalines.get(i).getInstruction().trim().equalsIgnoreCase("") && datalines.get(i).getInstruction().charAt(0) == ' ')
			{
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** missing operation code");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal format in operation field");
				ErrorHandling.setError(datalines.get(i).getAddress(), " **** unrecognized operation code");
				datalines.get(i).setObcode("      "); continue;
			}
			
			if(obcode == null && !(datalines.get(i).getInstruction().trim().equalsIgnoreCase("start") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("word") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("byte") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resb") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resw") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("end")))
			{ ErrorHandling.setError(datalines.get(i).getAddress(), " **** unrecognized operation code"); datalines.get(i).setObcode("      ");flag2 = true;}

			
			
			if(obcode == null && !(datalines.get(i).getInstruction().trim().equalsIgnoreCase("start") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("word") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("byte") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resb") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resw") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("end")))
			{
			try{
				Integer.parseInt(datalines.get(i).getOperand().trim(), 16);
				

				}catch(NumberFormatException e)
				{
					if (datalines.get(i).getOperand().trim().contains(",")){
						String []temp = datalines.get(i).getOperand().split(",");
						if(SYMTAB.CheckSymExist(temp[0]));
						else {ErrorHandling.setError(datalines.get(i).getAddress(), " **** undefined symbol in operand");datalines.get(i).setObcode("      ");flag2 = true;}
					    if(!temp[1].equalsIgnoreCase("x") || temp[1].equals(null) || !temp[2].equals(null)){ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal operand field");datalines.get(i).setObcode("      ");flag2 = true;}
					}
					else if(SYMTAB.CheckSymExist(datalines.get(i).getOperand().trim()));
					else { ErrorHandling.setError(datalines.get(i).getAddress(), " **** undefined symbol in operand"); datalines.get(i).setObcode("      ");flag2 = true;}
				    
					
				}
			}
			
			
			
			if (flag2)continue;
			
	
			
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("start") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resb") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("resw") || datalines.get(i).getInstruction().trim().equalsIgnoreCase("end"))
		    {datalines.get(i).setObcode("      "); continue;}
			
			try{
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("word"))
			{datalines.get(i).setObcode(String.format("%06x" , Integer.parseInt( (datalines.get(i).getOperand().trim()))).toUpperCase()); continue;}
			}catch(NumberFormatException e)
			{
				if(SYMTAB.CheckSymExist(datalines.get(i).getOperand().trim()))
				{
					datalines.get(i).setObcode(String.format("%06x", Integer.parseInt(SYMTAB.getSymAddress(datalines.get(i).getOperand().trim()).trim() , 16)).toUpperCase());
					continue;
				}
				else {ErrorHandling.setError(datalines.get(i).getAddress(), " **** undefined symbol in operand");datalines.get(i).setObcode("      ");continue;} 
			}
			
			if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("byte"))
			{
				continue;
				}		    
			
		    flag= true;
			try{
			Integer.parseInt(datalines.get(i).getOperand().trim(), 10);

			}catch(NumberFormatException e)
			{
				flag = false;
				
			}
			try{if(datalines.get(i).getOperand().trim().charAt(0) == '0')flag = true;}catch(StringIndexOutOfBoundsException e){}
			if(flag)
			{
			    operandAddress = datalines.get(i).getOperand();
			}
				
			else
			{
				if (datalines.get(i).getOperand().trim().contains(",")){
					String []temp = datalines.get(i).getOperand().split(",");
					if(SYMTAB.CheckSymExist(temp[0])) 
					operandAddress = Integer.toHexString(Integer.parseInt(SYMTAB.getSymAddress(temp[0].trim()) , 16) + 32768).toUpperCase();
					else {ErrorHandling.setError(datalines.get(i).getAddress(), " **** undefined symbol in operand");datalines.get(i).setObcode("      ");continue;}
				    if(!temp[1].equalsIgnoreCase("x") || temp[1].equals(null) || !temp[2].equals(null)){ErrorHandling.setError(datalines.get(i).getAddress(), " **** illegal operand field");datalines.get(i).setObcode("      ");continue;}
				}
				else if(SYMTAB.CheckSymExist(datalines.get(i).getOperand().trim())){operandAddress = SYMTAB.getSymAddress(datalines.get(i).getOperand().trim());}
				else { ErrorHandling.setError(datalines.get(i).getAddress(), " **** undefined symbol in operand"); datalines.get(i).setObcode("      ");continue;}
			    
			}
			if(datalines.get(i).getLabel().trim().equalsIgnoreCase("."))continue;
			
			datalines.get(i).setObcode(obcode+operandAddress);
			obcode = null;
			
			
		}

		
	}
	
	public void writeLisFile (LinkedList<DataLine> datalines) throws IOException
    {
 
        f = new File("LISFILE");
        BufferedWriter infoToWrite = new BufferedWriter(new FileWriter(f));     
        
        String line;
        String LOCCTR = null;
        String obcode;        
        String label;
        String Instruction;
        String operand;
        String comment;

        
        
        infoToWrite.write("SIC Assembler V1.2");
        infoToWrite.newLine();
        infoToWrite.newLine();

        
        for(int i = 0 ; i < datalines.size() ; i++  ) {  
             
        	LOCCTR = datalines.get(i).getAddress();
        	obcode = datalines.get(i).getObcode();
        	label = datalines.get(i).getLabel();
        	Instruction = datalines.get(i).getInstruction();
        	operand = datalines.get(i).getOperand();
        	comment = datalines.get(i).getComment();
        	      	
            
            
            
            
            
		   if(Instruction.trim().equalsIgnoreCase("word"))

		   {
			   try{
			obcode = String.format("%06x", Integer.parseInt(obcode)).toUpperCase();
  
		   }catch(NumberFormatException e){}
		   }
		   if(Instruction.trim().equalsIgnoreCase("byte"))
		   {
			   if(obcode.length() > 6)
			   {
			   ArrayList<String> strings = new ArrayList<String>();
				int index = 0;
				while (index < obcode.length()) {
				    strings.add(obcode.substring(index, Math.min(index + 6,obcode.length())));
				    index += 6;
				}
				obcode = strings.get(0);
				line = LOCCTR + " " + obcode + " " + label + " " + Instruction + "  " + operand + comment;
	        	infoToWrite.write(line);
	        	infoToWrite.newLine();
				for(int z = 1 ; z < strings.size() ; z++)
				{
					infoToWrite.write("     " + strings.get(z));
		            infoToWrite.newLine();
				}
				if(ErrorHandling.Errorhandling.containsKey(LOCCTR.trim()))
	            {
	            	String Error;
	            	for(int j = 0 ; j < ErrorHandling.getError(LOCCTR.trim()).size() ; j++)
	            	{
	            	  Error = ErrorHandling.getError(LOCCTR.trim()).get(j);
	            	  infoToWrite.write(Error);
	                  infoToWrite.newLine();

	                }

	            }
				continue;
		       }
			   else if(obcode.length() < 6)
			   {
				   obcode = String.format("%06x", Integer.parseInt(obcode.trim() , 16)).toUpperCase();
				
			   }
			   
		   }
		   
            
            line = LOCCTR + " " + obcode + " " + label + " " + Instruction + "  " + operand + comment;
        	infoToWrite.write(line);
            infoToWrite.newLine();
            if(ErrorHandling.Errorhandling.containsKey(LOCCTR.trim()))
            {
            	String Error;
            	for(int j = 0 ; j < ErrorHandling.getError(LOCCTR.trim()).size() ; j++)
            	{
            	  Error = ErrorHandling.getError(LOCCTR.trim()).get(j);
            	  if(Instruction.trim().equalsIgnoreCase("start"))continue;
            	  if(Error.equalsIgnoreCase(" **** No Previous ORG ")  && !Instruction.trim().equalsIgnoreCase("org"))continue;
            	  infoToWrite.write(Error);
                  infoToWrite.newLine();

                }

            }
            
        }
            
            
        infoToWrite.close();
        this.size = Integer.toHexString(Integer.parseInt(LOCCTR , 16) - Integer.parseInt(datalines.get(0).getAddress() , 16)).toUpperCase();
    }
	
	public void writeOBJFILE (LinkedList<DataLine> datalines) throws IOException
    {
 
        f = new File("OBJFILE");
        BufferedWriter infoToWrite = new BufferedWriter(new FileWriter(f));
        infoToWrite.write("H");
        String name = datalines.get(0).getLabel().trim();
        while(name.length() < 6)
        {
        	name = name + " ";       	
        }
        infoToWrite.write(name);
        infoToWrite.write("00"+datalines.get(0).getAddress());
        infoToWrite.write(this.size);
        infoToWrite.newLine();
        String line="";
        String address = datalines.get(0).getAddress();
        
        
        for (int i = 1 ; i < datalines.size()-1 ; i++)
		{
        	if(datalines.get(i).getObcode().trim().equals(null))continue;
        	if(line.length() > 54){ line = line + "            "; i--;}
        	else line = line + datalines.get(i).getObcode().trim();
        	if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("resw")){
        		for(int b = 0 ; b < Integer.parseInt(datalines.get(i).getOperand().trim())*6 ; b++){
        			line = line +" ";
        		}
        		if(line.length() > 54) line = line + "            ";
        		}
        	if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("resb")){
        		for(int b = 0 ; b < (Integer.parseInt(datalines.get(i).getOperand().trim())*2) ; b++){
        			line = line +" ";
        		}
        		if(line.length() > 54) line = line + "            ";
        		
        	}
        	if(datalines.get(i).getInstruction().trim().equalsIgnoreCase("org"))line = line + "                                                          ";;

        	if(line.length() >= 60)
        	{
        		
        		String []strings = line.split(" ");
        		try{line = strings[0];	}catch(ArrayIndexOutOfBoundsException e){}
        		for(int l = 1 ; l < strings.length ; l++)
        		{
        			line = line + strings[l];
        		}
        		String no = Integer.toHexString(line.length()/2).toUpperCase(); 
        		if(line.length()/2 < 16) no = "0"+no;
        		infoToWrite.write("T" + "00" + address + no + line );
        		address = datalines.get(i+1).getAddress();
        		infoToWrite.newLine();
        		line = "";
        		no = "";
        		
        	}
        	
		}
        
        if(!line.trim().equalsIgnoreCase(""))
        {
           
    		String []strings = line.split(" ");
    		line = strings[0];
    		for(int l = 1 ; l < strings.length ; l++)
    		{
    			line = line + strings[l];
    		}
    		String no = Integer.toHexString(line.length()/2).toUpperCase();
    		if(line.length()/2 < 16) no = "0" + no;
    		String substring;
    		if(line.length() == 2) substring = line.substring(0, 2);
    		else if(line.length() == 2) substring = line.substring(0, 4);
    		else  substring = line.substring(0, 6);
    		int yu=0;
    		for(int l = 1 ; l < datalines.size()-1 ; l++)
    		{
    			if(substring.equalsIgnoreCase(datalines.get(l).getObcode().trim()))
    				{yu = l;break;}
    		}
    		address = datalines.get(yu).getAddress();
    		infoToWrite.write("T" + "00" + address + no + line );
    		infoToWrite.newLine();
        }
        
        infoToWrite.write("E");
        infoToWrite.write("00"+datalines.get(0).getAddress());
        infoToWrite.close();

        //System.out.println("00"+datalines.get(0).getAddress());

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
					
		System.out.println(SYMTAB.SymTable.values()+"  " + SYMTAB.getSymAddress("c"));
		System.out.println(ErrorHandling.Errorhandling.values());
	  for (int i = 0 ; i < x.size() ; i++)
		{
			if(ErrorHandling.Errorhandling.containsKey(x.get(i).getAddress().trim()))
			{
				String Error;
				System.out.println(x.get(i).getAddress().trim() + " " );
				for(int j = 0 ; j < ErrorHandling.getError(x.get(i).getAddress().trim()).size() ; j++)
            	{
            	  Error = ErrorHandling.getError(x.get(i).getAddress().trim()).get(j);
          		  System.out.println(Error);

                }
			}

			
		}
		
	}

	 
	
}

