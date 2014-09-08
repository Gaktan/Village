package Village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
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
	private float maxVelocity;
	
	private VillageList owner;
	
	public Village() {
		this(0, 0, "Random", Color.red);
	}

	public Village(float x, float y, String name, Color color) {
		this(new Vector2f(x, y), name, color);
	}

	public Village(Vector2f position, String name, Color color) {
		super(position, 0, 0, false, false);
		this.name = name;
		setColor(color);
		
		villagerList = new ArrayList<Villager>();
		population = 0;
		lifeExpectancy = 80;
		maxSize = 20;
		growing = true;
		setLength(maxSize); setHeight(maxSize);
		
		popChart = new Chart(new Vector2f(400, 200), false);
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

		v.setDestination(v.getPosition());
	}
	
	public void remVillager(Villager v){
		population--;
		villagerList.remove(v);
	}
	
	public void render(Camera cam){
		for(Villager v : villagerList){
			if(cam.collide(v)){
				v.render();
			}
		}	
		popChart.render(cam, getColor());
		
		if(owner.isDisplayNames()){
			Rendering.printScreen(getX() + getLength() + 20,  getY() + 20,  name, 1);
			Rendering.printScreen(getX() + getLength() + 20,  getY() + 40,  ""+population, 0);
		}
	}
	
	public void update(float delta){
		for(Villager v : villagerList){
			if(v.isArrived()){
				destination(v, null);
			}
			v.update(delta);
		}
		maxVelocity = 0.002f / getSize();		//set the max speed of villagers
	}
	
	public void updateBaby(){
		setHeight(maxSize); setLength(maxSize);
		
		Iterator<Villager> it = villagerList.iterator();
		List<Villager> temp = new ArrayList<Villager>();
		
		while(it.hasNext()){
			Villager v = it.next();
			
			Villager v2 = v.makeBaby();
			if(v2 != null){
				temp.add(v2);
			}
		}
		if(!temp.isEmpty()){
			Iterator<Villager> it2 = temp.iterator();
			while(it2.hasNext()){
				Villager v2 = it2.next();
				addVillager(v2);
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
		Vector2f a1 = getPosition();
		Vector2f a2 = new Vector2f(getX() + getSize(), getY());
		Vector2f b1 = new Vector2f(getX(), getY() + getSize());
		Vector2f b2 = new Vector2f(getX() + getSize(), getY() + getSize());
		
		
		Rendering.drawLine(a1, a2, this.getColor());
		Rendering.drawLine(a2, b2, this.getColor());
		Rendering.drawLine(a1, b1, this.getColor());
		Rendering.drawLine(b1, b2, this.getColor());
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
	
	public Vector2f randomCoordInVillage(){		
		float x = Random.randFloat(getX(), getX() + getLength());
		float y = Random.randFloat(getY(), getY() + getHeight());
		
		return new Vector2f(x, y);
	}
	
	public void destination(Villager v, Vector2f p){
		
		if(p == null)
			v.setDestination(randomCoordInVillage());
		else
			v.setDestination(p);

		Vector2f a = bToA(v.getDestination(), v.getPosition());
		a.setX(a.getX() * maxVelocity);
		a.setY(a.getY() * maxVelocity);
		v.setVelocity(a);	
		v.setArrived(false);
	}
	
	public Vector2f bToA(Vector2f destination, Vector2f position){
		
		float x = destination.x - position.x;
		float y = destination.y - position.y;
		
		return new Vector2f(x, y);
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

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
}
