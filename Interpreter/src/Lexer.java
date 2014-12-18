import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lexer {
	private ArrayList<Token> tokens;
	private Iterator<Token> itr;
	private Token currentToken;
	/**
	 * 
	 * Reads in the file i think
	 * 
	 * @throws java.io.IOException
	 */
	private static String readInput(InputStream f) throws java.io.IOException {
		Reader stdin = new InputStreamReader(f);
		StringBuilder buf = new StringBuilder();
		char input[] = new char[1024];
		int read = 0;
		while ((read = stdin.read(input)) != -1) {
			buf.append(input, 0, read);
		}
		return buf.toString();
	}

	/**
	 * Konstruktorn som läser in
	 */
	public Lexer(InputStream in) throws java.io.IOException, SyntaxError {
		// This will contain the resulting tokens
		this.tokens = new ArrayList<Token>();
		this.lex(in);
		
		// prepare iterator and internal cache
		this.itr = tokens.iterator();
		if (itr.hasNext())
			this.currentToken = itr.next();
		else this.currentToken = null;
	}
	
	public boolean hasNext() {
		return (this.currentToken != null);
	}
	
	// moves the iterator and returns the old Token (like a normal iterator)
	public Token next(){
		Token tmp = this.currentToken;
		if (itr.hasNext())
			this.currentToken = itr.next();
		else
			this.currentToken = null;
		return tmp;
	}
	
	// returns token without moving the iterator
	public Token peek() throws NoSuchElementException {
		if (this.currentToken != null)
			return this.currentToken;
		else
			throw new NoSuchElementException();
	}
	
	private void lex(InputStream in) throws java.io.IOException, SyntaxError {
		String[] splitline = null;
		
		// match patterns
		Pattern keywordsPattern = Pattern.compile("forw|back|left|right|down|up|color|rep", Pattern.CASE_INSENSITIVE);
		//Pattern hexPattern = Pattern.compile("#[0-9a-f]{6}", Pattern.CASE_INSENSITIVE);
		
		String input = Lexer.readInput(in);

		// split string into array of lines, think about unix/windows line endings
		String[] lines = input.split("\\r?\\n");
		
		for (int index = 0; index < lines.length; index++){
			// ignore all lines which start with "[[:blank:]]?%" as a comment
			if ( lines[index].matches("^\\s*%.*$"))
				continue;
			
			// add blanks around all dots/quotes to be able to split by blanks
			lines[index] = lines[index].replace(".", " . ");
			lines[index] = lines[index].replace("\"", " \" ");
			// split it into string tokens, multiple whitespaces are squished to field delimiters
			splitline = lines[index].split("\\s+");
			for (int j = 0; j < splitline.length; j++) {
				
				// parse tokens and create Token objects in resulting ArrayList "tokens"
				
				// check if we found valid tokens, otherwise throw exception
				Matcher keymatcher = keywordsPattern.matcher(splitline[j]);
				//Matcher hexmatcher = hexPattern.matcher(splitline[j]);
				if (keymatcher.matches())
					this.tokens.add(new Token(TokenType.valueOf(splitline[j].toLowerCase()), splitline[j].toLowerCase(), index+1));
				else if (splitline[j].matches("\\d+"))
					this.tokens.add(new Token(TokenType.number, splitline[j], index+1));
				else if (splitline[j].matches("(?i:#[0-9a-f]{6})")) // case insensitive match "(?i:<pattern>)"
					this.tokens.add(new Token(TokenType.hex, splitline[j].toLowerCase(), index+1));
				else if (splitline[j].equals("."))
					this.tokens.add(new Token(TokenType.dot, "dot", index+1));
				else if (splitline[j].equals("\""))
					this.tokens.add(new Token(TokenType.quote, "quote", index+1));
				else {
					System.out.println("Invalid token '" + splitline[j] + "' in line " + (index+1) + " token " + j);
					throw new SyntaxError();
				}
			}
		}
	}
	
	public ArrayList<Token> getTokenList() {
		return this.tokens;
	}
}
