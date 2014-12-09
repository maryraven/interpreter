
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Parser {
	private Lexer lex;
	private Branch syntaxTree;

	public Parser(FileInputStream in) throws SyntaxError, IOException {
		this.lex = new Lexer(in);
		//this.syntaxTree = new Branch(null); // the root node is a branch and type null
		this.syntaxTree = this.parse(new Branch(null));
	}
	
	public Branch parse(Branch root) throws SyntaxError {
		return this.parse(root, 0);
	}

	// for some cases we need exact one more full statement. This is why we have numCmds. The overloaded version is for easy use
	public Branch parse(Branch root, int numCmds) throws SyntaxError {
		Token t = null;
		Branch newBranch = null;
		boolean doBreak = false;
		int cmds =  0;
		
		
		while (lex.hasNext() && !doBreak){
			// it was specified to get a certain amount of statements. 
			if (numCmds != 0) {
				// if we reach this number we break the loop and return
				if ( cmds < numCmds)
					cmds++;
				else
					break;
			}

			t = lex.next();
			
			// ToDo: change this to a switch statement. The if looks ugly as hell
			switch (t.getType()) {
				case forw:
				case back:
				case left:
				case right:
					newBranch = new Branch(t.getType());
					newBranch.addChild(this.makeLeaf(TokenType.number));
					if (this.hasDot())
						root.addChild(newBranch);
					break;
				
				case color:
					newBranch = new Branch(t.getType());
					newBranch.addChild(this.makeLeaf(TokenType.hex));
					if (this.hasDot())
						root.addChild(newBranch);
					break;
				
				case up:
				case down:
					if (this.hasDot())
						root.addChild(new Leaf(t.getType(), null));
					break;
					
				case rep:  // this last one will cause our loops to exit by adding a new leaf "quote"
					newBranch = new Branch(t.getType());
					newBranch.addChild(this.makeLeaf(TokenType.number));
					newBranch.addChild(this.loopBody());
					root.addChild(newBranch);
					break;
					
				case quote:
					doBreak = true;
					break;  // don't add anything, just return the changed root
					// ToDo: check if this breaks if quote is the last token!
				default:
					throw new SyntaxError();
			}
		}
		return root;
	}
		
	public ParseTree makeLeaf(TokenType datatype) throws SyntaxError {
		Token t = null;
		
		if (lex.hasNext()){
			t = lex.next();
			if (t.getType() == datatype) {
				return new Leaf(t.getType(), t.getData());
			} else {
				throw new SyntaxError();
			}
		} else
			throw new SyntaxError();
	}

	public boolean hasDot() throws SyntaxError {
		Token t = null;
		
		if (lex.hasNext()) {
			if (lex.next().getType() == TokenType.dot) {
				return true;
			}else {
				throw new SyntaxError();
			}
		} else
			throw new SyntaxError();
	}
	
	public ParseTree loopBody() throws SyntaxError {
		Branch body = new Branch(TokenType.loopbody);
		
		if (lex.hasNext()) {
			if (lex.peek().getType() == TokenType.quote){
				// next token is a quote. we peeked on it so we can ignore it
				lex.next();
				return this.parse(body);
			}else {
				// just get one statement, we#re in a loop with only one statement
				return this.parse(body, 1);
			}
		} else
			throw new SyntaxError();
	}
}