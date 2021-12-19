import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * For our purposes only two of the GLEventListeners matter. Those would be
 * init() and display().
 */
public class PacmanGLEventListener implements GLEventListener , KeyListener {

    protected String assetsFolderName = "Assets/";
    int maxWidth = 100 , maxHeight = 100;
    double x = 45 , y = 38.75 , speed = 0.25;

    double[][] TOP   = {{61.25,58} , {29.25,58}};
    double[][] DOWN  = {{29.25,29.25} , {61.25,29.25}};
    double[][] LEFT  = {{29.25,38.75} , {29.25,58}};
    double[][] RIGHT = {{61.25,38.75} , {61.25,58}};
    ArrayList<points> pointsList = new ArrayList<>();



    String textureNames[] = {"sprites/pacman-right/2.png","Background.jpeg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    public BitSet keyBits = new BitSet(256);
    int keyCode;
    boolean IS = false;

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName
                        +"//"+textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,texture[i].getWidth(),texture[i].getHeight(),
                        GL.GL_RGBA,GL.GL_UNSIGNED_BYTE,texture[i].getPixels());
            }
            catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

       // pointsList.add(new points(1.2,1.3,null,null,null,null));

    }
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        Draw(gl);
        handleKeyPress();
        DrawSprite(gl,x,y,1);
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void Draw(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureNames.length-1]);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
    public void DrawSprite(GL gl,double x, double y, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glScaled(0.05*scale, 0.05*scale, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            for (int i = 0; i < LEFT.length; i++) {
                if (((x == LEFT[i][0] && y ==LEFT[i][1]))){
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if (!IS) x -=speed;
            System.out.println(x+",,,"+y);
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            for (int i = 0; i < RIGHT.length; i++) {
                if (((x == RIGHT[i][0] && y == RIGHT[i][1]))) {
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if (!IS) x += speed;
            System.out.println(x + ",,," + y);
        }
        else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            for (int i = 0; i < DOWN.length; i++) {
                if (((x == DOWN[i][0] && y ==DOWN[i][1]))){
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if (!IS) y -= speed;
            System.out.println(x+",,,"+y);
        }
        else if (isKeyPressed(KeyEvent.VK_UP)) {
            for (int i = 0; i < TOP.length; i++) {
                if (((x == TOP[i][0] && y == TOP[i][1]))){
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if (!IS) y += speed;
            System.out.println(x+",,,"+y);
        }
        IS = false;
    }
    public void keyPressed(final KeyEvent event) {
        keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }
    public void keyReleased(final KeyEvent event) {
        keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
    public void keyTyped(final KeyEvent event) {
    }
}