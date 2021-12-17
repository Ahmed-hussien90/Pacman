/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.media.opengl.GLCanvas;
import javax.swing.*;

/**
 * This is a basic JOGL app. Feel free to reuse this code or modify it.
 */
public class PacmanApp extends JFrame {

    public static void main(String[] args) {
        final PacmanApp app = new PacmanApp();
//show what we've done
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        app.setVisible(true);
                    }
                }
        );
    }

    public PacmanApp() {
//set the JFrame title
        super("PacMan");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//only three JOGL lines of code ... and here they are
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(new PacmanGLEventListener());
//add the GLCanvas just like we would any Component
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(500, 300);
//center the JFrame on the screen
        setLocationRelativeTo(this);
    }
}

