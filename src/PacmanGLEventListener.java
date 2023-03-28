import Texture.TextureReader;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class PacmanGLEventListener implements GLEventListener, KeyListener , MouseListener {
    ArrayList<Points> pointsList = new ArrayList<>();
    ArrayList<Points> fruitsList = new ArrayList<>();
    ArrayList<Texts> TextsList = new ArrayList<>();
    ArrayList<Integer> KeyL = new ArrayList<>();

    private final int maxWidth = 100, maxHeight = 100; final double speed = 0.25;
    private static final int BUFFER_SIZE = 4096;
    int RandomR = 37 + (int)(Math.random()*4);;
    int RandomB = 37 + (int)(Math.random()*4);;
    int RandomO = 37 + (int)(Math.random()*4);;

    int levelNo = 1;
    int angle1 = 0 , angle2 = 0 , n = 0;
    double x , y , xR , yR , xB , yB , xO , yO;
    int index = 1 , indexR = 20 , indexB = 30 , indexO = 40;
    int keyCode, animation = 0, face = 0;
    int score = 0 , lvScore = 780;
    boolean start = false;
    boolean gameOver = false;

    String assetsFolderName = "Assets/";
    static String[] textureNames = {
        "sprites/pacman-right/1.png", "sprites/pacman-right/2.png"   , "sprites/pacman-right/3.png",
        "sprites/pacman-left/1.png" , "sprites/pacman-left/2.png"    , "sprites/pacman-left/3.png" ,
        "sprites/pacman-up/1.png"   , "sprites/pacman-up/2.png"      , "sprites/pacman-up/3.png",
        "sprites/pacman-down/1.png" , "sprites/pacman-down/2.png"    , "sprites/pacman-down/3.png",
        "sprites/extra/dot.png"     , "sprites/extra/apple.png"      , "Ready.png",
        "GameOver.png"              , "Win.png" , "menu.jpg"         ,"levels.png" ,
        "sprites/ghosts/blinky.png" ,"sprites/ghosts/pinky.png"      ,"sprites/ghosts/clyde.png",
        "Background.jpeg"
    };
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    static int[] textures = new int[textureNames.length];

    //GLEventListener Methods
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(GL_TEXTURE_2D,
                        GL.GL_RGBA, texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture[i].getPixels());
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        addPoints();
        playSound("Assets\\sounds\\pacman_beginning.wav",0);
        x  = pointsList.get(index).getX()-5; y = pointsList.get(index).getY();
        xR = pointsList.get(indexR).getX(); yR = pointsList.get(indexR).getY();
        xB = pointsList.get(indexB).getX(); yB = pointsList.get(indexB).getY();
        xO = pointsList.get(indexO).getX(); yO = pointsList.get(indexO).getY();
    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);

        if (KeyL.size() != 0) {
            handleKeyPress();
        }
        for (int i = 0; i < levelNo; i++) {
            handleKeyPressEnemy();
        }

        if (gameOver) {
            playSound("Assets\\sounds\\pacman_death.wav", 1);
            gameOver = false;
        }

        if (start) {
            drawDotAndFruits(gl);
            DrawSprite(gl, x, y, animation);
            updateScoreAndLevel(gl);
        }

        if (score == lvScore) {
            playSound("Assets\\sounds\\Victory.wav", 2);
            score = 0;
        }
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    //KeyListener Methods
    public void keyPressed(final KeyEvent event) {

           keyCode= event.getKeyCode();
           if(keyCode==38||keyCode==37|| keyCode==39||keyCode==40) {
               KeyL.add(keyCode);
           }

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

    //MouseListener Methods
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        System.out.println("("+x+","+y+")");

        if (x > 353 && x < 477 && y < 597 && y > 539 && !start) {
            start =true;
            levelNo = 1;
        }else  if (x > 355 && x < 485 && y < 680 && y > 624 && !start) {
            start =true;
            levelNo = 2;
        }else  if (x > 351 && x < 485 && y < 766 && y > 708 && !start) {
            start =true;
            levelNo = 3;
        }

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    //Our Methods
    private void handleKeyPress() {
        //Up
        if (isKeyPressed(38)) {
            int T = pointsList.get(index).getTop();
            if (T != -1) {
                if (pointsList.get(T).getY() == y) {
                    checkN();
                    index = T;
                }
                else {
                    y += speed; face = 6; animation++;
                }
            }
            else
                checkN();
        }
        //Down
        else if (isKeyPressed(40)) {
            int B = pointsList.get(index).getBottom();
            if (B != -1) {
                if (pointsList.get(B).getY() == y) {
                    checkN();
                    index = B;
                }
                else {
                    y -= speed; face = 9; animation++;
                }
            }
            else
                checkN();
        }
        //Left
        else if (isKeyPressed(37)) {
            int L = pointsList.get(index).getLeft();
            if (L == -2){
                index = 18;
                x = pointsList.get(index).getX();
                y = pointsList.get(index).getY();
                return;
            }
            if (L != -1) {
                if (pointsList.get(L).getX() == x) {
                    checkN();
                    index = L;
                }
                else {
                    x -= speed; face = 3; animation++;
                }
            }
            else
                checkN();
        }
        //Right
        else if (isKeyPressed(39)) {
            int R = pointsList.get(index).getRight();
            if (R == -2){
                index = 66;
                x = pointsList.get(index).getX();
                y = pointsList.get(index).getY();
                return;
            }
            if (R != -1) {
                if (pointsList.get(R).getX() == x) {
                    checkN();
                    index = R;
                }
                else {
                    x += speed; face = 0; animation++;
                }
            }
            else
                checkN();
        }
    }

    private void handleKeyPressEnemy() {
        //////Red ANMI
        //Up
        if (RandomR == 38) {
            int T = pointsList.get(indexR).getTop();
            if (T != -1) {
                if (pointsList.get(T).getY() == yR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = T;
                }
                else {
                    yR += speed;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }
        //Down
        else if (RandomR == 40) {
            int B = pointsList.get(indexR).getBottom();
            if (B != -1) {
                if (pointsList.get(B).getY() == yR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = B;
                }
                else {
                    yR -= speed;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }
        //Left
        else if (RandomR == 37) {
            int L = pointsList.get(indexR).getLeft();
            if (L == -2){
                indexR = 18;
                xR = pointsList.get(indexR).getX();
                yR = pointsList.get(indexR).getY();
                return;
            }
            if (L != -1) {
                if (pointsList.get(L).getX() == xR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = L;
                }
                else {
                    xR -= speed;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }
        //Right
        else if (RandomR == 39) {
            int R = pointsList.get(indexR).getRight();
            if (R == -2){
                indexR = 66;
                xR = pointsList.get(indexR).getX();
                yR = pointsList.get(indexR).getY();
                return;
            }
            if (R != -1) {
                if (pointsList.get(R).getX() == xR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = R;
                }
                else {
                    xR += speed;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }


        //////B ANMI
        if (RandomB == 38) {
            int T = pointsList.get(indexB).getTop();
            if (T != -1) {
                if (pointsList.get(T).getY() == yB) {
                    RandomB = 37 + (int)(Math.random()*4);
                    indexB = T;
                }
                else {
                    yB += speed;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }
        //Down
        else if (RandomB == 40) {
            int B = pointsList.get(indexB).getBottom();
            if (B != -1) {
                if (pointsList.get(B).getY() == yB) {
                    RandomB= 37 + (int)(Math.random()*4);
                    indexB = B;
                }
                else {
                    yB -= speed;
                }
            }
            else
                RandomB= 37 + (int)(Math.random()*4);
        }
        //Left
        else if (RandomB== 37) {
            int L = pointsList.get(indexB).getLeft();
            if (L == -2){
                indexB= 18;
                xB= pointsList.get(indexB).getX();
                yB = pointsList.get(indexB).getY();
                return;
            }
            if (L != -1) {
                if (pointsList.get(L).getX() == xB) {
                    RandomB = 37 + (int)(Math.random()*4);
                    indexB = L;
                }
                else {
                    xB-= speed;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }
        //Right
        else if (RandomB == 39) {
            int R = pointsList.get(indexB).getRight();
            if (R == -2){
                indexB= 66;
                xB = pointsList.get(indexB).getX();
                yB = pointsList.get(indexB).getY();
                return;
            }
            if (R != -1) {
                if (pointsList.get(R).getX() == xB) {
                    RandomB= 37 + (int)(Math.random()*4);
                    indexB = R;
                }
                else {
                    xB += speed;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }

        ////ORANGE ANMI
        //Up
        if (RandomO == 38) {
            int T = pointsList.get(indexO).getTop();
            if (T != -1) {
                if (pointsList.get(T).getY() == yO) {
                    RandomO = 37 + (int)(Math.random()*4);
                    indexO = T;
                }
                else {
                    yO += speed;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
        //Down
        else if (RandomO == 40) {
            int B = pointsList.get(indexO).getBottom();
            if (B != -1) {
                if (pointsList.get(B).getY() == yO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO = B;
                }
                else {
                    yO -= speed;
                }
            }
            else
                RandomO= 37 + (int)(Math.random()*4);
        }
        //Left
        else if (RandomO== 37) {
            int L = pointsList.get(indexO).getLeft();
            if (L == -2){
                indexO= 18;
                xO= pointsList.get(indexO).getX();
                yO = pointsList.get(indexO).getY();
                return;
            }
            if (L != -1) {
                if (pointsList.get(L).getX() == xO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO= L;
                }
                else {
                    xO-= speed;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
        //Right
        else if (RandomO == 39) {
            int R = pointsList.get(indexO).getRight();
            if (R == -2){
                indexO= 66;
                xO = pointsList.get(indexO).getX();
                yO = pointsList.get(indexO).getY();
                return;
            }
            if (R != -1) {
                if (pointsList.get(R).getX() == xO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO = R;
                }
                else {
                    xO += speed;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
    }

    private void checkN(){
        if (n < KeyL.size() - 1) {
            n++;
        }
    }

    private boolean killRange() {
        return (Math.abs(y-yR) <= 5 && Math.abs(x-xR) <= 5) ||
               (Math.abs(y-yB) <= 5 && Math.abs(x-xB) <= 5) ||
               (Math.abs(y-yO) <= 5 && Math.abs(x-xO) <= 5);
    }
    private void DrawSprite(GL gl, double x, double y, int animation) {
        if(killRange()){
            KeyL.clear();
            gameOver = true;
            start = false;
            n=0;
        }

        for(int i = 0; i<pointsList.size();i++){
            if(x == pointsList.get(i).getX() && y == pointsList.get(i).getY() && !pointsList.get(i).isChecked()){
                playSound("Assets\\sounds\\pacman_chomp.wav",-1); score+=10; pointsList.get(i).setChecked(true);
            }
        }
        for(int i = 0; i<fruitsList.size();i++) {
            if (x == fruitsList.get(i).getX() && y == fruitsList.get(i).getY() && !fruitsList.get(i).isChecked()) {
                playSound("Assets\\sounds\\pacman_eatfruit.wav",-1); score+=20; fruitsList.get(i).setChecked(true);
            }
        }

        int me = face + animation % 3;
        double[][] textureIdx = {
            {me, x, y},
            {19, xR, yR},
            {20, xB, yB},
            {21, xO, yO},
        };
        for (double[] i : textureIdx) {
            gl.glEnable(GL.GL_BLEND);
                gl.glBindTexture(GL_TEXTURE_2D, textures[(int) i[0]]);
                gl.glPushMatrix();
                    gl.glTranslated(i[1] / (maxWidth / 2.0) - 0.9, i[2] / (maxHeight / 2.0) - 0.9, 0);
                    gl.glScaled(0.05 , 0.05 , 1);
                    gl.glBegin(GL.GL_QUADS);
                        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
                        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
                        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
                    gl.glEnd();
                gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }
    }

    private void DrawBackground(GL gl) {
        int i =0;
        if(start){i=textureNames.length-1;}else{ i=17;}

            //drawing game background or Drawing Menu Background based on i

            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL_TEXTURE_2D, textures[i]);    // Turn Blending On
            gl.glPushMatrix();
            gl.glBegin(GL.GL_QUADS);
            gl.glScaled(0.05, 0.1, 1);
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

            if(!start) {
                //Drawing levels image
                gl.glEnable(GL.GL_BLEND);
                gl.glBindTexture(GL_TEXTURE_2D, textures[18]);
                gl.glPushMatrix();
                gl.glTranslated(0, -0.6, 0);
                gl.glScaled(0.3, 0.3, 1);
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

                score = 0;
            }
    }

    private void drawDotAndFruits(GL gl) {
        angle2+=2;
        for (int i = 1; i < pointsList.size(); i++) {
            if(!pointsList.get(i).isChecked()) {
                double x = pointsList.get(i).getX();
                double y = pointsList.get(i).getY();
                gl.glEnable(GL.GL_BLEND);
                gl.glBindTexture(GL_TEXTURE_2D, textures[12]);
                gl.glPushMatrix();
                gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
                gl.glScaled(0.075, 0.075, 1);
                gl.glRotated((angle1++)%360,0,0,1);
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

        for (int i = 0; i < fruitsList.size(); i++) {
            if(!fruitsList.get(i).isChecked()) {
                double x = fruitsList.get(i).getX();
                double y = fruitsList.get(i).getY();
                gl.glEnable(GL.GL_BLEND);
                gl.glBindTexture(GL_TEXTURE_2D, textures[13]);
                gl.glPushMatrix();
                gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
                gl.glScaled(0.03, 0.03, 1);
                gl.glRotated(angle2%360,0,0,1);

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

        for (int i = 0; i < TextsList.size(); i++) {
            if(TextsList.get(i).isAppear()) {
                gl.glEnable(GL.GL_BLEND);
                gl.glBindTexture(GL_TEXTURE_2D, textures[TextsList.get(i).getIndex()]);
                gl.glPushMatrix();
                gl.glTranslated(0,  0.25, 0);
                gl.glScaled(0.12, 0.08, 1);
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
    }

    private void addPoints() {
        double[][] initPoints = new Points().getInitPoints();
        double[][] Fruits = new Points().getFruits();

        pointsList.clear();
        for (double[] points : initPoints) {
            pointsList.add(new Points((int) points[0], points[1], points[2], (int) points[3], (int) points[4], (int) points[5], (int) points[6], false));
        }

        fruitsList.clear();
        for (double[] points : Fruits) {
            fruitsList.add(new Points((int) points[0], points[1], points[2], -1, -1, -1, -1, false));
        }

        TextsList.add(new Texts(14,false)); //for Ready
        TextsList.add(new Texts(15,false)); //for GameOver
        TextsList.add(new Texts(16,false));  //for Win
    }

    public synchronized void playSound(final String url,int idx) {
        new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
            public void run() {
                File soundFile = new File(url);
                if(idx == 0) {
                    while (!start){
                        try {
                            AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);

                            AudioFormat formatAudio = sampleStream.getFormat();

                            DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);

                            SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);

                            theAudioLine.open(formatAudio);

                            theAudioLine.start();

                            if (idx != -1) {
                                TextsList.get(idx).setAppear(true);
                            }

                            byte[] bufferBytes = new byte[BUFFER_SIZE];
                            int readBytes = -1;

                            while ((readBytes = sampleStream.read(bufferBytes)) != -1) {
                                theAudioLine.write(bufferBytes, 0, readBytes);
                            }

                            theAudioLine.drain();
                            theAudioLine.close();
                            sampleStream.close();

                            if (idx != -1) {
                                TextsList.get(idx).setAppear(false);
                            }

                        } catch (UnsupportedAudioFileException e) {
                            System.out.println("Unsupported file.");
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            System.out.println("Line not found.");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("Experienced an error.");
                            e.printStackTrace();
                        }
                   }
                }else {
                    try {
                        AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);
                        AudioFormat formatAudio = sampleStream.getFormat();
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);
                        SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);
                        theAudioLine.open(formatAudio);

                        theAudioLine.start();

                        if (idx != -1) {
                            TextsList.get(idx).setAppear(true);
                        }

                        byte[] bufferBytes = new byte[BUFFER_SIZE];
                        int readBytes = -1;

                        while ((readBytes = sampleStream.read(bufferBytes)) != -1) {
                            theAudioLine.write(bufferBytes, 0, readBytes);
                        }

                        theAudioLine.drain();
                        theAudioLine.close();
                        sampleStream.close();

                        if (idx != -1) {
                            TextsList.get(idx).setAppear(false);
                        }

                        if(idx ==2||idx==1){
                            addPoints();
                            reInit();
                        }

                    } catch (UnsupportedAudioFileException e) {
                        System.out.println("Unsupported file.");
                        e.printStackTrace();
                    } catch (LineUnavailableException e) {
                        System.out.println("Line not found.");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Experienced an error.");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void updateScoreAndLevel(GL gl){

        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(1, 0, 0, 0.5f);
        gl.glPushMatrix();

        GLUT glut = new GLUT();

        gl.glRasterPos2d(-0.1, 0.958);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Score : " + score);

        gl.glRasterPos2d(-0.9, 0.958);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "LV : " + levelNo);

        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);

    }

    public void reInit(){
        gameOver =false;
        start = false;
        index = 1 ; indexR = 20 ; indexB = 30 ; indexO = 40;
        playSound("Assets\\sounds\\pacman_beginning.wav",0);
        xR = pointsList.get(indexR).getX(); yR = pointsList.get(indexR).getY();
        xB = pointsList.get(indexB).getX(); yB = pointsList.get(indexB).getY();
        xO = pointsList.get(indexO).getX(); yO = pointsList.get(indexO).getY();
        angle1 = 0 ; angle2 = 0 ;
        animation = 0; face = 0;
        x = pointsList.get(index).getX()-5; y = pointsList.get(index).getY();

    }
}

