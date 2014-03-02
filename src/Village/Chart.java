package Village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;

public class Chart {

	private int i = 0;
	private float x, y;
	private List<ChartPoint> list;
	private static float height, length;
	private static float maxValue;
	
	public Chart(Point position, float length, float height){
		x = position.getX();
		y = position.getY();
		list = new ArrayList<ChartPoint>();
		i = 0;
		this.height = height;
		this.length = length;
		maxValue = 0;
	}
	
	public Chart(){
		this(new Point(0, 0), 200, 200);
	}
	
	public void addValue(float f){
		if(f > maxValue)
			maxValue = f;

		ChartPoint p = new ChartPoint(x + i, y - f);
		p.setRy(scaled(f, y));
		p.setRx(x + i);
		list.add(p);
		i += 2;
	}
	
	public void render(Color color){
		if(i > 2){
			shift();
			ChartPoint previous = list.get(0);
			for(ChartPoint p : list){
				p.setRy(scaled(p.getY() - y, y));
				Quad.drawLine(p.getRenderPosition(), previous.getRenderPosition(), color);
				
				previous = p;
			}
		}
		Quad.drawLine(new Point(x, y), new Point(x, y - height), Color.white);
		Quad.drawLine(new Point(x, y), new Point(x + length, y), Color.white);

		Quad.drawLine(new Point(x, y - height), arrow(new Point(x, y - height), -1, 1), Color.white);
		Quad.drawLine(new Point(x, y - height), arrow(new Point(x, y - height), 1, 1), Color.white);
		
		Quad.drawLine(new Point(x + length, y), arrow(new Point(x + length, y), -1, -1), Color.white);
		Quad.drawLine(new Point(x + length, y), arrow(new Point(x + length, y), -1, 1), Color.white);
	}
	
	public float scaled(float f, float dist){
		return ((f / maxValue) * height) + dist;
	}
	
	public void shift(){
		if(i > length){
			i -= 2;
			Iterator<ChartPoint> it = list.iterator();
			ChartPoint rem = null;
			while(it.hasNext()){
				ChartPoint p = it.next();
				p.setX(p.getX() - 2);
				p.setRx(p.getRx() - 2);
				if(p.getX() < x){
					rem = p;
				}
			}
			if(rem != null)
				list.remove(rem);
			if(y - rem.getY() == maxValue){
				newMax();
			}
		}
	}
	
	public void newMax(){
		maxValue = y; 
		for(ChartPoint p : list){
			if(y - p.getY() > maxValue)
				maxValue = y - p.getY();
		}
	}
	
	
	/**
	 * @param p
	 * @param direction
	 * -1 for negative, +1 for positive
	 */
	
	public Point arrow(Point p, int xDirection, int yDirection){
		Point p2 = p;
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
}