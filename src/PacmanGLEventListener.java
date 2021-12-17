 import javax.media.opengl.GL;
        import javax.media.opengl.GLAutoDrawable;
        import javax.media.opengl.GLDrawable;
        import javax.media.opengl.GLEventListener;

/**
 * For our purposes only two of the GLEventListeners matter. Those would be
 * init() and display().
 */
public class PacmanGLEventListener implements GLEventListener {

    public void reshape(
            GLDrawable drawable,
            int x,
            int y,
            int width,
            int height
    ) {
    }

    public void displayChanged(
            GLDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged
    ) {
    }

    @Override
    public void init(GLAutoDrawable glad) {
        GL gl = glad.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 500, 300);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, 500.0, 0.0, 300.0,-1.0,1.0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {


    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {
    }
}
