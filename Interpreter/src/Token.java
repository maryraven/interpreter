enum TokenType {
	forw, back, up, down, right, left, rep, color, dot, quote, data, number, hex, loopbody;
};

// Klass for att representera en token
// I praktiken vill man nog aven spara info om vilken rad/position i
// indata som varje token kommer ifran, for att kunna ge battre
// felmeddelanden
public class Token {
	
	private TokenType type = null;
	private String data = null;
	private int lineNumber = 0;
	
	/**
	 * Konstuktor for Forw Back Left Right
	 */
	public Token(TokenType type, String data, int lineNumber) {
		this.type = type;
		this.data = data;
		this.lineNumber = lineNumber;
	}

	public TokenType getType() {
		return type;
	}

	public String getData() {
		return data;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
}
