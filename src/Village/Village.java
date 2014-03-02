package Village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;

public class Village extends Entity{
	
	private List<Villager> villagerList;
	private int population;
	private String name;
	private int lifeExpectancy;
	private Chart popChart;
	private final float SIZE_LIM = 350;
	private final int MAX_POP = 3000;
	private float maxSize;
	private boolean growing;
	
	private VillageList owner;
	

	public Village() {
		this(0, 0, "Random", Color.red);
	}

	public Village(float x, float y, String name, Color color) {
		this(new Point(x, y), name, color);
	}

	public Village(Point position, String name, Color color) {
		super(position, 0, 0, false, false);
		this.name = name;
		setColor(color);
		
		villagerList = new ArrayList<Villager>();
		population = 0;
		lifeExpectancy = 80;
		maxSize = 20;
		growing = true;
		
		popChart = new Chart(new Point(400, 200), 800, 400);
	}
	
	public void addVillager(Villager v){
		population++;
		villagerList.add(v);
		v.setVillage(this);
		if(maxSize < SIZE_LIM && growing){
			maxSize += 0.025f;
		}
		else
			growing = false;

		destination(v);
	}
	
	public void remVillager(Villager v){
		population--;
		villagerList.remove(v);
	}
	
	
	public void render(Camera cam){
		for(Villager v : villagerList){
			if((v.getX() > cam.getX()) && (v.getX() < cam.getX() + cam.getLength())){
				if((v.getY() > cam.getY()) && (v.getY() < cam.getY() + cam.getHeight())){
					v.render();
				}
			}
		}
		
		popChart.render(getColor());
		

		if(owner.isDisplayNames()){
		    Quad.printScreen(getX() + getLength() + 20,  getY() + 20,  name, 1);
		    Quad.printScreen(getX() + getLength() + 20,  getY() + 40,  ""+population, 0);
		}
	}
	
	public void update(float delta){
		for(Villager v : villagerList){
			if(v.isArrived()){
				destination(v);
			}
			v.update(delta);
		}
	}
	
	public void updateBaby(){
		setHeight(maxSize); setLength(maxSize);
		
		Iterator<Villager> it = villagerList.iterator();
		List<Villager> temp = new ArrayList<Villager>();
		
		while(it.hasNext()){
			Villager v = (Villager) it.next();
			
			Villager v2 = v.makeBaby();
			if(v2 != null){
				temp.add(v2);
			}
		}
		if(!temp.isEmpty()){
			Iterator<Villager> it2 = temp.iterator();
			while(it2.hasNext()){
				addVillager(it2.next());
			}
		}
		popChart.addValue(population);
	}
	
	public void populate(int amount){
		Villager v;
		boolean dude;
		for(int i = 0 ; i < amount ; i++){
			dude = Random.randBool();
			v = new Villager(this, dude);
			addVillager(v);
		}
	}
	
	public void clear(){
		villagerList.clear();
		population = 0;
	}
	
	public void drawBoundaries(){
		Point a1 = getPosition();
		Point a2 = new Point(getX() + getSize(), getY());
		Point b1 = new Point(getX(), getY() + getSize());
		Point b2 = new Point(getX() + getSize(), getY() + getSize());
		
		
		Quad.drawLine(a1, a2, this.getColor());
		Quad.drawLine(a2, b2, this.getColor());
		Quad.drawLine(a1, b1, this.getColor());
		Quad.drawLine(b1, b2, this.getColor());
	}

	public void updateHealth(){
		
		Iterator<Villager> it = villagerList.iterator();
		while(it.hasNext()){
			Villager v = (Villager) it.next();
			v.setHealth(v.getHealth() + 1);
			
			int x = v.getHealth() + (int) (Math.random() * (lifeExpectancy - v.getHealth()));
			if(x == v.getHealth()){
				it.remove();
				population--;
			}		
			if(!v.isDude()){
				if(v.getBabyCoolDown() > 0){
					v.setBabyCoolDown(v.getBabyCoolDown() - 1);
				}
			}
		}

	}
	
	public Point randomCoordInVillage(){		
		float x = Random.randFloat(getX(), getX() + getLength());
		float y = Random.randFloat(getY(), getY() + getHeight());
		
		return new Point(x, y);
	}
	
	public void destination(Villager v){
		
		v.setDestination(randomCoordInVillage());
		Vector a = Point.bToA(v.getDestination(), v.getPosition());
		a.setX(a.getX() * v.getMAX_VELOCITY());
		a.setY(a.getY() * v.getMAX_VELOCITY());
		v.setVelocity(a);
		v.setTimeToMove(600);
	}
	
	
	
	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	public float getSIZE_LIM() {
		return SIZE_LIM;
	}

	public int getMAX_POP() {
		return MAX_POP;
	}

	public boolean isGrowing() {
		return growing;
	}

	public void setGrowing(boolean growing) {
		this.growing = growing;
	}

	public VillageList getOwner() {
		return owner;
	}

	public void setOwner(VillageList owner) {
		this.owner = owner;
	}
	
	
	
}
