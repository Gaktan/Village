package Village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ChartData {

	public List<Vector2f> list;
	public float maxValue;
	private ChartGraph graph;
	private Color color;

	public ChartData(ChartGraph graph, Color color){
		list = new ArrayList<Vector2f>();
		maxValue = 0;
		this.graph = graph;
		this.color = color;
	}

	public void init(){
		for(int j = 0; j < graph.getIndex() ; j+= graph.getSTEPS()){
			addValue(0);
		}
	}

	public void addValue(float f){
		if(f > maxValue)
			maxValue = f;

		int i = graph.getIndex();

		Vector2f p = new Vector2f(i, graph.scaled(f));
		list.add(p);
	}

	public void render(Camera cam, Vector2f position){
		if(list.size() < 2 ) return;
		Vector2f previous = list.get(0);
		for(Vector2f p : list){

			if(cam.collide(p, previous)){
				Rendering.drawLine(p, previous, color);
			}
			previous = p;
		}
	}

	public void shift(int STEPS){
		Iterator<Vector2f> it = list.iterator();
		Vector2f rem = null;

		float x = graph.getPosition().getX();
		float y = graph.getPosition().getY();

		while(it.hasNext()){
			Vector2f p = it.next();
			p.setX(p.getX() - STEPS);

			if(p.getX() < x){
				rem = p;
			}
		}
		if(rem != null){
			list.remove(rem);
			if(rem.getY() == maxValue){
				newMax();
			}
		}
	}

	public void newMax(){
		maxValue = 0; 
		for(Vector2f p : list){
			if(p.getY() > maxValue)
				maxValue = p.getY();
		}
	}
}