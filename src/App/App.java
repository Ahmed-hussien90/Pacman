package App;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.media.opengl.GLCanvas;
import javax.swing.*;

public class App extends JFrame {
    GLCanvas glcanvas = new GLCanvas();

    Animator animator = new FPSAnimator(100);

    PacmanApp listener = new PacmanApp();

    public static void main(String[] args) {
        loadAllDlls();

        new App().animator.start();
    }

    public static void loadAllDlls() {
        try {
            File dllFolder = new File("dlls");

            for (File dll : Objects.requireNonNull(dllFolder.listFiles())) {
                if (dll.getName().endsWith(".dll")) {
                    System.load(dll.getAbsolutePath());
                }
            }
        }catch (NullPointerException |  UnsatisfiedLinkError ex){
            System.out.println("Error loading DLL: " + ex.getMessage());
        }
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