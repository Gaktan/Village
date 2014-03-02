package Village;


public class Vector {
	
	private float x, y;
	
	public Vector(float x, float y){
		this.x = x;
		this.y = y;
	}
	public Vector(){
		this(0,0);
	}
	
	public float length(){
		return (float) Math.sqrt(x*x + y*y);
	}
	public float lengthSqr(){
		return (x*x + y*y);
	}
	
	public Vector times(float s){
		Vector scaled = new Vector(x*s, y*s);
		return scaled;
	}
	public Vector plus(Vector v){
		Vector r = new Vector(x+v.x, y+v.y);
		return r;
	}
	public Vector minus(Vector v){
		Vector r = new Vector(x-v.x, y-v.y);
		return r;
	}
	public Vector dividedBy(float s){
		Vector scaled = new Vector(x/s, y/s);
		return scaled;
	}
	
	public Vector normalized(){
		return this.dividedBy(length());
	}
	
	public static float DotProduct(Vector a, Vector b){
		return (a.x * b.x) + (a.y * b.y);
	}
	
	public static float Approach(float goal, float current, float dt){
		
		float difference = goal - current;
		
		if(difference > dt)
			return current + dt;
		if(difference < -dt)
			return current - dt;
		
		return goal;
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
	
	
}
