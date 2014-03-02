package Village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;

public class VillageList {
	private List<Village> list;
	private Chart chartTotal;
	private boolean displayNames;
	
	public VillageList(){
		list = new ArrayList<Village>();
		chartTotal = new Chart(new Point(400, 200), 800, 400);
		displayNames = true;
	}

	
	public void addVillage(Village v){
		list.add(v);
		v.setOwner(this);
	}
	
	public void remVillage(Village v){
		list.remove(v);
	}
	
	public void updateStat(){
		int value = 0;
		Iterator<Village> it = list.iterator();
		List<Village> temp = new ArrayList<Village>();
		Village v;
		while(it.hasNext()){
			v = it.next();
			value += v.getPopulation();
			v.updateHealth();
			v.updateBaby();
			
			if(v.getPopulation() == 0){
				temp.add(v);
			}
			
			if(v.isGrowing()){
				for(Village v2 : list){
					if(v != v2){
						if(v.collide(v2)){
							v.setGrowing(false);
							v2.setGrowing(false);
						}
					}
				}
			}
		}
		if(!temp.isEmpty()){
			Iterator it2 = temp.iterator();
			while(it2.hasNext()){
				list.remove(it2.next());
			}
		}
		chartTotal.addValue(value);		
	}
	
	public void update(float delta){
		for(Village v : list){
			v.update(delta);
		}
	}
	
	public void render(Camera cam){
		for(Village v : list){
			v.render(cam);
			v.drawBoundaries();
		}
		chartTotal.render(Color.white);
	}

	public boolean sameName(String str){
		for(Village v : list){
			if(v.getName().equals(str)){
				return true;
			}
		}
		return false;
	}
	
	
	public boolean isDisplayNames() {
		return displayNames;
	}


	public void setDisplayNames(boolean displayNames) {
		this.displayNames = displayNames;
	}


	public List<Village> getList() {
		return list;
	}


	public void setList(List<Village> list) {
		this.list = list;
	}
	
	
	
}
