package Village;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ChartGraph {

	private Vector2f position;
	private List<ChartData> list;
	private float HEIGHT, LENGTH;
	private int STEPS = 1;
	private int index;
	private float maxValue;
	private ChartData global;

	public ChartGraph(Vector2f position, float height, float length, int steps){
		this.position = position;
		this.HEIGHT = height;
		this.LENGTH = length;
		this.STEPS = steps;

		list = new ArrayList<ChartData>();
		
		global = new ChartData(this, Color.white);
		
		maxValue = 0;
		
		addChartData(global);
	}
	
	public void update(){
		index += STEPS;
		
		float total = 0;
		
		for(ChartData cd : list){
			if (cd != global){
				if(cd.list.size() > 0)
				total += cd.list.get(0).getY();
			}
		}
		
		if(index < 2)
			return;
		
		if(index > LENGTH*STEPS){
			index -= STEPS;
		}
		
		float max = 0;
		
		for(ChartData cd : list){
			cd.shift(STEPS);
			
			if(max < cd.maxValue)
				max = cd.maxValue;
		}
		
		maxValue = max;
		
		global.addValue(total);
	}

	public void render(Camera cam){
		
		for(ChartData cd : list){
			cd.render(cam, position);
		}
		
		drawLines(Color.gray);

		float x = position.getX();
		float y = position.getY();

		Rendering.drawLine(new Vector2f(x, y), new Vector2f(x, y - HEIGHT), Color.white);
		Rendering.drawLine(new Vector2f(x, y), new Vector2f(x + LENGTH, y), Color.white);

		Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), -1, 1), Color.white);
		Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), 1, 1), Color.white);

		Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, -1), Color.white);
		Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, 1), Color.white);
	}

	public void drawLines(Color color){
		int step = (int) (maxValue/10);
		int closest = (int) maxValue;

		float x = position.getX();
		float y = position.getY();

		for(int i = 0 ; i < 10; i++){
			float y2 = scaled(-closest);
			Rendering.drawLine(new Vector2f(x, y2), new Vector2f(x + LENGTH, y2), color);
			Rendering.printScreen(x + LENGTH, y2 - 15, ""+closest, 1);
			closest -= step;
		}
	}

	public float scaled(float f){
		return position.getY() - ((f / maxValue) * HEIGHT);
	}

	/**
	 * @param p
	 * @param direction
	 * -1 for negative, +1 for positive
	 */
	public Vector2f arrow(Vector2f p, int xDirection, int yDirection){
		Vector2f p2 = p;
		int coef = 5;
		if(xDirection == 1){
			if(yDirection == 1){
				p2.setY(p2.getY() + coef);

			}
			if(yDirection == -1){
				p2.setY(p2.getY() - coef);
			}
			p2.setX(p2.getX() + coef);

			return p2;
		}

		if(xDirection == -1){
			if(yDirection == 1){
				p2.setY(p2.getY() + coef);

			}
			if(yDirection == -1){
				p2.setY(p2.getY() - coef);
			}
			p2.setX(p2.getX() - coef);
			return p2;
		}

		return p2;
	}
	
	public void addChartData(ChartData cd){
		list.add(cd);
	}
	
	public void removeChartData(ChartData cd){
		list.remove(cd);
	}


	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public ChartGraph(){
		this(new Vector2f(0, 0), 800, 600, 1);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(float hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public float getLENGTH() {
		return LENGTH;
	}

	public void setLENGTH(float lENGTH) {
		LENGTH = lENGTH;
	}

	public int getSTEPS() {
		return STEPS;
	}

	public void setSTEPS(int sTEPS) {
		STEPS = sTEPS;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
