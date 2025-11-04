package App;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import javax.media.opengl.GLCanvas;
import javax.swing.*;

public class App extends JFrame {
    GLCanvas glcanvas = new GLCanvas();

    Animator animator = new FPSAnimator(100);

    PacmanApp listener = new PacmanApp();

    public static void main(String[] args) {
        new App().animator.start();
    }

    public App() {
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        animator.add(glcanvas);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setTitle("PAC-MAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 850);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}