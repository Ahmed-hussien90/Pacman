package App;

import Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class Pacman extends BaseJogl {
    ArrayList<Points> PointsList = new ArrayList<>();
    ArrayList<Points> FruitsList = new ArrayList<>();
    ArrayList<Texts> TextsList = new ArrayList<>();
    ArrayList<Integer> KeyList = new ArrayList<>();

    private final static String[] textureNames = {
        "pacman/r1.png"     , "pacman/r2.png"         , "pacman/r3.png",
        "pacman/l1.png"     , "pacman/l2.png"         , "pacman/l3.png",
        "pacman/t1.png"     , "pacman/t2.png"         , "pacman/t3.png",
        "pacman/b1.png"     , "pacman/b2.png"         , "pacman/b3.png",
        "extra/dot.png"     , "extra/apple.png"       , "Ready.png",
        "GameOver.png"      , "Win.png"               , "menu.jpg",
        "levels.png"        , "ghosts/blinky.png"     ,"ghosts/pinky.png",
        "ghosts/clyde.png"  , "Background.jpeg"
    };
    private static int[] textures = new int[textureNames.length];
    TextureReader.Texture textureArr[] = new TextureReader.Texture[textureNames.length];
    private static final int BUFFER_SIZE = 4096, WIDTH = 100, Height = 100;
    private final double SPEED = 0.25;
    private boolean StartGame = false,  GameOver = false;
    int keyCode, Level = 1, Angle = 0, Score = 0, FinalScore = 780, FaceAnimations = 0, Face = 0;

    int n = 0;
    double x , y , xR , yR , xB , yB , xO , yO;
    int RandomR = 37 + (int)(Math.random()*4), RandomB = 37 + (int)(Math.random()*4), RandomO = 37 + (int)(Math.random()*4);;
    int index = 1 , indexR = 20 , indexB = 30 , indexO = 40;



    private void addPoints() {
        double[][] initPoints = new Points().getInitPoints();
        double[][] Fruits = new Points().getFruits();

        PointsList.clear();
        for (double[] points : initPoints) {
            PointsList.add(new Points((int) points[0], points[1], points[2], (int) points[3], (int) points[4], (int) points[5], (int) points[6], false));
        }

        FruitsList.clear();
        for (double[] points : Fruits) {
            FruitsList.add(new Points((int) points[0], points[1], points[2], -1, -1, -1, -1, false));
        }

        TextsList.add(new Texts(14,false)); //for Ready
        TextsList.add(new Texts(15,false)); //for GameOver
        TextsList.add(new Texts(16,false));  //for Win
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
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Score : " + Score);

        gl.glRasterPos2d(-0.9, 0.958);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "LV : " + Level);

        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);

    }

    public void reInit(){
        GameOver =false;
        StartGame = false;
        index = 1 ; indexR = 20 ; indexB = 30 ; indexO = 40;
        playSound("Assets\\sounds\\pacman_beginning.wav",0);
        xR = PointsList.get(indexR).getX(); yR = PointsList.get(indexR).getY();
        xB = PointsList.get(indexB).getX(); yB = PointsList.get(indexB).getY();
        xO = PointsList.get(indexO).getX(); yO = PointsList.get(indexO).getY();
        Angle = 0 ;
        FaceAnimations = 0; Face = 0;
        x = PointsList.get(index).getX()-5; y = PointsList.get(index).getY();

    }

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                textureArr[i] = TextureReader.readTexture("Assets///" + textureNames[i], true);
                gl.glBindTexture(GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(GL_TEXTURE_2D,
                        GL.GL_RGBA, textureArr[i].getWidth(), textureArr[i].getHeight(),
                        GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, textureArr[i].getPixels());
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        addPoints();
        playSound("Assets\\sounds\\pacman_beginning.wav",0);
        x  = PointsList.get(index).getX()-5; y = PointsList.get(index).getY();
        xR = PointsList.get(indexR).getX(); yR = PointsList.get(indexR).getY();
        xB = PointsList.get(indexB).getX(); yB = PointsList.get(indexB).getY();
        xO = PointsList.get(indexO).getX(); yO = PointsList.get(indexO).getY();
    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();


        if(StartGame) {
            if(killRange()){ KeyList.clear(); GameOver = true; StartGame = false; n=0; }

            DrawTexture(gl, textureNames.length-1, new double[]{0,0},new double[]{1, 1});

            for (Points p : PointsList) {
                if(!p.isChecked()) {
                    DrawTexture(gl, 12, new double[]{p.getX() / (WIDTH / 2.0) - 0.9, p.getY() / (Height / 2.0) - 0.9},new double[]{0.075, 0.075});
                }
            }
            Angle +=2;
            for (Points f : FruitsList) {
                if(!f.isChecked()) {
                    DrawTexture(gl, 13, new double[]{f.getX() / (WIDTH / 2.0) - 0.9, f.getY() / (Height / 2.0) - 0.9},new double[]{0.03, 0.03});
                }
            }
            for (Texts t : TextsList) {
                if(t.isAppear) {
                    DrawTexture(gl, t.getIndex(), new double[]{0,  0.07},new double[]{0.17, 0.13});
                }
            }

            for(int i = 0; i< PointsList.size(); i++){
                if(x == PointsList.get(i).getX() && y == PointsList.get(i).getY() && !PointsList.get(i).isChecked()){
                    playSound("Assets\\sounds\\pacman_chomp.wav",-1); Score +=10; PointsList.get(i).setChecked(true);
                }
            }
            for(int i = 0; i< FruitsList.size(); i++) {
                if (x == FruitsList.get(i).getX() && y == FruitsList.get(i).getY() && !FruitsList.get(i).isChecked()) {
                    playSound("Assets\\sounds\\pacman_eatfruit.wav",-1); Score +=20; FruitsList.get(i).setChecked(true);
                }
            }

            int me = Face + FaceAnimations % 3;
            double[][] textureIdx = {
                    {me, x, y},
                    {19, xR, yR},
                    {20, xB, yB},
                    {21, xO, yO},
            };
            for (double[] i : textureIdx) {
                DrawTexture(gl, (int) i[0], new double[]{i[1] / (WIDTH / 2.0) - 0.9, i[2] / (Height / 2.0) - 0.9},new double[]{0.05, 0.05});
            }
            updateScoreAndLevel(gl);
        }else {
            DrawTexture(gl, 17, new double[]{0,0},new double[]{1, 1});
            DrawTexture(gl, 18, new double[]{0,-0.6},new double[]{0.3, 0.3});
        }

        if (KeyList.size() != 0) {
            handleKeyPress();
        }
        for (int i = 0; i < Level; i++) {
            handleKeyPressEnemy();
        }

        if (GameOver) {
            playSound("Assets\\sounds\\pacman_death.wav", 1);
            GameOver = false;
        }

        if (Score == FinalScore) {
            playSound("Assets\\sounds\\Victory.wav", 2);
            Score = 0;
        }
    }

    private void DrawTexture(GL g, int textureIdx, double[] position, double[] scale){
        g.glEnable(GL.GL_BLEND);
        g.glBindTexture(GL_TEXTURE_2D, textures[textureIdx]);
        g.glPushMatrix();
        g.glTranslated(position[0] , position[1], 0);
        g.glScaled(scale[0], scale[1], 1);
        if(textureIdx == 13) g.glRotated(Angle %360,0,0,1);
        g.glBegin(GL.GL_QUADS);
        g.glTexCoord2f(0.0f, 0.0f); g.glVertex3f(-1.0f, -1.0f, -1.0f);
        g.glTexCoord2f(1.0f, 0.0f); g.glVertex3f( 1.0f, -1.0f, -1.0f);
        g.glTexCoord2f(1.0f, 1.0f); g.glVertex3f( 1.0f,  1.0f, -1.0f);
        g.glTexCoord2f(0.0f, 1.0f); g.glVertex3f(-1.0f,  1.0f, -1.0f);
        g.glEnd();
        g.glPopMatrix();
        g.glDisable(GL.GL_BLEND);
    }

    private boolean killRange() {
        return (
                (Math.abs(y-yR) <= 5 && Math.abs(x-xR) <= 5) ||
                        (Math.abs(y-yB) <= 5 && Math.abs(x-xB) <= 5) ||
                        (Math.abs(y-yO) <= 5 && Math.abs(x-xO) <= 5)
        );
    }

    public void keyPressed(final KeyEvent event) {
        keyCode = event.getKeyCode();

        if(keyCode==38||keyCode==37||keyCode==39||keyCode==40) {
            KeyList.add(keyCode);
        }
    }

    public void mouseClicked(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if((355 < x&&x < 485) && !StartGame){
            StartGame = true;

            if (539 < y&&y < 597)
                Level = 1;
            else  if (624 < y&&y < 680)
                Level = 2;
            else  if (708 < y&&y < 766)
                Level = 3;
            else
                StartGame = false;
        }
    }

    public boolean isKeyPressed(int keyC) {
        return keyC == KeyList.get(n);

    }

    private void handleKeyPress() {
        int T = PointsList.get(index).getTop();
        int B = PointsList.get(index).getBottom();
        int L = PointsList.get(index).getLeft();
        int R = PointsList.get(index).getRight();

        if (isKeyPressed(38) && T != -1) {
            if (PointsList.get(T).getY() == y) {
                checkN(); index = T;
            }
            else {
                y += SPEED; Face = 6; FaceAnimations++;
            }
        }
        else if (isKeyPressed(40) && B != -1) {
            if (PointsList.get(B).getY() == y) {
                checkN(); index = B;
            }
            else {
                y -= SPEED; Face = 9; FaceAnimations++;
            }
        }
        else if (isKeyPressed(37) && L != -1) {
            if (L == -2){
                index = 18;
                x = PointsList.get(index).getX();
                y = PointsList.get(index).getY();
                return;
            }

            if (PointsList.get(L).getX() == x) {
                checkN(); index = L;
            }
            else {
                x -= SPEED; Face = 3; FaceAnimations++;
            }
        }
        else if (isKeyPressed(39) && R != -1) {
            if (R == -2){
                index = 66;
                x = PointsList.get(index).getX();
                y = PointsList.get(index).getY();
                return;
            }

            if (PointsList.get(R).getX() == x) {
                checkN(); index = R;
            }
            else {
                x += SPEED; Face = 0; FaceAnimations++;
            }
        }
        else
            checkN();
    }

    private void handleKeyPressEnemy() {
        if (RandomR == 38) {
            int T = PointsList.get(indexR).getTop();
            if (T != -1) {
                if (PointsList.get(T).getY() == yR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = T;
                }
                else {
                    yR += SPEED;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }
        else if (RandomR == 40) {
            int B = PointsList.get(indexR).getBottom();
            if (B != -1) {
                if (PointsList.get(B).getY() == yR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = B;
                }
                else {
                    yR -= SPEED;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }

        else if (RandomR == 37) {
            int L = PointsList.get(indexR).getLeft();
            if (L == -2){
                indexR = 18;
                xR = PointsList.get(indexR).getX();
                yR = PointsList.get(indexR).getY();
                return;
            }
            if (L != -1) {
                if (PointsList.get(L).getX() == xR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = L;
                }
                else {
                    xR -= SPEED;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }

        else if (RandomR == 39) {
            int R = PointsList.get(indexR).getRight();
            if (R == -2){
                indexR = 66;
                xR = PointsList.get(indexR).getX();
                yR = PointsList.get(indexR).getY();
                return;
            }
            if (R != -1) {
                if (PointsList.get(R).getX() == xR) {
                    RandomR = 37 + (int)(Math.random()*4);
                    indexR = R;
                }
                else {
                    xR += SPEED;
                }
            }
            else
                RandomR = 37 + (int)(Math.random()*4);
        }

        if (RandomB == 38) {
            int T = PointsList.get(indexB).getTop();
            if (T != -1) {
                if (PointsList.get(T).getY() == yB) {
                    RandomB = 37 + (int)(Math.random()*4);
                    indexB = T;
                }
                else {
                    yB += SPEED;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }
        //Down
        else if (RandomB == 40) {
            int B = PointsList.get(indexB).getBottom();
            if (B != -1) {
                if (PointsList.get(B).getY() == yB) {
                    RandomB= 37 + (int)(Math.random()*4);
                    indexB = B;
                }
                else {
                    yB -= SPEED;
                }
            }
            else
                RandomB= 37 + (int)(Math.random()*4);
        }
        //Left
        else if (RandomB== 37) {
            int L = PointsList.get(indexB).getLeft();
            if (L == -2){
                indexB= 18;
                xB= PointsList.get(indexB).getX();
                yB = PointsList.get(indexB).getY();
                return;
            }
            if (L != -1) {
                if (PointsList.get(L).getX() == xB) {
                    RandomB = 37 + (int)(Math.random()*4);
                    indexB = L;
                }
                else {
                    xB-= SPEED;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }
        //Right
        else if (RandomB == 39) {
            int R = PointsList.get(indexB).getRight();
            if (R == -2){
                indexB= 66;
                xB = PointsList.get(indexB).getX();
                yB = PointsList.get(indexB).getY();
                return;
            }
            if (R != -1) {
                if (PointsList.get(R).getX() == xB) {
                    RandomB= 37 + (int)(Math.random()*4);
                    indexB = R;
                }
                else {
                    xB += SPEED;
                }
            }
            else
                RandomB = 37 + (int)(Math.random()*4);
        }

        if (RandomO == 38) {
            int T = PointsList.get(indexO).getTop();
            if (T != -1) {
                if (PointsList.get(T).getY() == yO) {
                    RandomO = 37 + (int)(Math.random()*4);
                    indexO = T;
                }
                else {
                    yO += SPEED;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
        //Down
        else if (RandomO == 40) {
            int B = PointsList.get(indexO).getBottom();
            if (B != -1) {
                if (PointsList.get(B).getY() == yO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO = B;
                }
                else {
                    yO -= SPEED;
                }
            }
            else
                RandomO= 37 + (int)(Math.random()*4);
        }
        //Left
        else if (RandomO== 37) {
            int L = PointsList.get(indexO).getLeft();
            if (L == -2){
                indexO= 18;
                xO= PointsList.get(indexO).getX();
                yO = PointsList.get(indexO).getY();
                return;
            }
            if (L != -1) {
                if (PointsList.get(L).getX() == xO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO= L;
                }
                else {
                    xO-= SPEED;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
        //Right
        else if (RandomO == 39) {
            int R = PointsList.get(indexO).getRight();
            if (R == -2){
                indexO= 66;
                xO = PointsList.get(indexO).getX();
                yO = PointsList.get(indexO).getY();
                return;
            }
            if (R != -1) {
                if (PointsList.get(R).getX() == xO) {
                    RandomO= 37 + (int)(Math.random()*4);
                    indexO = R;
                }
                else {
                    xO += SPEED;
                }
            }
            else
                RandomO = 37 + (int)(Math.random()*4);
        }
    }

    private void checkN(){
        if (n < KeyList.size() - 1) { n++;}
    }

    public synchronized void playSound(final String url,int idx) {
        new Thread(new Runnable() {
            public void run() {
                File soundFile = new File(url);
                try {
                    AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);
                    AudioFormat formatAudio = sampleStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);
                    SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);
                    theAudioLine.open(formatAudio);
                    theAudioLine.start();

                    if (idx != -1) { TextsList.get(idx).setAppear(true); }

                    byte[] bufferBytes = new byte[BUFFER_SIZE];
                    int readBytes = -1;

                    while ((readBytes = sampleStream.read(bufferBytes)) != -1) {
                        theAudioLine.write(bufferBytes, 0, readBytes);
                    }
                    theAudioLine.drain(); theAudioLine.close(); sampleStream.close();

                    if (idx != -1) {
                        TextsList.get(idx).setAppear(false);
                    }

                    if(idx == 1||idx== 2){
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
        }).start();
    }
}