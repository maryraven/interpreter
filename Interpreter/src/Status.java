public class Status {	
	public String color;
	public double x;
	public double y;
	public int orientation;  // 0-360 degrees
	boolean isDown;
	
	public Status(Status original){
		this.clone(original);
	}
	
	public Status(double x, double y, String color, boolean isDown, int orientation) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.isDown = isDown;
		this.orientation = orientation;
	}
	
	public void clone(Status original) {
		this.color = original.color;
		this.x = original.x;
		this.y = original.y;
		this.orientation = original.orientation;
		this.isDown = original.isDown;
	}
}
