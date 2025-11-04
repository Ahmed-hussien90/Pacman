package App;

import Movement.*;
import Texture.TextureReader;
import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import static DataSources.KeyCode.*;

import java.util.List;
import java.util.Map;

import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class PacmanApp extends BaseJogl {
    ArrayList<Texts> TextsList = new ArrayList<>();
    ArrayList<Integer> KeyList = new ArrayList<>();

    String filePath = "Assets/";
    private final static String[] textureNames = {
            "pacman/r1.png", "pacman/r2.png", "pacman/r3.png",
            "pacman/l1.png", "pacman/l2.png", "pacman/l3.png",
            "pacman/t1.png", "pacman/t2.png", "pacman/t3.png",
            "pacman/b1.png", "pacman/b2.png", "pacman/b3.png",
            "extra/dot.png", "extra/apple.png", "Ready.png",
            "GameOver.png", "Win.png", "menu.jpg", "levels.png",
            "ghosts/blinky.png", "ghosts/pinky.png", "ghosts/clyde.png",
            "Background.jpeg"
    };
    private final static int[] textures = new int[textureNames.length];
    Pacman pacman;
    List<Pacman> Enemies = new ArrayList<>();

    int[] Texts = {14, 15, 16};

    private boolean StartGame = false;
    int Level = 1, angle = 0, score = 0, n = 0;
    double pacmanSpeed = 0.6, enemySpeed = 0.3;

    List<Integer> directions = List.of(UP.getCode(), LEFT.getCode(), RIGHT.getCode(), DOWN.getCode());
    Map<Integer, MoveCommand> moveCommands = Map.of(
            UP.getCode(), new MoveUp(),
            DOWN.getCode(), new MoveDown(),
            LEFT.getCode(), new MoveLeft(),
            RIGHT.getCode(), new MoveRight()
    );

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        int i = 0;
        TextureReader.Texture texture;
        for (String fileName : textureNames) {
            try {
                texture = TextureReader.readTexture(filePath + "//" + fileName, true);

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

        for (int T : Texts) {
            TextsList.add(new Texts(T, false));
        }

        reInit();
    }

    public void reInit() {
        Points.PointsList.forEach(point -> point.setChecked(false));

        Points.FruitsList.forEach(fruit -> fruit.setChecked(false));

        TextsList.forEach(text -> text.setAppear(false));

        TextsList.get(0).setAppear(true);
        SoundPlayer.playAsync("Assets\\sounds\\pacman_beginning.wav", () -> TextsList.get(0).setAppear(false));

        StartGame = false;
        score = 0;

        pacman = new Pacman(19, 1);

        for (int i = 0; i < 3; i++) {
            int index = (int) (Math.random() * Points.PointsList.size());
            Enemies.add(new Pacman(19 + i, index, 37 + (int) (Math.random() * 4)));
        }
    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        if (!StartGame) {
            drawTexture(gl, 17, new double[]{0, 0}, new double[]{1, 1});
            drawTexture(gl, 18, new double[]{0, -0.6}, new double[]{0.3, 0.3});
            return;
        }

        drawTexture(gl, textureNames.length - 1, new double[]{0, 0}, new double[]{1, 1});

        for (Points p : Points.PointsList) {
            if (!p.isChecked()) {
                drawTexture(gl, 12, p.getPositionView(), new double[]{0.075, 0.075});

                if (isPacmanTouched(p.getX(), p.getY(), 1)) {
                    SoundPlayer.playAsync("Assets\\sounds\\pacman_chomp.wav", null);
                    score += 10;
                    p.setChecked(true);
                }
            }
        }

        for (Points f : Points.FruitsList) {
            if (!f.isChecked()) {
                drawTexture(gl, 13, f.getPositionView(), new double[]{0.03, 0.03});

                if (isPacmanTouched(f.getX(), f.getY(), 1)) {
                    SoundPlayer.playAsync("Assets\\sounds\\pacman_eatfruit.wav", null);
                    score += 20;
                    f.setChecked(true);
                }
            }
        }

        for (Texts t : TextsList) {
            if (t.isAppear()) {
                drawTexture(gl, t.getIndex(), new double[]{0, 0.07}, new double[]{0.17, 0.13});
            }
        }

        drawTexture(gl, pacman.getFaceAnimated(), pacman.getPositionView(), new double[]{0.05, 0.05});

        Enemies.forEach(e -> drawTexture(gl, e.texture, e.getPositionView(), new double[]{0.05, 0.05}));

        if (!KeyList.isEmpty()) {
            moveCommands.get(KeyList.get(n)).execute(pacman, pacmanSpeed);

            if (!pacman.isMoving) {
                if (n < KeyList.size() - 1) {
                    n++;
                }
            }
        }


        Enemies.forEach(e ->{
            moveCommands.get(e.random).execute(e, enemySpeed * Level);

            if (!e.isMoving) {
                e.random = 37 + (int) (Math.random() * 4);
            }
        });

        if (isKilled() || isWon()) {
            n = 0;
            KeyList.clear();

            TextsList.get(isKilled() ? 1 : 2).setAppear(true);

            StartGame = false;

            SoundPlayer.playAsync(isKilled() ? "Assets\\sounds\\pacman_death.wav" : "Assets\\sounds\\Victory.wav", this::reInit);
        }

        updateScoreAndLevel(gl);
    }

    public void keyPressed(final KeyEvent e) {
        int key = e.getKeyCode();
        if (directions.contains(key)) {
            KeyList.add(key);
        }
    }

    public void mouseClicked(final MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (!StartGame && (355 < x && x < 485)) {
            StartGame = true;

            if (539 < y && y < 597)
                Level = 1;
            else if (624 < y && y < 680)
                Level = 2;
            else if (708 < y && y < 766)
                Level = 3;
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
        if (textureIdx == 13) {
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

    public void updateScoreAndLevel(GL gl) {
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(1, 0, 0, 0.5f);
        gl.glPushMatrix();
        GLUT glut = new GLUT();
        gl.glRasterPos2d(-0.1, 0.958);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Score : " + score);
        gl.glRasterPos2d(-0.9, 0.958);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "LV : " + Level);
        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);
    }

    private boolean isKilled() {
        boolean result = false;

        for (Pacman e : Enemies) {
            result |= isPacmanTouched(e.getX(), e.getY(), 4);
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