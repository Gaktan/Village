package Village;

import org.lwjgl.util.vector.Vector2f;


public class ChartPoint extends Vector2f{
	
	private Vector2f renderPosition;
	
	public ChartPoint(Vector2f position) {
		this(position.getX(), position.getY());
	}

	public ChartPoint(float x, float y) {
		super(x, y);
		this.renderPosition = new Vector2f(0, 0);
	}

	public Vector2f getRenderPosition() {
		return renderPosition;
	}

	public void setRenderPosition(Vector2f renderPosition) {
		this.renderPosition = renderPosition;
	}
	
	public float getRx(){
		return renderPosition.getX();
	}
	public float getRy(){
		return renderPosition.getY();
	}
	
	public void setRx(float rX){
		renderPosition.setX(rX);
	}
	public void setRy(float rY){
		renderPosition.setY(rY);
	}
}
