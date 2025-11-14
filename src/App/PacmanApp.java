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
    GL gl;
    ArrayList<Texts> TextsList = new ArrayList<>();
    LinkedList<KeyCode> pacmanKeyList = new LinkedList<>();
    String filePath = "Assets/";
    String soundPath = "Assets/sounds/";
    Pacman pacman;
    List<Pacman> enemies;
    KeyCode target;
    int index;


    private final static int[] textures = new int[Textures.getTotal()];
    private boolean StartGame, pauseGame;
    int level, angle, score;
    double pacmanSpeed = 0.4, enemySpeed = 0.15;

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
            for (String path : paths.getPath()) {
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
                } catch (IOException e) {
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
        Points.PointsList.forEach((id, point) -> point.setEaten(false));

        TextsList.forEach(text -> text.setAppear(false));

        TextsList.get(0).setAppear(true);

        StartGame = false;
        pauseGame = true;

        SoundPlayer.playAsync(soundPath + Begin.getSound(), () -> {
            pauseGame = false;
            TextsList.get(0).setAppear(false);
        });


        score = 0;

        pacman = new Pacman(PacmanRight.getIndex(0), 1, pacmanSpeed);

        enemies = new ArrayList<>();

        int index;
        for (int i = 0; i < 3; i++) {
            index = Points.PointsList.keySet().stream().skip(new Random().nextInt(Points.PointsList.size())).findFirst().get();
            enemies.add(new Pacman(Ghost.getIndex(i), index, enemySpeed));
        }
    }

    public void display(GLAutoDrawable gld) {
        gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        if (!StartGame) {
            drawTexture(Menu.getIndex(0), Menu.getPosition(), Menu.getScale());
            drawTexture(Levels.getIndex(0), Levels.getPosition(), Levels.getScale());
            return;
        }

        drawTexture(Background.getIndex(0), Background.getPosition(), Background.getScale());


        System.out.println(pacman.getIndex());
        Points.PointsList.forEach((id, p) -> {
            if (!p.isEaten()) {
                drawTexture(p.getTexture().getIndex(0), p.getPositionView(), p.getTexture().getScale());

                if (isPacmanTouched(p.getX(), p.getY(), 1)) {
                    SoundPlayer.playAsync(soundPath + PointEaten.getSound(), null);
                    score += 10;
                    p.setEaten(true);
                }
            }
        });

        for (Texts t : TextsList) {
            if (t.isAppear()) {
                drawTexture(t.getIndex(), Texts.getPosition(), Texts.getScale());
            }
        }

        if (!pauseGame) {
            if (!pacmanKeyList.isEmpty()) {
                if (pacmanKeyList.size() > 1 && pacman.isMoving()) {
                    int target = moveCommands.get(pacmanKeyList.getFirst()).getTarget(pacman);

                    if (KeyCode.isOpposite(pacmanKeyList.getFirst(), pacmanKeyList.get(1))) {
                        if (target != -1) {
                            pacman.setIndex(target);
                        }
                        pacmanKeyList.removeFirst();
                    } else if (moveCommands.get(pacmanKeyList.get(1)).getTarget(new Pacman(PacmanRight.getIndex(0), target, pacmanSpeed)) == -1) {
                        pacmanKeyList.addFirst(pacmanKeyList.getFirst());
                    }

                }

                moveCommands.get(pacmanKeyList.getFirst()).execute(pacman);
                if (!pacman.isMoving() && pacmanKeyList.size() > 1) {
                    pacmanKeyList.removeFirst();
                }
            }

            enemies.forEach(e -> {
                if (!e.getHomePath().isEmpty()) {
                    index = e.getHomePath().peek();
                    target = e.getTargetKeyCode(index);
                    if (e.getIndex() == index) {
                        e.getHomePath().pop();
                    }
                } else {
                    target = e.getRandom();
                    e.setSpeed(enemySpeed);
                    if (!e.isMoving()) {
                        e.setRandom();
                    }
                }
                moveCommands.get(target).execute(e);
            });
        }

        drawTexture(pacman.getFaceAnimated(), pacman.getPositionView(), pacman.getScale());

        enemies.forEach(e -> drawTexture(e.getTexture(), e.getPositionView(), e.getScale()));

        if (!pauseGame && (isPacmanKilled() || isPacmanWon())) {
            pauseGame = true;

            pacmanKeyList.clear();

            TextsList.get(isPacmanKilled() ? 1 : 2).setAppear(true);

            SoundPlayer.playAsync(soundPath + (isPacmanKilled() ? Death.getSound() : Victory.getSound()), this::reInit);
        }

        writeText(new double[]{-0.1, 0.958}, "Score : " + score);
        writeText(new double[]{-0.9, 0.958}, "Level : " + level);
    }

    public void keyPressed(final KeyEvent e) {
        KeyCode key = KeyCode.getKeyCode(e.getKeyCode());
        if (key != null) {
            pacmanKeyList.add(key);
        }

        if (e.getKeyCode() == 32) {
            enemies.get(0).setSpeed(enemySpeed * level * 5);
            enemies.get(0).setHomePath(82);
        }
    }

    public void mouseClicked(final MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (!StartGame && (300 < x && x < 522)) {
            StartGame = true;

            if (530 < y && y < 603)
                level = 1;
            else if (610 < y && y < 690)
                level = 2;
            else if (700 < y && y < 775)
                level = 3;
            else
                StartGame = false;

            enemies.forEach(en -> en.setSpeed(enemySpeed * level));
        }
    }

    private void drawTexture(int textureIdx, double[] position, double[] scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL_TEXTURE_2D, textures[textureIdx]);
        gl.glPushMatrix();

        gl.glTranslated(position[0], position[1], 0);
        gl.glScaled(scale[0], scale[1], 1);

        if (textureIdx == Fruit.getIndex(0)) {
            gl.glRotated((double) (angle++ / 2) % 360, 0, 0, 1);
        }

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

    public void writeText(double[] position, String text) {
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

    private boolean isPacmanKilled() {
        boolean result = false;

        for (Pacman e : enemies) {
            result |= isPacmanTouched(e.getX(), e.getY(), 2);
        }

        return result;
    }

    private boolean isPacmanWon() {
        return score == (10 * Points.PointsList.size());
    }

    private boolean isPacmanTouched(double x, double y, double distance) {
        return Math.abs(pacman.getY() - y) <= distance && Math.abs(pacman.getX() - x) <= distance;
    }
}