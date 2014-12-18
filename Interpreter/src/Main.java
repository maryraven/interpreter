import java.io.FileInputStream;



/**
 * Exempel p rekursiv medkning: utvrdera aritmetiska uttryck med
 * heltal, +, -, *, och /
 *
 * Expr -> Term { PM Term }
 * Term -> Factor { MD Factor }
 * Factor -> NUM | LPAREN Expr RPAREN
 *
 * PM r `+' eller `-', MD r `*' eller '/'
 *
 * Parsar uttrycket och skriver sedan ut vrdet av det.
 *
 * Provkrning frn terminal p fil "test.in"
 *
 * javac *.java
 * java Main < test.in
 *
 */
public class Main {
	public static void main(String args[]) throws java.io.IOException, SyntaxError {
		Parser parser = new Parser(new FileInputStream("F:\\input.in"));
		Interpretor interpreteTHIS = new Interpretor(parser.getSyntaxTree());
		//System.out.println(result.evaluate());
	}
}
