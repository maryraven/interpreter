import java.util.Iterator;

public class Interpretor {
	private Branch parseTree;
	private Status state;
	
	public Interpretor(Branch pt){
		this.parseTree = pt;
		state = new Status(0, 0, "#0000ff", true);
		this.state = this.evaluate(this.parseTree, this.state);
	}
	
	public Status evaluate(Branch pt, Status state){
		Iterator<ParseTree> itr = pt.iterator();
		ParseTree current = null;
		
		while(itr.hasNext()) {
			current = itr.next();
			
			// if we hit a branch then call evaluate recursively to evaluate the subtree
			if(current instanceof Branch) {
				state = this.evaluate((Branch)current, state);
			}
		}
		
		return state;
	}
}
