
import java.util.ArrayList;
import java.util.Iterator;

public class Parser {
	private ArrayList<Token> tokens;
	Iterator<Token> itr;
	
	private ParseTree syntaxTree;
	
	public String color;
	public boolean penDown;
	public int angle;

	public Parser(ArrayList<Token> tokens) throws SyntaxError {
		this.tokens = tokens;
		this.itr = tokens.iterator();
		this.color = "#0000FF"; // blue color
		this.angle = 0;
		//this.syntaxTree = new Branch(null); // the root node is a branch and type null
		this.syntaxTree = this.statement();
	}

	public ParseTree statement() throws SyntaxError {
		Token t = null;
		
		while (itr.hasNext()){
			t = itr.next();
			if (t.getType() == TokenType.forw ||
					t.getType() == TokenType.back ||
					t.getType() == TokenType.left ||
					t.getType() == TokenType.right ||
					t.getType() == TokenType.color) {
				return new Branch(t.getType(), this.parameter(), this.statement());
			} else if (t.getType() == TokenType.up ||
						t.getType() == TokenType.down){
				return new Branch(t.getType(), this.cmdend(), this.statement());
			} else if (t.getType() == TokenType.quote) {  // this last one will casuse our loops to exit by adding a new leaf "quote"
				return new Leaf(t.getType(), t.getData());
			}else {
				throw new SyntaxError();
			}
		}
		return null;
	}
		
	public ParseTree parameter() throws SyntaxError {
		Token t = null;
		
		while (itr.hasNext()){
			t = itr.next();
			if (t.getType() == TokenType.number ||
					t.getType() == TokenType.hex) {
				return new Branch(t.getType(), this.cmdend(), new Leaf(t.getType(), t.getData()));
			} else {
				throw new SyntaxError();
			}
		}
		return null;
	}

	public ParseTree cmdend() throws SyntaxError {
		Token t = null;
		
		while (itr.hasNext()){
			t = itr.next();
			if (t.getType() == TokenType.dot) {
				return new Leaf(t.getType(), "dot");
			}else {
				throw new SyntaxError();
			}
		}
		return null;
	}
	
	public ParseTree block() throws SyntaxError {
		Token t = null;
		
		while (itr.hasNext()){
			t = itr.next();
			if (t.getType() == TokenType.quote) {
				return new Branch(t.getType(), this.statement(), null);
			}else {
				throw new SyntaxError();
			}
		}
		return null;
	}

	/**
	public Branch parse(Branch currentNode) throws SyntaxError {
		
		Token currentToken = null;
		Token tmpToken = null;
		Branch tmpBranch = null;
		
				
		while (itr.hasNext()) {
			currentToken = itr.next();
			
			switch (currentToken.getType()) {
			// we're adding leafs here which have the same syntax
			case down:
			case up:
				if (!itr.hasNext() || itr.next().getType() != TokenType.dot)
					throw new SyntaxError();

				currentNode.setLeft(new Leaf(currentToken.getType(), currentToken.getData()));
				break;

			// This is all branches of the same type: statement data .
			case color:
				// check next element for propper syntax
				if (itr.hasNext()) {
					tmpToken = itr.next();
				} else
					throw new SyntaxError();
				
				// check propper command end
				if (!itr.hasNext() || itr.next().getType() != TokenType.dot)
					throw new SyntaxError();
				
				// ToDo: check valid number here!!
				if (tmpToken.getType() != TokenType.hex)
					throw new SyntaxError();
				//check data if correct color code
				
				tmpBranch = new Branch(currentToken.getType());
				tmpBranch.setLeft(new Leaf(tmpToken.getType(), tmpToken.getData()));
				tmpBranch.setLeft(new Leaf(tmpToken.getType(), tmpToken.getData()));
				rootNode.addChild(tmpBranch);
				break;
				
			case left:
			case right:
				// check next element for propper syntax
				if (itr.hasNext()) {
					tmpToken = itr.next();
				} else
					throw new SyntaxError();
				
				// check propper command end
				if (!itr.hasNext() || itr.next().getType() != TokenType.dot)
					throw new SyntaxError();
				
				// ToDo: check valid number here!!
				if (tmpToken.getType() != TokenType.number)
					throw new SyntaxError();
				//check data if angel
				
				tmpBranch = new Branch(currentToken.getType());
				tmpBranch.addChild(new Leaf(tmpToken.getType(), tmpToken.getData()));
				rootNode.addChild(tmpBranch);
				break;
				
			case forw:
			case back:
				// check next element for propper syntax
				if (itr.hasNext()) {
					tmpToken = itr.next();
				} else
					throw new SyntaxError();
				
				// check propper command end
				if (!itr.hasNext() || itr.next().getType() != TokenType.dot)
					throw new SyntaxError();
				
				// ToDo: check valid number here!!
				if (tmpToken.getType() != TokenType.number)
					throw new SyntaxError();
				//check data if range correct etc
				
				tmpBranch = new Branch(currentToken.getType());
				tmpBranch.addChild(new Leaf(tmpToken.getType(), tmpToken.getData()));
				rootNode.addChild(tmpBranch);
				break;
				
			case rep:
				
				/** TODO!!
				if (itr.next().getType() == TokenType.number) {
					
				} else
					throw new SyntaxError();
				
				if (itr.next().getType() == TokenType.quote) {
					
				} else
					throw new SyntaxError();
				
				// check for commands
				
				if (itr.next().getType() == TokenType.quote) {
					
				} else
					throw new SyntaxError();
				
				break;
				
				
			case quote:
				// here should the actual part of the tree be returned
				// we'll work recursive so we can assume that if we find a
				// quote, this is an ending quote, not another nested loop
				return rootNode; 
				
			default:
				throw new SyntaxError(); 
			}
		}
		return null;
	}
	**/
}