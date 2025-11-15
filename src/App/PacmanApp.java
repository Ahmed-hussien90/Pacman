package App;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

import Movement.*;
import Texture.TextureReader;
import GameEntity.*;
import DataSources.*;
import static DataSources.KeyCode.*;
import static DataSources.Textures.*;
import static DataSources.Sound.*;

import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

public class PacmanApp extends BaseJogl {
    private final int[] textures = new int[Textures.getTotal()];
    private boolean startGame, pauseGame;
    private int index, level, angle, score;
    double pacmanSpeed = 0.6, enemySpeed = 0.6;
    GL gl;
    Pacman pacman;
    List<Texts> TextsList;
    List<Enemy> enemies;
    List<Fire> fires = new ArrayList<>();
    LinkedList<KeyCode> pacmanKeyList = new LinkedList<>();

    Map<KeyCode, MoveCommand> moveCommands = Map.of(
            UP, new MoveUp(),
            DOWN, new MoveDown(),
            LEFT, new MoveLeft(),
            RIGHT, new MoveRight()
    );

    public void init(GLAutoDrawable gld) {
        gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textures.length, textures, 0);

        int i = 0;
        TextureReader.Texture texture;
        for (Textures paths : Textures.values()) {
            for (String path : paths.getPath()) {
                try {
                    texture = TextureReader.readTexture("Assets/" + path, true);

                    gl.glBindTexture(GL_TEXTURE_2D, textures[i++]);

                    new GLU().gluBuild2DMipmaps(
                            GL_TEXTURE_2D,
                            GL.GL_RGBA,
                            texture.width(),
                            texture.height(),
                            GL.GL_RGBA,
                            GL.GL_UNSIGNED_BYTE,
                            texture.pixels()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        TextsList = List.of(
                new Texts(Texts.getIndex(0), false),
                new Texts(Texts.getIndex(1), false),
                new Texts(Texts.getIndex(2), false)
        );

        SoundPlayer.setMuted(false);

        reInit();
    }

    public void reInit() {
        Points.PointsList.forEach((id, point) -> point.setEaten(false));

        TextsList.forEach(text -> text.setAppear(false));


        score = 0;
        startGame = false;
        pauseGame = true;

        TextsList.get(0).setAppear(true);
        SoundPlayer.playAsync(Begin, () -> {
            pauseGame = false;
            TextsList.get(0).setAppear(false);
        });


        pacman = new Pacman(PacmanRight.getIndex(0), 1, pacmanSpeed);

        enemies = List.of(
                new Enemy(Ghost.getIndex(0), 80, enemySpeed * level),
                new Enemy(Ghost.getIndex(1), 82, enemySpeed * level),
                new Enemy(Ghost.getIndex(2), 83, enemySpeed * level)
        );
    }

    public void display(GLAutoDrawable gld) {
        gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        if (!startGame) {
            drawTexture(Menu);
            drawTexture(Levels);
            return;
        }

        drawTexture(Background);

        Points.PointsList.forEach((id, p) -> {
            if (!p.isEaten()) {
                drawTexture(p.getTexture().getIndex(0), new double[]{p.getXView(), p.getYView()}, p.getTexture());

                if (pacman.isTouched(p.getX(), p.getY())) {
                    SoundPlayer.playAsync(p.getSound(), null);
                    score += 10;
                    p.setEaten(true);
                }
            }
        });

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
                    KeyCode target = Points.PointsList.get(e.getIndex()).getTargetKeyCode(index);
                    if(target != null) {
                        moveCommands.get(target).execute(e);
                    }
                    if (e.getIndex() == index) {
                        e.getHomePath().pop();
                    }
                } else {
                    KeyCode target = e.getRandom();
                    moveCommands.get(target).execute(e);
                    if (!e.isMoving()) {
                        e.setRandom();
                    }
                    if(e.isDead()) {
                        e.setDead(false);
                    }
                }
            });
        }



        fires.removeIf((fire) -> !fire.isMoving() && Points.PointsList.get(fire.getIndex()).getTargetIndex(fire.getFaceDirection()) == -1);

        fires.removeIf(fire -> {
            moveCommands.get(fire.getFaceDirection()).execute(fire);

            drawTexture(fire);

            return enemies.stream().anyMatch(e -> {
                if (e.isTouched(fire.getX(), fire.getY())) {
                    e.decreaseHealth(100);
                    return true;
                }
                return false;
            });
        });

        drawTexture(pacman);

        enemies.forEach(this::drawTexture);

        TextsList.forEach(t -> {
            if (t.isAppear()) {
                drawTexture(t.getIndex(), Texts.getPosition(), Texts);
            }
        });

        if (!pauseGame && (pacman.isKilled(enemies) || pacman.isWon(score))) {
            pauseGame = true;

            pacmanKeyList.clear();

            TextsList.get(pacman.isKilled(enemies) ? 1 : 2).setAppear(true);

            SoundPlayer.playAsync(pacman.isKilled(enemies) ? Death : Victory, this::reInit);
        }

        writeText(new double[]{-0.1, 0.958}, "Score : " + score);
        writeText(new double[]{-0.9, 0.958}, "Level : " + level);
    }

    public void keyPressed(final KeyEvent e) {
        if (!pauseGame) {
            KeyCode key = KeyCode.getKeyCode(e.getKeyCode());
            if (key != null) {
                pacmanKeyList.add(key);
            }

            if (e.getKeyCode() == 32) {
                KeyCode faceDirection =  pacmanKeyList.isEmpty() ? RIGHT : pacmanKeyList.getFirst();

                fires.add(new Fire(Fire, pacman, faceDirection));
                SoundPlayer.playAsync(PacmanFire, null);
            }
        }
    }

    public void mouseClicked(final MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (!startGame && (300 < x && x < 522)) {
            startGame = true;

            if (530 < y && y < 603)
                level = 1;
            else if (610 < y && y < 690)
                level = 2;
            else if (700 < y && y < 775)
                level = 3;
            else
                startGame = false;

            enemies.forEach(en -> en.setSpeed(enemySpeed * level));
        }
    }

    private void drawTexture(GameEntity entity) {
        int textureId = entity.getTextureId();

        if(entity instanceof Enemy && ((Enemy) entity).isDead()) {
            textureId = Ghost.getIndex(3);
        }

        drawTexture(textureId, new double[]{entity.getXView(), entity.getYView()}, entity.getTexture());
    }

    private void drawTexture(Textures texture) {
        drawTexture(texture.getIndex(0), texture.getPosition(), texture);
    }

    private void drawTexture(int textureIdx, double[] position, Textures texture) {
        EnableDisable(gl, GL.GL_BLEND, () -> {
            gl.glBindTexture(GL_TEXTURE_2D, textures[textureIdx]);
            PushPopMatrix(gl, () -> {
                gl.glTranslated(position[0], position[1], 0);
                gl.glScaled(texture.getScale()[0], texture.getScale()[1], 1);

                if (textureIdx == Fruit.getIndex(0)) {
                    angle = (++angle/2) % 360;
                    gl.glRotated(angle, 0, 0, 1);
                }

                BeginEnd(gl);
            });
        });
    }

    public void writeText(double[] position, String text) {
        DisableEnable(gl, GL_TEXTURE_2D, () -> {
            gl.glMatrixMode(GL.GL_MODELVIEW);
            PushPopAttrib(gl, GL_CURRENT_BIT, () -> {
                gl.glColor4f(1, 0, 0, 0.5f);
                PushPopMatrix(gl, () -> {
                    gl.glRasterPos2d(position[0], position[1]);
                    new GLUT().glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, text);
                });
            });
        });
    }
}