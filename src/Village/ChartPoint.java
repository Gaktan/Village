package Village;

public class ChartPoint extends Point{
	
	private Point renderPosition;
	
	public ChartPoint(Point position) {
		this(position.getX(), position.getY());
	}

	public ChartPoint(float x, float y) {
		super(x, y);
		this.renderPosition = new Point(0, 0);
	}

	public Point getRenderPosition() {
		return renderPosition;
	}

	public void setRenderPosition(Point renderPosition) {
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
