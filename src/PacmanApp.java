import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import javax.media.opengl.GLCanvas;
import javax.swing.*;

public class PacmanApp extends JFrame {

    GLCanvas glcanvas = new GLCanvas();
    Animator animator = new FPSAnimator(70);
    PacmanGLEventListener listener = new PacmanGLEventListener();

    public static void main(String[] args) {
        new PacmanApp().animator.start();
    }

    public PacmanApp() {
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

