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

public class PacmanGLEventListener implements GLEventListener, KeyListener {
    ArrayList<points> pointsList = new ArrayList<>();
    ArrayList<Integer> KeyL = new ArrayList<>();
    int n = 0;
    final int maxWidth = 100, maxHeight = 100;
    final double speed = 0.25;
    double x, y;
    int index = 1;
    int keyCode, animation = 0, face = 0;
    int count=0;

    String assetsFolderName = "Assets/";
    String textureNames[] = {"sprites/pacman-right/1.png", "sprites/pacman-right/2.png", "sprites/pacman-right/3.png",
            "sprites/pacman-left/1.png", "sprites/pacman-left/2.png", "sprites/pacman-left/3.png",
            "sprites/pacman-up/1.png", "sprites/pacman-up/2.png", "sprites/pacman-up/3.png",
            "sprites/pacman-down/1.png", "sprites/pacman-down/2.png", "sprites/pacman-down/3.png",
            "sprites/extra/dot.png", "Background.jpeg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];


    //GLEventListener Methods
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName
                        + "//" + textureNames[i], true);
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
        x = pointsList.get(index).getX();
        y = pointsList.get(index).getY();


    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        if (KeyL.size() != 0)
            handleKeyPress();
        DrawSprite(gl, x, y, 1, animation);

            double x1, x2, y1, y2, length;
        for (int i = 2; i <pointsList.size()-1; i++) {
            x1 = pointsList.get(i).getX();
            y1 = pointsList.get(i).getY();
            x2 = pointsList.get(i + 1).getX();
            y2 = pointsList.get(i + 1).getY();
            if (x1 == x2) {
                if (y1 >= y2) {
                    for (double j = y2; j < y1; j += 1.5)
                        drawdot(gl, x1, j);
                } else {
                    for (double j = y1; j < y2; j += 1.5)
                        drawdot(gl, x1, j);
                }
            } else if (y1 == y2) {
                if (x1 >= x2) {
                    for (double j = x2; j < x1; j += 1.5)
                        drawdot(gl, j, y1);
                } else {
                    for (double j = x1; j < x2; j += 1.5)
                        drawdot(gl, j, y1);
                }
            }}

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    //KeyListener Methods
    public void keyPressed(final KeyEvent event) {
        keyCode = event.getKeyCode();
        KeyL.add(keyCode);
        if (keyCode == 38)
            System.out.println("Direction is Top");
        else if (keyCode == 40)
            System.out.println("Direction is Down");
        else if (keyCode == 39)
            System.out.println("Direction is Right");
        else if (keyCode == 37)
            System.out.println("Direction is Left");
    }

    public void keyReleased(final KeyEvent event) {
    }

    public void keyTyped(final KeyEvent event) {
    }

    public boolean isKeyPressed(int keyC) {
        return keyC == KeyL.get(n);
    }

    //Our Methods
    private void handleKeyPress() {
        //Up
        if (isKeyPressed(38)) {
            int T = pointsList.get(index).getTop();
            if (T != -1) {
                if (pointsList.get(T).getY() == y) {
                    if (n < KeyL.size() - 1) {
                        n++;
                    }
                    index = T;
                } else {
                    y += speed;
                    face = 6;
                    animation++;

                }
            } else {
                if (n < KeyL.size() - 1) {
                    n++;
                }
            }
        }
        //Down
        else if (isKeyPressed(40)) {
            int B = pointsList.get(index).getBottom();
            if (B != -1) {
                if (pointsList.get(B).getY() == y) {
                    if (n < KeyL.size() - 1) {
                        n++;
                    }
                    index = B;
                } else {
                    y -= speed;
                    face = 9;
                    animation++;
                }
            } else {
                if (n < KeyL.size() - 1) {
                    n++;
                }
            }
        }
        //Left
        else if (isKeyPressed(37)) {
            int L = pointsList.get(index).getLeft();
            if (L != -1) {
                if (pointsList.get(L).getX() == x) {
                    if (n < KeyL.size() - 1) {
                        n++;
                    }
                    index = L;
                } else {
                    x -= speed;
                    face = 3;
                    animation++;
                }
            } else {
                if (n < KeyL.size() - 1) {
                    n++;
                }
            }
        }
        //Right
        else if (isKeyPressed(39)) {
            int R = pointsList.get(index).getRight();
            if (R != -1) {
                if (pointsList.get(R).getX() == x) {
                    if (n < KeyL.size() - 1) {
                        n++;
                    }
                    index = R;
                } else {
                    x += speed;
                    face = 0;
                    animation++;
                }
            } else {
                if (n < KeyL.size() - 1) {
                    n++;
                }
            }
        }

    }

    private void DrawSprite(GL gl, double x, double y, float scale, int animation) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[face + animation % 3]);
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.05 * scale, 0.05 * scale, 1);
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
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureNames.length - 1]);
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

    private void drawdot(GL gl, double x, double y) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureNames.length - 2]);
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

    private void addPoints() {
        pointsList.add(new points(0, 0, 0, 0, 0, 0, 0));
        pointsList.add(new points(1, 45, 38.75, -1, -1, 50, 2));
        pointsList.add(new points(2, 61.25, 38.75, 3, 67, 1, -1));
        pointsList.add(new points(3, 61.25, 48, 4, 2, -1, 17));
        pointsList.add(new points(4, 61.25, 58, -1, 3, 5, -1));
        pointsList.add(new points(5, 50.75, 58, 6, -1, 53, 4));
        pointsList.add(new points(6, 50.75, 68, -1, 5, -1, 7));
        pointsList.add(new points(7, 61.75, 68, 8, -1, 6, -1));
        pointsList.add(new points(8, 61.75, 77.5, -1, 7, 9, 16));
        pointsList.add(new points(9, 51, 77.5, 10, -1, 57, 8));
        pointsList.add(new points(10, 51, 90.5, -1, 9, -1, 11));
        pointsList.add(new points(11, 71.5, 90.5, -1, 16, 10, 12));
        pointsList.add(new points(12, 89.25, 90.5, -1, 13, 11, -1));
        pointsList.add(new points(13, 89.25, 77.5, 12, 14, 16, -1));
        pointsList.add(new points(14, 89.25, 67.75, 13, -1, 15, -1));
        pointsList.add(new points(15, 72, 67.75, 16, 17, -1, 14));///
        pointsList.add(new points(16, 72, 77.5, 11, 15, 8, 13));/////
        pointsList.add(new points(17, 72, 48, 15, 19, 3, 18));//////
        pointsList.add(new points(18, 90, 48, -1, -1, 17, 66));
        pointsList.add(new points(19, 72, 29, 17, 30, 67, 20));/////
        pointsList.add(new points(20, 90, 29, -1, 21, 19, -1));
        pointsList.add(new points(21, 90, 19, 20, -1, 22, -1));
        pointsList.add(new points(22, 82, 19, -1, 23, -1, 21));
        pointsList.add(new points(23, 82, 10, 22, -1, 31, 24));
        pointsList.add(new points(24, 90, 10, -1, 25, 23, -1));
        pointsList.add(new points(25, 90, 0, 24, -1, 26, -1));
        pointsList.add(new points(26, 50.5, 0, 27, -1, 38, 25));
        pointsList.add(new points(27, 50.5, 10, -1, 26, -1, 28));
        pointsList.add(new points(28, 61.5, 10, 29, -1, 27, -1));
        pointsList.add(new points(29, 61.5, 19, -1, 28, 33, 30));
        pointsList.add(new points(30, 72, 19, 19, 31, 29, -1));
        pointsList.add(new points(31, 72, 10, 30, -1, -1, 23));
        pointsList.add(new points(32, 51, 29, -1, 33, -1, 67));
        pointsList.add(new points(33, 51, 19, 32, -1, 34, 29));
        pointsList.add(new points(34, 40, 19, 49, -1, 35, 33));
        pointsList.add(new points(35, 29, 19, -1, 36, 46, 34));
        pointsList.add(new points(36, 29, 10, 35, -1, -1, 37));
        pointsList.add(new points(37, 40, 10, -1, 38, 36, -1));
        pointsList.add(new points(38, 40, 0, 37, -1, 39, 26));
        pointsList.add(new points(39, 0, 0, 40, -1, -1, 38));
        pointsList.add(new points(40, 0, 10, -1, 39, -1, 41));
        pointsList.add(new points(41, 8, 10, 42, -1, 40, 47));
        pointsList.add(new points(42, 8, 19, -1, 41, 43, -1));
        pointsList.add(new points(43, 0, 19, 44, -1, -1, 42));
        pointsList.add(new points(44, 0, 29, -1, 43, -1, 45));
        pointsList.add(new points(45, 18, 29, 65, 46, 44, 48));
        pointsList.add(new points(46, 18, 19, 45, 47, -1, 35));
        pointsList.add(new points(47, 18, 10, 46, -1, 41, -1));
        pointsList.add(new points(48, 29, 29, 50, -1, 45, 49));
        pointsList.add(new points(49, 40, 29, -1, 34, 48, -1));
        pointsList.add(new points(50, 29, 38.75, 51, 48, -1, 1));
        pointsList.add(new points(51, 29, 48, 52, 50, 65, -1));
        pointsList.add(new points(52, 29, 58, -1, 51, -1, 53));
        pointsList.add(new points(53, 40, 58, 54, -1, 52, 5));
        pointsList.add(new points(54, 40, 68, -1, 53, 55, -1));
        pointsList.add(new points(55, 29, 68, 56, -1, -1, 54));
        pointsList.add(new points(56, 29, 77.5, -1, 55, 64, 57));
        pointsList.add(new points(57, 40, 77.5, 58, -1, 56, 9));
        pointsList.add(new points(58, 40, 90.5, -1, 57, 59, -1));
        pointsList.add(new points(59, 18, 90.5, -1, 64, 60, 58));
        pointsList.add(new points(60, 0, 90.5, -1, 61, -1, 59));
        pointsList.add(new points(61, 0, 77.5, 60, 62, -1, 64));
        pointsList.add(new points(62, 0, 68, 61, -1, -1, 63));
        pointsList.add(new points(63, 18, 68, 64, 65, 62, -1));
        pointsList.add(new points(64, 18, 77.5, 59, 63, 61, 56));
        pointsList.add(new points(65, 18, 48, 63, 45, 66, 51));

        pointsList.add(new points(66, 0, 48, -1, -1, 18, 65));
        pointsList.add(new points(67, 52, 29, -1, -1, 32, 19));
        pointsList.add(new points(68, 55, 29, -1, -1, 32, 19));
        pointsList.add(new points(69, 58, 29, -1, -1, 32, 19));
        pointsList.add(new points(70, 61, 29, -1, -1, 32, 19));
        pointsList.add(new points(71, 64, 29, -1, -1, 32, 19));

        pointsList.add(new points(72, 67, 29, 2, -1, 32, 19));
        pointsList.add(new points(73, 70, 29, 2, -1, 32, 19));
        pointsList.add(new points(74, 72, 29, 2, -1, 32, 19));

        pointsList.add(new points(75, 72, 26, 19, 30, -1, -1));
        pointsList.add(new points(76, 72, 19, 19, 30, -1, -1));


        pointsList.add(new points(77, 74, 10, -1, -1, 31, 24));
        pointsList.add(new points(78, 81, 10, -1, -1, 31, 24));

        pointsList.add(new points(79, 18, 30, 65, 45, -1, -1));
        pointsList.add(new points(80, 18, 48, 65, 45, -1, -1));

        pointsList.add(new points(81, 11, 10, -1, -1, 41, 47));
        pointsList.add(new points(82, 17, 10, -1, -1, 41, 47));

        pointsList.add(new points(83, 20, 29, -1, -1, 45, 48));
        pointsList.add(new points(84, 28, 29, -1, -1, 45, 48));

        pointsList.add(new points(85, 29, 30, 50, 48, -1, -1));
        pointsList.add(new points(86, 29, 37.75, 50, 48, -1, -1));

        pointsList.add(new points(87, 20, 19, -1, -1, 46, 35));
        pointsList.add(new points(88, 30, 19, -1, -1, 46, 35));

        pointsList.add(new points(89, 40, 20, 49, 34, -1, -1));
        pointsList.add(new points(90, 40, 30, 49, 34, -1, -1));


        pointsList.add(new points(91, 52, 19, -1, -1, 33, 29));
        pointsList.add(new points(92, 60, 19, -1, -1, 33, 29));

        pointsList.add(new points(93, 61.25, 37.75, 2, 73, -1, -1));
        pointsList.add(new points(94, 61.25, 29, 2, 73, -1, -1));

        pointsList.add(new points(95, 72, 30, 17, 19, -1, -1));
        pointsList.add(new points(96, 72, 47, 17, 19, -1, -1));

        pointsList.add(new points(97, 62, 48, -1, -1, 3, 17));
        pointsList.add(new points(98, 70, 48, -1, -1, 3, 17));

        pointsList.add(new points(99, 19, 48.1, -1, -1, 65, 51));
        pointsList.add(new points(100, 28, 48.1, -1, -1, 65, 51));

        pointsList.add(new points(101, 19, 77.5, -1, -1, 64, 56));
        pointsList.add(new points(102, 28, 77.5, -1, -1, 64, 56));

        pointsList.add(new points(103, 62.75, 77.5, -1, -1, 8, 16));
        pointsList.add(new points(104, 71, 77.5, -1, -1, 8, 16));

    }
}