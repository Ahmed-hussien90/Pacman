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
    PacObject pacman = new PacObject(19,1);
    PacObject[] Enemies = {
        new PacObject(19, 20, 37 + (int)(Math.random()*4)),
        new PacObject(20, 30, 37 + (int)(Math.random()*4)),
        new PacObject(21, 40, 37 + (int)(Math.random()*4)),
    };
    int[] Texts = {14, 15, 16};
    double[][] initPoints = new Points().getInitPoints();
    double[][] Fruits = new Points().getInitFruits();
    private final static int[] textures = new int[textureNames.length];
    TextureReader.Texture[] textureArr = new TextureReader.Texture[textureNames.length];
    private static final int BUFFER_SIZE = 4096, WIDTH = 100, Height = 100;
    private final double SPEED = 0.25;
    private boolean StartGame = false,  GameOver = false;
    int keyCode, Level = 1, Angle = 0, Score = 0, FinalScore = 780, FaceAnimations = 0, Face = 0, n = 0;
    private void ResetPoints() {
        PointsList.clear();
        for (double[] points : initPoints) {
            PointsList.add(new Points((int) points[0], points[1], points[2], (int) points[3], (int) points[4], (int) points[5], (int) points[6], false));
        }

        FruitsList.clear();
        for (double[] points : Fruits) {
            FruitsList.add(new Points((int) points[0], points[1], points[2], -1, -1, -1, -1, false));
        }

        TextsList.clear();
        for (int T: Texts) {
            TextsList.add(new Texts(T,false));
        }
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
                new GLU().gluBuild2DMipmaps(GL_TEXTURE_2D, GL.GL_RGBA, textureArr[i].getWidth(), textureArr[i].getHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, textureArr[i].getPixels());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reInit();
    }
    public void reInit(){
        ResetPoints();

        PlaySound("Assets\\sounds\\pacman_beginning.wav",0);

        StartGame = GameOver = false;

        pacman.index = 1; Angle = 0; FaceAnimations = 0; Face = 0; Score = 0;

        pacman.x = PointsList.get(pacman.index).getX()-5; pacman.y = PointsList.get(pacman.index).getY();

        Enemies[0].index = 20; Enemies[1].index = 30; Enemies[2].index = 40;
        for (PacObject E: Enemies) {
            E.x = PointsList.get(E.index).getX(); E.y = PointsList.get(E.index).getY();
        }
    }
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        Angle +=2;

        if(StartGame) {
            if(killRange()){ KeyList.clear(); GameOver = true; StartGame = false; n=0; }

            DrawTexture(gl, textureNames.length-1, new double[]{0,0},new double[]{1, 1});

            for (Points p : PointsList)
                if(!p.isChecked())
                    DrawTexture(gl, 12, new double[]{p.getX() / (WIDTH / 2.0) - 0.9, p.getY() / (Height / 2.0) - 0.9},new double[]{0.075, 0.075});

            for (Points f : FruitsList)
                if(!f.isChecked())
                    DrawTexture(gl, 13, new double[]{f.getX() / (WIDTH / 2.0) - 0.9, f.getY() / (Height / 2.0) - 0.9},new double[]{0.03, 0.03});

            for (Texts t : TextsList)
                if(t.isAppear)
                    DrawTexture(gl, t.getIndex(), new double[]{0,  0.07},new double[]{0.17, 0.13});

            for(Points P : PointsList){
                if(pacman.x == P.getX() && pacman.y == P.getY() && !P.isChecked()){
                    PlaySound("Assets\\sounds\\pacman_chomp.wav",-1); Score +=10; P.setChecked(true);
                }
            }
            for(Points F : FruitsList) {
                if (pacman.x == F.getX() && pacman.y == F.getY() && !F.isChecked()) {
                    PlaySound("Assets\\sounds\\pacman_eatfruit.wav",-1); Score +=20; F.setChecked(true);
                }
            }
            DrawTexture(gl, Face + FaceAnimations % 3, new double[]{pacman.x / (WIDTH / 2.0) - 0.9, pacman.y / (Height / 2.0) - 0.9},new double[]{0.05, 0.05});
            for (PacObject E : Enemies) {
                DrawTexture(gl, E.texture, new double[]{E.x / (WIDTH / 2.0) - 0.9, E.y / (Height / 2.0) - 0.9},new double[]{0.05, 0.05});
            }
            UpdateScoreAndLevel(gl);
        } else {
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
            PlaySound("Assets\\sounds\\pacman_death.wav", 1);
            GameOver = false;
        }

        if (Score == FinalScore) {
            PlaySound("Assets\\sounds\\Victory.wav", 2);
            Score = 0;
        }
    }    private void DrawTexture(GL g, int textureIdx, double[] position, double[] scale){
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
    public void UpdateScoreAndLevel(GL gl){
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(1, 0, 0, 0.5f);
        gl.glPushMatrix();
        GLUT glut = new GLUT();
        gl.glRasterPos2d(-0.1, 0.958); glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Score : " + Score);
        gl.glRasterPos2d(-0.9, 0.958); glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "LV : " + Level);
        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);
    }
    private boolean killRange() {
        return (
                (Math.abs(pacman.y-Enemies[0].y) <= 5 && Math.abs(pacman.x-Enemies[0].x) <= 5) ||
                (Math.abs(pacman.y-Enemies[1].y) <= 5 && Math.abs(pacman.x-Enemies[1].x) <= 5) ||
                (Math.abs(pacman.y-Enemies[2].y) <= 5 && Math.abs(pacman.x-Enemies[2].x) <= 5)
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
        int T = PointsList.get(pacman.index).getTop();
        int B = PointsList.get(pacman.index).getBottom();
        int L = PointsList.get(pacman.index).getLeft();
        int R = PointsList.get(pacman.index).getRight();

        if (isKeyPressed(38) && T != -1) {
            if (PointsList.get(T).getY() == pacman.y) {
                checkN(); pacman.index = T;
            } else {
                pacman.y += SPEED; Face = 6; FaceAnimations++;
            }
        } else if (isKeyPressed(40) && B != -1) {
            if (PointsList.get(B).getY() == pacman.y) {
                checkN(); pacman.index = B;
            } else {
                pacman.y -= SPEED; Face = 9; FaceAnimations++;
            }
        } else if (isKeyPressed(37) && L != -1) {
            if (L == -2){
                pacman.index = 18;
                pacman.x = PointsList.get(pacman.index).getX();
                pacman.y = PointsList.get(pacman.index).getY();
                return;
            }
            if (PointsList.get(L).getX() == pacman.x) {
                checkN(); pacman.index = L;
            } else {
                pacman.x -= SPEED; Face = 3; FaceAnimations++;
            }
        } else if (isKeyPressed(39) && R != -1) {
            if (R == -2){
                pacman.index = 66;
                pacman.x = PointsList.get(pacman.index).getX();
                pacman.y = PointsList.get(pacman.index).getY();
                return;
            }
            if (PointsList.get(R).getX() == pacman.x) {
                checkN(); pacman.index = R;
            } else {
                pacman.x += SPEED; Face = 0; FaceAnimations++;
            }
        } else checkN();
    }
    private void handleKeyPressEnemy() {
        for (PacObject E : Enemies) {
            if (E.random == 38) {
                int T = PointsList.get(E.index).getTop();
                if (T != -1) {
                    if (PointsList.get(T).getY() == E.y) {
                        E.random = 37 + (int)(Math.random()*4);
                        E.index = T;
                    }
                    else {
                        E.y += SPEED;
                    }
                }
                else
                    E.random = 37 + (int)(Math.random()*4);
            } else if (E.random == 40) {
                int B = PointsList.get(E.index).getBottom();
                if (B != -1) {
                    if (PointsList.get(B).getY() == E.y) {
                        E.random = 37 + (int)(Math.random()*4);
                        E.index = B;
                    }
                    else {
                        E.y -= SPEED;
                    }
                }
                else
                    E.random = 37 + (int)(Math.random()*4);
            } else if (E.random == 37) {
                int L = PointsList.get(E.index).getLeft();
                if (L == -2){
                    E.index = 18;
                    E.x = PointsList.get(E.index).getX();
                    E.y = PointsList.get(E.index).getY();
                    return;
                }
                if (L != -1) {
                    if (PointsList.get(L).getX() == E.x) {
                        E.random = 37 + (int)(Math.random()*4);
                        E.index = L;
                    }
                    else {
                        E.x -= SPEED;
                    }
                }
                else
                    E.random = 37 + (int)(Math.random()*4);
            } else if (E.random == 39) {
                int R = PointsList.get(E.index).getRight();
                if (R == -2){
                    E.index = 66;
                    E.x = PointsList.get(E.index).getX();
                    E.y = PointsList.get(E.index).getY();
                    return;
                }
                if (R != -1) {
                    if (PointsList.get(R).getX() == E.x) {
                        E.random = 37 + (int)(Math.random()*4);
                        E.index = R;
                    }
                    else {
                        E.x += SPEED;
                    }
                }
                else
                    E.random = 37 + (int)(Math.random()*4);
            }
        }
    }
    private void checkN(){
        if (n < KeyList.size() - 1) { n++;}
    }
    public synchronized void PlaySound(final String url, int idx) { new Thread(new Runnable() { public void run() {
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
    }}).start();}
}