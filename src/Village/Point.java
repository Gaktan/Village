package Village;


public class Point {
	
	private float x, y;
	
	public Point(float x, float y){
		this.x = x;
		this.y = y;
	}
	public Point(){
		this(0,0);
	}
	public void AddVectorX(float f){
		x = x + f;
	}
	
	 public void AddVectorY(float f){
		 y = y + f;
	 }
	 public static Vector bToA(Point a, Point b){	//Calculates vector v from b to a
		Vector v = new Vector();
		
		v.setX(a.x - b.x);
		v.setY(a.y - b.y);
		return v;
	 }
	
	/**
	 * 
	 * GET SET
	 * 
	 */
	
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
	
	
}
