package Village;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class RightClickMenu extends Entity{

	private List<Option> options;
	private final float OPTION_HEIGHT = 20;
	
	public RightClickMenu(Vector2f position, float height, float length, int maxNumberOfOptions) {
		super(position, height, length, true, false);
		setHeight(height);
		setLength(length);
		options = new ArrayList<Option>(maxNumberOfOptions);
		this.setColor(Color.gray);
	}
	
	public void addOption(Option o){
		o.setOwner(this);
		o.update();
		options.add(o);
		
		setHeight(getHeight() + OPTION_HEIGHT);
	}

	public void render(Vector2f position){
		setX(position.getX()); setY(position.getY());
		drawBoundaries();
		for(Option o : options){
			
			o.update();
			o.render();
		}
	}
	
	public void drawBoundaries(){
		Entity e = new Entity(getX(), getY(), getHeight(), getLength(), true, false);
		e.setColor(Color.gray);
		Rendering.renderQuad(e);
	}

	public void whichCollide(Entity mousePos){
		for(Option o : options){
			if(mousePos.collide(o)){
				o.exec();
			}
		}
	}

	public static class Option extends Entity{
		
		private String name;
		private RightClickMenu owner;
		private static int GlobalNumber = 0;
		private int number;
		public Option(String name){
			this.name = name;
			number = GlobalNumber++;
		}
		
		public void update(){
			setX(owner.getX());
			setY(owner.getY() + (owner.OPTION_HEIGHT * number));
			setHeight(owner.OPTION_HEIGHT);
			setLength(owner.getLength());
		}
		
		public void render(){
			Rendering.printScreen(this.getX(), this.getY(), name, 0);
		}
		
		public void setOwner(RightClickMenu owner){
			this.owner = owner;
		}
		
		public void exec(){
			if(name.equals("Create Village")){	
				
				Entity e = owner;
				float height = owner.getHeight();
				float length = owner.getLength();
				e.setHeight(20); e.setLength(20);
				boolean collide = false;
				for(Village v : Main.villageList.getList()){
					if(e.collide(v)){
						collide = true;
						break;
					}
				}
				e.setHeight(height); e.setLength(length);
				
				if(!collide){
					String name = Random.randName();
					while(Main.villageList.sameName(name)){
						name = Random.randName();
					}
					Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
					Village v = new Village(owner.getX(), owner.getY(), name, c);
					
					if(name.equals("Tak")){
						v.populate(6000);
					}
					v.populate(50);
					Main.villageList.addVillage(v);
				}
				else
					System.out.println("Cannot place a village here");

			}
			if(name.equals("Remove Village")){
				Village temp = null;
				Entity e = owner;
				float height = owner.getHeight();
				float length = owner.getLength();
				e.setHeight(1); e.setLength(1);
				for(Village v : Main.villageList.getList()){
					if(e.collide(v)){
						temp = v;
						break;
					}
				}
				e.setHeight(height); e.setLength(length);
				if(temp != null){
					Main.villageList.remVillage(temp);
				}
				else
					System.out.println("Nothing to remove");
			}
		}
	}
}