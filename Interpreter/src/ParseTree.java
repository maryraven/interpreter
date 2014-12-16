import java.util.ArrayList;
import java.util.Iterator;

// Ett syntaxtrad
abstract class ParseTree {
	protected TokenType token;
	public abstract String getData();
	public abstract ParseTree getChild(int index);
}

class Leaf extends ParseTree{
	private String data;
	
	public Leaf(TokenType token, String data) {
		this.token = token;
		this.data = data;
	}
	
	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public ParseTree getChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}

class Branch extends ParseTree implements Iterable{
	private ArrayList<ParseTree> children;
	
	public Branch(TokenType operation) {
		this.token = operation;
		this.children = new ArrayList<ParseTree>();
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
	
	public boolean hasChildren() {
		return (children.size() > 0);
	}
	
	public ParseTree getChild(int index) {
		return children.get(index);
	}

	@Override
	public Iterator<ParseTree> iterator() {
		return children.iterator();
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}
}