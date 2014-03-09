package Village;

import org.newdawn.slick.Color;

import org.lwjgl.opengl.*;

import Village.RightClickMenu.Option;

public class Main {
	public static boolean GameRunning = true;
	
	public static Camera camera = new Camera();
	
	public static boolean rMenu;
	public static boolean speed = false;	//Set to false for normal speed
	public static RightClickMenu menu;
	public static Point mousePos;
	
	public static VillageList villageList;

	public static void gameLoop() throws InterruptedException
	{
	double lastFpsTime = 0;
	int fps = 0;
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 900;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;	//1000000000
	
	Village vallon = new Village(20, 20, "Vallon sur gée", Color.red);
	Village loue = new Village(20, 350, "Loué", Color.green);
	
	vallon.populate(50);
	loue.populate(250);
	
	villageList = new VillageList();
	villageList.addVillage(vallon); villageList.addVillage(loue);
	
	menu = new RightClickMenu(new Point(0, 0), 0, 150, 10);
	menu.addOption(new Option("Create Village"));
	menu.addOption(new Option("Remove Village"));
	menu.addOption(new Option("not yet"));
	
	   // keep looping round til the game ends
	   while (GameRunning)
	   {
	      // work out how long its been since the last update, this
	      // will be used to calculate how far the entities should
	      // move this loop
	      long now = System.nanoTime();
	      long updateLength = now - lastLoopTime;
	      lastLoopTime = now;
	      float delta = updateLength / ((float)OPTIMAL_TIME);

	      // update the frame counter
	      lastFpsTime += updateLength;
	      fps++;
	      
	      
	      // update our FPS counter if a second has passed since
	      // we last recorded
	      
	      if (lastFpsTime >= 1000000000)//1000000000
	      {
			System.out.println("(FPS: "+fps+")");
	        lastFpsTime = 0;
	        fps = 0;	
	        
	        if(!speed){
				villageList.updateStat();
	        }
	      }
	      if(speed){
				villageList.updateStat();
	      }
	      // Clear the color information.
	      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	      
	      Controls.update(camera, delta);
	      camera.update(delta);
	      villageList.update(delta);
	      
	      villageList.render(camera);
	      
	      if(rMenu){
	    	  menu.render(mousePos);
	      }
	      	
	      Display.update();
	      
//	      try{
//	    	  Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
//	      }catch(IllegalArgumentException e){}
	   }
	}
	
	public static void main(String[] args) {
			
	Rendering.Init();
		
		try {
			gameLoop();
		} catch (InterruptedException f) {
			System.exit(0);
			f.printStackTrace();
		}
	}
}

