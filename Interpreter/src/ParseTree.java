import java.util.ArrayList;

// Ett syntaxtrad
abstract class ParseTree {
	protected TokenType token;
	abstract public String process();
}

class Leaf extends ParseTree{
	private String data;
	
	public Leaf(TokenType token, String data) {
		this.token = token;
		this.data = data;
	}

	@Override
	public String process() {
		return this.data;
	}
}

class Branch extends ParseTree{
	
	private ArrayList<ParseTree> children;
	
	public Branch(TokenType operation) {
		this.token = operation;
		this.children = new ArrayList<ParseTree>();
	}
	
	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addChild(ParseTree pt) {
		children.add(pt);
	}

	public TokenType getOperation() {
		return this.token;
	}

	public void setOperation(TokenType token) {
		this.token = token;
	}
}