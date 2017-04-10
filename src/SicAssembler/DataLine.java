package SicAssembler;

public class DataLine {
	
	private String label;
	private String Instruction;
	private String operand;
	private String address;
	private String comment;
	private String obcode;
	
	public DataLine(String label , String Instruction , String operand , String address , String comment , String obcode)
	{
		this.label = label;
		this.Instruction = Instruction;
		this.operand = operand;
		this.address = address;
		this.comment = comment;		
		this.obcode = obcode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInstruction() {
		return Instruction;
	}

	public void setInstruction(String instruction) {
		Instruction = instruction;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getObcode() {
		return obcode;
	}

	public void setObcode(String obcode) {
		this.obcode = obcode;
	}
	
	

}
