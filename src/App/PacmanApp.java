package App;

import DataSources.KeyCode;
import DataSources.Textures;
import Movement.*;
import Texture.TextureReader;
import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

import static DataSources.KeyCode.*;
import static DataSources.Textures.*;
import static DataSources.Sound.*;

import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class PacmanApp extends BaseJogl {
    ArrayList<Texts> TextsList = new ArrayList<>();
    List<KeyCode> keyList = new ArrayList<>();

    String filePath = "Assets/";
    String soundPath = "Assets/sounds/";

    private final static int[] textures = new int[23];
    Pacman pacman;
    List<Pacman> enemies;

    private boolean StartGame, PauseGame;
    int level, angle, score;
    double pacmanSpeed = 0.4, enemySpeed = 0.1;

    Map<KeyCode, MoveCommand> moveCommands = Map.of(
            UP, new MoveUp(),
            DOWN, new MoveDown(),
            LEFT, new MoveLeft(),
            RIGHT, new MoveRight()
    );

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textures.length, textures, 0);

        int i = 0;
        TextureReader.Texture texture;

        for (Textures paths : Textures.values()) {
            for(String path: paths.getPath()) {
                try {
                    texture = TextureReader.readTexture(filePath + "//" + path, true);

                    gl.glBindTexture(GL_TEXTURE_2D, textures[i++]);

                    new GLU().gluBuild2DMipmaps(
                            GL_TEXTURE_2D,
                            GL.GL_RGBA,
                            texture.getWidth(),
                            texture.getHeight(),
                            GL.GL_RGBA,
                            GL.GL_UNSIGNED_BYTE,
                            texture.getPixels()
                    );
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        TextsList.add(new Texts(Texts.getIndex(0), false));
        TextsList.add(new Texts(Texts.getIndex(1), false));
        TextsList.add(new Texts(Texts.getIndex(2), false));

        reInit();
    }

    public void reInit() {
        Points.PointsList.forEach(point -> point.setEaten(false));

        Points.FruitsList.forEach(fruit -> fruit.setEaten(false));

        TextsList.forEach(text -> text.setAppear(false));

        TextsList.get(0).setAppear(true);

        StartGame = false;
        PauseGame = true;
        SoundPlayer.playAsync(soundPath + Begin.getSound(), () -> {
            PauseGame = false;
            TextsList.get(0).setAppear(false);
        });


        score = 0;

        pacman = new Pacman(PacmanRight.getIndex(0), 1);

        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = (int) (Math.random() * Points.PointsList.size());
            enemies.add(new Pacman(Ghost.getIndex(i), index, 37 + (int) (Math.random() * 4)));
        }
    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        if (!StartGame) {
            drawTexture(gl, Menu.getIndex(0), new double[]{0, 0}, new double[]{1, 1});
            drawTexture(gl, Levels.getIndex(0), new double[]{0, -0.6}, new double[]{0.3, 0.3});
            return;
        }

        drawTexture(gl, Background.getIndex(0), new double[]{0, 0}, new double[]{1, 1});

        for (Points p : Points.PointsList) {
            if (!p.isEaten()) {
                drawTexture(gl, Point.getIndex(0), p.getPositionView(), new double[]{0.075, 0.075});

                if (isPacmanTouched(p.getX(), p.getY(), 1)) {
                    SoundPlayer.playAsync(soundPath + PointEaten.getSound(), null);
                    score += 10;
                    p.setEaten(true);
                }
            }
        }

        for (Points f : Points.FruitsList) {
            if (!f.isEaten()) {
                drawTexture(gl, Fruits.getIndex(0), f.getPositionView(), new double[]{0.03, 0.03});

                if (isPacmanTouched(f.getX(), f.getY(), 1)) {
                    SoundPlayer.playAsync(soundPath + FruitEaten.getSound(), null);
                    score += 20;
                    f.setEaten(true);
                }
            }
        }

        for (Texts t : TextsList) {
            if (t.isAppear()) {
                drawTexture(gl, t.getIndex(), t.getPositionView(), t.getScale());
            }
        }

        if (!PauseGame) {
            if (!keyList.isEmpty()) {
                if(keyList.size() > 1 && pacman.isMoving()) {
                    int target = moveCommands.get(keyList.get(0)).getTarget(pacman);

                    if(KeyCode.isOpposite(keyList.get(0), keyList.get(1))) {
                        if(target != -1) {
                            pacman.setIndex(target);
                        }
                        keyList.remove(0);
                    } else if(moveCommands.get(keyList.get(1)).getTarget(new Pacman(target)) == -1) {
                        keyList.add(0, keyList.get(0));

                    }
                }

                moveCommands.get(keyList.get(0)).execute(pacman, pacmanSpeed);

                if (!pacman.isMoving() && keyList.size() > 1) {
                    keyList.remove(0);
                }
            }

            enemies.forEach(e -> {
                moveCommands.get(e.getRandom()).execute(e, enemySpeed * level);

                if (!e.isMoving()) {
                    e.setRandom();
                }
            });
        }

        drawTexture(gl, pacman.getFaceAnimated(), pacman.getPositionView(), pacman.getScale());

        enemies.forEach(e -> drawTexture(gl, e.getTexture(), e.getPositionView(), e.getScale()));

        if (!PauseGame && (isKilled() || isWon())) {
            PauseGame = true;

            keyList.clear();

            TextsList.get(isKilled() ? 1 : 2).setAppear(true);

            SoundPlayer.playAsync(soundPath + (isKilled() ? Death.getSound() : Victory.getSound()), this::reInit);
        }

        writeText(gl, new double[]{-0.1, 0.958}, "Score : " + score);
        writeText(gl, new double[]{-0.9, 0.958}, "Level : " + level);
    }

    public void keyPressed(final KeyEvent e) {
        KeyCode key = KeyCode.getKeyCode(e.getKeyCode());
        if (key != null) {
            keyList.add(key);
        }
    }

    public void mouseClicked(final MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (!StartGame && (355 < x && x < 485)) {
            StartGame = true;

            if (539 < y && y < 597)
                level = 1;
            else if (624 < y && y < 680)
                level = 2;
            else if (708 < y && y < 766)
                level = 3;
            else
                StartGame = false;
        }
    }

    private void drawTexture(GL g, int textureIdx, double[] position, double[] scale) {
        g.glEnable(GL.GL_BLEND);
        g.glBindTexture(GL_TEXTURE_2D, textures[textureIdx]);
        g.glPushMatrix();

        g.glTranslated(position[0], position[1], 0);
        g.glScaled(scale[0], scale[1], 1);

        if (textureIdx == 16) {
            g.glRotated((double) (angle++ / 2) % 360, 0, 0, 1);
        }

        g.glBegin(GL.GL_QUADS);
        g.glTexCoord2f(0.0f, 0.0f);
        g.glVertex3f(-1.0f, -1.0f, -1.0f);
        g.glTexCoord2f(1.0f, 0.0f);
        g.glVertex3f(1.0f, -1.0f, -1.0f);
        g.glTexCoord2f(1.0f, 1.0f);
        g.glVertex3f(1.0f, 1.0f, -1.0f);
        g.glTexCoord2f(0.0f, 1.0f);
        g.glVertex3f(-1.0f, 1.0f, -1.0f);
        g.glEnd();

        g.glPopMatrix();
        g.glDisable(GL.GL_BLEND);
    }

    public void writeText(GL gl, double[] position, String text) {
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(1, 0, 0, 0.5f);
        gl.glPushMatrix();
        gl.glRasterPos2d(position[0], position[1]);
        new GLUT().glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, text);
        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);
    }

    private boolean isKilled() {
        boolean result = false;

        for (Pacman e : enemies) {
            result |= isPacmanTouched(e.getX(), e.getY(), 2);
        }

        return result;
    }

    private boolean isWon() {
        return score == (10 * Points.PointsList.size()) + (20 * Points.FruitsList.size());
    }

    private boolean isPacmanTouched(double x, double y, double distance) {
        return Math.abs(pacman.getY() - y) <= distance && Math.abs(pacman.getX() - x) <= distance;
    }
}