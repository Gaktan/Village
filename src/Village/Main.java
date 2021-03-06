package Village;

import org.newdawn.slick.Color;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;

import Village.RightClickMenu.Option;

public class Main {
	public static boolean GameRunning = true;

	public static Camera camera = new Camera();

	public static boolean rMenu;
	public static boolean speed = false;	//Set to false for normal speed
	public static RightClickMenu menu;
	public static Vector2f mousePos;

	public static VillageList villageList;
	
	public static ChartGraph chart;

	public static void gameLoop() throws InterruptedException
	{
		double lastFpsTime = 0;
		int fps = 0;
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 900;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;	//1000000000
		
		chart = new ChartGraph(new Vector2f(500, 300), 600, 800, 2);

		Village vallon = new Village(20, 20, "Vallon sur g�e", Color.red);
		Village loue = new Village(20, 350, "Lou�", Color.green);

		vallon.populate(50);
		loue.populate(250);

		villageList = new VillageList();
		villageList.addVillage(vallon); villageList.addVillage(loue);

		menu = new RightClickMenu(new Vector2f(0, 0), 0, 150, 10);
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
				int x = (int) camera.getPosition().getX();
				int y = (int) camera.getPosition().getY();
				Display.setTitle("Villages " + fps + " FPS. CamPos : (" + x + ", " + y + ")");
				lastFpsTime = 0;
				fps = 0;	

				if(!speed){
					villageList.updateStat();
					chart.update();
				}
			}
			if(speed){
				villageList.updateStat();
				chart.update();
			}

			update(delta);

			render();


			try{
				Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
			}catch(IllegalArgumentException e){}
		}
	}

	public static void update(float delta){
		Controls.update(camera, delta);
		camera.update(delta);
		villageList.update(delta);
	}

	public static void render(){

		// Clear the color information.
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		chart.render(camera);
		
		villageList.render(camera);

		if(rMenu){
			menu.render(mousePos);
		}

		Display.update();
	}

	public static void main(String[] args) {

		Rendering.init();

		try {
			gameLoop();
		} catch (InterruptedException f) {
			System.exit(0);
			f.printStackTrace();
		}
	}
}

