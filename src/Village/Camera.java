package Village;

import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera extends Entity{
	
	private Vector VectorGoal;
	
	public Camera(){
		this.setX(0);
		this.setY(0);
		setHeight(Rendering.getHeight());
		setLength(Rendering.getLength());
		VectorGoal = new Vector(0, 0);
	}

	public void update(float dt){
		
		setvX(Vector.Approach(getVgX(), this.getvX(), dt/50));
		setvY(Vector.Approach(getVgY(), this.getvY(), dt/50));
		float x = getvX();
		float y = getvY();

		glTranslatef(-x, -y, 0);
		
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		
	}
	
	
	public Point camPos(){
		return new Point(getHeight() + getX(), getLength() + getY());
	}

	public float getxCenter() {
		return getX() + getHeight() / 2;
	}

	public float getyCenter() {
		return getY() + getLength() / 2;
	}

	public Vector getVectorGoal() {
		return VectorGoal;
	}

	public void setVectorGoal(Vector vectorGoal) {
		VectorGoal = vectorGoal;
	}
	
	public float getVgX(){
		return VectorGoal.getX();
	}
	public void setVgX(float x){
		VectorGoal.setX(x);
	}
	public float getVgY(){
		return VectorGoal.getY();
	}
	public void setVgY(float y){
		VectorGoal.setY(y);
	}
}
 