package Village;


import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Villager extends Entity{
	
	private int health;
	private Villager mom;
	private Village village;
	private boolean dude;
	private int babyCoolDown;
	private static final int MAX_BABIES = 3;
	private int kids;
	
	private Vector2f destination;
	private boolean arrived;
	
	public Villager() {
		this(new Vector2f(0, 0), true);
	}
	
	public Villager(Village vi, boolean dude){
		this(vi.randomCoordInVillage(), dude);
	}

	public Villager(Vector2f position, boolean dude) {
		//size = 3
		super(position, 3,3, true, true);
		
		health = 0 + (int) (Math.random() * (50 - 0));		//between 0 & 50 yo
		mom = null;
		village = null;
		this.dude = dude;
		kids = 0;
		arrived = false;
		
		if(!dude){
			babyCoolDown = 0 + (int) (Math.random() * (10 - 0));
			this.setColor(new Color(Color.pink));
		}
		else{
			this.setColor(new Color(0, 0.5f, 1));
		}
		
		boolean rand = Random.randBool();
		setRender(rand);
		setMoving(rand);

	}
	
	public Villager(float x, float y, boolean dude) {
		this(new Vector2f(x, y), dude);
	}

	
	public Villager makeBaby(){
		if(village.getPopulation() <= village.getMAX_POP()){	//there is room
			if(!dude){											//girl
				if(kids <= MAX_BABIES){							//if less than the maximum amount of babies
					if(health > 18 && health < 60){				//18 - 60
						if(babyCoolDown == 0){					//wants kids
							if(village.getPopulation() > 2){	//people in town
								int x = 0 + (int) (Math.random() * (100 - 0));	
								if(x > 75){						//25% chance making baby
									Villager baby = new Villager(village, Random.randBool());
									baby.mom = this;
									baby.health = 0;
									kids++;
									babyCoolDown = 0 + (int) (Math.random() * (15 - 0));
									return baby;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	

	public void update(float delta) {
		if(isMoving()){
			super.update(delta);
		}
		
		int diff = 10;
		Entity e = new Entity(destination, diff, diff, false, false);
		if(collide(e)){
			arrived = true;
			setvX(0); setvY(0);
		}

		if(getX() + getLength() > village.getX() + village.getLength()){
			setX(village.getX() + village.getLength() - getLength());
			setvX(Random.randFloat(village.getMaxVelocity(), 0));
		}
		if(getX() < village.getX()){
			setX(village.getX());
			setvX(Random.randFloat(0, village.getMaxVelocity()));
		}
		if(getY() + getHeight() > village.getY() + village.getHeight()){
			setY(village.getY() + village.getHeight() - getHeight());
			setvY(Random.randFloat(-village.getMaxVelocity(), 0));
		}
		if(getY() < village.getY()){
			setY(village.getY());
			setvY(Random.randFloat(0, village.getMaxVelocity()));
		}
	}
	
	public void render(){
		if(isRender()){
			Rendering.renderQuad(this);
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Villager getMom() {
		return mom;
	}

	public void setMom(Villager mom) {
		this.mom = mom;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public boolean isDude() {
		return dude;
	}

	public void setDude(boolean dude) {
		this.dude = dude;
	}

	public int getBabyCoolDown() {
		return babyCoolDown;
	}

	public void setBabyCoolDown(int babyCoolDown) {
		this.babyCoolDown = babyCoolDown;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
}
