package Village;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import Village.RightClickMenu.Option;

import static org.lwjgl.opengl.GL11.*;


public class Main {
	public static boolean GameRunning = true;
	
	public static Camera camera = new Camera();
	
	public static TrueTypeFont[] font = new TrueTypeFont[2];
	private static Font[] awtFont = new Font[2];
	
	public static boolean rMenu;
	public static RightClickMenu menu;
	public static Point mousePos;
	
	public static VillageList villageList;
	
	public static void Init(){
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			Display.setVSyncEnabled(true);
			Display.setTitle("Villages");
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
 
		glShadeModel(GL_SMOOTH);        
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);                    
 
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        glClearDepth(1);                                       
 
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
 
        glViewport(0,0,800,600);
		glMatrixMode(GL_MODELVIEW);
 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 600, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		awtFont[0] = new Font("Times New Roman", Font.BOLD, 16);
		font[0] = new TrueTypeFont(awtFont[0], true);
		awtFont[1] = new Font("Times New Roman", Font.BOLD, 24);
		font[1] = new TrueTypeFont(awtFont[1], true);

	}
	
	public static void gameLoop() throws InterruptedException
	{
	double lastFpsTime = 0;
	int fps = 0;
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 900;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;	//1000000000
	boolean speed = true;
	
	Village vallon = new Village(20, 20, "Vallon sur gée", Color.red);
	Village loue = new Village(20, 350, "Loué", Color.green);
	//Village leMans = new Village(-1000, -400, 1000, "Le Mans", Color.cyan);
	
	vallon.populate(50);
	loue.populate(250);
	//leMans.populate(3000);
	
	villageList = new VillageList();
	villageList.addVillage(vallon); villageList.addVillage(loue);
	
	menu = new RightClickMenu(new Point(0, 0), 0, 150, 10);
	menu.addOption(new Option("Create Village"));
	menu.addOption(new Option("Remove Village"));
	menu.addOption(new Option("Tu veux voir ma bite ?"));
	
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
			if(!speed){
	    		 System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;	
	    	 }
			villageList.updateStat();
			
	      }
	      
	      // Clear the color information.
	      glClear(GL_COLOR_BUFFER_BIT);
	      
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
	
	public static void DrawLine(Point point1, Point point2){				
		glDisable(GL_BLEND);
		glColor3f(1, 0, 0);
		glBegin(GL_LINES);
		{
			glVertex3f(point1.getX(), point1.getY(), 0);
			glVertex3f(point2.getX(), point2.getY(), 0);
		}
		glEnd();
		glEnable(GL_BLEND);
	}
	
	public static void main(String[] args) {
			
	Init();
		
		try {
			gameLoop();
		} catch (InterruptedException f) {
			System.exit(0);
			f.printStackTrace();
		}
	}
}

