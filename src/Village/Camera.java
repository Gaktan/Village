package Village;


import static org.lwjgl.opengl.GL11.glTranslatef;

public class Camera extends Entity{
	
	public Camera(){
		this.setX(0);
		this.setY(0);
		setLength(800);
		setHeight(600);
	}

	public void update(float dt){

		glTranslatef(-getvX(), -getvY(), 0);
		
		
		this.setX(this.getX() + getvX());
		this.setY(this.getY() + getvY());
	}
	
	public Point camPos(){
		return new Point(getLength() + getX(), getHeight() + getY());
	}

	public float getxCenter() {
		return getX() + 300;
	}

	public float getyCenter() {
		return getY() + 400;
	}
}
 