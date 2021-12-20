import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
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

    String textureNames[] = {"sprites/pacman-right/2.png", "Background.jpeg", "sprites/extra/dot.png"};
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
                        GL.GL_RGBA, texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture[i].getPixels());
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        addPoints();
    }

    private void addPoints() {

        pointsList.add(new points(0, 0, 0, 0, 0, 0));
        pointsList.add(new points(45, 38.75, 0, 0, 50, 2));
        pointsList.add(new points(61.25, 38.75, 3, 67, 1, -1));
        pointsList.add(new points(61.25, 48, 4, 2, -1, 17));
        pointsList.add(new points(61.25, 58, -1, 3, 5, -1));
        pointsList.add(new points(50.75, 58, 6, -1, 53, 4));
        pointsList.add(new points(50.75, 68, -1, 5, -1, 7));
        pointsList.add(new points(61.75, 68, 8, -1, 6, -1));
        pointsList.add(new points(61.75, 77.5, -1, 7, 9, 16));
        pointsList.add(new points(51, 77.5, 10, -1, 57, 8));
        pointsList.add(new points(51, 90.5, -1, 9, -1, 11));
        pointsList.add(new points(71.5, 90.5, -1, 16, 10, 12));
        pointsList.add(new points(89.25, 90.5, -1, 13, 11, -1));
        pointsList.add(new points(89.25, 77.5, 12, 14, 16, -1));
        pointsList.add(new points(89.25, 67.75, 13, -1, 15, -1));
        pointsList.add(new points(72, 67.75, 16, 17, -1, 14));
        pointsList.add(new points(72, 77.5, 11, 15, 8, 13));
        pointsList.add(new points(72, 48, 15, 19, 3, 18));
        pointsList.add(new points(90, 48, -1, -1, 17, -1));
        pointsList.add(new points(72, 29, 17, 30, 67, 20));
        pointsList.add(new points(90, 29, -1, 21, 19, -1));
        pointsList.add(new points(90, 19, 20, -1, 22, -1));
        pointsList.add(new points(82, 19, -1, 23, -1, 21));
        pointsList.add(new points(82, 10, 22, -1, 31, 24));
        pointsList.add(new points(90, 10, -1, 25, 23, -1));
        pointsList.add(new points(90, 0, 24, -1, 26, -1));
        pointsList.add(new points(50.5, 0, 27, -1, 38, 25));
        pointsList.add(new points(50.5, 10, -1, 26, -1, 28));
        pointsList.add(new points(61.5, 10, 29, -1, 27, -1));
        pointsList.add(new points(61.5, 19, 19, 31, 29, -1));
        pointsList.add(new points(72, 19, -1, -1, -1, -1));
        pointsList.add(new points(72, 10, -1, -1, -1, -1));
        pointsList.add(new points(51, 29, -1, -1, -1, -1));
        pointsList.add(new points(51, 19, -1, -1, -1, -1));
        pointsList.add(new points(40, 19, -1, -1, -1, -1));
        pointsList.add(new points(29, 19, -1, -1, -1, -1));
        pointsList.add(new points(29, 10, -1, -1, -1, -1));
        pointsList.add(new points(40, 10, -1, -1, -1, -1));
        pointsList.add(new points(40, 0, -1, -1, -1, -1));
        pointsList.add(new points(0, 0, -1, -1, -1, -1));
        pointsList.add(new points(0, 10, -1, -1, -1, -1));
        pointsList.add(new points(8, 10, -1, -1, -1, -1));
        pointsList.add(new points(8, 19, -1, -1, -1, -1));
        pointsList.add(new points(0, 19, -1, -1, -1, -1));
        pointsList.add(new points(0, 29, -1, -1, -1, -1));
        pointsList.add(new points(18, 29, -1, -1, -1, -1));
        pointsList.add(new points(18, 19, -1, -1, -1, -1));
        pointsList.add(new points(18, 10, -1, -1, -1, -1));
        pointsList.add(new points(29, 29, -1, -1, -1, -1));
        pointsList.add(new points(40, 29, -1, -1, -1, -1));
        pointsList.add(new points(29, 38.5, -1, -1, -1, -1));
        pointsList.add(new points(29, 48, -1, -1, -1, -1));
        pointsList.add(new points(29, 58, -1, -1, -1, -1));
        pointsList.add(new points(40, 58, -1, -1, -1, -1));
        pointsList.add(new points(40, 68, -1, -1, -1, -1));
        pointsList.add(new points(29, 68, -1, -1, -1, -1));
        pointsList.add(new points(29, 77.5, -1, -1, -1, -1));
        pointsList.add(new points(40, 77.5, -1, -1, -1, -1));
        pointsList.add(new points(40, 90.5, -1, -1, -1, -1));
        pointsList.add(new points(18, 90.5, -1, -1, -1, -1));
        pointsList.add(new points(0, 90.5, -1, -1, -1, -1));
        pointsList.add(new points(0, 77.5, -1, -1, -1, -1));
        pointsList.add(new points(0, 68, -1, -1, -1, -1));
        pointsList.add(new points(18, 68, -1, -1, -1, -1));
        pointsList.add(new points(18, 77.5, -1, -1, -1, -1));
        pointsList.add(new points(18, 48, -1, -1, -1, -1));
        pointsList.add(new points(0, 48, -1, -1, -1, -1));
        pointsList.add(new points(61.25, 29, -1, -1, -1, -1));

    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        handleKeyPress();
        DrawSprite(gl, x, y, 1);
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);    // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glScaled(0.05, 0.1, 1);
        // Front Face
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


    public void DrawSprite(GL gl, double x, double y, float scale){
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


        drawdot(gl);

    }

    private void drawdot(GL gl) {

        for (int i = 1; i < pointsList.size(); i++) {
            double x = pointsList.get(i).getX();
            double y = pointsList.get(i).getY();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]);
            gl.glPushMatrix();
            gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
            gl.glScaled(0.05, 0.05, 1);
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
    }

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            for (int i = 0; i < LEFT.length; i++) {
                if (((x == LEFT[i][0] && y == LEFT[i][1]))) {
                    keyBits.clear(keyCode);
                    IS = true;
                }
                if (x < 0.25) {
                } else {

                    if (!IS) x -= speed;
                    System.out.println(x + ",,," + y);
                }
            }}
        else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            for (int i = 0; i < RIGHT.length; i++) {
                if (((x == RIGHT[i][0] && y == RIGHT[i][1]))) {
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if(x>90.5){}else{
                if (!IS) x += speed;
                System.out.println(x + ",,," + y);
            }}
        else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            for (int i = 0; i < DOWN.length; i++) {
                if (((x == DOWN[i][0] && y ==DOWN[i][1]))){
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if(y<0.25){}else{
                if (!IS) y -= speed;
                System.out.println(x+",,,"+y);
            }}
        else if (isKeyPressed(KeyEvent.VK_UP)) {
            for (int i = 0; i < TOP.length; i++) {
                if (((x == TOP[i][0] && y == TOP[i][1]))){
                    keyBits.clear(keyCode);
                    IS = true;
                }
            }
            if(y>90.5){}else{
                if (!IS) y += speed;
                System.out.println(x+",,,"+y);
            }}
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