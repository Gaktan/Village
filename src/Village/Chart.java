package Village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;

public class Chart {

	private int i;
	private float x, y;
	private List<ChartPoint> list;
	private final float HEIGHT = 400, LENGTH = 800;
	private static float maxValue;
	private boolean global;
	private static int iGlobal;
	
	public Chart(Point position, boolean global){
		x = position.getX();
		y = position.getY();
		list = new ArrayList<ChartPoint>();
		i = 0;
		maxValue = 0;
		this.global = global;
		
		if(global){
			iGlobal = i;
		}
		if(iGlobal != i){
			init();
		}
	}
	
	public Chart(){
		this(new Point(0, 0), false);
	}
	
	public void init(){
		for(int j = 0; j < iGlobal ; j+= 2){
			addValue(0);
		}
	}
	
	public void addValue(float f){
		if(f > maxValue)
			maxValue = f;

		ChartPoint p = new ChartPoint(x + i, y - f);
		p.setRy(scaled(f, y));
		p.setRx(x + i);
		list.add(p);
		i += 2;
		if(global){
			iGlobal += 2;
		}
	}
	
	public void render(Color color){
		if(i > 2){
			shift();
			ChartPoint previous = list.get(0);
			for(ChartPoint p : list){
				p.setRy(scaled(p.getY() - y, y));
				Rendering.drawLine(p.getRenderPosition(), previous.getRenderPosition(), color);
				
				previous = p;
			}
		}
		Rendering.drawLine(new Point(x, y), new Point(x, y - HEIGHT), Color.white);
		Rendering.drawLine(new Point(x, y), new Point(x + LENGTH, y), Color.white);

		Rendering.drawLine(new Point(x, y - HEIGHT), arrow(new Point(x, y - HEIGHT), -1, 1), Color.white);
		Rendering.drawLine(new Point(x, y - HEIGHT), arrow(new Point(x, y - HEIGHT), 1, 1), Color.white);
		
		Rendering.drawLine(new Point(x + LENGTH, y), arrow(new Point(x + LENGTH, y), -1, -1), Color.white);
		Rendering.drawLine(new Point(x + LENGTH, y), arrow(new Point(x + LENGTH, y), -1, 1), Color.white);
	}
	
	public float scaled(float f, float dist){
		return ((f / maxValue) * HEIGHT) + dist;
	}
	
	public void shift(){
		if(i > LENGTH){
			if(global){
				iGlobal -= 2;
			}
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