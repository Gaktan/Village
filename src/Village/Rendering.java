package Village;


import java.awt.Font;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Rendering {

	private static TrueTypeFont[] font = new TrueTypeFont[2];
	private static Font[] awtFont = new Font[2];
	private static final int length = 800, height = 600; 
	private static int quadID;
	private static int quadColor;

	public static void colorToGL(Color color){
		glColor3f(color.r, color.g, color.b);
	}

	public static void renderQuad(Entity v){
		glDisable(GL_TEXTURE_2D);
		colorToGL(v.getColor());

		// Create a new draw matrix for custom settings.
		glPushMatrix();
		{
			// Translate to the location.
			glTranslatef(v.getX(), v.getY(), 0);

			// Begin drawing with QUADS. A QUAD is a polygon with four vertices.
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, v.getHeight());
				glVertex2f(v.getLength(), v.getHeight());
				glVertex2f(v.getLength(), 0);
			}
			glEnd();  // End the drawing.
		}
		glPopMatrix();  // Restore original settings.
	}

	public static void renderTextureQuad(Entity v){
		float value = 0.78f;
		colorToGL(v.getColor());
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("D:/soft/.Prog/Java/Projets/Sans titre.png"));
		} catch (IOException e) {
			System.out.println("Texture not found.");
			e.printStackTrace();
		}
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());

		// Create a new draw matrix for custom settings.
		glPushMatrix();
		{
			// Translate to the location.
			glTranslatef(v.getX(), v.getY(), 0);

			// Begin drawing with QUADS. A QUAD is a polygon with four vertices.
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);

				glTexCoord2f(0, value);
				glVertex2f(0, v.getHeight());

				glTexCoord2f(value, value);
				glVertex2f(v.getLength(), v.getHeight());

				glTexCoord2f(value, 0);
				glVertex2f(v.getLength(), 0);

			}
			glEnd();  // End the drawing.
		}
		glPopMatrix();  // Restore original settings.
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	public static void drawLine(Vector2f point1, Vector2f point2, Color color){		
		glDisable(GL_BLEND);
		glLineWidth(1); 
		colorToGL(color);
		glBegin(GL_LINES);
		{
			glVertex3f(point1.getX(), point1.getY(), 1);
			glVertex3f(point2.getX(), point2.getY(), 1);
		}
		glEnd();
	}

	public static void printScreen(float x, float y, String text1, int size){
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		glEnable(GL_BLEND);
		font[size].drawString(x, y, text1 , Color.white, 2, 4);
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	public static void init(){
		try {
			Display.setDisplayMode(new DisplayMode(length, height));
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
		Display.setVSyncEnabled(false);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
		glClearDepth(1);                                       

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glViewport(0,0, length, height);
		glMatrixMode(GL_MODELVIEW);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, length, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		awtFont[0] = new Font("Times New Roman", Font.BOLD, 16);
		font[0] = new TrueTypeFont(awtFont[0], true);
		awtFont[1] = new Font("Times New Roman", Font.BOLD, 24);
		font[1] = new TrueTypeFont(awtFont[1], true);

	}

	public static int getHeight() {
		return height;
	}

	public static int getLength() {
		return length;
	}

}
