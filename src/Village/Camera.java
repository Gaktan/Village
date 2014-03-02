package Village;


import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera extends Entity{
	
	private float height = 800;
	private float length = 600;
	
	public Camera(){
		this.setX(0);
		this.setY(0);
	}
	
	public float getHeight() {
		return height;
	}

	public float getLength() {
		return length;
	}

	public void update(float dt){

		glTranslatef(-getvX(), -getvY(), 0);
		
		
		this.setX(this.getX() + getvX());
		this.setY(this.getY() + getvY());
	}
	
	public Point camPos(){
		return new Point(length + getX(), getHeight() + getY());
	}

	public float getxCenter() {
		return getX() + 400;
	}

	public float getyCenter() {
		return getY() + 300;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setLength(float length) {
		this.length = length;
	}

	
}
 