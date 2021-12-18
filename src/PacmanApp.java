/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import java.awt.*;
import javax.media.opengl.GLCanvas;
import javax.swing.*;

/**
 * This is a basic JOGL app. Feel free to reuse this code or modify it.
 */
public class PacmanApp extends JFrame {

    GLCanvas glcanvas = new GLCanvas();
    Animator animator = new FPSAnimator(40);
    PacmanGLEventListener listener = new PacmanGLEventListener();

    public static void main(String[] args) {
        new PacmanApp().animator.start();
    }

    public PacmanApp() {
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
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

