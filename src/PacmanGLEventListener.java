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

public class PacmanGLEventListener implements GLEventListener , KeyListener {
    ArrayList<points> pointsList = new ArrayList<>();
    final int maxWidth = 100 , maxHeight = 100;
    final double speed = 0.25;
    double x = 45 , y = 38.75;

    String assetsFolderName = "Assets/";
    String textureNames[] = {"sprites/pacman-right/2.png","Background.jpeg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    BitSet keyBits = new BitSet(256);

    ///For Test
    double[][] TOP   = {{61.25,58} , {29.25,58}};
    double[][] DOWN  = {{29.25,29.25} , {61.25,29.25}};
    double[][] LEFT  = {{29.25,38.75} , {29.25,58}};
    double[][] RIGHT = {{61.25,38.75} , {61.25,58}};
    boolean IS = false;
    int keyCode;

    //GLEventListener Methods
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

    //KeyListener Methods
    public void keyPressed(final KeyEvent event) {
        keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }
    public void keyReleased(final KeyEvent event) {
        keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }
    public void keyTyped(final KeyEvent event) {
    }
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    //Our Methods
    private void handleKeyPress() {

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
    private void DrawSprite(GL gl,double x, double y, float scale){
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
    private void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);
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
    private void addPoints() {
        pointsList.add(new points( 0,0    , 0    , 0 , 0 , 0 , 0 ));
        pointsList.add(new points( 1,38.75, 45   , -1, -1, 50, 2 ));
        pointsList.add(new points( 2,61.25, 38.75, 3 , 67, 1 , -1));
        pointsList.add(new points( 3,61.25, 48   , 4 , 2 , -1, 17));
        pointsList.add(new points( 4,61.25, 58   , -1, 3 , 5 , -1));
        pointsList.add(new points( 5,50.75, 58   , 6 , -1, 53, 4 ));
        pointsList.add(new points( 6,50.75, 68   , -1, 5 , -1, 7 ));
        pointsList.add(new points( 7,61.75, 68   , 8 , -1, 6 , -1));
        pointsList.add(new points( 8,61.75, 77.5 , -1, 7 , 9 , 16));
        pointsList.add(new points( 9,51   , 77.5 , 10, -1, 57, 8 ));
        pointsList.add(new points(10,51   , 90.5 , -1, 9 , -1, 11));
        pointsList.add(new points(11,71.5 , 90.5 , -1, 16, 10, 12));
        pointsList.add(new points(12,89.25, 90.5 , -1, 13, 11, -1));
        pointsList.add(new points(13,89.25, 77.5 , 12, 14, 16, -1));
        pointsList.add(new points(14,89.25, 67.75, 13, -1, 15, -1));
        pointsList.add(new points(15,72   , 67.75, 16, 17, -1, 14));
        pointsList.add(new points(16,72   , 77.5 , 11, 15, 8 , 13));
        pointsList.add(new points(17,72   , 48   , 15, 19, 3 , 18));
        pointsList.add(new points(18,90   , 48   , -1, -1, 17, -1));
        pointsList.add(new points(19,72   , 29   , 17, 30, 67, 20));
        pointsList.add(new points(20,90   , 29   , -1, 21, 19, -1));
        pointsList.add(new points(21,90   , 19   , 20, -1, 22, -1));
        pointsList.add(new points(22,82   , 19   , -1, 23, -1, 21));
        pointsList.add(new points(23,82   , 10   , 22, -1, 31, 24));
        pointsList.add(new points(24,90   , 10   , -1, 25, 23, -1));
        pointsList.add(new points(25,90   , 0    , 24, -1, 26, -1));
        pointsList.add(new points(26,50.5 , 0    , 27, -1, 38, 25));
        pointsList.add(new points(27,50.5 , 10   , -1, 26, -1, 28));
        pointsList.add(new points(28,61.5 , 10   , 29, -1, 27, -1));
        pointsList.add(new points(29,61.5 , 19   , -1, 28, 33, 30));
        pointsList.add(new points(30,72   , 19   , 19, 31, 29, -1));
        pointsList.add(new points(31,72   , 10   , 30, -1, -1, 23));
        pointsList.add(new points(32,51   , 29   , -1, 33, -1, 67));
        pointsList.add(new points(33,51   , 19   , 32, -1, 34, 29));
        pointsList.add(new points(34,40   , 19   , 49, -1, 35, 33));
        pointsList.add(new points(35,29   , 10   , -1, 36, 46, 34));
        pointsList.add(new points(36,29   , 29   , 35, -1, -1, 37));
        pointsList.add(new points(37,40   , 19   , -1, 38, 36, -1));
        pointsList.add(new points(38,40   , 19   , 37, -1, 39, 26));
        pointsList.add(new points(39,0    , 19   , 40, -1, -1, 38));
        pointsList.add(new points(40,0    , 10   , -1, 39, -1, 41));
        pointsList.add(new points(41,8    , 10   , 42, -1, 40, 47));
        pointsList.add(new points(42,8    , 0    , -1, 41, 43, -1));
        pointsList.add(new points(43,0    , 0    , 44, -1, -1, 42));
        pointsList.add(new points(44,0    , 10   , -1, 43, -1, 45));
        pointsList.add(new points(45,18   , 10   , 65, 46, 44, 48));
        pointsList.add(new points(46,18   , 19   , 45, 47, -1, 35));
        pointsList.add(new points(47,18   , 19   , 46, -1, 41, -1));
        pointsList.add(new points(48,29   , 29   , 50, -1, 45, 49));
        pointsList.add(new points(49,40   , 29   , -1, 34, 48, -1));
        pointsList.add(new points(50,29   , 19   , 51, 48, -1, -1));
        pointsList.add(new points(51,29   , 10   , 52, 50, 65, -1));
        pointsList.add(new points(52,29   , 29   , -1, 51, -1, 53));
        pointsList.add(new points(53,40   , 29   , 54, -1, 52, 5 ));
        pointsList.add(new points(54,40   , 38.75, -1, 53, 55, -1));
        pointsList.add(new points(55,29   , 48   , 56, -1, -1, 54));
        pointsList.add(new points(56,29   , 58   , -1, 55, 64, 57));
        pointsList.add(new points(57,40   , 58   , 58, -1, 56, 9 ));
        pointsList.add(new points(58,40   , 68   , -1, 57, 59, -1));
        pointsList.add(new points(59,18   , 68   , -1, 64, 61, 56));
        pointsList.add(new points(60,0    , 77.5 , -1, 61, -1, 59));
        pointsList.add(new points(61,0    , 77.5 , 60, 62, -1, 64));
        pointsList.add(new points(62,0    , 68   , 61, -1, -1, 63));
        pointsList.add(new points(63,18   , 68   , 64, 65, 62, -1));
        pointsList.add(new points(64,18   , 77.5 , 59, 63, 61, 56));
        pointsList.add(new points(65,18   , 48   , 63, 45, 66, 51));
        pointsList.add(new points(66,0    , 48   , -1, -1, -1, 65));
        pointsList.add(new points(67,61.25, 29   , 2 , -1, 32, 19));
    }
}