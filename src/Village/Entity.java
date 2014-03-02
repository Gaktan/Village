package Village;

import org.newdawn.slick.Color;

public class Entity {

	private Point position;
	private float height;
	private float length;
	private boolean render;
	private boolean moving;
	private Vector velocity;
	private Color color;
	
	public Entity(Point position, float height, float length, boolean render, boolean moving) {
		super();
		this.position = position;
		this.render = render;
		this.moving = moving;
		this.height = height;
		this.length = length;
		this.velocity = new Vector(0, 0);
		color = new Color(Color.white);
	}
	public Entity(float x, float y, float height, float length, boolean render, boolean moving) {
		this(new Point(x, y), height, length, render, moving);
	}
	
	public Entity(){
		this(new Point(0,0), 0, 0, false, false);
	}

	public void render(){
		if(render){
			Quad.renderQuad(this);
		}
	}

	public void update(float delta){
		if(moving){
			this.setX(this.getX() + this.getvX() * delta);
			this.setY(this.getY() + this.getvY() * delta);
		}
	}
	
	public boolean collide(Entity e){
		
		if(		((getX() <= e.getX())					&& (getX() + getLength() >= e.getX()))
			|| 	((getX() <= e.getX() + e.getLength()) 	&& (getX() + getLength() >= e.getX() + getLength()))){
			
			if(		((getY() <= e.getY())					&& (getY() + getHeight() >= e.getY()))
					|| 	((getY() <= e.getY() + e.getHeight()) 	&& (getY() + getHeight() >= e.getY() + getHeight()))){
					
						return true;
					}
			}
		
		
		return false;
	}
	
	public float getSize(){
		return getHeight();
	}

	public float getX(){
		return position.getX();
	}
	
	public float getY(){
		return position.getY();
	}
	
	public void setX(float x){
		position.setX(x);
	}
	
	public void setY(float y){
		position.setY(y);
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	public float getvX(){
		return velocity.getX();
	}
	
	public float getvY(){
		return velocity.getY();
	}
	
	public void setvX(float x){
		velocity.setX(x);
	}
	
	public void setvY(float y){
		velocity.setY(y);
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	@Override
	public String toString() {
		return "Entity [position=" + position + ", height=" + height
				+ ", length=" + length + ", render=" + render + ", moving="
				+ moving + ", velocity=" + velocity + ", color=" + color + "]";
	}
	
	
}
