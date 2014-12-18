import java.util.Iterator;

public class Interpretor {
	private Branch parseTree;
	private Status state;
	
	public Interpretor(Branch pt) throws SyntaxError{
		this.parseTree = pt;
		state = new Status(0, 0, "#0000ff", false, 0);
		this.state = this.evaluate(this.parseTree, this.state);
	}
	
	public Status calc(Status start, int distance){
		Status newState = new Status(start);
		//for math-formel pa startkoordinaten och angle	
		newState.x = start.x + distance*Math.cos((Math.PI * start.orientation)/180);
		newState.y = start.y + distance*Math.sin((Math.PI * start.orientation)/180);
		
		return newState;
	}
	
	public Status evaluate(Branch pt, Status state) throws SyntaxError{
		Iterator<ParseTree> itr = pt.iterator();
		ParseTree current = null;
		// copy for the old status to calculate propperly
		Status oldState = new Status(state);
		Status newState = new Status(state);
		
		while(itr.hasNext()) {
			current = itr.next();
					
			switch(current.token) {
			case up:
				newState.isDown = false;
				break;
			case down:
				newState.isDown = true;
				break;
			case color:
				newState.color = (current).getChild(0).getData();
				break;
			case forw:
				newState = this.calc(newState, Integer.parseInt(current.getChild(0).getData()));
				break;
			case back:
				newState = this.calc(newState, - Integer.parseInt(current.getChild(0).getData()));
				break;
			case left:
				newState.orientation += Integer.parseInt(current.getChild(0).getData()) % 360;
				if(newState.orientation > 360)
					newState.orientation = newState.orientation % 360;
				break;
			case right:
				newState.orientation -= Integer.parseInt(current.getChild(0).getData()) % 360;
				if(newState.orientation < 0)
					newState.orientation = 360 - Math.abs(newState.orientation);
				else if(newState.orientation > 360)
					newState.orientation = newState.orientation % 360;
				break;
				
			case rep:
				// recursively work on loop block
				for (int i = 0; i < Integer.parseInt(current.getChild(0).getData()); i++){
					newState = this.evaluate((Branch)current.getChild(1), newState);
					// reset old state to avoid wrong printouts after the recursion
					oldState.clone(newState);
				}
				break;
				
			default:
				throw new SyntaxError();
			}
			
			
			// check if we need to print something
			if(newState.isDown && ( ( Double.compare(oldState.x, newState.x) != 0) || ( Double.compare(oldState.y, newState.y) != 0 ) ) ){
					System.out.format("%s %.4f %.4f %.4f %.4f%n", oldState.color.toUpperCase(), oldState.x, oldState.y, newState.x, newState.y);
			}
			
			oldState.clone(newState);
		}
		return newState;
	}
}
