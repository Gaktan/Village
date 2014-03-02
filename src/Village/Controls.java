package Village;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class Controls {
	
	private static int zoom = 0;
	private static float camSpeed = 10;
	private static float scale = 1;
	private static boolean display = true;
	
	public static void update(Camera camera, float dt)
	{
		while(Keyboard.next())
		{
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					camera.setvX(camSpeed);
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
		        	camera.setvX(-camSpeed);
		        }	        	
		        if (Keyboard.getEventKey() == Keyboard.KEY_UP){
		        	camera.setvY(-camSpeed);
		        }  
		        if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
		        	camera.setvY(camSpeed);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
		        	System.out.println("Stopping process...");
		        	System.exit(0);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
		        	Main.speed = true;
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
//		        	Main.loue.clear();
//		        	Main.loue.populate(200);
		        	System.out.println(Random.randName());
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_A){
		        	if(display){
		        		Main.villageList.setDisplayNames(false);
		        		
		        	}
		        	if(!display){
		        		Main.villageList.setDisplayNames(true);
		        	}
		        	 display = !display;
		        }
		    }
		    else {
		        if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
		        	camera.setvX(0);
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
		        	camera.setvX(0);
		        }	        	
		        if (Keyboard.getEventKey() == Keyboard.KEY_UP){
		        	camera.setvY(0);
		        }  
		        if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
		        	camera.setvY(0);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
		        	Main.speed = false;
		        }
		    }
		}
		while(Mouse.next()){
			
			if(Mouse.getEventButtonState()){
				if(Mouse.isButtonDown(1)){
					Main.mousePos = getMousePos(camera);
					Main.rMenu = true;
				}
				if(Mouse.isButtonDown(0)){
					Entity e = new Entity(getMousePos(camera), 1, 1, false, false);
					if(e.collide(Main.menu)){
						Main.menu.whichCollide(e);
					}
					Main.rMenu = false;
				}
			}
			
			
			/*
			 * 
			 * ZOOMING
			 * 
			 */
			
	        int dWheel = Mouse.getDWheel();
	        float zoomFactor = 0;
	        boolean set = false;
	        if (dWheel < 0) {
		        if(zoom < 3){
		        	zoomFactor = 0.5f;
		        	set = true;
		        	zoom++;
		        	scale *= zoomFactor;
		        }
	        } else if (dWheel > 0){
		        if(zoom > 0){
		        	zoomFactor = 2;
		        	set = true;
		        	zoom--;
		        	scale *= zoomFactor;
		        }
	        }
	        
	        if(set){
	        	camSpeed /= zoomFactor;
	        	camera.setHeight(camera.getHeight() / zoomFactor);
	        	camera.setLength(camera.getLength() / zoomFactor);
	        	
	        	
	        	glTranslated(camera.getX(), camera.getY(), 1);
	        	glScaled(zoomFactor, zoomFactor, 1);
	        	glTranslated(-camera.getX(), -camera.getY(), -1);
	        	
	        	//glTranslatef(oldPos.getX(), oldPos.getY(), 0);
	        	if(camera.getvY() > 0)
	        		camera.setvY(camSpeed);
	        	if(camera.getvY() < 0)
	        		camera.setvY(-camSpeed);
	        	if(camera.getvX() > 0)
	        		camera.setvX(camSpeed);
	        	if(camera.getvX() < 0)
	        		camera.setvX(-camSpeed);
	        }
		}
	}
	public static Point getMousePos(Camera camera){
		float x = (camera.getX() + (Mouse.getX()/scale));
		float y = (camera.getY() - Mouse.getY()/scale);
		
		float xToScale = x / camera.getLength();
		float yToScale = y / camera.getHeight();
		
		x = camera.getLength() * xToScale;
		y = camera.getHeight() * yToScale;
		
		y = camera.getLength() + y -(200/scale);

		return new Point(x, y);
	}
}
