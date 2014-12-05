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
	
	private ParseTree left;
	private ParseTree right;
	
	public Branch(TokenType operation, ParseTree left, ParseTree right) {
		this.token = operation;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public TokenType getToken() {
		return this.token;
	}

	public void setToken(TokenType token) {
		this.token = token;
	}

	public ParseTree getLeft() {
		return left;
	}

	public void setLeft(ParseTree left) {
		this.left = left;
	}

	public ParseTree getRight() {
		return right;
	}

	public void setRight(ParseTree right) {
		this.right = right;
	}
}