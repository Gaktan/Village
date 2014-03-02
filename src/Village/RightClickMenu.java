package Village;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class RightClickMenu extends Entity{

	private List<Option> options;
	private final float OPTION_HEIGHT = 20;
	
	public RightClickMenu(Point position, float height, float length, int maxNumberOfOptions) {
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

	public void render(Point position){
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
		Quad.renderQuad(e);
	}

	public void whichCollide(Entity mousePos){
		Option buff = null;
		for(Option o : options){
			if(mousePos.collide(o)){
				o.exec();
				buff = o;
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
			Quad.printScreen(this.getX(), this.getY(), name, 0);
		}
		
		public void setOwner(RightClickMenu owner){
			this.owner = owner;
		}
		
		public void exec(){
			if(name.equals("Create Village")){
				Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
				
				String name = Random.randName();
				while(Main.villageList.sameName(name)){
					System.out.println("It had same");
					name = Random.randName();
				}
				
				Village v = new Village(owner.getX(), owner.getY(), name, c);
				v.populate(50);
				Main.villageList.addVillage(v);
			}
			if(name.equals("Remove Village")){
				Village temp = null;
				Entity e = owner;
				float height = owner.getHeight();
				float length = owner.getLength();
				e.setColor(Color.blue);
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