package Village;

import java.awt.Font;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Quad {
	
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
	
	public static void drawLine(Point point1, Point point2, Color color){		
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
		Main.font[size].drawString(x, y, text1 , Color.white, 2, 4);
		Main.font[size].drawString(x, y, text1 , Color.white);
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}
}
